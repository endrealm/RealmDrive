package net.endrealm.realmdrive.inst.serializers;

import net.endrealm.realmdrive.exceptions.NotAPrimitiveTypeException;
import net.endrealm.realmdrive.inst.SimplePrimitiveDriveElement;
import net.endrealm.realmdrive.interfaces.CustomSerializer;
import net.endrealm.realmdrive.interfaces.DriveElement;

import java.util.Date;

public class DateSerializer implements CustomSerializer<Date> {

    @Override
    public DriveElement toDriveEndpoint(Date element) {
        try {
            return new SimplePrimitiveDriveElement(element.getTime());
        } catch (NotAPrimitiveTypeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Date fromEndpoint(DriveElement endpoint, Class<Date> dateClass) {
        try {
            return new Date(endpoint.getAsLong());
        } catch (NotAPrimitiveTypeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean supportsClass(Class clazz) {
        return Date.class.equals(clazz);
    }
}
