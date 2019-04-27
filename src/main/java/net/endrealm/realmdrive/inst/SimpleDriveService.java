package net.endrealm.realmdrive.inst;

import net.endrealm.realmdrive.interfaces.*;


/**
 * @author johannesjumpertz
 *
 * Simple implementation of a statistics service
 */
public class SimpleDriveService implements DriveService {

    /**
     * The drive reader that should be used to read values
     */
    private final DriveReader reader;

    /**
     * The drive writer that should be used to write values
     */
    private final RealmWriter writer;

    /**
     * The conversion handler that contains various methods to convert objects
     */
    private final ConversionHandler conversionHandler;

    /**
     * The drive backend used to save data. Should only be used if both {@link DriveReader} and {@link RealmWriter} can not be used
     */
    private final DriveBackend driveBackend;

    /**
     * Instantiates a new simple drive service
     *
     * @param driveBackend the backend to be used
     * @param reader the reader to be used
     * @param writer the writer to be used
     */
    public SimpleDriveService(DriveBackend driveBackend, DriveReader reader, RealmWriter writer, ConversionHandler conversionHandler) {
        this.reader = reader;
        this.writer = writer;
        this.conversionHandler = conversionHandler;
        this.driveBackend = driveBackend;
    }

    /**
     * Retrieves the backend of the given drive service
     *
     * @return the used backend instance
     */
    @Override
    public DriveBackend getBackend() {
        return driveBackend;
    }

    /**
     * Retrieves the reader used to read drive data
     *
     * @return the reader
     */
    @Override
    public DriveReader getReader() {
        return reader;
    }

    /**
     * Retrieves the writer used to write new drive into the backend
     *
     * @return the writer
     */
    @Override
    public RealmWriter getWriter() {
        return writer;
    }

    /**
     * Retrieves the conversion handler used to convert objects
     *
     * @return the converter
     */
    @Override
    public ConversionHandler getConversionHandler() {
        return conversionHandler;
    }
}
