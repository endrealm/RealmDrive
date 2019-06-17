package net.endrealm.realmdrive.interfaces;

public interface CustomSerializer<T> {

    DriveElement toDriveEndpoint(T element);
    T fromEndpoint(DriveElement endpoint);

    boolean supportsClass(Class clazz);
}
