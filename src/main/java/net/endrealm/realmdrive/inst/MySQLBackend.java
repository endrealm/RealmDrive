package net.endrealm.realmdrive.inst;

import com.mongodb.QueryBuilder;
import net.endrealm.realmdrive.interfaces.DriveBackend;
import net.endrealm.realmdrive.interfaces.DriveObject;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;

import java.util.List;

public class MySQLBackend implements DriveBackend {
    /**
     * Creates a connection to the backend
     *
     * @param hostURL  host url used when establishing the connection
     * @param username the username used when establishing the connection
     * @param password the password used when establishing the connection
     * @param database database used when connecting. (Some backends might have this in their url)
     * @param table    default table used. Depending on the type a default table might be needed
     */
    @Override
    public void connect(String hostURL, String username, String password, String database, String table) {

    }

    /**
     * Writes an object to the database.
     *
     * @param driveObject object to save
     */
    @Override
    public void write(DriveObject driveObject) {

    }

    /**
     * Reads one object matching the query parameters
     *
     * @param queryDetails the query to use
     * @return object empty if none found
     */
    @Override
    public DriveObject findOne(Query queryDetails) {
        return null;
    }

    /**
     * Read all objects matching the query parameters
     *
     * @param queryDetails the query to use
     * @return empty list if not found
     */
    @Override
    public List<DriveObject> findAll(Query queryDetails) {
        return null;
    }

    /**
     * Sets the service the backend is used by.
     *
     * @param service service the backend is used by
     */
    @Override
    public void setService(DriveService service) {

    }

    /**
     * Removes one element equal to the query details and adds the element in place
     *
     * @param element      element to insert
     * @param queryDetails used to filter for deletion
     */
    @Override
    public void writeReplace(DriveObject element, Query queryDetails) {

    }

    /**
     * Deletes all entries matched by the query
     *
     * @param queryDetails the query details used to filter
     */
    @Override
    public void deleteAll(Query queryDetails) {

    }

    /**
     * Deletes an object from the backend if found
     *
     * @param queryDetails the query details used to filter
     */
    @Override
    public void delete(Query queryDetails) {

    }

    /**
     * Executes a raw query
     *
     * @param query backend dependant. For MongoDB see {@link QueryBuilder}
     * @return query response
     */
    @Override
    public Iterable rawQuery(Object query) {
        return null;
    }
}
