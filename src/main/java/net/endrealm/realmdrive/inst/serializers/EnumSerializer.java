package net.endrealm.realmdrive.inst.serializers;

import net.endrealm.realmdrive.inst.SimplePrimitiveDriveElement;
import net.endrealm.realmdrive.interfaces.CustomSerializer;
import net.endrealm.realmdrive.interfaces.DriveElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnumSerializer implements CustomSerializer<Object> {

    @Override
    public DriveElement toDriveEndpoint(Object element) {
        return new SimplePrimitiveDriveElement(element.toString());
    }

    @Override
    public Object fromEndpoint(DriveElement endpoint, Class<Object> objectClass) {

        try {
            Method method = objectClass.getDeclaredMethod("valueOf", String.class);

            return method.invoke(null, endpoint.getAsString());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean supportsClass(Class clazz) {
        return clazz.isEnum();
    }
}
