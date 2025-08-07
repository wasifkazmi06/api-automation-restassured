package dbUtils;

import constants.UtilConstants;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import util.ReadProperties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class Ocr_DBAccessObject extends DBAccessObject {

    private static Connection conn = null;
    public static String env = System.getProperty("env");

    /**
     * create postgresql DB connection
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static synchronized Connection getOcrPostgresqlDbConnection() throws SQLException, ClassNotFoundException {
        int ocrDbPort = 0;
        String ocrDbHost = null;
        try {
            if (conn == null) {
                if (env.equalsIgnoreCase("sbox")) {
                    ocrDbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.OCRDB_PORT).toString());
                    ocrDbHost = ReadProperties.testConfigMap.get(UtilConstants.OCRDB_HOST).toString();
                } else {
                    ocrDbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.OCRDB_LOCAL_PORT).toString());
                    ocrDbHost = ReadProperties.testConfigMap.get(UtilConstants.OCRDB_LOCAL_HOST).toString();
                }
                String ocrDbName = ReadProperties.testConfigMap.get(UtilConstants.OCRDB_DBNAME).toString();
                String ocrDbUserName = ReadProperties.testConfigMap.get(UtilConstants.OCRDB_USER).toString();
                String ocrDbPassword = ReadProperties.testConfigMap.get(UtilConstants.OCRDB_PASSWORD).toString();

                // URL for final DB connection
                String ocrDbUrl = "jdbc:postgresql://" + ocrDbHost + ":" + ocrDbPort + "/" + ocrDbName;
                conn = getGenericDbConnection(ocrDbUrl, ocrDbUserName, ocrDbPassword);
            }
        } catch (Exception connectionException) {
            Assert.assertTrue(false, "Exception while creating OCR DB connection " + connectionException.getStackTrace());
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
            closeDBConnection(getOcrPostgresqlDbConnection());
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception closing postgresql OCR DB connection: " + e.getStackTrace());
        }
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
            returnedDataSet = select(getOcrPostgresqlDbConnection(), selectCommand);
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
            int insertResultCount = insert(getOcrPostgresqlDbConnection(), insertCommand);

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
            int updateResultCount = update(getOcrPostgresqlDbConnection(), updateCommand);
            log.warn(" PostgreSqlDbAccessObject: updateOnPostgreSqlDb - " + updateResultCount + " Rows Effected! ");
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception while updating record into DB: " + e.getStackTrace());
        }
    }

    /**
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public static void updateOnPostgreSqlDb(PreparedStatement statement) throws SQLException, ClassNotFoundException {
        try {
            int updateResultCount = update(getOcrPostgresqlDbConnection(), statement);
            log.warn(" PostgreSqlDbAccessObject: updateOnPostgreSqlDb - " + updateResultCount + " Rows Effected! ");
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception while updating record into DB: " + e.getStackTrace());
        }
    }

    /**
     * @param deleteCommand
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static int deleteOnPostgreSqlDb(String deleteCommand) throws SQLException, ClassNotFoundException {
        int deleteResultCount = 0;
        try {
            deleteResultCount = delete(getOcrPostgresqlDbConnection(), deleteCommand);
            log.warn(" MySqlDbAccessObject: deleteOnMySqlDb - " + deleteResultCount + " Rows Effected! ");
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception coming while deleting row with message " + e.getMessage());
        }
        return deleteResultCount;
    }

    public static void createQuery(String query, ArrayList<Object> values) throws SQLException, ClassNotFoundException {
        try {
            PreparedStatement preparedStatement = getOcrPostgresqlDbConnection().prepareStatement(query);
            for (int index = 0; index < values.size(); index++) {
                Object value = values.get(index);
                if (value instanceof String) {
                    preparedStatement.setString(index + 1, (String) value);
                } else if (value instanceof Double) {
                    preparedStatement.setDouble(index + 1, (Double) value);
                } else if (value instanceof Integer) {
                    preparedStatement.setInt(index + 1, (Integer) value);
                } else if (value instanceof Date) {
                    Date date = (Date) value;
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    preparedStatement.setDate(index + 1, sqlDate);
                } else {
                    preparedStatement.setNull(index + 1, java.sql.Types.NULL);
                    Assert.assertTrue(false, "Getting error while creating queries with reason: Invalid column value");
                }
            }
            updateOnPostgreSqlDb(preparedStatement);
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception coming while creating query with message " + e.getMessage());
        }
    }
}
