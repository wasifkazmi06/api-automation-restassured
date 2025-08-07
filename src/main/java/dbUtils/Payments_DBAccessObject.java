package dbUtils;

import constants.UtilConstants;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import util.ReadProperties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class Payments_DBAccessObject extends DBAccessObject {
    private static Connection conn = null;
    public static String env = System.getProperty("env");

    /**
     * create postgresql DB connection
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static synchronized Connection getPaymentPostgresqlDbConnection() throws SQLException, ClassNotFoundException {
        int paymentDbPort = 0;
        String paymentDbHost = null;
        try {
            if (conn == null) {
                if (env.equalsIgnoreCase("sbox")) {
                    paymentDbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.PAYMENT_PORT).toString());
                    paymentDbHost = ReadProperties.testConfigMap.get(UtilConstants.PAYMENT_HOST).toString();
                } else {
                    paymentDbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.PAYMENT_LOCAL_PORT).toString());
                    paymentDbHost = ReadProperties.testConfigMap.get(UtilConstants.PAYMENT_LOCAL_HOST).toString();
                }
                String paymentDbName = ReadProperties.testConfigMap.get(UtilConstants.PAYMENT_DBNAME).toString();
                String paymentDbUserName = ReadProperties.testConfigMap.get(UtilConstants.PAYMENT_USER).toString();
                String paymentDbPassword = ReadProperties.testConfigMap.get(UtilConstants.PAYMENT_PASSWORD).toString();
                String paymentDbUrl = "jdbc:postgresql://" + paymentDbHost + ":" + paymentDbPort + "/" + paymentDbName;
                conn = getGenericDbConnection(paymentDbUrl, paymentDbUserName, paymentDbPassword);
            }
        } catch (Exception e) {
            Assert.assertTrue(false, "Payment DB connection failed with message " + e.getMessage());
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

            returnedDataSet = select(getPaymentPostgresqlDbConnection(), selectCommand);
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception while fetching record from Payment DB: " + e.getStackTrace());
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
            int insertResultCount = insert(getPaymentPostgresqlDbConnection(), insertCommand);
            log.warn(" postgresqlDbAccessObject: insertIntopostgresqlDb - " + insertResultCount + " Rows Effected! ");
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception while inserting record into Payment DB: " + e.getStackTrace());
        }
    }

    /**
     * @param updateCommand
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void updateOnDb(String updateCommand) throws SQLException, ClassNotFoundException {
        try {
            int updateResultCount = update(getPaymentPostgresqlDbConnection(), updateCommand);
            log.warn("postgresqlDbAccessObject: updateOnPostgresqlDb - " + updateResultCount + " Rows Effected! ");
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception while updating record into Payment DB: " + e.getStackTrace());
        }
    }

    public static int updateOnDbandReturnCount(String updateCommand) throws SQLException, ClassNotFoundException {
        int updateResultCount = 0;
        try {
            updateResultCount = update(getPaymentPostgresqlDbConnection(), updateCommand);
            log.warn("postgresqlDbAccessObject: updateOnPostgresqlDb - " + updateResultCount + " Rows Affected! ");
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception while updating record into Payment DB: " + e.getMessage());
        }
        return updateResultCount;
    }

    /***
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void closeMyDbConnection() throws SQLException, ClassNotFoundException {
        try {
            closeDBConnection(getPaymentPostgresqlDbConnection());
            log.warn("Payment DB connection closed successfully");
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception closing Payment DB connection: " + e.getStackTrace());
        }
    }

    /**
     * @param deleteCommand
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static int deleteOnMyDb(String deleteCommand) throws SQLException, ClassNotFoundException {
        int deleteResultCount = 0;
        try {
            deleteResultCount = delete(getPaymentPostgresqlDbConnection(), deleteCommand);
            log.warn(" postgresqlDbAccessObject: deleteOnpostgresqlDb - " + deleteResultCount + " Rows Effected! ");
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception coming while deleting row with message " + e.getMessage());
        }
        return deleteResultCount;
    }
}
