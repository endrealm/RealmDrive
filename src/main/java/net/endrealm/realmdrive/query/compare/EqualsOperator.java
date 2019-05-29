package net.endrealm.realmdrive.query.compare;

import net.endrealm.realmdrive.query.QueryComponent;
import net.endrealm.realmdrive.utils.JsonUtils;
import net.endrealm.realmdrive.utils.MySQLUtils;

/**
 * @author johannesjumpertz
 *
 * Equals call in query
 */
public class EqualsOperator<T extends QueryComponent> extends CompareOperator<T> {

    private Object value;
    private String field;

    public EqualsOperator(T parent) {
        super(parent);
        value = "none";
        field = "";
    }

    public EqualsOperator<T> setValue(Object object) {
        value = object;
        return this;
    }

    public EqualsOperator<T> setField(String field) {
        this.field = field;
        return this;
    }

    /**
     * @return a json representation according to mongo db
     */
    @Override
    public String toJson() {
        return String.format("{\"%s\": { $eq: %s }}",field, JsonUtils.parsePrimitive(value));
    }

    /**
     * @return a sql representation according to the jdbc syntax
     */
    @Override
    public String toSQL() {
        return String.format("%s = %s",
                field,
                MySQLUtils.getSQLRepr(value)
                );
    }
}
