package net.endrealm.realmdrive.query.logics;

import net.endrealm.realmdrive.query.Expression;
import net.endrealm.realmdrive.query.ExpressionStack;
import net.endrealm.realmdrive.query.QueryComponent;
import net.endrealm.realmdrive.query.compare.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author johannesjumpertz
 *
 * Or operator for queries
 */
public class OrOperator<T extends QueryComponent> extends LogicOperator<T> implements ExpressionStack {

    private ArrayList<Expression> expressions;

    public OrOperator(T parent) {
        super(parent);
        expressions = new ArrayList<>();
    }

    @Override
    public AndOperator<OrOperator<T>> addAnd() {
        AndOperator<OrOperator<T>> operator = new AndOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public OrOperator<OrOperator<T>> addOr() {
        OrOperator<OrOperator<T>> operator = new OrOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public NotOperator<OrOperator<T>> addNot() {
        NotOperator<OrOperator<T>> operator = new NotOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public NorOperator<OrOperator<T>> addNor() {
        NorOperator<OrOperator<T>> operator = new NorOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public EqualsOperator<OrOperator<T>> addEq() {
        EqualsOperator<OrOperator<T>> operator = new EqualsOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public NotEqualOperator<OrOperator<T>> addNe() {
        NotEqualOperator<OrOperator<T>> operator = new NotEqualOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public GreaterThanOperator<OrOperator<T>> addGt() {
        GreaterThanOperator<OrOperator<T>> operator = new GreaterThanOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public GreaterThanEqualsOperator<OrOperator<T>> addGte() {
        GreaterThanEqualsOperator<OrOperator<T>> operator = new GreaterThanEqualsOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public LessThanEqualsOperator<OrOperator<T>> addLte() {
        LessThanEqualsOperator<OrOperator<T>> operator = new LessThanEqualsOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public LessThanOperator<OrOperator<T>> addLt() {
        LessThanOperator<OrOperator<T>> operator = new LessThanOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public ValueInOperator<OrOperator<T>> addIn() {
        ValueInOperator<OrOperator<T>> operator = new ValueInOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public ValueNotInOperator<OrOperator<T>> addNin() {
        ValueNotInOperator<OrOperator<T>> operator = new ValueNotInOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public ValueBetweenOperator<OrOperator<T>> addBet() {
        ValueBetweenOperator<OrOperator<T>> operator = new ValueBetweenOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    /**
     * @return a json representation according to mongo db
     */
    @Override
    public String toJson() {
        return String.format("{$or: [%s]}",
                expressions.stream().map(QueryComponent::toJson).collect(Collectors.joining(",")));
    }

    /**
     * @return a sql representation according to the jdbc syntax
     */
    @Override
    public String toSQL() {
        return String.format(
                "(%s)",
                expressions.stream().map(e ->  "("+e.toSQL()+")").collect(Collectors.joining(" OR "))
        );
    }

    public OrOperator<T> addMany(ArrayList<Expression> expressions) {
        this.expressions.addAll(expressions);
        return this;
    }
}
