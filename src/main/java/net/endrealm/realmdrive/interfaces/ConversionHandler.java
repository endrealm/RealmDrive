package net.endrealm.realmdrive.interfaces;

import net.endrealm.realmdrive.annotations.SaveVar;

import java.util.List;

/**
 * @author johannesjumpertz
 *
 * Defines a realm drive conversion handler. Class is used to translate Java objects into Driver Objects
 */
public interface ConversionHandler {

    /**
     * Register classes used for conversion.
     * @param classes the classes to be added. Class should contain at least one field marked with {@link SaveVar}
     */
    void registerClasses(Class<?>... classes);

    /**
     * Register serializers used to convert complex endpoints to database values.
     * Priority follows LiFo, last elements will be prioritised
     *
     * @param serializers the serializers to add
     */
    void registerSerializers(CustomSerializer... serializers);

    /**
     * Transforms a stats object into a classes object. Target class must be registered.
     *
     * @param statisticsObject the object to convert
     * @param clazz target clazz to convert to
     * @param <T> type of target
     * @return returns instance of target type
     * @throws ClassCastException if object can not be transformed.
     */
    <T> T transform(DriveObject statisticsObject, Class<T> clazz);

    /**
     * Creates a new list with the given class
     *
     * @param array array to transform
     * @param clazz target clazz
     * @return the list
     * @throws ClassCastException thrown when two lists are in one another
     */
    List createList(DriveElementArray array, Class clazz);

    /**
     * Transforms a basic object into a DriveObject. This does not work for primitives. Class must be registered.
     *
     * @param object object to transform
     * @return transformed object
     * @throws ClassCastException thrown when object can not be transformed
     */
    DriveObject transform(Object object);

    /**
     * Tries to automatically transform the statistics object into a registered one
     *
     * @param statisticsObject object to be transformed
     * @return null if element does not contain a className or registered class isn't found
     */
    Object transformAutomatically(DriveObject statisticsObject);

    /**
     * Sets the service the reader is used by.
     * @param service service the reader is used by
     */
    void setService(DriveService service);
}
