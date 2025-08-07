package dbUtils;

import constants.UtilConstants;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import util.ReadProperties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class Shylock_DBAccessObject extends DBAccessObject {

    private static Connection conn = null;
    public static String env = System.getProperty("env");

    /**
     * create postgresql DB connection
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static synchronized Connection  getShylockMysqlDbConnection() throws SQLException, ClassNotFoundException {
        int shylockDbPort = 0;
        String shylockDbHost = null;
        try {
            if (conn == null) {
                if (env.equalsIgnoreCase("sbox")) {
                    shylockDbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.SHYLOCK_PORT).toString());
                    shylockDbHost = ReadProperties.testConfigMap.get(UtilConstants.SHYLOCK_HOST).toString();
                } else {
                    shylockDbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.SHYLOCK_LOCAL_PORT).toString());
                    shylockDbHost = ReadProperties.testConfigMap.get(UtilConstants.SHYLOCK_LOCAL_HOST).toString();
                }
                String shylockDbName = ReadProperties.testConfigMap.get(UtilConstants.SHYLOCK_DBNAME).toString();
                String shylockDbUserName = ReadProperties.testConfigMap.get(UtilConstants.SHYLOCK_USER).toString();
                String shylockDbPassword = ReadProperties.testConfigMap.get(UtilConstants.SHYLOCK_PASSWORD).toString();

                // URL for final DB connection
                String shylockDbUrl = "jdbc:mysql://" + shylockDbHost + ":" + shylockDbPort + "/" + shylockDbName;
                conn = getGenericDbConnection(shylockDbUrl, shylockDbUserName, shylockDbPassword);
            }
        } catch (Exception connectionException) {
            Assert.assertTrue(false, "Exception while creating Shylock DB connection with message " + connectionException.getStackTrace());
        }
        return conn;
    }

    /**
     * @param selectCommand from DB
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ResultSet selectFromDb(String selectCommand) throws SQLException, ClassNotFoundException {
        ResultSet returnedDataSet = null;
        try {
            returnedDataSet = select(getShylockMysqlDbConnection(), selectCommand);
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception while fetching record from Shylock DB: " + e.getStackTrace());
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
            int insertResultCount = insert(getShylockMysqlDbConnection(), insertCommand);
            log.warn(" MySqlDbAccessObject: insertIntoMySqlDb - " + insertResultCount + " Rows Effected! ");
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception while inserting record into shylock DB: " + e.getStackTrace());
        }
    }

    /**
     * @param updateCommand
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void updateOnMySqlDb(String updateCommand) throws SQLException, ClassNotFoundException {
        try {
            int updateResultCount = update(getShylockMysqlDbConnection(), updateCommand);
            log.warn("MySqlDbAccessObject: updateOnMySqlDb - " + updateResultCount + " Rows Effected! ");
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception while updating record into shylock DB: " + e.getStackTrace());
        }
    }

    /***
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void closeMysqlDbConnection() throws SQLException, ClassNotFoundException {
        try {
            closeDBConnection(getShylockMysqlDbConnection());
            log.warn("Shylock DB connection closed successfully");
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception closing mysql Shylock DB connection: " + e.getStackTrace());
        }
    }

    /**
     * @param deleteCommand
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static int deleteOnMySqlDb(String deleteCommand) throws SQLException, ClassNotFoundException {
        int deleteResultCount = 0;
        try {
            deleteResultCount = delete(getShylockMysqlDbConnection(), deleteCommand);
            log.warn(" MySqlDbAccessObject: deleteOnMySqlDb - " + deleteResultCount + " Rows Effected! ");
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception coming while deleting row with message " + e.getMessage());
        }
        return deleteResultCount;
    }

}
