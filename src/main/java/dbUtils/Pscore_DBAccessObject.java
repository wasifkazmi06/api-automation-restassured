package dbUtils;

import constants.UtilConstants;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import util.ReadProperties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class Pscore_DBAccessObject extends DBAccessObject {

    private static Connection conn = null;
    public static String env = System.getProperty("env");


    /**
     * create postgresql DB connection
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static synchronized Connection getPscorePostgresqlDbConnection() throws Exception {
        int pscoreDbPort = 0;
        String pscoreDbHost = null;
        try {
            if (conn == null) {
                if (env.equalsIgnoreCase("sbox")) {
                    pscoreDbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.PSCORE_PORT).toString());
                    pscoreDbHost = ReadProperties.testConfigMap.get(UtilConstants.PSCORE_HOST).toString();
                } else {
                    pscoreDbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.PSCORE_LOCAL_PORT).toString());
                    pscoreDbHost = ReadProperties.testConfigMap.get(UtilConstants.PSCORE_LOCAL_HOST).toString();
                }
                String pscoreDbName = ReadProperties.testConfigMap.get(UtilConstants.PSCORE_DBNAME).toString();
                String pscoreDbUserName = ReadProperties.testConfigMap.get(UtilConstants.PSCORE_USER).toString();
                String pscoreDbPassword = ReadProperties.testConfigMap.get(UtilConstants.PSCORE_PASSWORD).toString();

                // URL for final DB connection
                String pscoreDbUrl = "jdbc:postgresql://" + pscoreDbHost + ":" + pscoreDbPort + "/" + pscoreDbName;
                conn = getGenericDbConnection(pscoreDbUrl, pscoreDbUserName, pscoreDbPassword);
            }
        } catch (Exception e) {
            Assert.assertTrue(false, "Pscore DB connection failed with message " + e.getMessage());
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
            closeDBConnection(getPscorePostgresqlDbConnection());
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
            returnedDataSet = select(getPscorePostgresqlDbConnection(), selectCommand);
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
            int insertResultCount = insert(getPscorePostgresqlDbConnection(), insertCommand);
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
    public static void updateOnMySqlDb(String updateCommand) throws SQLException, ClassNotFoundException {
        try {
            int updateResultCount = update(getPscorePostgresqlDbConnection(), updateCommand);
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
            closeDBConnection(getPscorePostgresqlDbConnection());
            log.warn("Pscore DB connection closed successfully");
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception closing postgresql pscore DB connection: " + e.getStackTrace());
        }
    }
}
