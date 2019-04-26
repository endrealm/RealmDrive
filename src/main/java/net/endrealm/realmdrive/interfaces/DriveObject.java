package net.endrealm.realmdrive.interfaces;

import net.endrealm.realmdrive.exceptions.NotAPrimitiveTypeException;
import net.endrealm.realmdrive.exceptions.ObjectReadOnlyException;

/**
 * @author johannesjumpertz
 *
 * Defines a drive object. Used to contain multiple drive Elements
 */
public interface DriveObject extends DriveElement {
    /**
     * Retrieves an item from the list
     * @param key used to identify an item
     * @return a drive element. Null if not found
     */
    DriveElement get(String key);

    /**
     * Retrieves an item from the list
     * @param key used to identify an item
     * @return a drive object. Null if not found
     */
    DriveObject getAsObject(String key);

    /**
     * Writes an element to the list
     * @param key key to affiliate the element with
     * @param element element to store
     * @throws ObjectReadOnlyException thrown if this object does not support writing
     */
    void setObject(String key, DriveElement element) throws ObjectReadOnlyException;

    /**
     * Writes an primitive element to the list
     * @param key key to affiliate the element with
     * @param object primitive type object
     * @throws NotAPrimitiveTypeException thrown if object parameter is not a primitive type
     * @throws ObjectReadOnlyException thrown if this object does not support writing
     */
    void setPrimitive(String key, Object object) throws ObjectReadOnlyException, NotAPrimitiveTypeException;

    /**
     * Returns this object converted to an object of the specified type
     * @param <T> type to convert object to
     * @return converted object
     * @throws ClassCastException thrown if this object can not be converted into the specified object.
     */
    <T> T getAsTypedObject(Class<T> clazz) throws ClassCastException;
}
