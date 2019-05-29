package net.endrealm.realmdrive.query.compare;

import net.endrealm.realmdrive.query.QueryComponent;
import net.endrealm.realmdrive.utils.JsonUtils;
import net.endrealm.realmdrive.utils.MySQLUtils;

/**
 * @author johannesjumpertz
 *
 * Not call in query
 */
public class NotEqualOperator<T extends QueryComponent> extends CompareOperator<T> {

    private Object value;
    private String field;

    public NotEqualOperator(T parent) {
        super(parent);
        value = "none";
        field = "";
    }

    public NotEqualOperator<T> setValue(Object object) {
        value = object;
        return this;
    }

    public NotEqualOperator<T> setField(String field) {
        this.field = field;
        return this;
    }

    /**
     * @return a json representation according to mongo db
     */
    @Override
    public String toJson() {
        return String.format("{\"%s\": { $ne: %s }}", field, JsonUtils.parsePrimitive(value));
    }

    /**
     * @return a sql representation according to the jdbc syntax
     */
    @Override
    public String toSQL() {
        return String.format("%s != %s",
                field,
                MySQLUtils.getSQLRepr(value)
        );
    }
}
