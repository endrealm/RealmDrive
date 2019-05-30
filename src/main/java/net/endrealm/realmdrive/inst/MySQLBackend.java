package net.endrealm.realmdrive.inst;

import com.mongodb.QueryBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.endrealm.realmdrive.exceptions.EmbeddingNotSupportedException;
import net.endrealm.realmdrive.exceptions.UndefinedNotSupportedException;
import net.endrealm.realmdrive.interfaces.DriveBackend;
import net.endrealm.realmdrive.interfaces.DriveElement;
import net.endrealm.realmdrive.interfaces.DriveObject;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;
import net.endrealm.realmdrive.utils.MySQLUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author johannesjumpertz
 *
 * Simple driver implementation to allow using MYSQL
 *
 */
public class MySQLBackend implements DriveBackend {

    private Connection connection;
    private String table;

    /**
     * Creates a connection to the backend
     *
     * @param hostURL  host url used when establishing the connection
     * @param username the username used when establishing the connection
     * @param password the password used when establishing the connection
     * @param database database used when connecting. (Some backends might have this in their url)
     * @param table    unused
     */
    @Override
    public void connect(String hostURL, String username, String password, String database, String table) {
        this.table = table;
        try {
            this.connection = DriverManager.getConnection(hostURL + "/" + database+"?autoReconnect=true", username,
                    password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes an object to the database.
     *
     * @param driveObject object to save
     */
    @Override
    public void write(DriveObject driveObject) {
        try {
            MySQLEntity mySQLEntity = new MySQLEntity(driveObject);
            PreparedStatement statement = connection.prepareStatement(
                    String.format("INSERT INTO %s (%s) VALUES (%s)",
                            "'"+mySQLEntity.getTableName()+"'",
                            mySQLEntity.getKeysAsSQL(),
                            mySQLEntity.getValuesAsSQL())
            );

            statement.execute();
        } catch (UndefinedNotSupportedException | EmbeddingNotSupportedException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads one object matching the query parameters
     *
     * @param queryDetails the query to use
     * @return object empty if none found
     */
    @Override
    public DriveObject findOne(Query queryDetails) {

        String query = createQuery(queryDetails);

        return null;
    }

    /**
     * Creates a new mysql query string form the query object
     *
     * @param query the query object to use
     * @return the mysql conform query
     */
    private String createQuery(Query query) {
        return query.toSQLQuery("SELECT", table);
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

    /**
     * Prepares to use a specific entity. This is required for some databases (e.g. MySQL) to work.
     * It will automatically try to generate and modify tables to fit the entities.
     *
     * @param clazz the class of the entity to save
     */
    @Override
    public void prepareEntity(Class<?> clazz) {
        //TODO: create tables
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class MySQLEntity {
        private String tableName;
        private HashMap<String, Object> values = new HashMap<>();

        public MySQLEntity(DriveObject driveObject) throws UndefinedNotSupportedException, EmbeddingNotSupportedException {
            try {
                String[] parts = driveObject.get("class").getAsString().split("\\.");
                tableName = MySQLUtils.getConventionName(parts[parts.length-1]);
            } catch (Exception ex) {
                throw new UndefinedNotSupportedException();
            }
            for(Map.Entry<String, DriveElement> entry : driveObject.getSubComponents().entrySet()) {
                if(entry.getValue().getPrimitiveValue() == null)
                    throw new EmbeddingNotSupportedException();
                values.put(entry.getKey(), entry.getValue().getPrimitiveValue());
            }
        }

        public String getKeysAsSQL() {
            return String.join(",", values.keySet());
        }

        public String getValuesAsSQL() {
            return String.join(",",
                    values.values().stream().map((e) -> {
                        if(e instanceof Number)
                            return e.toString();
                        return "'"+e.toString()+"'";
                    }).collect(Collectors.toList())
            );
        }

        public String toTable() {
            return "";//TODO: create table
        }
    }
}
