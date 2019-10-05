package net.endrealm.realmdrive.inst.serializers;

import net.endrealm.realmdrive.exceptions.NotAPrimitiveTypeException;
import net.endrealm.realmdrive.inst.SimplePrimitiveDriveElement;
import net.endrealm.realmdrive.interfaces.CustomSerializer;
import net.endrealm.realmdrive.interfaces.DriveElement;

import java.util.UUID;

public class UUIDSerializer implements CustomSerializer<UUID> {

    @Override
    public DriveElement toDriveEndpoint(UUID element) {
        try {
            return new SimplePrimitiveDriveElement(element.toString());
        } catch (NotAPrimitiveTypeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UUID fromEndpoint(DriveElement endpoint, Class<UUID> uuidClass) {
        try {
            return UUID.fromString(endpoint.getAsString());
        } catch (NotAPrimitiveTypeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean supportsClass(Class clazz) {
        return UUID.class.equals(clazz);
    }
}
