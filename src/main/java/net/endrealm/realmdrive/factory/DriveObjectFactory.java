package net.endrealm.realmdrive.factory;

import net.endrealm.realmdrive.exceptions.NotAPrimitiveTypeException;
import net.endrealm.realmdrive.inst.SimpleDriveObject;
import net.endrealm.realmdrive.inst.SimpleElementArray;
import net.endrealm.realmdrive.inst.SimplePrimitiveDriveElement;
import net.endrealm.realmdrive.interfaces.DriveElement;
import net.endrealm.realmdrive.interfaces.DriveElementArray;
import net.endrealm.realmdrive.interfaces.DriveObject;
import net.endrealm.realmdrive.interfaces.DriveService;

/**
 * @author johannesjumpertz
 *
 * A factory creating drive object
 *
 * @see DriveObject
 * @see DriveElement
 */
public class DriveObjectFactory {

    /**
     * The drive service this factory is used by
     */
    private DriveService driveService;

    /**
     * Creates a new drive object factory using the specified {@link DriveService}
     * @param driveService the service using the factory
     */
    public DriveObjectFactory(DriveService driveService) {
        this.driveService = driveService;
    }

    /**
     * Instantiates a new empty {@link DriveObject}
     *
     * @return a empty {@link DriveObject}
     *
     * @see SimpleDriveObject
     */
    public DriveObject createEmptyObject() {
        return new SimpleDriveObject(this);
    }

    /**
     * Creates a new primitive element
     *
     * @param value value to be contained
     * @return null if the value isn't a primitive
     * @throws NotAPrimitiveTypeException when value is not a primitive
     */
    public DriveElement createPrimitive(Object value) throws NotAPrimitiveTypeException {
        return new SimplePrimitiveDriveElement(value);

    }

    public DriveElementArray createEmptyArray() {
        return new SimpleElementArray(this);
    }

    /**
     * @return drive service used by this factory
     */
    public DriveService getDriveService() {
        return driveService;
    }
}
