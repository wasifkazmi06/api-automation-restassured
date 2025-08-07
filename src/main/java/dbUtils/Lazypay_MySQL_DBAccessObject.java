package dbUtils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import constants.UtilConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import util.ReadProperties;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class Lazypay_MySQL_DBAccessObject extends DBAccessObject{

    private static Connection conn = null;

    /**
     * create connection
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static synchronized Connection getMySqlDbConnection() throws SQLException,ClassNotFoundException {

        String dbUrl;

        if(conn == null){
            String dbName = ReadProperties.testConfigMap.get(UtilConstants.LAZYPAY_DBNAME).toString();
            String dbUserName = ReadProperties.testConfigMap.get(UtilConstants.LAZYPAY_USER).toString();
            String dbPassword = ReadProperties.testConfigMap.get(UtilConstants.LAZYPAY_PASSWORD).toString();
            String dbHost = ReadProperties.testConfigMap.get(UtilConstants.LAZYPAY_HOST).toString();
            int dbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.LAZYPAY_PORT).toString());

            dbUrl = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

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
     * @param deleteCommand
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void insertIntoMySqlDb(String deleteCommand) throws SQLException,ClassNotFoundException {
        int insertResultCount = insert(getMySqlDbConnection(), deleteCommand);
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

    /**
     *
     * @param filePath
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void loadDataDump(String filePath) throws SQLException, ClassNotFoundException, IOException {

        SQLScriptRunner runner = new SQLScriptRunner(getMySqlDbConnection(), false, false);
        runner.runScript(new BufferedReader(new FileReader(filePath)));

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
        final JSch jsch = new JSch();
        Session session = jsch.getSession( strSshUser, strSshHost, nSshPort );
        session.setPassword( strSshPassword );

        final Properties config = new Properties();
        config.put( "StrictHostKeyChecking", "no" );
        session.setConfig( config );
        session.connect();
        session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
    }

}
