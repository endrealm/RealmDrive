package net.endrealm.realmdrive.exceptions;

/**
 * @author johannesjumpertz
 *
 * Exception thrown when an inserted value is not a primitive although it should be
 */
public class NotAPrimitiveTypeException extends RuntimeException {

    /**
     * Creates a new empty primitive exception
     */
    public NotAPrimitiveTypeException() {
        super();
    }

    /**
     * Creates a new primitive exception
     * @param root root cause that threw this exception
     */
    public NotAPrimitiveTypeException(Throwable root) {
        super(root);
    }

    /**
     * Creates a new primitive exception
     * @param value the value that caused this exception
     */
    public NotAPrimitiveTypeException(Object value) {
        super(value.toString());
    }
}
