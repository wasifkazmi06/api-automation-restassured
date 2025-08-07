package dbUtils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

public class MongoDBAccessObject {
    private static MongoDatabase database = null;


    protected static synchronized MongoDatabase getMongoDbConnection(String URI, String databaseName) {
        if (database == null) {
            MongoClientURI mongoClientURI = new MongoClientURI(URI);
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            database = mongoClient.getDatabase(databaseName);
        }
        return database;
    }

    /**
     * @param collection
     * @param key
     * @param value
     * @param include
     * @return
     */
    public static synchronized Document selectFromMongoDb(String collection, String key, String value, String include) {
        Document document = database.getCollection(collection).find(eq(key, value)).projection(fields(include(include), excludeId())).first();
        return document;
    }

    /**
     * @param collection
     * @param key
     * @param value
     * @return document
     */
    public static synchronized Document selectFromMongoDb(String collection, String key, String value) {
        Document document = database.getCollection(collection).find(eq(key, value)).first();
        return document;
    }

    public static synchronized Document selectFromMongoDb(String collection, String key, ObjectId value) {
        Document document = database.getCollection(collection).find(eq(key, value)).first();
        return document;
    }

}
