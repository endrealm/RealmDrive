package net.endrealm.realmdrive.inst;

import lombok.Data;
import net.endrealm.realmdrive.annotations.InjectParent;
import net.endrealm.realmdrive.annotations.SaveVar;
import net.endrealm.realmdrive.exceptions.NotAPrimitiveTypeException;
import net.endrealm.realmdrive.exceptions.ObjectReadOnlyException;
import net.endrealm.realmdrive.factory.DriveObjectFactory;
import net.endrealm.realmdrive.interfaces.*;
import net.endrealm.realmdrive.utils.ReflectionUtils;
import net.endrealm.realmdrive.utils.properties.ClassProperties;
import net.endrealm.realmdrive.utils.properties.FieldProperties;
import net.endrealm.realmdrive.utils.properties.PropertyReader;

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
     * A list of endpoint serializers
     */
    private ArrayList<CustomSerializer> serializers;

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
        serializers = new ArrayList<>();
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

    @Override
    public void registerSerializers(CustomSerializer... serializers) {
        List<CustomSerializer> serializersList = Arrays.asList(serializers);
        Collections.reverse(serializersList);
        this.serializers.addAll(0, serializersList);
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
        return transform(statisticsObject, clazz, null);
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
    public <T> T transform(DriveObject statisticsObject, Class<T> clazz, Object parent) throws ClassCastException {

        if(statisticsObject == null || statisticsObject.isEmpty())
            return null;

        {
            Object object = getConvertedEndpoint(statisticsObject, clazz);
            if(object != null)
                //noinspection unchecked
                return (T) object;
        }

        // Map class
        {
            DriveElement classElement = statisticsObject.get("className");

            if(classElement != null) {
                try {
                    Class<?> storedClass = Class.forName(classElement.getAsString());
                    if(clazz.isAssignableFrom(storedClass)) {
                        //noinspection unchecked
                        clazz = (Class<T>) storedClass;
                    }
                } catch (ClassNotFoundException ignored) {}
            }

            if(!classes.contains(clazz))
                throw new ClassCastException(String.format("Failed to cast! %s is not registered!", clazz.getName()));
        }

        ClassProperties classProperties = PropertyReader.readProperties(clazz);

        try {

            Constructor<T> constructor = null;

            // If wants to inject parent find matching constructor
            if(clazz.getAnnotation(InjectParent.class) != null && parent != null) {
                Class<?> parentType = parent.getClass();

                while (constructor == null && parentType != null) {
                    try {
                        constructor = clazz.getConstructor(parentType);
                    } catch (NoSuchMethodException | SecurityException ignore) {}
                    finally {
                        parentType = parentType.getSuperclass();
                    }
                }
             }

            if(constructor == null)
                constructor = clazz.getConstructor();
            T instance = constructor.getParameterCount() == 0 ? constructor.newInstance() : constructor.newInstance(parent);

            for(Field field : ReflectionUtils.getAllFields(clazz)) {
                FieldProperties fieldProperties = PropertyReader.readProperties(field, classProperties);

                if(!fieldProperties.isRead())
                    continue;

                boolean protection = field.isAccessible();
                field.setAccessible(true);

                DriveElement value = statisticsObject.get(fieldProperties.getName());

                // Read from aliases
                {
                    int i = 0;
                    while(value == null) {
                        if(i >= fieldProperties.getAliases().size())
                            break;

                        value = statisticsObject.get(fieldProperties.getAliases().get(i));
                        i++;
                    }
                }

                // Value not found in object
                if(value == null) {

                    // if this field is only optional skip it
                    if(fieldProperties.isOptional())
                        continue;

                    throw new ClassCastException(String.format("Found unmapped field %s in %s!", field.getName(), field.getDeclaringClass().getName()));
                }

                {
                    Object object = getConvertedEndpoint(value, field.getType());
                    if(object != null) {
                        field.set(instance, object);
                        continue;
                    }

                }

                if(value instanceof DriveElementArray) {
                    DriveElementArray array = value.getAsElementArray();

                    if(array.getContents().isEmpty()) {
                        field.set(instance, new ArrayList<>());
                        continue;
                    }

                    ParameterizedType listType = (ParameterizedType) field.getGenericType();
                    Class<?> listGenericClass = (Class<?>) listType.getActualTypeArguments()[0];
                    List list = createList(array, listGenericClass, instance);
                    field.set(instance, list);

                }
                else if(value.getPrimitiveValue() == null)
                    field.set(instance, transform(value.getAsObject(), field.getType(), instance));
                else {

                    // Handle primitives
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

    private Object getConvertedEndpoint(DriveElement element, Class clazz) {
        for(CustomSerializer serializer : serializers) {
            if(serializer.supportsClass(clazz)) {
                //noinspection unchecked
                return serializer.fromEndpoint(element, clazz);
            }
        }
        return null;
    }

    private DriveElement getElementEndpoint(Object element, Class clazz) {
        for(CustomSerializer serializer : serializers) {
            if(serializer.supportsClass(clazz)) {
                //noinspection unchecked
                return serializer.toDriveEndpoint(element);
            }
        }
        return null;
    }

    @Override
    public List createList(DriveElementArray array, Class clazz, Object parent) throws ClassCastException {
        List list = new ArrayList<>();
        if(PRIMITIVE_CLASSES.contains(clazz)) {
            for(DriveElement driveElement : array.getContents())
                list.add(driveElement == null ? null : driveElement.getPrimitiveValue());
        } else if(DriveElementArray.class.isAssignableFrom(clazz)) {
            throw new ClassCastException("List stacking is not supported");
        } else {
            for(DriveElement driveElement : array.getContents())
                list.add(driveElement == null ? null : transform(driveElement.getAsObject(), clazz, parent));
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

        List<Field> fieldList = ReflectionUtils.getAllFields(clazz);

        ClassProperties classProperties = PropertyReader.readProperties(clazz);

        DriveObject statisticsObject = objectFactory.createEmptyObject();

        try {

            statisticsObject.setPrimitive("className", object.getClass().getName());

            for(Field field : fieldList) {
                FieldProperties fieldProperties = PropertyReader.readProperties(field, classProperties);

                if(!fieldProperties.isWrite())
                    continue;

                boolean protection = field.isAccessible();
                field.setAccessible(true);

                Object value = field.get(object);

                if(value == null) {

                    // if this field is only optional skip it
                    if(fieldProperties.isOptional())
                        continue;

                    throw new ClassCastException(String.format("Found empty non optional field %s in %s!", field.getName(), field.getDeclaringClass().getName()));
                }

                DriveElement driveElement = getElementEndpoint(value, value.getClass());
                if(driveElement != null) {
                    statisticsObject.setObject(fieldProperties.getName(), driveElement);
                }
                else if(PRIMITIVE_CLASSES.contains(value.getClass()))
                    statisticsObject.setObject(fieldProperties.getName(), objectFactory.createPrimitive(value));
                else if(List.class.isAssignableFrom(field.getType())) {
                    DriveElementArray array = objectFactory.createEmptyArray();
                    for(Object obj : (List)value) {
                        if(PRIMITIVE_CLASSES.contains(obj.getClass()))
                            array.addObject(objectFactory.createPrimitive(obj));
                        else
                            array.addObject(transform(obj));
                    }
                    statisticsObject.setObject(fieldProperties.getName(), array);
                }
                else {
                    statisticsObject.setObject(fieldProperties.getName(), transform(value));
                }

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
