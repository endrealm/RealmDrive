package net.endrealm.realmdrive.inst;

import lombok.Data;
import net.endrealm.realmdrive.exceptions.NotAPrimitiveTypeException;
import net.endrealm.realmdrive.factory.DriveObjectFactory;
import net.endrealm.realmdrive.interfaces.DriveElement;
import net.endrealm.realmdrive.interfaces.DriveElementArray;
import net.endrealm.realmdrive.interfaces.DriveObject;

import java.util.HashMap;

/**
 * @author johannesjumpertz
 *
 * Implementation of a drive object
 */
@Data
public class SimpleDriveObject implements DriveObject {

    /**
     * A list of elements in this object
     */
    private HashMap<String, DriveElement> elementHashMap;

    /**
     * The factory that created this object
     */
    private final DriveObjectFactory factory;

    /**
     * Creates a new drive object
     * @param factory the factory the object was created in
     */
    public SimpleDriveObject(DriveObjectFactory factory) {
        this.factory = factory;
        elementHashMap = new HashMap<>();
    }


    /**
     * Retrieves an item from the list
     *
     * @param key used to identify an item
     * @return a drive element. Null if not found
     */
    @Override
    public DriveElement get(String key) {
        return elementHashMap.get(key);
    }

    /**
     * Retrieves an item from the list
     *
     * @param key used to identify an item
     * @return a drive object. Null if not found
     */
    @Override
    public DriveObject getAsObject(String key) {
        return (DriveObject) elementHashMap.get(key);
    }

    /**
     * Writes an element to the list
     *
     * @param key     key to affiliate the element with
     * @param element element to store
     */
    @Override
    public void setObject(String key, DriveElement element) {
        elementHashMap.put(key, element);
    }

    /**
     * Writes an primitive element to the list
     *
     * @param key    key to affiliate the element with
     * @param object primitive type object
     */
    @Override
    public void setPrimitive(String key, Object object) {
        elementHashMap.put(key, factory.createPrimitive(object));
    }

    /**
     * Returns this object converted to an object of the specified type
     *
     * @param clazz class to cast object to
     * @return converted object
     * @throws ClassCastException thrown if this object can not be converted into the specified object.
     */
    @Override
    public <T> T getAsTypedObject(Class<T> clazz) throws ClassCastException {
        return factory.getDriveService().getConversionHandler().transform(this, clazz);
    }

    /**
     * Checks if this object is empty
     *
     * @return true if object does not contain any values
     */
    @Override
    public boolean isEmpty() {
        return elementHashMap.isEmpty();
    }

    /**
     * @return this element as a {@link Boolean}
     * @throws ClassCastException         thrown if the element is not that type
     * @throws NotAPrimitiveTypeException thrown if the element is not a primitive type
     */
    @Override
    public Boolean getAsBoolean() throws ClassCastException, NotAPrimitiveTypeException {
        throw new NotAPrimitiveTypeException();
    }

    /**
     * @return this element as a {@link Short}
     * @throws ClassCastException         thrown if the element is not that type
     * @throws NotAPrimitiveTypeException thrown if the element is not a primitive type
     */
    @Override
    public Short getAsShort() throws ClassCastException, NotAPrimitiveTypeException {
        throw new NotAPrimitiveTypeException();
    }

    /**
     * @return this element as a {@link Integer}
     * @throws ClassCastException         thrown if the element is not that type
     * @throws NotAPrimitiveTypeException thrown if the element is not a primitive type
     */
    @Override
    public Integer getAsInt() throws ClassCastException, NotAPrimitiveTypeException {
        throw new NotAPrimitiveTypeException();
    }

    /**
     * @return this element as a {@link Long}
     * @throws ClassCastException         thrown if the element is not that type
     * @throws NotAPrimitiveTypeException thrown if the element is not a primitive type
     */
    @Override
    public Long getAsLong() throws ClassCastException, NotAPrimitiveTypeException {
        throw new NotAPrimitiveTypeException();
    }

    /**
     * @return this element as a {@link Character}
     * @throws ClassCastException         thrown if the element is not that type
     * @throws NotAPrimitiveTypeException thrown if the element is not a primitive type
     */
    @Override
    public Character getAsChar() throws ClassCastException, NotAPrimitiveTypeException {
        throw new NotAPrimitiveTypeException();
    }

    /**
     * @return this element as a {@link String}
     * @throws ClassCastException         thrown if the element is not that type
     * @throws NotAPrimitiveTypeException thrown if the element is not a primitive type
     */
    @Override
    public String getAsString() throws ClassCastException, NotAPrimitiveTypeException {
        throw new NotAPrimitiveTypeException();
    }

    /**
     * @return this element as a {@link Float}
     * @throws ClassCastException         thrown if the element is not that type
     * @throws NotAPrimitiveTypeException thrown if the element is not a primitive type
     */
    @Override
    public Float getAsFloat() throws ClassCastException, NotAPrimitiveTypeException {
        throw new NotAPrimitiveTypeException();
    }

    /**
     * @return this element as a {@link Double}
     * @throws ClassCastException         thrown if the element is not that type
     * @throws NotAPrimitiveTypeException thrown if the element is not a primitive type
     */
    @Override
    public Double getAsDouble() throws ClassCastException, NotAPrimitiveTypeException {
        throw new NotAPrimitiveTypeException();
    }

    /**
     * @return this element as a {@link DriveObject}
     * @throws ClassCastException thrown if the element is not that type
     */
    @Override
    public DriveObject getAsObject() throws ClassCastException {
        return this;
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
        throw new ClassCastException("This is a statistics object not an array");
    }

    /**
     * @return primitive value stored in the element. Null if not storing a primitive.
     */
    @Override
    public Object getPrimitiveValue() {
        return null;
    }

    /**
     * @return returns a list of sub objects. Null if storing a primitive.
     */
    @Override
    public HashMap<String, DriveElement> getSubComponents() {
        return elementHashMap;
    }
}
