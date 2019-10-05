package net.endrealm.realmdrive.utils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author johannesjumpertz
 *
 * Various utility methods regarding reflection
 */
public class ReflectionUtils {
    /**
     * Returns all Fields public, protected, private and those that are inherited
     *
     * @param fields list to add fields to
     * @param type class to scan
     * @return the fields
     */
    private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    /**
     * Retrieves all fields of a class.
     * Method searches super classes as well. Fields can be private, protected, public or package private.
     * @param type class to scan
     * @return list of fields
     */
    public static List<Field> getAllFields(Class<?> type) {
        return getAllFields(new ArrayList<>(), type);
    }

    /**
     * Finds all fields with at least one of the specified annotations.
     * Method searches super classes as well. Fields can be private, protected, public or package private.
     *
     * @param type clazz to scan
     * @param annotations list of annotations
     * @return all fields with one or more of the specified annotations
     */
    public static List<Field> getAllAnnotatedFields(Class<?> type, Class... annotations) {
        List<Field> rawFields = new ArrayList<>();
        List<Field> annotatedFields = new ArrayList<>();

        getAllFields(rawFields, type);

        for(Field field : rawFields)
            if(hasAnnotation(field, annotations))
                annotatedFields.add(field);

        return annotatedFields;
    }

    /**
     * Checks if a field has at least one of the specified annotations
     *
     * @param field field to check
     * @param annotations list of annotations
     * @return does field have one or more of the specified annotations
     */
    private static boolean hasAnnotation(Field field, Class[] annotations) {
        for(Class clazz : annotations)
            if(field.getAnnotation(clazz) != null)
                return true;
        return false;
    }

    /**
     * @return a set of classes that are marked as drive primitives.
     */
    public static Set<Class<?>> getPrimitiveWrapperTypes()
    {
        Set<Class<?>> prims = new HashSet<>();

        prims.add(Character.class);
        prims.add(String.class);
        prims.add(Boolean.class);
        prims.add(Byte.class);
        prims.add(Short.class);
        prims.add(Integer.class);
        prims.add(Long.class);
        prims.add(Float.class);
        prims.add(Double.class);

        return prims;
    }
}
