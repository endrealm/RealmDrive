package net.endrealm.realmdrive.query.compare;

import net.endrealm.realmdrive.query.QueryComponent;
import net.endrealm.realmdrive.utils.JsonUtils;

/**
 * @author johannesjumpertz
 *
 * Greater than call in query
 */
public class GreaterThanOperator<T extends QueryComponent> extends CompareOperator<T> {

    private Object value;
    private String field;

    public GreaterThanOperator(T parent) {
        super(parent);
        value = "none";
        field = "";
    }

    public GreaterThanOperator<T> setValue(Object object) {
        value = object;
        return this;
    }

    public GreaterThanOperator<T> setField(String field) {
        this.field = field;
        return this;
    }

    /**
     * @return a json representation according to mongo db
     */
    @Override
    public String toJson() {
        return String.format("{\"%s\": { $gt: %s }}",field, JsonUtils.parsePrimitive(value));
    }
}
