package neo.dcs;

import dbUtils.DCS_Mongo_DBAccessObject;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.Timestamp;

@Slf4j
public class DynamicConfigSupportData {


    public static final String clientName = "DCS_Automation_Client" + new Timestamp(System.currentTimeMillis()).getTime();
    public static final String verticalName = "DCS_Automation_Vertical" + new Timestamp(System.currentTimeMillis()).getTime();
    ;
    public static String configName = "testKey01";
    public static String configValue = "testAutomationValue";


    public boolean verifyRegisteredVertical(String verticalID) {
        DCS_Mongo_DBAccessObject.getMongoDbConnection();
        ObjectId _id = new ObjectId(verticalID);
        Document val = DCS_Mongo_DBAccessObject.selectFromMongoDb("client_vertical", "_id", _id);
        log.info("result is " + val.get("name"));
        boolean result = val.get("name").toString().equalsIgnoreCase(verticalName);
        log.info("result is " + result);
        return result;
    }

    public boolean verifyRegisteredClient(String clientID) {
        DCS_Mongo_DBAccessObject.getMongoDbConnection();
        Document val = DCS_Mongo_DBAccessObject.selectFromMongoDb("client", "_id", clientID);
        log.info("result is " + val.get("name"));
        boolean result = val.get("name").toString().equalsIgnoreCase(clientName);
        log.info("result is " + result);
        return result;
    }

    public boolean verifyUpdateClient(String clientID, String verticalId) {
        DCS_Mongo_DBAccessObject.getMongoDbConnection();
        Document val = DCS_Mongo_DBAccessObject.selectFromMongoDb("client", "_id", clientID);
        log.info("result is " + val.get("name"));
        boolean nameUpdateResult = val.get("name").toString().equalsIgnoreCase(clientName + "_update");
        log.info("result is " + nameUpdateResult);
        boolean verticalUpdateResult = val.get("vertical_id").toString().equals(verticalId);
        log.info("verticalUpdateResult is " + val.get("vertical_id").toString());
        boolean clientUpdateStatus = nameUpdateResult && verticalUpdateResult;
        return clientUpdateStatus;
    }
}
