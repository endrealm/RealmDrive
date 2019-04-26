package net.endrealm.realmdrive.interfaces;

import net.endrealm.realmdrive.query.Query;

/**
 * @author johannesjumpertz
 *
 * This defines a drive reader object
 */
public interface DriveReader {

    /**
     * Does the backend contain one or more objects that can be identified through the query details
     * @param query query details used to search for the objects
     * @return one or more instances found
     */
    boolean containsObject(Query query);

    /**
     * Read the first object instance found by the specified parameters
     * @param query query details used to search for the objects
     * @return first object that was found. Returns null if none is found
     */
    DriveObject readObject(Query query);

    /**
     * Read the first object instance found by the specified parameters
     * @param query query details used to search for the objects
     * @param clazz clazz to convert the result into
     * @param <T> will try to convert the object into the type
     * @return first object that was found. Returns null if none is found
     * @throws ClassCastException thrown if found object can not be converted into T
     */
    <T> T readObject(Query query, Class<T> clazz) throws ClassCastException;

    /**
     * Read all object instances found by the specified parameters
     * @param query query details used to search for the objects
     * @return objects that were found
     */
    Iterable<DriveObject> readAllObjects(Query query);

    /**
     * Read all object instances found by the specified parameters
     * @param query query details used to search for the objects
     * @param clazz clazz to convert the results into
     * @param <T> will try to convert the objects into the type
     * @return objects that were found
     * @throws ClassCastException thrown if found objects can not be converted into T
     */
    <T> Iterable<T> readAllObjects(Query query, Class<T> clazz) throws ClassCastException;

    /**
     * Sets the service the reader is used by.
     * @param service service the reader is used by
     */
    void setService(DriveService service);
}
