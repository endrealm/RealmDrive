package net.endrealm.realmdrive.query;

import lombok.Getter;
import net.endrealm.realmdrive.query.compare.*;
import net.endrealm.realmdrive.query.logics.AndOperator;
import net.endrealm.realmdrive.query.logics.NorOperator;
import net.endrealm.realmdrive.query.logics.NotOperator;
import net.endrealm.realmdrive.query.logics.OrOperator;

import java.util.ArrayList;
import java.util.stream.Collectors;


/**
 * @author johannesjumpertz
 *
 * Defines a query base object
 */
public class Query implements QueryComponent, ExpressionStack {
    private ArrayList<QueryComponent> components;
    @Getter
    private String tableName;
    @Getter
    private String databaseName;

    public Query() {
        components = new ArrayList<>();
    }

    /**
     * Does nothing for now.
     * Should be called at the end of a query creation.
     *
     * @return the query
     */
    public Query build() {
        return this;
    }

    @Override
    public AndOperator<Query> addAnd() {
        AndOperator<Query> operator = new AndOperator<>(this);
        components.add(operator);
        return operator;
    }

    @Override
    public OrOperator<Query> addOr() {
        OrOperator<Query> operator = new OrOperator<>(this);
        components.add(operator);
        return operator;
    }

    @Override
    public NotOperator<Query> addNot() {
        NotOperator<Query> operator = new NotOperator<>(this);
        components.add(operator);
        return operator;
    }

    @Override
    public NorOperator<Query> addNor() {
        NorOperator<Query> operator = new NorOperator<>(this);
        components.add(operator);
        return operator;
    }

    @Override
    public EqualsOperator<Query> addEq() {
        EqualsOperator<Query> operator = new EqualsOperator<>(this);
        components.add(operator);
        return operator;
    }

    @Override
    public NotEqualOperator<Query> addNe() {
        NotEqualOperator<Query> operator = new NotEqualOperator<>(this);
        components.add(operator);
        return operator;
    }

    @Override
    public GreaterThanOperator<Query> addGt() {
        GreaterThanOperator<Query> operator = new GreaterThanOperator<>(this);
        components.add(operator);
        return operator;
    }

    @Override
    public GreaterThanEqualsOperator<Query> addGte() {
        GreaterThanEqualsOperator<Query> operator = new GreaterThanEqualsOperator<>(this);
        components.add(operator);
        return operator;
    }

    @Override
    public LessThanEqualsOperator<Query> addLte() {
        LessThanEqualsOperator<Query> operator = new LessThanEqualsOperator<>(this);
        components.add(operator);
        return operator;
    }

    @Override
    public LessThanOperator<Query> addLt() {
        LessThanOperator<Query> operator = new LessThanOperator<>(this);
        components.add(operator);
        return operator;
    }

    @Override
    public ValueInOperator<Query> addIn() {
        ValueInOperator<Query> operator = new ValueInOperator<>(this);
        components.add(operator);
        return operator;
    }

    @Override
    public ValueNotInOperator<Query> addNin() {
        ValueNotInOperator<Query> operator = new ValueNotInOperator<>(this);
        components.add(operator);
        return operator;
    }

    @Override
    public ValueBetweenOperator<Query> addBet() {
        ValueBetweenOperator<Query> operator = new ValueBetweenOperator<>(this);
        components.add(operator);
        return operator;
    }

    @Override
    public String toJson() {
        return "{"+
                components.stream()
                        .map(it -> {
                            String json = it.toJson();
                            json = json.substring(1);
                            json = json.substring(0, json.length()-1);
                            return json;
                        } )
                        .collect(Collectors.joining(","))
                +"}";
    }

    /**
     * If none selected default table will be used
     *
     * @param tableName name of the target table
     * @return this query instance
     */
    public Query setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * If none selected default database will be used
     *
     * @param databaseName name of the target database
     * @return this query instance
     */
    public Query setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }
}
