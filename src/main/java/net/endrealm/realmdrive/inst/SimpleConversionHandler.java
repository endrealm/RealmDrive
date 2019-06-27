package net.endrealm.realmdrive.inst;

import lombok.Data;
import net.endrealm.realmdrive.annotations.SaveVar;
import net.endrealm.realmdrive.exceptions.NotAPrimitiveTypeException;
import net.endrealm.realmdrive.exceptions.ObjectReadOnlyException;
import net.endrealm.realmdrive.factory.DriveObjectFactory;
import net.endrealm.realmdrive.interfaces.*;
import net.endrealm.realmdrive.utils.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @author johannesjumpertz
 *
 * Used to convert drive objects
 */
@Data
public class SimpleConversionHandler implements ConversionHandler {

    /**
     * A list of classes that can be used to parse objects
     */
    private ArrayList<Class<?>> classes;

    /**
     * Factory used to instantiate new objects
     */
    private DriveObjectFactory objectFactory;

    /**
     * The drive service the conversion handler is connected to
     */
    private DriveService driveService;
    /**
     * A list of classes considered as primitive
     */
    private final Set<Class<?>> PRIMITIVE_CLASSES;

    /**
     * Creates an empty Conversion Handler
     */
    public SimpleConversionHandler() {
        classes = new ArrayList<>();
        PRIMITIVE_CLASSES = ReflectionUtils.getPrimitiveWrapperTypes();
    }

    /**
     * Register classes used for conversion.
     * @param classes the classes to be added. Class should contain at least one field marked with {@link SaveVar}
     */
    @Override
    public void registerClasses(Class<?>... classes) {
        this.classes.addAll(Arrays.asList(classes));
        for(Class<?> clazz : classes)
            this.driveService.getBackend().prepareEntity(clazz);
    }

    /**
     * Transforms a stats object into a classes object. Target class must be registered.
     *
     * @param statisticsObject the object to convert
     * @param clazz target clazz to convert to
     * @param <T> type of target
     * @return returns instance of target type
     * @throws ClassCastException if object can not be transformed.
     */
    @Override
    public <T> T transform(DriveObject statisticsObject, Class<T> clazz) throws ClassCastException {

        if(statisticsObject == null || statisticsObject.isEmpty())
            return null;

        if(!classes.contains(clazz))
            throw new ClassCastException(String.format("Failed to cast! %s is not registered!", clazz.getName()));

        try {
            Constructor<T> constructor = clazz.getConstructor();
            T instance = constructor.newInstance();

            for(Field field : ReflectionUtils.getAllAnnotatedFields(clazz, SaveVar.class)) {
                boolean protection = field.isAccessible();
                field.setAccessible(true);

                DriveElement value = statisticsObject.get(field.getName());

                if(value == null)
                    throw new ClassCastException(String.format("Found unmapped field %s in %s!", field.getName(), field.getDeclaringClass().getName()));

                if(value instanceof DriveElementArray) {
                    DriveElementArray array = value.getAsElementArray();

                    if(array.getContents().isEmpty()) {
                        field.set(instance, new ArrayList<>());
                        continue;
                    }

                    ParameterizedType listType = (ParameterizedType) field.getGenericType();
                    Class<?> listGenericClass = (Class<?>) listType.getActualTypeArguments()[0];
                    List list = createList(array, listGenericClass);
                    field.set(instance, list);

                }
                else if(value.getPrimitiveValue() == null)
                    field.set(instance, transform(value.getAsObject(), field.getType()));
                else {

                    Object primitiveValue = value.getPrimitiveValue();

                    if((field.getType() == float.class || field.getType() == Float.class) && primitiveValue.getClass() == Double.class) {
                        field.set(instance, (float)((double) primitiveValue));
                    } else
                        field.set(instance, primitiveValue);
                }

                field.setAccessible(protection);
            }

            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClassCastException(String.format("Failed to cast! Maybe %s does not have an empty public constructor!", clazz.getName()));
        }
    }

