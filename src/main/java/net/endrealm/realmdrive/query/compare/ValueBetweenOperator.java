package net.endrealm.realmdrive.query.compare;

import net.endrealm.realmdrive.query.QueryComponent;
import net.endrealm.realmdrive.utils.JsonUtils;
import net.endrealm.realmdrive.utils.MySQLUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author johannesjumpertz
 *
 * Call testing if found value is in between to integers
 * THis only works with integers though.
 *
 * Use {@link LessThanOperator} and {@link GreaterThanOperator} to test more efficiently
 */
public class ValueBetweenOperator<T extends QueryComponent> extends CompareOperator<T> {

    private int start, end;
    private boolean startInclusive, endInclusive;
    private String field;

    public ValueBetweenOperator(T parent) {
        super(parent);
        field = "";
        startInclusive = true;
        endInclusive = false;
    }

    /**
     * Sets the start value. Exclusive by default. Overwrite this behaviour by calling setStartInclusive(true)
     *
     * @param startVal the start value
     * @return this object instance
     */
    public ValueBetweenOperator<T> setStart(int startVal) {
        this.start = startVal;
        return this;
    }

    public ValueBetweenOperator<T> setField(String field) {
        this.field = field;
        return this;
    }

    /**
     * Sets the start value to be in/excluded
     *
     * @param startInclusive include start value
     * @return this object instance
     */
    public ValueBetweenOperator<T> setStartInclusive(boolean startInclusive) {
        this.startInclusive = startInclusive;
        return this;
    }

    /**
     * @return a json representation according to mongo db
     */
    @Override
    public String toJson() {
        return String.format("{\"%s\": { $in: [%s] }}", field,
                getAllVals().stream().map(JsonUtils::parsePrimitive).collect(Collectors.joining(",")));
    }

    /**
     * Returns a list of all included values
     *
     * @return a list of all values
     */
    private List<Integer> getAllVals() {
        List<Integer> values = new ArrayList<>();

        for(int i = startInclusive ? start : start+1;
            i < (endInclusive? end : end-1);
            i++)
            values.add(i);

        return values;
    }

    /**
     * @return a sql representation according to the jdbc syntax
     */
    @Override
    public String toSQL() {
        return String.format("%s BETWEEN %s AND %s",
                field,
                MySQLUtils.getSQLRepr(startInclusive? start : start+1),
                MySQLUtils.getSQLRepr(endInclusive? end : end-1)
        );
    }
}
