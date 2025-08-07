package dbUtils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import constants.UtilConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import util.ReadProperties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class KYC_MySQLSsh_DBAccessObject extends DBAccessObject{
    private static Connection conn = null;
    public static String env = System.getProperty("env");

    /**
     * create connection
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static synchronized Connection getMySqlDbConnection() throws SQLException,ClassNotFoundException {

        if(conn == null){
            String dbName = ReadProperties.testConfigMap.get(UtilConstants.KYC_DBNAME).toString();
            String dbUserName = ReadProperties.testConfigMap.get(UtilConstants.UPI_USER).toString();
            String dbPassword = ReadProperties.testConfigMap.get(UtilConstants.UPI_PASSWORD).toString();
            String dbHost = ReadProperties.testConfigMap.get(UtilConstants.KYC_HOST).toString();
            int dbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.UPI_PORT).toString());

            String sshUser= ReadProperties.testConfigMap.get(UtilConstants.SSH_USER).toString();
            String sshPassword= ReadProperties.testConfigMap.get(UtilConstants.SSH_PASSWORD).toString();
            int sshPort=Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.SSH_PORT).toString());
            int localPort=Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.KYC_LOCAL_PORT).toString());
            String sshHost= ReadProperties.testConfigMap.get(UtilConstants.SSH_HOST).toString();

          // KYC_MySQLSsh_DBAcceaaObject.doSshTunnel(sshUser, sshPassword, sshHost, sshPort, dbHost, localPort, dbPort);

            String dbUrl = "jdbc:mysql://"+ dbHost+ ":" + localPort +"/" + dbName;
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = getGenericDbConnection(dbUrl, dbUserName, dbPassword);
        }

        return conn;
    }

    public static synchronized Connection getKycMysqlConnectionForXpress() throws SQLException,ClassNotFoundException {
        int kycDbPort = 0;
        String kycDbHost = null;
        try {
            if (conn == null) {
                if (env.equalsIgnoreCase("sbox")) {
                    kycDbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.KYC_PORT).toString());
                    kycDbHost = ReadProperties.testConfigMap.get(UtilConstants.KYC_HOST).toString();
                } else {
                    kycDbPort = Integer.parseInt(ReadProperties.testConfigMap.get(UtilConstants.KYC_PORT_LOCAL).toString());
                    kycDbHost = ReadProperties.testConfigMap.get(UtilConstants.KYC_LOCAL_HOST).toString();
                }
                String kycDbName = ReadProperties.testConfigMap.get(UtilConstants.KYC_DBNAME).toString();
                String kycDbUserName = ReadProperties.testConfigMap.get(UtilConstants.KYC_USER).toString();
                String kycDbPassword = ReadProperties.testConfigMap.get(UtilConstants.KYC_PASSWORD).toString();

                // URL for final DB connection
                String kycDbUrl = "jdbc:mysql://" + kycDbHost + ":" + kycDbPort + "/" + kycDbName;
                conn = getGenericDbConnection(kycDbUrl, kycDbUserName, kycDbPassword);
            }
        } catch (Exception e) {
            Assert.assertTrue(false, "KYC DB connection failed with message " + e.getMessage());
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
    public static void deleteOnMySqlDbForXpress(String deleteCommand) throws SQLException,ClassNotFoundException {
        int deleteResultCount = delete(getKycMysqlConnectionForXpress(), deleteCommand);
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
     * @param selectCommand
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ResultSet selectFromMySqlDbForXpress(String selectCommand) throws SQLException,ClassNotFoundException {
        return select(getKycMysqlConnectionForXpress(), selectCommand);
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