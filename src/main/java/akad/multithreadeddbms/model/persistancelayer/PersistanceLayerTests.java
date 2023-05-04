package akad.multithreadeddbms.model.persistancelayer;

import java.sql.Connection;
import java.sql.SQLException;

public class PersistanceLayerTests {

    public static void main(String[] args) throws SQLException {
        System.out.println("Starting Test");
        testGetDatabaseConnection();

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
        System.out.println("Test finished");

    }
}

