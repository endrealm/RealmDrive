package net.endrealm.realmdrive.utils;

import net.endrealm.realmdrive.exceptions.NotAPrimitiveTypeException;
import net.endrealm.realmdrive.exceptions.ObjectReadOnlyException;
import net.endrealm.realmdrive.factory.DriveObjectFactory;
import net.endrealm.realmdrive.interfaces.DriveElementArray;
import net.endrealm.realmdrive.interfaces.DriveObject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Map;

/**
 * Contains Bson dependant methods. Calling these without Bson as a dependency will cause errors
 */
public class BsonUtils {
    /**
     * <b>Warning:</b> If your project does not have Bson as a dependency, calling this will throw errors
     * Converts a Json document into a drive object
     *
     * @param jsonObject json to be converted
     * @param objectFactory object factory used to instantiate new objects
     * @return the drive object
     *
     */
    public static DriveObject unStringify(Document jsonObject, DriveObjectFactory objectFactory) {

        DriveObject statisticsObject = objectFactory.createEmptyObject();

        try {
            for(Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                Object element = entry.getValue();

                //skip id objects
                if(element instanceof ObjectId)
                    continue;

                //handle objects
                if(element instanceof Document) {
                    try {
                        statisticsObject.setObject(entry.getKey(), unStringify((Document) element, objectFactory));
                    } catch (ObjectReadOnlyException e) {
                        e.printStackTrace();
                    }
                    //handle lists
                } else if(element instanceof ArrayList) {
                    ArrayList basicBSONList = (ArrayList) element;
                    DriveElementArray array = objectFactory.createEmptyArray();
                    for(Object obj : basicBSONList) {
                        //skip id objects
                        if(obj instanceof ObjectId)
                            continue;
                        //handle objects
                        if(obj instanceof Document) {
                            array.addObject(unStringify((Document) obj, objectFactory));
                            //handle primitives
                        } else if(obj != null) {
                            array.addPrimitive(obj);
                        } else {
                            array.addObject(null);
                        }
                    }
                    statisticsObject.setObject(entry.getKey(), array);
                    //handle primitives
                } else if(element != null) {
                    statisticsObject.setPrimitive(entry.getKey(), element);
                } else {
                    statisticsObject.setObject(entry.getKey(), null);
                }
            }
        } catch (ObjectReadOnlyException | NotAPrimitiveTypeException e) {
            e.printStackTrace();
        }


        return statisticsObject;
    }
}
