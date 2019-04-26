package net.endrealm.realmdrive.utils;

import lombok.Builder;
import lombok.Data;

/**
 * @author johannesjumpertz
 *
 * Used to create a drive service
 */
@Builder
@Data
public class DriveSettings {

    /**
     * The url used to connect to the database
     */
    private String hostURL;

    /**
     * The database name. Sometimes already contained in the url
     */
    private String database;

    /**
     * Default table/collection name. Used if it cant be assigned automatically.
     */
    private String table;

    /**
     * The username to use when establishing a connection
     */
    private String username;

    /**
     * The password to use when establishing a connection
     */
    private String password;

    /**
     * The type of the backend
     *
     * @see BackendType
     */
    private BackendType type;

    /**
     * A small enum for constant types
     */
    public enum BackendType {
        MONGO_DB
    }
}