    /**
     * Creates a new list with the given class
     *
     * @param array array to transform
     * @param clazz target clazz
     * @return the list
     * @throws ClassCastException thrown when two lists are in one another
     */
    @Override
    public List createList(DriveElementArray array, Class clazz) throws ClassCastException {
        List list = new ArrayList<>();
        if(PRIMITIVE_CLASSES.contains(clazz)) {
            for(DriveElement driveElement : array.getContents())
                list.add(driveElement.getPrimitiveValue());
        } else if(DriveElementArray.class.isAssignableFrom(clazz)) {
            throw new ClassCastException("List stacking is not supported");
        } else {
            for(DriveElement driveElement : array.getContents())
                list.add(transform(driveElement.getAsObject(), clazz));
        }

        return list;
    }


    /**
     * Transforms a basic object into a DriveObject. This does not work for primitives. Class must be registered.
     *
     * @param object object to transform
     * @return transformed object
     * @throws ClassCastException thrown when object can not be transformed
     */
    @Override
    public DriveObject transform(Object object) throws ClassCastException {

        if(object == null)
            return null;

        Class clazz = object.getClass();
        if(!classes.contains(clazz))
            throw new ClassCastException(String.format("Failed to cast! %s is not registered!", clazz.getName()));

        List<Field> fieldList = ReflectionUtils.getAllAnnotatedFields(clazz, SaveVar.class);

        if(fieldList.isEmpty())
            throw new ClassCastException(String.format("Class %s must contain at least one field annotated with SaveVar", clazz.getName()));

        DriveObject statisticsObject = objectFactory.createEmptyObject();

        try {

            statisticsObject.setPrimitive("className", object.getClass().getName());

            for(Field field : fieldList) {
                boolean protection = field.isAccessible();
                field.setAccessible(true);

                Object value = field.get(object);

                if(PRIMITIVE_CLASSES.contains(value.getClass()))
                    statisticsObject.setObject(field.getName(), objectFactory.createPrimitive(value));
                else if(List.class.isAssignableFrom(field.getType())) {
                    DriveElementArray array = objectFactory.createEmptyArray();
                    for(Object obj : (List)value) {
                        if(PRIMITIVE_CLASSES.contains(obj.getClass()))
                            array.addObject(objectFactory.createPrimitive(obj));
                        else
                            array.addObject(transform(obj));
                    }
                    statisticsObject.setObject(field.getName(), array);
                }
                else
                    statisticsObject.setObject(field.getName(), transform(value));

                field.setAccessible(protection);
            }
        } catch (IllegalAccessException | ObjectReadOnlyException | NotAPrimitiveTypeException e) {
            e.printStackTrace();
            throw new ClassCastException();
        }

        return statisticsObject;
    }

    /**
     * Turns DriveElement into a Json string
     *
     * @param element element to transform
     * @return json string
     */
    @Deprecated
    public String stringify(DriveElement element) {
        if(element == null)
            return "{}";

        if(element instanceof DriveElementArray) {
            String json = "[";

            for(DriveElement elem : element.getAsElementArray().getContents())
                json = json + stringify(elem) + ",";

            json = json.substring(0, json.length()-1); // remove , after last element
            json = json + "]";
            return json;
        }
        else if(!(element instanceof DriveObject)) {
            Object value = element.getPrimitiveValue();

            if(value instanceof String || value instanceof Character)
                return "\"" + value + "\"";

            return ""+value;
        }

        String json = "{";
        for(Map.Entry<String, DriveElement> entry : element.getSubComponents().entrySet()) {
            json = json + "\""+entry.getKey()+ "\":" + stringify(entry.getValue()) +",";
        }

        json = json.substring(0, json.length()-1); // remove , after last element
        json = json + "}";

        return json;
    }

    /**
     * Tries to automatically transform the statistics object into a registered one
     *
     * @param statisticsObject object to be transformed
     * @return null if element does not contain a className or registered class isn't found
     */
    @Override
    public Object transformAutomatically(DriveObject statisticsObject) {
        DriveElement element = statisticsObject.get("className");
        if(element == null)
            return null;

        for(Class clazz : classes)
            if(clazz.getName().equals(element.getPrimitiveValue()))
                return transform(statisticsObject, clazz);

        return null;
    }

    /**
     * Sets the service the reader is used by.
     *
     * @param service service the reader is used by
     */
    @Override
    public void setService(DriveService service) {
        this.objectFactory = new DriveObjectFactory(service);
        this.driveService = service;
    }
}
