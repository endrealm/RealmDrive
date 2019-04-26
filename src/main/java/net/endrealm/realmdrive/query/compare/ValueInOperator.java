package net.endrealm.realmdrive.query.compare;

import net.endrealm.realmdrive.query.QueryComponent;
import net.endrealm.realmdrive.utils.JsonUtils;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author johannesjumpertz
 *
 * Call testing if found value matches one of the listed
 */
public class ValueInOperator<T extends QueryComponent> extends CompareOperator<T> {

    private ArrayList<Object> values;
    private String field;

    public ValueInOperator(T parent) {
        super(parent);
        values = new ArrayList<>();
        field = "";
    }

    public ValueInOperator<T> addValue(Object object) {
        values.add(object);
        return this;
    }

    public ValueInOperator<T> setField(String field) {
        this.field = field;
        return this;
    }

    /**
     * @return a json representation according to mongo db
     */
    @Override
    public String toJson() {
        return String.format("{\"%s\": { $in: [%s] }}", field,
                values.stream().map(JsonUtils::parsePrimitive).collect(Collectors.joining(",")));
    }
}
