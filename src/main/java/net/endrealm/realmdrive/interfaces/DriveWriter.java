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
     * @param object element to be inserted, will be transformed automatically
     */
    void write(Object object);

    /**
     * Write an entry to the backend.
     *
     * @param element element to be inserted
     * @param overwrite should existing parameters be overwritten? If not a new value will be inserted.
     * @param queryDetails used two find duplicates
     */
    void write(DriveObject element, boolean overwrite, Query queryDetails);

    /**
     * Write an entry to the backend.
     *
     * @param object element to be inserted, will be transformed automatically
     * @param overwrite should existing parameters be overwritten? If not a new value will be inserted.
     * @param queryDetails used two find duplicates
     */
    void write(Object object, boolean overwrite, Query queryDetails);

    /**
     * Deletes entries matching the query from the backend
     * @param queryDetails used to find targets to delete
     * @param amount amount of entries to be deleted
     */
    void delete(Query queryDetails, int amount);

    /**
     * Sets the service the writer is used by.
     * @param service service the writer is used by
     */
    void setService(DriveService service);
}
