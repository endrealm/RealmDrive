package net.endrealm.realmdrive.exceptions;

/**
 * @author johannesjumpertz
 *
 * Thrown if trying to call a write operation on a read only object
 */
public class ObjectReadOnlyException extends Exception {

    /**
     * Creates a new readonly exception
     */
    public ObjectReadOnlyException() {
        super("Tried writing to an readonly object");
    }

    /**
     * Creates a new readonly exception
     * @param root root cause that threw this exception
     */
    public ObjectReadOnlyException(Throwable root) {
        super("Tried writing to an readonly object", root);
    }
}
