package net.endrealm.realmdrive;

import net.endrealm.realmdrive.factory.DriveObjectFactory;
import net.endrealm.realmdrive.inst.SimpleConversionHandler;
import net.endrealm.realmdrive.inst.SimpleDriveService;
import net.endrealm.realmdrive.interfaces.DriveBackend;
import net.endrealm.realmdrive.interfaces.DriveObject;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.model.Bar;
import net.endrealm.realmdrive.model.Choo;
import net.endrealm.realmdrive.model.Foo;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Conversion Handler Tests")
class ConversionTests {
    private final DriveObjectFactory factory;
    private final SimpleConversionHandler conversionHandler;

    ConversionTests() {
        this.conversionHandler = new SimpleConversionHandler();

        DriveBackend driveBackend = mock(DriveBackend.class);

        DriveService service = new SimpleDriveService(driveBackend, null, null, conversionHandler);
        conversionHandler.setService(service);

        this.factory = new DriveObjectFactory(service);

    }

    @BeforeEach
    void init(TestInfo testInfo) {
        String callingTest = testInfo.getDisplayName();
        System.out.printf("Executing %s...\n", callingTest);
    }

    @Test
    @DisplayName("Convert to Object Test")
    @Order(1)
    void testConvertToObject() {
        Bar bar = new Bar('a',"test",1,2,3.2f,1.54d, (short)1);
        Foo foo = new Foo('a',"test",1,2,3.2f,1.54d, bar);
        conversionHandler.registerClasses(Foo.class, Bar.class);
        DriveObject fooObject = conversionHandler.transform(foo);
        assertEquals(foo, conversionHandler.transform(fooObject, Foo.class));
        assertEquals(foo, conversionHandler.transformAutomatically(fooObject));
        assertEquals(foo, conversionHandler.transform(fooObject, Choo.class));

    }
}
