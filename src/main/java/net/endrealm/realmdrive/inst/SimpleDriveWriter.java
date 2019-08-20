package net.endrealm.realmdrive.inst;

import net.endrealm.realmdrive.interfaces.DriveObject;
import net.endrealm.realmdrive.interfaces.DriveService;
import net.endrealm.realmdrive.interfaces.DriveWriter;
import net.endrealm.realmdrive.query.Query;
import net.endrealm.realmdrive.utils.ThreadUtils;

import java.util.function.Consumer;

/**
 * @author johannesjumpertz
 *
 * Simple implementation of the drive writer
 * Just does rudimentary stuff. Actually not more than writing the data to the backend.
 */
public class SimpleDriveWriter implements DriveWriter {

    /**
     * drive service using this writer
     */
    private DriveService driveService;

    @Override
    public void write(DriveObject element) {
        write(element, new Query().build());
    }

    @Override
    public void writeAsync(DriveObject element, Runnable onFinish) {
        writeAsync(element, new Query().build(), onFinish);
    }

    @Override
    public void write(Object object) {
        write(object, new Query());
    }

    @Override
    public void writeAsync(Object object, Runnable onFinish) {
        writeAsync(object, new Query().build(), onFinish);

    }

    @Override
    public void write(DriveObject element, Query query) {
        driveService.getBackend().write(element, query);
    }

    @Override
    public void writeAsync(DriveObject element, Query query, Runnable onFinish) {
        ThreadUtils.createNewThread(
                () -> {
                    write(element, query);
                    onFinish.run();
                }
        );
    }

    @Override
    public void write(Object object, Query query) {
        write(driveService.getConversionHandler().transform(object), query);
    }

    @Override
    public void writeAsync(Object object, Query query, Runnable onFinish) {
        ThreadUtils.createNewThread(
                () -> {
                    write(object, query);
                    onFinish.run();
                }
        );
    }

    /**
     * Write an entry to the backend.
     *
     * @param element       element to be inserted
     * @param overwrite     should existing parameters be overwritten? If not a new value will be inserted.
     * @param queryDetails used two find duplicates
     */
    @Override
    public void write(DriveObject element, boolean overwrite, Query queryDetails) {
        driveService.getBackend().writeReplace(element, queryDetails);
    }

    /**
     * Write an entry to the backend.
     *
     * @param element      element to be inserted
     * @param overwrite    should existing parameters be overwritten? If not a new value will be inserted.
     * @param queryDetails used two find duplicates
     * @param onFinish     invoked upon finish
     */
    @Override
    public void writeAsync(DriveObject element, boolean overwrite, Query queryDetails, Runnable onFinish) {
        ThreadUtils.createNewThread(
                () -> {
                    write(element, overwrite, queryDetails);
                    onFinish.run();
                }
        );
    }

    /**
     * Write an entry to the backend.
     *
     * @param object       element to be inserted, will be transformed automatically
     * @param overwrite    should existing parameters be overwritten? If not a new value will be inserted.
     * @param queryDetails used two find duplicates
     */
    @Override
    public void write(Object object, boolean overwrite, Query queryDetails) {
        write(driveService.getConversionHandler().transform(object), overwrite, queryDetails);
    }

    /**
     * Write an entry to the backend.
     *
     * @param object       element to be inserted, will be transformed automatically
     * @param overwrite    should existing parameters be overwritten? If not a new value will be inserted.
     * @param queryDetails used two find duplicates
     * @param onFinish     invoked upon finish
     */
    @Override
    public void writeAsync(Object object, boolean overwrite, Query queryDetails, Runnable onFinish) {
        ThreadUtils.createNewThread(
                () -> {
                    write(object, overwrite, queryDetails);
                    onFinish.run();
                }
        );
    }

    @Override
    public void replace(DriveObject element, Query queryDetails) {
        driveService.getBackend().replace(element, queryDetails);
    }

    @Override
    public void replaceAsync(DriveObject element, Query queryDetails, Runnable onFinish) {
        ThreadUtils.createNewThread(() -> {
           replace(element, queryDetails);
           onFinish.run();
        });
    }

    @Override
    public void replace(Object object, Query queryDetails) {
        replace(driveService.getConversionHandler().transform(object), queryDetails);
    }

    @Override
    public void replaceAsync(Object object, Query queryDetails, Runnable onFinish) {
        replaceAsync(driveService.getConversionHandler().transform(object), queryDetails, onFinish);

    }

    /**
     * Deletes entries matching the query from the backend
     *
     * @param queryDetails used to find targets to delete
     * @param amount        amount of entries to be deleted
     */
    @Override
    public void delete(Query queryDetails, int amount) {
        if(amount < 0)
            driveService.getBackend().deleteAll(queryDetails);
        else if(amount > 0)
            for(int i = 0; i < amount; i++)
                driveService.getBackend().delete(queryDetails);
    }

    /**
     * Deletes entries matching the query from the backend
     *
     * @param queryDetails used to find targets to delete
     * @param amount       amount of entries to be deleted
     * @param onFinish     invoked upon finis
     */
    @Override
    public void deleteAsync(Query queryDetails, int amount, Runnable onFinish) {
        ThreadUtils.createNewThread(
                () -> {
                    delete(queryDetails, amount);
                    onFinish.run();
                }
        );
    }

    /**
     * Sets the service the writer is used by.
     *
     * @param service service the writer is used by
     */
    @Override
    public void setService(DriveService service) {
        this.driveService = service;
    }
}
