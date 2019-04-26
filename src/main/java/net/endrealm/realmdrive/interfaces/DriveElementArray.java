package net.endrealm.realmdrive.interfaces;

import net.endrealm.realmdrive.exceptions.NotAPrimitiveTypeException;
import net.endrealm.realmdrive.exceptions.ObjectReadOnlyException;

import java.util.List;

/**
 * @author johannesjumpertz
 *
 * Defines a drive element array. Used to contain multiple drive Elements
 */
public interface DriveElementArray extends DriveElement {
    /**
     * Retrieves an item from the list
     * @param num used to identify an item
     * @return a drive element. Null if not found
     */
    DriveElement get(int num);

    /**
     * Retrieves an item from the list
     * @param num used to identify an item
     * @return a drive object. Null if not found
     */
    DriveObject getAsObject(int num);

    /**
     * Writes an element to the list
     * @param num position to insert at
     * @param element element to store
     * @throws ObjectReadOnlyException thrown if this object does not support writing
     */
    void addObject(int num, DriveElement element) throws ObjectReadOnlyException;

    /**
     * Writes an primitive element to the list
     * @param num position to insert at
     * @param object primitive type object
     * @throws NotAPrimitiveTypeException thrown if object parameter is not a primitive type
     * @throws ObjectReadOnlyException thrown if this object does not support writing
     */
    void addPrimitive(int num, Object object) throws ObjectReadOnlyException, NotAPrimitiveTypeException;

    /**
     * Returns this object converted to an object of the specified type
     * @param <T> type to convert object to
     * @return converted object
     * @throws ClassCastException thrown if this object can not be converted into the specified object.
     */
    <T> List<T> getAsTypedList(Class<T> clazz) throws ClassCastException;

    /**
     * Returns all contents as a mutable list
     *
     * @return contents
     */
    List<DriveElement> getContents();

    /**
     * Adds an element to the end of the array
     *
     * @param element element to be added
     */
    void addObject(DriveElement element);

    /**
     * Adds an primitive element to the end of the array
     *
     * @param object element to be added
     * @throws NotAPrimitiveTypeException thrown if object parameter is not a primitive type
     */
    void addPrimitive(Object object) throws NotAPrimitiveTypeException;
}
