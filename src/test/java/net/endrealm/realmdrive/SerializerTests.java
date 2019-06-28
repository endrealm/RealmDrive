package net.endrealm.realmdrive;

import net.endrealm.realmdrive.factory.DriveObjectFactory;
import net.endrealm.realmdrive.inst.SimpleConversionHandler;
import net.endrealm.realmdrive.inst.SimpleDriveService;
import net.endrealm.realmdrive.interfaces.DriveBackend;
import net.endrealm.realmdrive.interfaces.DriveObject;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.model.Bar;
import net.endrealm.realmdrive.model.Baz;
import net.endrealm.realmdrive.model.Foo;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Conversion Handler Tests")
class SerializerTests {
    private final DriveObjectFactory factory;
    private final SimpleConversionHandler conversionHandler;

    SerializerTests() {
        this.conversionHandler = new SimpleConversionHandler();

        DriveBackend driveBackend = mock(DriveBackend.class);

        DriveService service = new SimpleDriveService(driveBackend, null, null, conversionHandler);
        conversionHandler.setService(service);

        conversionHandler.registerClasses(Baz.class);

        this.factory = new DriveObjectFactory(service);

    }

    @BeforeEach
    void init(TestInfo testInfo) {
        String callingTest = testInfo.getDisplayName();
        System.out.printf("Executing %s...\n", callingTest);
    }

    @Test
    @DisplayName("Serialization Test")
    @Order(1)
    void testSerialize() {
        Baz baz = new Baz("Hey", UUID.randomUUID());

        System.out.println(conversionHandler.stringify(conversionHandler.transform(baz)));
    }
}
