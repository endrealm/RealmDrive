package net.endrealm.realmdrive.annotations;

import net.endrealm.realmdrive.inst.SimpleConversionHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks all fields as to be saved. This can be
 * overwritten by {@link SaveVar} and {@link IgnoreVar}
 *
 * @see SimpleConversionHandler
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SaveAll {
    /**
     * @return field names to be ignored
     */
    String[] ignored() default {};
}
