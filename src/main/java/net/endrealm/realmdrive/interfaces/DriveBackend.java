package net.endrealm.realmdrive.interfaces;

import net.endrealm.realmdrive.query.Query;

/**
 * @author johannesjumpertz
 *
 * Defines a drive backend
 */
public interface DriveBackend {

    /**
     * Creates a connection to the backend
     *
     * @param hostURL  host url used when establishing the connection
     * @param username the username used when establishing the connection
     * @param password the password used when establishing the connection
     * @param database database used when connecting. (Some backends might have this in their url)
     * @param table default table used. Depending on the type a default table might be needed
     */
    void connect(String hostURL, String username, String password, String database, String table);

    /**
     * Writes an object to the database.
     *
     * @param driveObject object to save
     */
    void write(DriveObject driveObject);

    /**
     * Reads one object matching the query parameters
     *
     * @param queryDetails the query to use
     * @return object empty if none found
     */
    DriveObject findOne(Query queryDetails);

    /**
     * Read all objects matching the query parameters
     *
     * @param queryDetails the query to use
     * @return empty list if not found
     */
    Iterable<DriveObject> findAll(Query queryDetails);

    /**
     * Sets the service the backend is used by.
     *
     * @param service service the backend is used by
     */
    void setService(DriveService service);

    /**
     * Removes one element equal to the query details and adds the element in place
     *
     * @param element element to insert
     * @param queryDetails used to filter for deletion
     */
    void writeReplace(DriveObject element, Query queryDetails);

    /**
     * Deletes all entries matched by the query
     *
     * @param queryDetails the query details used to filter
     */
    void deleteAll(Query queryDetails);

    /**
     * Deletes an object from the backend if found
     * @param queryDetails the query details used to filter
     */
    void delete(Query queryDetails);

    /**
     * Executes a raw query
     * @param query backend dependant. For MongoDB see {@link com.mongodb.QueryBuilder}
     * @return query response
     */
    Iterable rawQuery(Object query);
}
