package net.endrealm.realmdrive;

import net.endrealm.realmdrive.factory.DriveObjectFactory;
import net.endrealm.realmdrive.inst.SimpleConversionHandler;
import net.endrealm.realmdrive.inst.SimpleDriveService;
import net.endrealm.realmdrive.interfaces.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Drive Element Tests")
class ElementTests {

    private final DriveObjectFactory factory;

    ElementTests() {
        ConversionHandler conversionHandler = new SimpleConversionHandler();

        DriveService service = new SimpleDriveService(null, null, null, conversionHandler);
        conversionHandler.setService(service);

        this.factory = new DriveObjectFactory(service);

    }

    @BeforeEach
    void init(TestInfo testInfo) {
        String callingTest = testInfo.getDisplayName();
        System.out.printf("Executing %s...\n", callingTest);
    }

    @Test
    @Order(1)
    @DisplayName("Primitive Element Test")
    void testPrimitiveElements() {

        Object[] objects = {1, 1L, 1.0f, 1.0d, (short) 1, (byte) 1, "1", '1'};

        for(Object object : objects) {
            testPrimitiveElement(object, object.getClass());
        }
    }


    void testPrimitiveElement(Object object, Class clazz) {
        DriveElement element = factory.createPrimitive(object);
        assertThrows(ClassCastException.class, element::getAsObject);
        assertThrows(ClassCastException.class, element::getAsElementArray);
        assertEquals(element, element.getAsElement());
        assertNull(element.getSubComponents());
        assertEquals(object, element.getPrimitiveValue());

    }

    @Test
    @Order(2)
    @DisplayName("Element Base Array Test")
    void testBaseElementArray() {
        DriveElementArray elementArray = factory.createEmptyArray();

        assertThrows(ClassCastException.class, elementArray::getAsObject);
        assertEquals(elementArray, elementArray.getAsElement());
        assertEquals(elementArray, elementArray.getAsElementArray());

        assertNotNull(elementArray.getContents());
        assertTrue(elementArray.getContents().isEmpty());
    }

    @Test
    @Order(2)
    @DisplayName("Drive Object Base Test")
    void testBaseObject() {
        DriveObject driveObject = factory.createEmptyObject();

        assertThrows(ClassCastException.class, driveObject::getAsElementArray);
        assertEquals(driveObject, driveObject.getAsElement());
        assertEquals(driveObject, driveObject.getAsObject());

        assertNotNull(driveObject.getSubComponents());
        assertTrue(driveObject.getSubComponents().isEmpty());

    }

    @Test
    @Order(3)
    @DisplayName("Element Filled Array Test")
    void testFilledElementArray() {
        DriveElementArray elementArray = factory.createEmptyArray();
        List<DriveElement> elements = new ArrayList<>();

        elements.add(factory.createPrimitive(10));
        elements.add(factory.createPrimitive("Hey Guys"));
        elements.add(factory.createPrimitive(1.4f));
        elements.add(factory.createPrimitive(4));

        {
            DriveObject object = factory.createEmptyObject();
            object.setObject("goodKey", factory.createPrimitive("Nope"));
            elements.add(object);
        }

        {
            DriveElementArray array = factory.createEmptyArray();
            array.addObject(factory.createPrimitive("Nope"));
            elements.add(array);
        }

        for(DriveElement element : elements)
            elementArray.addObject(element);

        assertIterableEquals(elements, elementArray.getContents());
    }

    @Test
    @Order(3)
    @DisplayName("Filled Drive Object Test")
    void testFilledDriveObject() {
        DriveObject driveObject = factory.createEmptyObject();
        HashMap<String, DriveElement> elements = new HashMap<>();

        elements.put("intPrim", factory.createPrimitive(10));
        elements.put("stringPrim", factory.createPrimitive("Hey Guys"));
        elements.put("floatPrim", factory.createPrimitive(1.4f));
        elements.put("intPrim2", factory.createPrimitive(4));

        {
            DriveObject object = factory.createEmptyObject();
            object.setObject("goodKey", factory.createPrimitive("Nope"));
            elements.put("subObject", object);
        }

        {
            DriveElementArray array = factory.createEmptyArray();
            array.addObject(factory.createPrimitive("Nope"));
            elements.put("subArray", array);
        }

        for(Map.Entry<String, DriveElement> element : elements.entrySet())
            driveObject.setObject(element.getKey(), element.getValue());

        assertIterableEquals(elements.entrySet(), driveObject.getSubComponents().entrySet());
    }
}
