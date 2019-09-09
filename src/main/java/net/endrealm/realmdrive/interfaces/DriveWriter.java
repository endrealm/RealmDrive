package net.endrealm.realmdrive.interfaces;

import net.endrealm.realmdrive.query.Query;

/**
 * @author johannesjumpertz
 *
 * This defines a drive writer object
 */
public interface DriveWriter {

    /**
     * Write an entry to the backend. When already existent two entries will exist.
     *
     * @param element element to be inserted
     */
    void write(DriveObject element);

    /**
     * Write an entry to the backend. When already existent two entries will exist.
     *
     * @param element element to be inserted
     * @param onFinish invoked upon finish
     */
    void writeAsync(DriveObject element, Runnable onFinish);

    /**
     * Write an entry to the backend. When already existent two entries will exist.
     *
     * @param object element to be inserted, will be transformed automatically
     */
    void write(Object object);

    /**
     * Write an entry to the backend. When already existent two entries will exist.
     *
     * @param object element to be inserted, will be transformed automatically
     * @param onFinish invoked upon finish
     */
    void writeAsync(Object object, Runnable onFinish);

    /**
     * Write an entry to the backend. When already existent two entries will exist.
     *
     * @param element element to be inserted
     * @param query used to define the targeted table
     */
    void write(DriveObject element, Query query);

    /**
     * Write an entry to the backend. When already existent two entries will exist.
     *
     * @param element element to be inserted
     * @param query used to define the targeted table
     * @param onFinish invoked upon finish
     */
    void writeAsync(DriveObject element, Query query, Runnable onFinish);

    /**
     * Write an entry to the backend. When already existent two entries will exist.
     *
     * @param object element to be inserted, will be transformed automatically
     * @param query used to define the targeted table
     */
    void write(Object object, Query query);

    /**
     * Write an entry to the backend. When already existent two entries will exist.
     *
     * @param object element to be inserted, will be transformed automatically
     * @param query used to define the targeted table
     * @param onFinish invoked upon finish
     */
    void writeAsync(Object object, Query query, Runnable onFinish);

    /**
     * Write an entry to the backend.
     *
     * @param element element to be inserted
     * @param overwrite should existing parameters be overwritten? If not a new value will be inserted.
     * @param queryDetails used two find duplicates
     */
    @Deprecated
    void write(DriveObject element, boolean overwrite, Query queryDetails);

    /**
     * Write an entry to the backend.
     *
     * @param element element to be inserted
     * @param overwrite should existing parameters be overwritten? If not a new value will be inserted.
     * @param queryDetails used two find duplicates
     * @param onFinish invoked upon finish
     */
    @Deprecated
    void writeAsync(DriveObject element, boolean overwrite, Query queryDetails, Runnable onFinish);


    /**
     * Write an entry to the backend.
     *
     * @param object element to be inserted, will be transformed automatically
     * @param overwrite should existing parameters be overwritten? If not a new value will be inserted.
     * @param queryDetails used two find duplicates
     */
    @Deprecated
    void write(Object object, boolean overwrite, Query queryDetails);

    /**
     * Write an entry to the backend.
     *
     * @param object element to be inserted, will be transformed automatically
     * @param overwrite should existing parameters be overwritten? If not a new value will be inserted.
     * @param queryDetails used two find duplicates
     * @param onFinish invoked upon finish
     */
    @Deprecated
    void writeAsync(Object object, boolean overwrite, Query queryDetails, Runnable onFinish);


    /**
     * Write an entry to the backend.
     *
     * @param element element to be inserted
     * @param queryDetails used two find duplicates
     */
    @Deprecated
    void replace(DriveObject element, Query queryDetails);

    /**
     * Write an entry to the backend.
     *
     * @param element element to be inserted
     * @param queryDetails used two find duplicates
     * @param onFinish invoked upon finish
     */
    @Deprecated
    void replaceAsync(DriveObject element, Query queryDetails, Runnable onFinish);


    /**
     * Write an entry to the backend.
     *
     * @param object element to be inserted, will be transformed automatically
     * @param queryDetails used two find duplicates
     */
    @Deprecated
    void replace(Object object, Query queryDetails);

    /**
     * Write an entry to the backend.
     *
     * @param object element to be inserted, will be transformed automatically
     * @param queryDetails used two find duplicates
     * @param onFinish invoked upon finish
     */
    @Deprecated
    void replaceAsync(Object object, Query queryDetails, Runnable onFinish);

    /**
     * Deletes entries matching the query from the backend
     *
     * @param queryDetails used to find targets to delete
     * @param amount amount of entries to be deleted
     */
    void delete(Query queryDetails, int amount);

    /**
     * Deletes entries matching the query from the backend
     *
     * @param queryDetails used to find targets to delete
     * @param amount amount of entries to be deleted
     * @param onFinish invoked upon finis
     */
    void deleteAsync(Query queryDetails, int amount,Runnable onFinish);

    /**
     * Sets the service the writer is used by.
     * @param service service the writer is used by
     */
    void setService(DriveService service);
}
