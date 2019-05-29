package net.endrealm.realmdrive.query.compare;

import net.endrealm.realmdrive.query.QueryComponent;
import net.endrealm.realmdrive.utils.JsonUtils;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author johannesjumpertz
 *
 * Call testing if found value does not match any of the listed
 */
public class ValueNotInOperator<T extends QueryComponent> extends CompareOperator<T> {

    private ArrayList<Object> values;
    private String field;

    public ValueNotInOperator(T parent) {
        super(parent);
        values = new ArrayList<>();
        field = "";
    }

    public ValueNotInOperator<T> addValue(Object object) {
        values.add(object);
        return this;
    }

    public ValueNotInOperator<T> setField(String field) {
        this.field = field;
        return this;
    }

    /**
     * @return a json representation according to mongo db
     */
    @Override
    public String toJson() {
        return String.format("{\"%s\": { $nin: [%s] }}", field,
                values.stream().map(JsonUtils::parsePrimitive).collect(Collectors.joining(",")));
    }

    /**
     * @return a sql representation according to the jdbc syntax
     */
    @Override
    public String toSQL() {
        return String.format(
                "NOT ( %s )",
                new ValueInOperator<>(this).setField(field).addValue(values).toSQL());
    }

}
