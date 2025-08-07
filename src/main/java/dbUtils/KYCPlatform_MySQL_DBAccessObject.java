package dbUtils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import constants.UtilConstants;
import lombok.SneakyThrows;
import util.ReadProperties;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KYCPlatform_MySQL_DBAccessObject extends DBAccessObject{

    private static Connection conn = null;
    public static synchronized Connection getMySqlDbConnection() throws SQLException,ClassNotFoundException {

        if(conn == null){
            String dbName = ReadProperties.testConfigMap.get(UtilConstants.KYCPlatform_DBName).toString();
            String dbUserName = ReadProperties.testConfigMap.get(UtilConstants.UPI_USER).toString();
            String dbPassword = ReadProperties.testConfigMap.get(UtilConstants.UPI_PASSWORD).toString();
            String dbHost = ReadProperties.testConfigMap.get(UtilConstants.UPI_HOST).toString();
            int dbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.UPI_PORT).toString());
            String dbUrl = "jdbc:mysql://" + dbHost + ":"+ dbPort +"/" + dbName;
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
    }

    /**
     *
     * @param insertCommand
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void insertIntoMySqlDb(String insertCommand) throws SQLException,ClassNotFoundException {
        int insertResultCount = insert(getMySqlDbConnection(), insertCommand);
        log.warn(" MySqlDbAccessObject: insertIntoMySqlDb - " + insertResultCount + " Rows Effected! ");
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

    @SneakyThrows
    private static void doSshTunnel(String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort )
    {
        try {
            final JSch jsch = new JSch();
            Session session = jsch.getSession( strSshUser, strSshHost, nSshPort );
            session.setPassword( strSshPassword );
            final Properties config = new Properties();
            config.put( "StrictHostKeyChecking", "no" );
            session.setConfig( config );
            session.connect();
            session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
        }catch( JSchException j){
            j.printStackTrace();
        }
    }
}