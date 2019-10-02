package net.endrealm.realmdrive.interfaces;

public interface CustomSerializer<T> {

    DriveElement toDriveEndpoint(T element);
    T fromEndpoint(DriveElement endpoint, Class<T> tClass);

    boolean supportsClass(Class clazz);
}
