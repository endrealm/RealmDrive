package net.endrealm.realmdrive.inst;

import lombok.Data;
import net.endrealm.realmdrive.exceptions.NotAPrimitiveTypeException;
import net.endrealm.realmdrive.interfaces.DriveElement;
import net.endrealm.realmdrive.interfaces.DriveElementArray;
import net.endrealm.realmdrive.interfaces.DriveObject;
import net.endrealm.realmdrive.utils.ReflectionUtils;

import java.util.HashMap;

/**
 * @author johannesjumpertz
 *
 * Simple implementation to contain a primitive value
 */
@Data
public class SimplePrimitiveDriveElement implements DriveElement {

    /**
     * Value to store
     */
    private Object value;

    /**
     * Creates a new drive element to store a primitive
     *
     * @param value the value to save
     * @throws NotAPrimitiveTypeException thrown if the value is not a primitive
     */
    public SimplePrimitiveDriveElement(Object value) throws NotAPrimitiveTypeException {
        if(!ReflectionUtils.getPrimitiveWrapperTypes().contains(value.getClass()))
            throw new NotAPrimitiveTypeException(value + " " + value.getClass());
        this.value = value;
    }

    /**
     * @return this element as a {@link Boolean}
     * @throws ClassCastException         thrown if the element is not that type
     */
    @Override
    public Boolean getAsBoolean() throws ClassCastException {
        return (Boolean) value;
    }

    /**
     * @return this element as a {@link Short}
     * @throws ClassCastException         thrown if the element is not that type
     */
    @Override
    public Short getAsShort() throws ClassCastException {
        return (Short) value;
    }

    /**
     * @return this element as a {@link Integer}
     * @throws ClassCastException         thrown if the element is not that type
     */
    @Override
    public Integer getAsInt() throws ClassCastException {
        return (Integer) value;
    }

    /**
     * @return this element as a {@link Long}
     * @throws ClassCastException         thrown if the element is not that type
     */
    @Override
    public Long getAsLong() throws ClassCastException {
        return (Long) value;
    }

    /**
     * @return this element as a {@link Character}
     * @throws ClassCastException         thrown if the element is not that type
     */
    @Override
    public Character getAsChar() throws ClassCastException {
        return (Character) value;
    }

    /**
     * @return this element as a {@link String}
     * @throws ClassCastException         thrown if the element is not that type
     */
    @Override
    public String getAsString() throws ClassCastException {
        return (String) value;
    }

    /**
     * @return this element as a {@link Float}
     * @throws ClassCastException         thrown if the element is not that type
     */
    @Override
    public Float getAsFloat() throws ClassCastException {
        return (Float) value;
    }

    /**
     * @return this element as a {@link Double}
     * @throws ClassCastException         thrown if the element is not that type
     */
    @Override
    public Double getAsDouble() throws ClassCastException {
        return (Double) value;
    }

    /**
     * @return this element as a {@link DriveObject}
     * @throws ClassCastException thrown if the element is not that type
     */
    @Override
    public DriveObject getAsObject() throws ClassCastException {
        throw new ClassCastException("This is a primitive drive element, not a drive object");
    }

    /**
     * @return this element as a {@link DriveElement}
     * @throws ClassCastException thrown if the element is not that type
     */
    @Override
    public DriveElement getAsElement() throws ClassCastException {
        return this;
    }

    /**
     * @return this element as a {@link DriveElementArray}
     * @throws ClassCastException thrown if the element is not that type
     */
    @Override
    public DriveElementArray getAsElementArray() throws ClassCastException {
        throw new ClassCastException("This is an drive primitive element not an array");
    }

    /**
     * @return primitive value stored in the element. Null if not storing a primitive.
     */
    @Override
    public Object getPrimitiveValue() {
        return value;
    }

    /**
     * @return returns a list of sub objects. Null if storing a primitive.
     */
    @Override
    public HashMap<String, DriveElement> getSubComponents() {
        return null;
    }

    @Override
    public DriveElement subtract(DriveElement driveElement) {

        if(!(driveElement instanceof SimplePrimitiveDriveElement))
            return deepClone();

        if(((SimplePrimitiveDriveElement) driveElement).value != value)
            return deepClone();

        return null;
    }

    @Override
    public DriveElement add(DriveElement driveElement) {
        return driveElement;
    }

    @Override
    public DriveElement deepClone() {
        return new SimplePrimitiveDriveElement(value);
    }
}
