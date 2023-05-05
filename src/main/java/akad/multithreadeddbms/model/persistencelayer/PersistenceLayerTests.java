package akad.multithreadeddbms.model.persistencelayer;

import java.sql.Connection;
import java.sql.SQLException;

public class PersistenceLayerTests {

    public static void main(String[] args) throws SQLException, InterruptedException {
        System.out.println("Starting Test");
        testGetDatabaseConnection();
        testDatabaseConnectionPool();
        testThreadPool();

    }

    private static void testGetDatabaseConnection() throws SQLException {
        Connection conn = DatabaseConnection.getDatabaseConnection();
        if (conn == null) {
            System.err.println("getDatabaseConnection returned null");
            System.exit(1);
        }
        if (conn.isClosed()) {
            System.err.println("Connection is closed");
            System.exit(1);
        }
        conn.close();
        System.out.println("DbConnection Test finished");

    }

    private static void testDatabaseConnectionPool() throws SQLException {
        DatabaseConnection dbConnection = new DatabaseConnection();
        DatabaseConnectionPool dbPool = new DatabaseConnectionPool(dbConnection);

        for (int i = 0; i < 10; i++) {
            Connection connection = dbPool.getConnectionFromPool();

            if (DatabaseConnectionPool.validateConnection(connection)) {
                System.out.println("Connection Nr: " + i + " is valid");
            }

            DatabaseConnectionPool.releaseConnection(connection);
        }
        System.out.println("DbConnPool Test finished");
    }

    private static void testThreadPool() throws InterruptedException {
        ThreadPool newPool = new ThreadPool();

        for (int i = 0; i < 10; i++) {
            Thread newThread = newPool.getThreadFromPool();
            if (newThread != null) {
                System.out.println("Thread not null");
            } else {System.out.println("Thread null");}
        }
    }
}

