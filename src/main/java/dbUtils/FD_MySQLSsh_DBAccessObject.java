package dbUtils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import constants.UtilConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import util.ReadProperties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class FD_MySQLSsh_DBAccessObject extends DBAccessObject{
    private static Connection conn = null;

    /**
     * create connection
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static synchronized Connection getMySqlDbConnection() throws SQLException,ClassNotFoundException {

        if(conn == null){
            String dbName = ReadProperties.testConfigMap.get(UtilConstants.FD_DBNAME).toString();
            String dbUserName = ReadProperties.testConfigMap.get(UtilConstants.FD_USER).toString();
            String dbPassword = ReadProperties.testConfigMap.get(UtilConstants.FD_HPASSWORD).toString();
            String dbHost = ReadProperties.testConfigMap.get(UtilConstants.FD_HOST).toString();
            int dbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.LAZYCARD_PORT).toString());


            String sshUser= ReadProperties.testConfigMap.get(UtilConstants.FD_SSH_USER).toString();
            String sshPassword= ReadProperties.testConfigMap.get(UtilConstants.FD_SSH_PASSWORD).toString();
         //   int sshPort=Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.SSH_PORT).toString());
            int localPort=Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.SSH_LOCAL_PORT).toString());
            String sshHost= ReadProperties.testConfigMap.get(UtilConstants.FD_SSH_HOST).toString();




            FD_MySQLSsh_DBAccessObject.doSshTunnel(sshUser, sshPassword, sshHost, dbHost, localPort, dbPort);
            String dbUrl = "jdbc:mysql://localhost:"+ localPort +"/" + dbName;
            //  String dbUrl = "jdbc:mysql://mu-rds-lazycard-sbox-rw.lazypay.net:3306/lazycard";
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

    /***
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void closeMySqlDbConnection() throws SQLException,ClassNotFoundException  {
        closeDBConnection(getMySqlDbConnection());
    }

    @SneakyThrows
    private static void doSshTunnel(String strSshUser, String strSshPassword, String strSshHost, String strRemoteHost, int nLocalPort, int nRemotePort )
    {
        final JSch jsch = new JSch();
        Session session = jsch.getSession( strSshUser, strSshHost );
        session.setPassword( strSshPassword );

        final Properties config = new Properties();
        config.put( "StrictHostKeyChecking", "no" );
        session.setConfig( config );
        session.connect();
        session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
    }
}