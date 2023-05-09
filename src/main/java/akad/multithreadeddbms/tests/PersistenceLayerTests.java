package akad.multithreadeddbms.tests;

import akad.multithreadeddbms.model.persistencelayer.DatabaseConnection;
import akad.multithreadeddbms.model.persistencelayer.DatabaseConnectionPool;
import akad.multithreadeddbms.model.persistencelayer.ThreadPool;

import java.sql.Connection;
import java.sql.SQLException;

public class PersistenceLayerTests {


    public static boolean testGetDatabaseConnection() throws SQLException {
        Connection conn = DatabaseConnection.getDatabaseConnection();
        if (conn == null) {
            System.err.println("getDatabaseConnection returned null");
            return false;
        }
        if (conn.isClosed()) {
            System.err.println("Connection is closed");
            return false;
        }
        conn.close();
        return true;
    }

    public static boolean testDatabaseConnectionPool() throws SQLException {
        DatabaseConnection dbConnection = new DatabaseConnection();
        DatabaseConnectionPool dbPool = new DatabaseConnectionPool(dbConnection);
        int i = 0;
        for (int j = 0; j < 10; j++) {
            Connection connection = dbPool.getConnectionFromPool();
            if (DatabaseConnectionPool.validateConnection(connection)) {
                i++;
            }
            DatabaseConnectionPool.releaseConnection(connection);
        }
        return i == 10;
    }


    public static boolean testThreadPool() throws InterruptedException {
        ThreadPool newPool = new ThreadPool();
        int i = 0;
        for (int j = 0; j < 10; j++) {
            Thread newThread = newPool.getThreadFromPool();
            if (newThread != null) {
                i++;
            }
        }
        return i == 10;
    }

}

