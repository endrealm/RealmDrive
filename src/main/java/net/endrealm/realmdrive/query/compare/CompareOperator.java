package net.endrealm.realmdrive.query.compare;

import net.endrealm.realmdrive.query.Expression;
import net.endrealm.realmdrive.query.QueryComponent;

/**
 * @author johannesjumpertz
 *
 * Compare call in query
 */
public abstract class CompareOperator<T extends QueryComponent> implements Expression {

    private T parent;

    public CompareOperator(T parent) {

        this.parent = parent;
    }

    public T close() {
        return parent;
    }
}
