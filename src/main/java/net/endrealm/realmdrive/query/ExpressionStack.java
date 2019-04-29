package net.endrealm.realmdrive.query;

import net.endrealm.realmdrive.query.compare.*;
import net.endrealm.realmdrive.query.logics.AndOperator;
import net.endrealm.realmdrive.query.logics.NorOperator;
import net.endrealm.realmdrive.query.logics.NotOperator;
import net.endrealm.realmdrive.query.logics.OrOperator;

/**
 * @author johannesjumpertz
 *
 * Defines an object providing the full stack of expressions
 */
public interface ExpressionStack {
    //Logic
    AndOperator addAnd();
    OrOperator addOr();
    NotOperator addNot();
    NorOperator addNor();

    //Compare
    EqualsOperator addEq();
    NotEqualOperator addNe();
    GreaterThanOperator addGt();
    GreaterThanEqualsOperator addGte();
    LessThanEqualsOperator addLte();
    LessThanOperator addLt();
    ValueInOperator addIn();
    ValueNotInOperator addNin();
    ValueBetweenOperator addBet();
}
