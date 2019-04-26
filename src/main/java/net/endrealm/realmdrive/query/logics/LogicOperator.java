package net.endrealm.realmdrive.query.logics;

import net.endrealm.realmdrive.query.Expression;
import net.endrealm.realmdrive.query.QueryComponent;

/**
 * @author johannesjumpertz
 *
 * Base class for all logic query operators
 */
public abstract class LogicOperator<T extends QueryComponent> implements Expression {

    private T parent;

    public LogicOperator(T parent) {

        this.parent = parent;
    }

    public T close() {
        return parent;
    }
}
