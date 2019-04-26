package net.endrealm.realmdrive.query.compare;

import net.endrealm.realmdrive.query.QueryComponent;
import net.endrealm.realmdrive.utils.JsonUtils;

/**
 * @author johannesjumpertz
 *
 * Less than call in query
 */
public class LessThanOperator<T extends QueryComponent> extends CompareOperator<T> {

    private Object value;
    private String field;

    public LessThanOperator(T parent) {
        super(parent);
        value = "none";
        field = "";
    }

    public LessThanOperator<T> setValue(Object object) {
        value = object;
        return this;
    }

    public LessThanOperator<T> setField(String field) {
        this.field = field;
        return this;
    }

    /**
     * @return a json representation according to mongo db
     */
    @Override
    public String toJson() {
        return String.format("{\"%s\": { $lt: %s }}", field, JsonUtils.parsePrimitive(value));
    }
}
