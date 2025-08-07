package dbUtils;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import constants.UtilConstants;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import util.ReadProperties;

import static com.mongodb.client.model.Filters.eq;

@Slf4j
public class Lazycard_Mongo_DBAccessObject {


    public static synchronized MongoCollection getMongocollection(String databaseNeame,String collectionName)
    {
        String host = ReadProperties.testConfigMap.get(UtilConstants.LAZYCARD_MONGO_HOST).toString();
        MongoClient mongoClient = new MongoClient( host, 27017 );
        MongoDatabase database = mongoClient.getDatabase(databaseNeame);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        return collection;
    }


    public static Document getFirstMatchingDocument(String databaseNeame,String collectionName,String fieldName,Integer value)
    {
        Document myDoc = (Document)getMongocollection(databaseNeame,collectionName).find(eq(fieldName, value)).first();
        return myDoc;
    }
    public static Document getFirstMatchingDocument(String databaseNeame,String collectionName,Bson query)
    {
        Document myDoc = (Document)getMongocollection(databaseNeame,collectionName).find(query).first();
        return myDoc;
    }


    public static void removeCollection(String databaseNeame,String collectionName,Bson query)
    {

        try {
            DeleteResult result = getMongocollection(databaseNeame, collectionName).deleteMany(query);
            if (result.wasAcknowledged()) {
                log.info("Document deleted successfully \nNo of Document(s) Deleted : "
                        + result.getDeletedCount());
            }
        } catch (MongoException e) {
            log.error("Exception occurred while delete Many Document : " + e, e);
        }
    }


}
