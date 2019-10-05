package net.endrealm.realmdrive.annotations;

import net.endrealm.realmdrive.inst.SimpleConversionHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field to only be saved not loaded.
 * Do <b>not</b> use {@link ReadOnly} together with this.
 * To enable both read and write use {@link SaveVar}.
 *
 * @see SimpleConversionHandler
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface WriteOnly {
}
