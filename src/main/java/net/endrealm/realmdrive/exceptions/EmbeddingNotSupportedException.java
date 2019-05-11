package net.endrealm.realmdrive.exceptions;

/**
 * @author johannesjumpertz
 *
 * Exception thrown when an inserted object is not registered
 */
public class EmbeddingNotSupportedException extends Exception {

    /**
     * Creates a new empty embedding not supported exception
     */
    public EmbeddingNotSupportedException() {
        super();
    }

    /**
     * Creates a new embedding not supported exception
     *
     * @param root root cause that threw this exception
     */
    public EmbeddingNotSupportedException(Throwable root) {
        super(root);
    }

    /**
     * Creates a new embedding not supported exception
     *
     * @param value the value that caused this exception
     */
    public EmbeddingNotSupportedException(Object value) {
        super(value.toString());
    }
}
