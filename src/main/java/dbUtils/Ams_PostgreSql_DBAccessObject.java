package dbUtils;

import constants.UtilConstants;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import util.ReadProperties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dbUtils.DBAccessObject.*;

@Slf4j
public class Ams_PostgreSql_DBAccessObject {
    private static Connection conn = null;
    public static String env = System.getProperty("env");


    /**
     * create postgresql DB connection
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static synchronized Connection getAmsPostgresqlDbConnection() throws Exception {
        int amsDbPort = 0;
        String amsDbHost = null;
        try {
            if (conn == null) {
                if (env.equalsIgnoreCase("sbox")) {
                    amsDbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.AMS_PORT_XPRESS).toString());
                    amsDbHost = ReadProperties.testConfigMap.get(UtilConstants.AMS_HOST_XPRESS).toString();
                } else {
                    amsDbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.AMS_LOCAL_PORT).toString());
                    amsDbHost = ReadProperties.testConfigMap.get(UtilConstants.AMS_LOCAL_HOST).toString();
                }
                String amsDbName = ReadProperties.testConfigMap.get(UtilConstants.AMS_DBNAME_XPRESS).toString();
                String amsDbUserName = ReadProperties.testConfigMap.get(UtilConstants.AMS_USER_XPRESS).toString();
                String amsDbPassword = ReadProperties.testConfigMap.get(UtilConstants.AMS_PASSWORD).toString();

                // URL for final DB connection
                String amsDbUrl = "jdbc:postgresql://" + amsDbHost + ":" + amsDbPort + "/" + amsDbName;
                conn = getGenericDbConnection(amsDbUrl, amsDbUserName, amsDbPassword);
            }
        } catch (Exception e) {
            Assert.assertTrue(false, "AMS DB connection failed with message " + e.getMessage());
        }
        return conn;
    }

    /***
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void closePostgresqlDbConnection() throws SQLException, ClassNotFoundException {
        try {
            closeDBConnection(getAmsPostgresqlDbConnection());
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception closing postgresql DB connection: " + e.getStackTrace());
        }
    }

    /**
     * @param selectCommand from DB
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ResultSet selectFromDb(String selectCommand) throws Exception {
        ResultSet returnedDataSet = null;
        try {
            returnedDataSet = select(getAmsPostgresqlDbConnection(), selectCommand);
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception while fetching record from DB: " + e.getStackTrace());
        }
        return returnedDataSet;
    }

    /**
     * @param insertCommand
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void insertIntoDb(String insertCommand) throws SQLException, ClassNotFoundException {
        try {
            int insertResultCount = insert(getAmsPostgresqlDbConnection(), insertCommand);
            log.warn(" PostgreSqlDbAccessObject: insertIntoPostgreSqlDb - " + insertResultCount + " Rows Effected! ");
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception while inserting record into DB: " + e.getStackTrace());
        }
    }

    /**
     * @param updateCommand
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void updateOnPostgreSqlDb(String updateCommand) throws SQLException, ClassNotFoundException {
        try {
            int updateResultCount = update(getAmsPostgresqlDbConnection(), updateCommand);
            log.warn(" PostgreSqlDbAccessObject: updateOnPostgreSqlDb - " + updateResultCount + " Rows Effected! ");
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception while updating record into DB: " + e.getStackTrace());
        }
    }

    /***
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void closePostgreSqlDbConnection() throws Exception {
        try {
            closeDBConnection(getAmsPostgresqlDbConnection());
            log.warn("AMS DB connection closed successfully");
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception closing postgresql AMS DB connection: " + e.getStackTrace());
        }
    }
}
