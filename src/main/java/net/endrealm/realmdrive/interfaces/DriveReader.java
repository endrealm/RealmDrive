package net.endrealm.realmdrive.interfaces;

import net.endrealm.realmdrive.query.Query;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author johannesjumpertz
 *
 * This defines a drive reader object
 */
public interface DriveReader {

    /**
     * Does the backend contain one or more objects that can be identified through the query details
     *
     * @param query query details used to search for the objects
     * @return one or more instances found
     */
    boolean containsObject(Query query);

    /**
     * Does the backend contain one or more objects that can be identified through the query details
     *
     * @param query query details used to search for the objects
     * @param onResult invoked upon result
     */
    void containsObjectAsync(Query query, Consumer<Boolean> onResult);

    /**
     * Read the first object instance found by the specified parameters
     * @param query query details used to search for the objects
     * @return first object that was found. Returns null if none is found
     */
    DriveObject readObject(Query query);

    /**
     * Read the first object instance found by the specified parameters
     * @param query query details used to search for the objects
     * @param onSuccess this is invoked if a result is received
     * @param onFailure this is invoked if no result is found
     */
    void readObjectAsync(Query query, Consumer<DriveObject> onSuccess, Runnable onFailure);

    /**
     * Read the first object instance found by the specified parameters
     *
     * @param query query details used to search for the objects
     * @param clazz clazz to convert the result into
     * @param <T> will try to convert the object into the type
     * @return first object that was found. Returns null if none is found
     * @throws ClassCastException thrown if found object can not be converted into T
     */
    <T> T readObject(Query query, Class<T> clazz) throws ClassCastException;

    /**
     * Read the first object instance found by the specified parameters
     *
     * @param query query details used to search for the objects
     * @param clazz clazz to convert the result into
     * @param onSuccess invoked upon found result
     * @param onFailure invoked when no result is queried
     * @param onError called if an error occurs
     * @param <T> will try to convert the object into the type
     */
    <T> void readObjectAsync(Query query, Class<T> clazz, Consumer<T> onSuccess, Runnable onFailure, Consumer<Throwable> onError);

    /**
     * Read all object instances found by the specified parameters
     *
     * @param query query details used to search for the objects
     * @return objects that were found
     */
    List<DriveObject> readAllObjects(Query query);


    /**
     * Read all object instances found by the specified parameters
     *
     * @param query query details used to search for the objects
     * @param onResult invoked upon result
     */
    void readAllObjectsAsync(Query query, Consumer<List<DriveObject>> onResult);

    /**
     * Read all object instances found by the specified parameters
     * @param query query details used to search for the objects
     * @param clazz clazz to convert the results into
     * @param <T> will try to convert the objects into the type
     * @return objects that were found
     * @throws ClassCastException thrown if found objects can not be converted into T
     */
    <T> List<T> readAllObjects(Query query, Class<T> clazz) throws ClassCastException;


    /**
     * Read all object instances found by the specified parameters
     * @param query query details used to search for the objects
     * @param clazz clazz to convert the results into
     * @param <T> will try to convert the objects into the type
     * @param onSuccess invoked upon result
     * @param onError called when an error occurs
     */
    <T> void readAllObjects(Query query, Class<T> clazz, Consumer<List<T>> onSuccess, Consumer<Throwable> onError);

    /**
     * Sets the service the reader is used by.
     * @param service service the reader is used by
     */
    void setService(DriveService service);
}
