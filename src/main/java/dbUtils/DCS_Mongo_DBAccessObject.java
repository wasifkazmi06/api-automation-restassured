package dbUtils;


import com.mongodb.client.MongoDatabase;

public class DCS_Mongo_DBAccessObject extends MongoDBAccessObject {
    public static final String dcs_db_uri = "mongodb://lazycard:nv4WVgKSrgqJHYD6@mu-ddb-lazycard-sbox.lazypay.net:27017/?authSource=admin&authMechanism=SCRAM-SHA-1";

    public static synchronized MongoDatabase getMongoDbConnection() {
        return getMongoDbConnection(dcs_db_uri, "dynamic_config");
    }
}
