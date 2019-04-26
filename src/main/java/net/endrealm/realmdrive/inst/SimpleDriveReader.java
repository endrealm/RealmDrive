package net.endrealm.realmdrive.inst;

import net.endrealm.realmdrive.interfaces.DriveObject;
import net.endrealm.realmdrive.interfaces.DriveReader;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * @author johannesjumpertz
 *
 * Simple implementation of a drive reader.
 * Just does rudimentary stuff. Actually not more than grabbing the data from the backend and handing it over.
 */
public class SimpleDriveReader implements DriveReader {

    /**
     * drive service using this reader
     */
    private DriveService driveService;

    /**
     * Does the backend contain one or more objects that can be identified through the query details
     *
     * @param query query details used to search for the objects
     * @return one or more instances found
     */
    @Override
    public boolean containsObject(Query query) {
        return readObject(query) != null;
    }

    /**
     * Read the first object instance found by the specified parameters
     *
     * @param query query details used to search for the objects
     * @return first object that was found. Returns null if none is found
     */
    @Override
    public DriveObject readObject(Query query) {
        return driveService.getBackend().findOne(query);
    }

    /**
     * Read the first object instance found by the specified parameters
     *
     * @param query query details used to search for the objects
     * @param clazz  clazz to convert the result into
     * @return first object that was found. Returns null if none is found
     * @throws ClassCastException thrown if found object can not be converted into T
     */
    @Override
    public <T> T readObject(Query query, Class<T> clazz) throws ClassCastException {
        return driveService.getConversionHandler().transform(readObject(query), clazz);
    }

    /**
     * Read all object instances found by the specified parameters
     *
     * @param query query details used to search for the objects
     * @return objects that were found
     */
    @Override
    public Iterable<DriveObject> readAllObjects(Query query) {
        return driveService.getBackend().findAll(query);
    }

    /**
     * Read all object instances found by the specified parameters
     *
     * @param query quarry details used to search for the objects
     * @param clazz  clazz to convert the results into
     * @return objects that were found
     * @throws ClassCastException thrown if found objects can not be converted into T
     */
    @Override
    public <T> Iterable<T> readAllObjects(Query query, Class<T> clazz) throws ClassCastException {
        List<T> list = new ArrayList<>();
        for (DriveObject obj: readAllObjects(query)) {
            list.add(driveService.getConversionHandler().transform(obj, clazz))
;        }
        return list;
    }

    /**
     * Sets the service the reader is used by.
     *
     * @param service service the reader is used by
     */
    @Override
    public void setService(DriveService service) {
        this.driveService = service;
    }
}
