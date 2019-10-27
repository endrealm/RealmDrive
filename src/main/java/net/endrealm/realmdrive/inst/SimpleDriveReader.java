package net.endrealm.realmdrive.inst;

import net.endrealm.realmdrive.interfaces.DriveObject;
import net.endrealm.realmdrive.interfaces.DriveReader;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import net.endrealm.realmdrive.utils.ThreadUtils;
import net.endrealm.realmdrive.utils.paging.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author johannesjumpertz
 *
 * Simple implementation of a drive reader.
 * Just does rudimentary stuff. Actually not more than grabbing the data from the backend and handing it over.
 */
public class SimpleDriveReader implements DriveReader {

    /**
     * drive service using this reader
     */
    private DriveService driveService;

    /**
     * Does the backend contain one or more objects that can be identified through the query details
     *
     * @param query query details used to search for the objects
     * @return one or more instances found
     */
    @Override
    public boolean containsObject(Query query) {
        return readObject(query) != null;
    }

    /**
     * Does the backend contain one or more objects that can be identified through the query details
     *
     * @param query    query details used to search for the objects
     * @param onResult invoked upon result
     */
    @Override
    public void containsObjectAsync(Query query, Consumer<Boolean> onResult) {
        ThreadUtils.createNewThread(
                () -> {
                    onResult.accept(containsObject(query));
                }
        );
    }

    /**
     * Read the first object instance found by the specified parameters
     *
     * @param query query details used to search for the objects
     * @return first object that was found. Returns null if none is found
     */
    @Override
    public DriveObject readObject(Query query) {
        return driveService.getBackend().findOne(query);
    }

    /**
     * Read the first object instance found by the specified parameters
     *
     * @param query     query details used to search for the objects
     * @param onSuccess this is invoked if a result is received
     * @param onFailure this is invoked if no result is found
     */
    @Override
    public void readObjectAsync(Query query, Consumer<DriveObject> onSuccess, Runnable onFailure) {
        ThreadUtils.createNewThread(
                () -> {
                    DriveObject result = readObject(query);
                    if(result != null)
                        onSuccess.accept(result);
                    else
                        onFailure.run();
                }
        );
    }

    /**
     * Read the first object instance found by the specified parameters
     *
     * @param query query details used to search for the objects
     * @param clazz  clazz to convert the result into
     * @return first object that was found. Returns null if none is found
     * @throws ClassCastException thrown if found object can not be converted into T
     */
    @Override
    public <T> T readObject(Query query, Class<T> clazz) throws ClassCastException {
        return driveService.getConversionHandler().transform(readObject(query), clazz);
    }

    /**
     * Read the first object instance found by the specified parameters
     *
     * @param query     query details used to search for the objects
     * @param clazz     clazz to convert the result into
     * @param onSuccess invoked upon found result
     * @param onFailure invoked when no result is queried
     * @param onError   called if an error occurs
     */
    @Override
    public <T> void readObjectAsync(Query query, Class<T> clazz, Consumer<T> onSuccess, Runnable onFailure, Consumer<Throwable> onError) {
        ThreadUtils.createNewThread(
                () -> {
                    try {
                        T result = readObject(query, clazz);

                        if(result != null)
                            onSuccess.accept(result);
                        else
                            onFailure.run();

                    } catch (Exception ex) {
                        onError.accept(ex);
                    }
                }
        );
    }

    /**
     * Read all object instances found by the specified parameters
     *
     * @param query query details used to search for the objects
     * @return objects that were found
     */
    @Override
    public List<DriveObject> readAllObjects(Query query) {
        return driveService.getBackend().findAll(query);
    }

    /**
     * Read all object instances found by the specified parameters
     *
     * @param query    query details used to search for the objects
     * @param onResult invoked upon result
     */
    @Override
    public void readAllObjectsAsync(Query query, Consumer<List<DriveObject>> onResult) {
        ThreadUtils.createNewThread(
                () -> {
                    List<DriveObject> result = readAllObjects(query);
                    onResult.accept(result);
                }
        );
    }

    /**
     * Read all object instances found by the specified parameters
     *
     * @param query quarry details used to search for the objects
     * @param clazz  clazz to convert the results into
     * @return objects that were found
     * @throws ClassCastException thrown if found objects can not be converted into T
     */
    @Override
    public <T> List<T> readAllObjects(Query query, Class<T> clazz) throws ClassCastException {
        List<T> list = new ArrayList<>();
        for (DriveObject obj: readAllObjects(query)) {
            list.add(driveService.getConversionHandler().transform(obj, clazz))
;        }
        return list;
    }

    /**
     * Read all object instances found by the specified parameters
     *
     * @param query     query details used to search for the objects
     * @param clazz     clazz to convert the results into
     * @param onSuccess invoked upon result
     * @param onError   called when an error occurs
     */
    @Override
    public <T> void readAllObjects(Query query, Class<T> clazz, Consumer<List<T>> onSuccess, Consumer<Throwable> onError) {
        ThreadUtils.createNewThread(
                () -> {
                    try {
                        List<T> result = readAllObjects(query, clazz);
                        onSuccess.accept(result);

                    } catch (Exception ex) {
                        onError.accept(ex);
                    }
                }
        );
    }

    @Override
    public List<DriveObject> readPagedObjects(Query query, Pageable pageable) {
        return driveService.getBackend().findAll(query, pageable);
    }

    @Override
    public void readPagedObjectsAsync(Query query, Pageable pageable, Consumer<List<DriveObject>> onResult) {
        ThreadUtils.createNewThread(
                () -> {
                    List<DriveObject> result = readPagedObjects(query, pageable);
                    onResult.accept(result);
                }
        );
    }

    @Override
    public <T> List<T> readPagedObjects(Query query, Pageable pageable, Class<T> clazz) throws ClassCastException {
        List<T> list = new ArrayList<>();
        for (DriveObject obj: readPagedObjects(query, pageable)) {
            list.add(driveService.getConversionHandler().transform(obj, clazz));
        }
        return list;
    }

    @Override
    public <T> void readPagedObjectsAsync(Query query, Pageable pageable, Class<T> clazz, Consumer<List<T>> onSuccess, Consumer<Throwable> onError) {
        ThreadUtils.createNewThread(
                () -> {
                    try {
                        List<T> result = readPagedObjects(query, pageable, clazz);
                        onSuccess.accept(result);

                    } catch (Exception ex) {
                        onError.accept(ex);
                    }
                }
        );
    }

    /**
     * Sets the service the reader is used by.
     *
     * @param service service the reader is used by
     */
    @Override
    public void setService(DriveService service) {
        this.driveService = service;
    }
}
