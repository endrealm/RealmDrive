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
 * And operator for queries
 */
public class AndOperator<T extends QueryComponent> extends LogicOperator<T> implements ExpressionStack {

    private ArrayList<Expression> expressions;

    public AndOperator(T parent) {
        super(parent);
        expressions = new ArrayList<>();
    }

    @Override
    public AndOperator<AndOperator<T>> addAnd() {
        AndOperator<AndOperator<T>> operator = new AndOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public OrOperator<AndOperator<T>> addOr() {
        OrOperator<AndOperator<T>> operator = new OrOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public NotOperator<AndOperator<T>> addNot() {
        NotOperator<AndOperator<T>> operator = new NotOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public NorOperator<AndOperator<T>> addNor() {
        NorOperator<AndOperator<T>> operator = new NorOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public EqualsOperator<AndOperator<T>> addEq() {
        EqualsOperator<AndOperator<T>> operator = new EqualsOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public NotEqualOperator<AndOperator<T>> addNe() {
        NotEqualOperator<AndOperator<T>> operator = new NotEqualOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public GreaterThanOperator<AndOperator<T>> addGt() {
        GreaterThanOperator<AndOperator<T>> operator = new GreaterThanOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public GreaterThanEqualsOperator<AndOperator<T>> addGte() {
        GreaterThanEqualsOperator<AndOperator<T>> operator = new GreaterThanEqualsOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public LessThanEqualsOperator<AndOperator<T>> addLte() {
        LessThanEqualsOperator<AndOperator<T>> operator = new LessThanEqualsOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public LessThanOperator<AndOperator<T>> addLt() {
        LessThanOperator<AndOperator<T>> operator = new LessThanOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public ValueInOperator<AndOperator<T>> addIn() {
        ValueInOperator<AndOperator<T>> operator = new ValueInOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public ValueNotInOperator<AndOperator<T>> addNin() {
        ValueNotInOperator<AndOperator<T>> operator = new ValueNotInOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    @Override
    public ValueBetweenOperator<AndOperator<T>> addBet() {
        ValueBetweenOperator<AndOperator<T>> operator = new ValueBetweenOperator<>(this);
        expressions.add(operator);
        return operator;
    }

    /**
     * @return a json representation according to mongo db
     */
    @Override
    public String toJson() {
        return String.format("{$and: [%s]}",
                expressions.stream().map(QueryComponent::toJson).collect(Collectors.joining(",")));
    }
}
