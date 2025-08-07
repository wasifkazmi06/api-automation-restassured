package dbUtils;


import constants.UtilConstants;
import lombok.extern.slf4j.Slf4j;
import util.ReadProperties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class UPI_MySQL_DBAccessObject extends DBAccessObject{
    private static Connection conn = null;

    /**
     * create connection
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static synchronized Connection getMySqlDbConnection() throws SQLException,ClassNotFoundException {

        if(conn == null){
            String dbName = ReadProperties.testConfigMap.get(UtilConstants.UPI_DBNAME).toString();
            String dbUserName = ReadProperties.testConfigMap.get(UtilConstants.UPI_USER).toString();
            String dbPassword = ReadProperties.testConfigMap.get(UtilConstants.UPI_PASSWORD).toString();
            String dbHost = ReadProperties.testConfigMap.get(UtilConstants.UPI_HOST).toString();
            int dbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.UPI_PORT).toString());


            String dbUrl = "jdbc:mysql://" + dbHost + ":"+ dbPort +"/" + dbName;
            System.out.println(dbUrl);
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = getGenericDbConnection(dbUrl, dbUserName, dbPassword);
        }

        return conn;
    }

    /**
     *
     * @param deleteCommand
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void deleteOnMySqlDb(String deleteCommand) throws SQLException,ClassNotFoundException {
        int deleteResultCount = delete(getMySqlDbConnection(), deleteCommand);
        log.warn(" MySqlDbAccessObject: deleteOnMySqlDb - " + deleteResultCount + " Rows Effected! ");
        closeMySqlDbConnection();
    }

    /**
     *
     * @param deleteCommand
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void insertIntoMySqlDb(String deleteCommand) throws SQLException,ClassNotFoundException {
        int insertResultCount = insert(getMySqlDbConnection(), deleteCommand);
        log.warn(" MySqlDbAccessObject: insertIntoMySqlDb - " + insertResultCount + " Rows Effected! ");
        closeMySqlDbConnection();
    }

    /**
     *
     * @param updateCommand
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void updateOnMySqlDb(String updateCommand) throws SQLException,ClassNotFoundException {
        int updateResultCount = update(getMySqlDbConnection(), updateCommand);
        log.warn(" MySqlDbAccessObject: updateOnMySqlDb - " + updateResultCount + " Rows Effected! ");
        closeMySqlDbConnection();
    }

    /**
     *
     * @param selectCommand
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ResultSet selectFromMySqlDb(String selectCommand) throws SQLException,ClassNotFoundException {
        return select(getMySqlDbConnection(), selectCommand);
    }

    /***
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void closeMySqlDbConnection() throws SQLException,ClassNotFoundException  {
        closeDBConnection(getMySqlDbConnection());
    }


}
