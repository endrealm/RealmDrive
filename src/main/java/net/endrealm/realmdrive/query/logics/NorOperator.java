package net.endrealm.realmdrive.query.logics;

import net.endrealm.realmdrive.query.Expression;
import net.endrealm.realmdrive.query.ExpressionStack;
import net.endrealm.realmdrive.query.QueryComponent;
import de.findlunch.stats.query.compare.*;
import net.endrealm.realmdrive.query.compare.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author johannesjumpertz
 *
 * Nor operator for queries
 */
public class NorOperator<T extends QueryComponent> extends LogicOperator<T> implements ExpressionStack {

    private ArrayList<Expression> expressions;

    public NorOperator(T parent) {
        super(parent);
        expressions = new ArrayList<>();
    }

    @Override
    public AndOperator<NorOperator<T>> addAnd() {
        AndOperator<NorOperator<T>> operator = new AndOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public OrOperator<NorOperator<T>> addOr() {
        OrOperator<NorOperator<T>> operator = new OrOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public NotOperator<NorOperator<T>> addNot() {
        NotOperator<NorOperator<T>> operator = new NotOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public NorOperator addNor() {
        NorOperator<NorOperator<T>> operator = new NorOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public EqualsOperator<NorOperator<T>> addEq() {
        EqualsOperator<NorOperator<T>> operator = new EqualsOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public NotEqualOperator<NorOperator<T>> addNe() {
        NotEqualOperator<NorOperator<T>> operator = new NotEqualOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public GreaterThanOperator<NorOperator<T>> addGt() {
        GreaterThanOperator<NorOperator<T>> operator = new GreaterThanOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public GreaterThanEqualsOperator<NorOperator<T>> addGte() {
        GreaterThanEqualsOperator<NorOperator<T>> operator = new GreaterThanEqualsOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public LessThanEqualsOperator<NorOperator<T>> addLte() {
        LessThanEqualsOperator<NorOperator<T>> operator = new LessThanEqualsOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public LessThanOperator<NorOperator<T>> addLt() {
        LessThanOperator<NorOperator<T>> operator = new LessThanOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public ValueInOperator<NorOperator<T>> addIn() {
        ValueInOperator<NorOperator<T>> operator = new ValueInOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public ValueNotInOperator<NorOperator<T>> addNin() {
        ValueNotInOperator<NorOperator<T>> operator = new ValueNotInOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    /**
     * @return a json representation according to mongo db
     */
    @Override
    public String toJson() {
        return String.format("{$nor: [%s]}",
                expressions.stream().map(QueryComponent::toJson).collect(Collectors.joining(",")));    }
}
