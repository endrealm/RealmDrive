package net.endrealm.realmdrive.utils;

/**
 * Utility class for threads
 */
public class ThreadUtils {

    /**
     * Creates a new thread. Later introduce more complex thread handling. See issue #10
     * @param runnable runnable to run on start
     * @return the new thread
     */
    public static Thread createNewThread(Runnable runnable) {
        return new Thread(runnable);
    }
}
