package net.endrealm.realmdrive.factory;

import net.endrealm.realmdrive.inst.MongoBackend;
import net.endrealm.realmdrive.inst.SimpleDriveReader;
import net.endrealm.realmdrive.inst.SimpleDriveService;
import net.endrealm.realmdrive.inst.SimpleRealmWriter;
import net.endrealm.realmdrive.interfaces.DriveBackend;
import net.endrealm.realmdrive.interfaces.DriveReader;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.interfaces.RealmWriter;
import net.endrealm.realmdrive.utils.DriveSettings;

/**
 * @author johannesjumpertz
 *
 * Factory to create drive services
 */
public class DriveServiceFactory {

    /**
     * Get a drive service
     * @return a drive service
     */
    public DriveService getDriveService(DriveSettings settings) {

        assert settings.getType() != null;

        DriveBackend backend = getDriveBackend(settings.getType());
        RealmWriter writer = new SimpleRealmWriter();
        DriveReader reader = new SimpleDriveReader();

        DriveService driveService = new SimpleDriveService(backend, reader, writer);

        backend.connect(settings.getHostURL(), settings.getUsername(), settings.getPassword(), settings.getDatabase(), settings.getTable());

        backend.setService(driveService);
        writer.setService(driveService);
        reader.setService(driveService);

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
