package net.endrealm.realmdrive.interfaces;

import net.endrealm.realmdrive.exceptions.NotAPrimitiveTypeException;

import java.util.HashMap;

/**
 * @author johannesjumpertz
 *
 * Defines a drive element. This is the base class for everything retrieved and saved to the backend.
 */
public interface DriveElement {
    /**
     * @return this element as a {@link Boolean}
     * @throws ClassCastException thrown if the element is not that type
     * @throws NotAPrimitiveTypeException thrown if the element is not a primitive type
     */
    Boolean getAsBoolean() throws ClassCastException, NotAPrimitiveTypeException;

    /**
     * @return this element as a {@link Short}
     * @throws ClassCastException thrown if the element is not that type
     * @throws NotAPrimitiveTypeException thrown if the element is not a primitive type
     */
    Short getAsShort() throws ClassCastException, NotAPrimitiveTypeException;

    /**
     * @return this element as a {@link Integer}
     * @throws ClassCastException thrown if the element is not that type
     * @throws NotAPrimitiveTypeException thrown if the element is not a primitive type
     */
    Integer getAsInt() throws ClassCastException, NotAPrimitiveTypeException;

    /**
     * @return this element as a {@link Long}
     * @throws ClassCastException thrown if the element is not that type
     * @throws NotAPrimitiveTypeException thrown if the element is not a primitive type
     */
    Long getAsLong() throws ClassCastException, NotAPrimitiveTypeException;

    /**
     * @return this element as a {@link Character}
     * @throws ClassCastException thrown if the element is not that type
     * @throws NotAPrimitiveTypeException thrown if the element is not a primitive type
     */
    Character getAsChar() throws ClassCastException, NotAPrimitiveTypeException;

    /**
     * @return this element as a {@link String}
     * @throws ClassCastException thrown if the element is not that type
     * @throws NotAPrimitiveTypeException thrown if the element is not a primitive type
     */
    String getAsString() throws ClassCastException, NotAPrimitiveTypeException;

    /**
     * @return this element as a {@link Float}
     * @throws ClassCastException thrown if the element is not that type
     * @throws NotAPrimitiveTypeException thrown if the element is not a primitive type
     */
    Float getAsFloat() throws ClassCastException, NotAPrimitiveTypeException;

    /**
     * @return this element as a {@link Double}
     * @throws ClassCastException thrown if the element is not that type
     * @throws NotAPrimitiveTypeException thrown if the element is not a primitive type
     */
    Double getAsDouble() throws ClassCastException, NotAPrimitiveTypeException;

    /**
     * @return this element as a {@link DriveObject}
     * @throws ClassCastException thrown if the element is not that type
     */
    DriveObject getAsObject() throws ClassCastException;

    /**
     * @return this element as a {@link DriveElement}
     * @throws ClassCastException thrown if the element is not that type
     */
    DriveElement getAsElement() throws ClassCastException;

    /**
     * @return this element as a {@link DriveElementArray}
     * @throws ClassCastException thrown if the element is not that type
     */
    DriveElementArray getAsElementArray() throws ClassCastException;

    /**
     * @return primitive value stored in the element. Null if not storing a primitive.
     */
    Object getPrimitiveValue();

    /**
     * @return returns a list of sub objects. Null if storing a primitive.
     */
    HashMap<String, DriveElement> getSubComponents();
}
