package net.endrealm.realmdrive.inst;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.endrealm.realmdrive.factory.DriveObjectFactory;
import net.endrealm.realmdrive.interfaces.*;
import net.endrealm.realmdrive.query.Query;
import net.endrealm.realmdrive.utils.BsonUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.BasicBSONList;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author johannesjumpertz
 *
 * A backend implementation for the MongoDB system
 */
public class MongoBackend implements DriveBackend {

    private MongoClient mongoClient;
    private DriveService driveService;
    private MongoDatabase database;
    private MongoCollection collection;

    /**
     * Creates a connection to the backend
     *
     * @param hostURL  host url used when establishing the connection
     * @param username the username used when establishing the connection
     * @param password the password used when establishing the connection
     * @param database database used when connecting. (Some backends might have this in their url)
     * @param table default table used. Depending on the type a default table might be needed
     */
    @Override
    public void connect(String hostURL, String username, String password, String database, String table) {
        assert hostURL != null;

        this.mongoClient = new MongoClient(new MongoClientURI(hostURL, MongoClientOptions.builder().heartbeatFrequency(1000)));
        assert database != null;
        this.database = mongoClient.getDatabase(database);
        assert table != null;
        this.collection = this.database.getCollection(table);
    }

    /**
     * Sets the service the backend is used by.
     * @param service service the backend is used by
     */
    @Override
    public void setService(DriveService service) {
        this.driveService = service;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void write(DriveObject driveObject, Query query) {
        collection.insertOne(toMongoDocument(driveObject));
    }

    /**
     * Send a query directly to the mongo database
     *
     * @param query the raw query used to send the message
     * @return query response
     */
    public Iterable sendRawQuery(Bson query) {
        return collection.find(query);
    }

    /**
     * Executes a raw query
     * @param query backend dependant. For MongoDB see {@link com.mongodb.QueryBuilder}
     * @return query response
     */
    @Deprecated
    @Override
    public Iterable rawQuery(Object query) {

        if(!(query instanceof QueryBuilder))
            return null;

        return null;//TODO implement
    }

    /**
     * Prepares to use a specific entity. This is required for some databases (e.g. MySQL) to work.
     * It will automatically try to generate and modify tables to fit the entities.
     *
     * @param clazz the class of the entity to save
     */
    @Override
    public void prepareEntity(Class<?> clazz) {
        // Does nothing as mongo does not require predefined structures
    }

    /**
     * Reads one object matching the query parameters
     * @param queryDetails the query to use
     * @return object empty if none found
     */
    @Override
    public DriveObject findOne(Query queryDetails) {
        Document query = readQuery(queryDetails);

        MongoCollection collection = readCollectionFromQuery(queryDetails);

        FindIterable iterable = collection.find(query);
        Document result = (Document) iterable.first();

        if(result == null)
            return null;

        return BsonUtils.unStringify(result, new DriveObjectFactory(driveService));

    }

    private MongoCollection readCollectionFromQuery(Query queryDetails) {
        MongoDatabase database =
                queryDetails.getDatabaseName() == null ?
                        this.database :
                        mongoClient.getDatabase(queryDetails.getDatabaseName());

        return queryDetails.getTableName() == null ?
                this.collection :
                database.getCollection(queryDetails.getTableName());
    }

    /**
     * Read all objects matching the query parameters
     *
     * @param queryDetails the query to use
     * @return empty list if not found
     */
    @Override
    public List<DriveObject> findAll(Query queryDetails) {
        Document query = readQuery(queryDetails);
        FindIterable iterable = readCollectionFromQuery(queryDetails).find(query);
        ArrayList<DriveObject> result = new ArrayList<>();
        for(Object item : iterable) {
            Document document = (Document) item;
            result.add(BsonUtils.unStringify(document, new DriveObjectFactory(driveService)));
        }

        return result;
    }

    /**
     * Interprets a query detail instance to a document
     *
     * @param query the query
     * @return query document
     */
    @SuppressWarnings("unchecked")
    private Document readQuery(Query query) {

        BasicDBObject dbObject = BasicDBObject.parse(query.toJson());

        return new Document(dbObject.toMap());
    }

    /**
     * Converts a drive object into a mongo db document
     *
     * @param statisticsObject the drive object to convert
     * @return the converted mongo db document
     */
    private Document toMongoDocument(DriveObject statisticsObject) {
        Document dbObject = new Document();

        for(Map.Entry<String, DriveElement> entry : statisticsObject.getSubComponents().entrySet()) {
            DriveElement element = entry.getValue();

            if(element.getPrimitiveValue() != null) {
                dbObject.append(entry.getKey(), element.getPrimitiveValue());
            } else {
                if(element instanceof DriveObject)
                    dbObject.append(entry.getKey(), toMongoDocument(element.getAsObject()));
                else
                    dbObject.append(entry.getKey(), toMongoList(element.getAsElementArray()));
            }

        }

        return dbObject;
    }

    /**
     * Converts a statistics array into a mongo readable list
     *
     * @param array array to be converted
     * @return mongo list
     */
    private BasicBSONList toMongoList(DriveElementArray array) {
        BasicBSONList basicBSONList = new BasicBSONList();

        for(DriveElement element : array.getContents()) {
            if(element.getPrimitiveValue() != null) {
                basicBSONList.add(element.getPrimitiveValue());
            } else {
                if(element instanceof DriveObject)
                    basicBSONList.add(toMongoDocument(element.getAsObject()));
                else
                    basicBSONList.add(toMongoList(element.getAsElementArray()));
            }
        }

        return basicBSONList;
    }


    /**
     * Removes one element equal to the query details and adds the element in place
     *
     * @param element       element to insert
     * @param queryDetails used to filter for deletion
     */
    @Override
    public void writeReplace(DriveObject element, Query queryDetails) {
        delete(queryDetails);
        write(element, queryDetails);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void replace(DriveObject element, Query query) {
        readCollectionFromQuery(query).replaceOne(readQuery(query), toMongoDocument(element));
    }

    /**
     * Deletes all entries matched by the query
     *
     * @param queryDetails the query details used to filter
     */
    @Override
    public void deleteAll(Query queryDetails) {
        readCollectionFromQuery(queryDetails).deleteMany(readQuery(queryDetails));
    }

    /**
     * Deletes an object from the backend if found
     *
     * @param queryDetails the query details used to filter
     */
    @Override
    public void delete(Query queryDetails) {
        readCollectionFromQuery(queryDetails).deleteOne(readQuery(queryDetails));
    }
}
