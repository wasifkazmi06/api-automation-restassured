package dbUtils;


import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import io.qameta.allure.Step;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.Properties;

public class DBAccessObject {

        /**
         * establish Db connection
         * @param dbUrl
         * @param dbUserName
         * @param dbPassword
         * @return
         * @throws SQLException
         * @throws ClassNotFoundException
         */
        public static synchronized Connection getGenericDbConnection(String dbUrl, String dbUserName, String dbPassword) throws SQLException, ClassNotFoundException {
                return DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        }

        /**
         * select data and returns resultset
         * @param connection
         * @param Query
         * @return
         * @throws SQLException
         */
        @Step
        public static synchronized ResultSet select(Connection connection, String Query) throws SQLException {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(Query);
                return resultSet;
        }

        /**
         * update Db
         * @param connection
         * @param Query
         * @return
         * @throws SQLException
         */
        public static synchronized int update(Connection connection, String Query) throws SQLException {
                Statement statement = connection.createStatement();
                int resultSet = statement.executeUpdate(Query);
                return resultSet;
        }

        /**
         * update Db
         * @param connection
         * @return
         * @throws SQLException
         */
        public static synchronized int update(Connection connection,PreparedStatement statement) throws SQLException {
                //Statement statement = connection.createStatement();
                int resultSet = statement.executeUpdate();
                return resultSet;
        }

        /**
         * delete data method
         * @param connection
         * @param query
         * @return
         * @throws SQLException
         */

        public static int delete(Connection connection, String query) throws SQLException {
                return update(connection, query);
        }

        /**
         * insert method
         * @param connection
         * @param query
         * @return
         * @throws SQLException
         */
        public static int insert(Connection connection, String query) throws SQLException {
                return update(connection, query);
        }

        /**
         * close DB Connection
         * @param connection
         * @throws SQLException
         */
        public static void closeDBConnection(Connection connection) throws SQLException {
                if (connection != null && !connection.isClosed()) {
                        connection.close();
                }
        }

}

