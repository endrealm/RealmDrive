package net.endrealm.realmdrive.query.logics;

import net.endrealm.realmdrive.query.Expression;
import net.endrealm.realmdrive.query.ExpressionStack;
import net.endrealm.realmdrive.query.QueryComponent;
import de.findlunch.stats.query.compare.*;
import net.endrealm.realmdrive.query.compare.*;

/**
 * @author johannesjumpertz
 *
 * Not operator for queries
 */
public class NotOperator<T extends QueryComponent> extends LogicOperator<T> implements ExpressionStack {

    private Expression expression;

    public NotOperator(T parent) {
        super(parent);
    }

    @Override
    public AndOperator<NotOperator<T>> addAnd() {
        AndOperator<NotOperator<T>> operator = new AndOperator<>(this);
        expression = operator;
        return operator;
    }

    @Override
    public OrOperator<NotOperator<T>> addOr() {
        OrOperator<NotOperator<T>> operator = new OrOperator<>(this);
        expression = operator;
        return operator;
    }

    @Override
    public NotOperator<NotOperator<T>> addNot() {
        NotOperator<NotOperator<T>> operator = new NotOperator<>(this);
        expression = operator;
        return operator;
    }

    @Override
    public NorOperator<NotOperator<T>> addNor() {
        NorOperator<NotOperator<T>> operator = new NorOperator<>(this);
        expression = operator;
        return operator;
    }

    @Override
    public EqualsOperator<NotOperator<T>> addEq() {
        EqualsOperator<NotOperator<T>> operator = new EqualsOperator<>(this);
        expression = operator;
        return operator;
    }

    @Override
    public NotEqualOperator<NotOperator<T>> addNe() {
        NotEqualOperator<NotOperator<T>> operator = new NotEqualOperator<>(this);
        expression = operator;
        return operator;
    }

    @Override
    public GreaterThanOperator<NotOperator<T>> addGt() {
        GreaterThanOperator<NotOperator<T>> operator = new GreaterThanOperator<>(this);
        expression = operator;
        return operator;
    }

    @Override
    public GreaterThanEqualsOperator<NotOperator<T>> addGte() {
        GreaterThanEqualsOperator<NotOperator<T>> operator = new GreaterThanEqualsOperator<>(this);
        expression = operator;
        return operator;
    }

    @Override
    public LessThanEqualsOperator<NotOperator<T>> addLte() {
        LessThanEqualsOperator<NotOperator<T>> operator = new LessThanEqualsOperator<>(this);
        expression = operator;
        return operator;
    }

    @Override
    public LessThanOperator<NotOperator<T>> addLt() {
        LessThanOperator<NotOperator<T>> operator = new LessThanOperator<>(this);
        expression = operator;
        return operator;
    }

    @Override
    public ValueInOperator<NotOperator<T>> addIn() {
        ValueInOperator<NotOperator<T>> operator = new ValueInOperator<>(this);
        expression = operator;
        return operator;
    }

    @Override
    public ValueNotInOperator<NotOperator<T>> addNin() {
        ValueNotInOperator<NotOperator<T>> operator = new ValueNotInOperator<>(this);
        expression = operator;
        return operator;
    }

    /**
     * @return a json representation according to mongo db
     */
    @Override
    public String toJson() {
        return String.format("{$not: %s}", expression.toJson());
    }
}
