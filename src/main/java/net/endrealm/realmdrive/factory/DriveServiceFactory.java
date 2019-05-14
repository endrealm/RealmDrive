package net.endrealm.realmdrive.factory;

import net.endrealm.realmdrive.inst.*;
import net.endrealm.realmdrive.interfaces.*;
import net.endrealm.realmdrive.utils.DriveSettings;

/**
 * @author johannesjumpertz
 *
 * Factory to create drive services
 */
public class DriveServiceFactory {

    /**
     * Get a drive service
     *
     * @param settings the settings used to build the drive service
     * @return a drive service
     */
    public DriveService getDriveService(DriveSettings settings) {

        assert settings.getType() != null;

        DriveBackend backend = getDriveBackend(settings.getType());
        DriveWriter writer = new SimpleDriveWriter();
        DriveReader reader = new SimpleDriveReader();
        ConversionHandler conversion = new SimpleConversionHandler();

        DriveService driveService = new SimpleDriveService(backend, reader, writer, conversion);

        backend.connect(settings.getHostURL(), settings.getUsername(), settings.getPassword(), settings.getDatabase(), settings.getTable());

        backend.setService(driveService);
        writer.setService(driveService);
        reader.setService(driveService);
        conversion.setService(driveService);

        return driveService;
    }

    /**
     * Selects a backend for the given type
     * @param type type backend should be
     * @return the backend. Defaulted to {@link MongoBackend}
     */
    private DriveBackend getDriveBackend(DriveSettings.BackendType type) {
        switch (type) {
            case MONGO_DB: return new MongoBackend();
            //Add further backend types
            default: return new MongoBackend();
        }
    }

}
