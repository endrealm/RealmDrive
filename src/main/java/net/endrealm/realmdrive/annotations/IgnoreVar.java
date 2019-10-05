package net.endrealm.realmdrive.annotations;

import net.endrealm.realmdrive.inst.SimpleConversionHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field to not be saved.
 *
 * @see SimpleConversionHandler
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IgnoreVar {
}
