package net.endrealm.realmdrive.annotations;

import net.endrealm.realmdrive.inst.SimpleConversionHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Used to mark fields to be saved into the drive system. If
 * other objects are linked, they have to be registered as
 * well.
 *
 * Fields with this annotation will be both read and written.
 * To control this behaviour add {@link ReadOnly} or {@link WriteOnly}.
 *
 * @see SimpleConversionHandler
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SaveVar {
    /**
     * @return name to save/load under
     */
    String name() default "";

    /**
     * @return optional names to load from
     */
    String[] aliases() default {};

    /**
     * @return if false exception will be thrown upon null value
     */
    boolean optional() default true;
}
