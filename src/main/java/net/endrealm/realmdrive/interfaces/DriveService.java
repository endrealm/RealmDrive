package net.endrealm.realmdrive.interfaces;

/**
 * @author johannesjumpertz
 *
 * Defines methods of a drive service
 */
public interface DriveService {

    /**
     * Retrieves the backend of the given drive service
     * @return the used backend instance
     */
    DriveBackend getBackend();

    /**
     * Retrieves the reader used to read drive data
     * @return the reader
     */
    DriveReader getReader();

    /**
     * Retrieves the writer used to write new drive into the backend
     * @return the writer
     */
    RealmWriter getWriter();

    /**
     * Retrieves the conversion handler used to convert objects
     * @return the converter
     */
    ConversionHandler getConversionHandler();

}
