package net.endrealm.realmdrive.inst;

import lombok.Data;
import net.endrealm.realmdrive.exceptions.NotAPrimitiveTypeException;
import net.endrealm.realmdrive.factory.DriveObjectFactory;
import net.endrealm.realmdrive.interfaces.DriveElement;
import net.endrealm.realmdrive.interfaces.DriveElementArray;
import net.endrealm.realmdrive.interfaces.DriveObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author johannesjumpertz
 *
 * Implementation of a {@link DriveElementArray}
 */
@Data
public class SimpleElementArray implements DriveElementArray {

    /**
     * List of elements
     */
    private ArrayList<DriveElement> list;

    /**
     * The factory that created this object
     */
    private final DriveObjectFactory factory;

    /**
     * Creates a new empty element array
     *
     * @param factory factory that created this object
     */
    public SimpleElementArray(DriveObjectFactory factory) {
        this.factory = factory;
        list = new ArrayList<>();
    }

    /**
     * Retrieves an item from the list
     *
     * @param num used to identify an item
     * @return a drive element. Null if not found
     */
    @Override
    public DriveElement get(int num) {
        return list.get(num);
    }

    /**
     * Retrieves an item from the list
     *
     * @param num used to identify an item
     * @return a drive object. Null if not found
     */
    @Override
    public DriveObject getAsObject(int num) {
        return list.get(num) == null? null : list.get(num).getAsObject();
    }

    /**
     * Writes an element to the list
     *
     * @param num     position to insert at
     * @param element element to store
     */
    @Override
    public void addObject(int num, DriveElement element) {
        list.add(num, element);
    }

    /**
     * Writes an primitive element to the list
     *
     * @param num    position to insert at
     * @param object primitive type object
     */
    @Override
    public void addPrimitive(int num, Object object) {
        addObject(num, factory.createPrimitive(object));
    }

    /**
     * Returns this object converted to an object of the specified type
     *
     * @param clazz type to convert object to
     * @return converted object
     * @throws ClassCastException thrown if this object can not be converted into the specified object.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> getAsTypedList(Class<T> clazz) throws ClassCastException {
        return factory.getDriveService().getConversionHandler().createList(this, clazz);
    }

    /**
     * Returns all contents as a mutable list
     *
     * @return contents
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<DriveElement> getContents() {
        return (List<DriveElement>) list.clone();
    }

    /**
     * Adds an element to the end of the array
     *
     * @param element element to be added
     */
    @Override
    public void addObject(DriveElement element) {
        list.add(element);
    }

    /**
     * Adds an primitive element to the end of the array
     *
     * @param object element to be added
     */
    @Override
    public void addPrimitive(Object object) {
        list.add(factory.createPrimitive(object));
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
        return  null;
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
        return this;
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
        return null;
    }
}
