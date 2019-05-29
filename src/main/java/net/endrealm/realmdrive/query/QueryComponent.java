package net.endrealm.realmdrive.query;

/**
 * @author johannesjumpertz
 *
 * Defines a component of a query
 */
public interface QueryComponent {

    /**
     * @return a json representation according to mongo db
     */
    String toJson();

    /**
     * @return a sql representation according to the jdbc syntax
     */
    String toSQL();
}
