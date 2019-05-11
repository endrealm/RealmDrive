package net.endrealm.realmdrive.exceptions;

/**
 * @author johannesjumpertz
 *
 * Exception thrown when an inserted value contains embedded values and this is not supported by the current backend
 */
public class UndefinedNotSupportedException extends Exception {

    /**
     * Creates a new empty undefined not supported exception
     */
    public UndefinedNotSupportedException() {
        super();
    }

    /**
     * Creates a new undefined not supported exception
     *
     * @param root root cause that threw this exception
     */
    public UndefinedNotSupportedException(Throwable root) {
        super(root);
    }

    /**
     * Creates a new undefined not supported exception
     *
     * @param value the value that caused this exception
     */
    public UndefinedNotSupportedException(Object value) {
        super(value.toString());
    }
}
