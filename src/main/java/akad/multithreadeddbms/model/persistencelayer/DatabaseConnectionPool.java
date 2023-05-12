package akad.multithreadeddbms.model.persistencelayer;

import java.sql.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DatabaseConnectionPool {
    private static final int MAX_POOL_SIZE = 10; // Maximale Anzahl an Verbindungen zur Datenbank
    private static final long DELAY = 6000; // Zeitintervall für die Überprüfung von Verbindungen
    private static final long TIMEOUT = 6000; // Zeitintervall, nachdem eine Verbindung als "abgelaufen" gilt
    private static List<Connection> connectionPool; // Liste der Verbindungen zur Datenbank

    //Nimmt DBConnection als Argument für Dependency Injection
    public DatabaseConnectionPool(DatabaseConnection dbConnection) throws SQLException, ClassNotFoundException {
        // Hier wird die Verbindungsliste initialisiert
        connectionPool = new ArrayList<>(MAX_POOL_SIZE);
        // Hier werden die Verbindungen zur Datenbank erstellt und der Verbindungsliste hinzugefügt
        populateConnectionPool(connectionPool, dbConnection);
        // ScheduledExecutorService für die periodische Überprüfung von Verbindungen
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(MAX_POOL_SIZE);
        executorService.scheduleAtFixedRate(() -> {
            try {
                validateConnectionPool(dbConnection);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }, DELAY, TIMEOUT, TimeUnit.MILLISECONDS);
        // Hier wird die periodische Überprüfung von Verbindungen gestartet
    }

    // Diese Methode erstellt Verbindungen zur Datenbank und fügt sie der Verbindungsliste hinzu.
    private synchronized void populateConnectionPool(List<Connection> connectionPool, DatabaseConnection dbconnection) throws ClassNotFoundException {
        int connectionsAdded = 0;
        // Hier wird gewartet, bis die maximale Anzahl an Verbindungen zur Datenbank erreicht wird
        while(connectionsAdded < MAX_POOL_SIZE) {
            // Hier wird eine neue Verbindung zur Datenbank erstellt und der Verbindungsliste hinzugefügt
            connectionPool.add(DatabaseConnection.getDatabaseConnection());
            connectionsAdded++;
        }
    }

    // Diese Methode überprüft die Verbindungsliste auf geschlossene Verbindungen
    private void validateConnectionPool(DatabaseConnection dbConnection) throws ClassNotFoundException {
        // Hier werden alle Verbindungen in der Verbindungsliste überprüft
        for (Connection connection : connectionPool) {
            try {
                // Hier wird überprüft, ob eine Verbindung geschlossen ist oder nicht
                if (!validateConnection(connection)) {
                    // Hier wird die Verbindung geschlossen und aus der Verbindungsliste entfernt
                    connection.close();
                    connectionPool.remove(connection);
                    // Hier wird eine neue Verbindung zur Datenbank erstellt und der Verbindungsliste hinzugefügt
                    if (connectionPool.size() < MAX_POOL_SIZE) {
                        connectionPool.add(DatabaseConnection.getDatabaseConnection());
                    }
                }
            } catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    // Diese Methode überprüft, ob eine Verbindung zur Datenbank geschlossen ist oder nicht
    public static boolean validateConnection(Connection connection) {
        try {
            // Hier wird überprüft, ob eine Verbindung geschlossen ist oder nicht
            if (connection.isClosed() || !connectionHealthCheck(connection)) {
                return false;
            }
            } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    // Diese Methode überprüft, ob eine Verbindung zur Datenbank besteht oder nicht
    private static boolean connectionHealthCheck(Connection connection) {

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT 1");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // Diese Methode gibt eine Verbindung zur Datenbank aus dem Pool zurück
    public synchronized Connection getConnectionFromPool() throws SQLException {
        // Hier wird gewartet, bis eine Verbindung verfügbar wird
        while (connectionPool.isEmpty()) {
            try {
                // Hier wird gewartet, bis eine Verbindung verfügbar wird
                wait();
            } catch (InterruptedException e) {
                // Hier wird die InterruptedException in eine SQLException umgewandelt
                Thread.currentThread().interrupt();
                throw new SQLException(e);
            }
        }
        // Hier wird die letzte Verbindung aus der Verbindungsliste entfernt und ausgegeben
        return connectionPool.remove(connectionPool.size() - 1);
    }

    // Diese Methode schließt alle Verbindungen zur Datenbank
    public static void closeConnectionPool() throws SQLException {
        for (Connection connection : connectionPool) {
            connection.close();
        }
    }

    // Diese Methode gibt eine Verbindung zurück in den Pool
    public static void releaseConnection(Connection connection) {

        synchronized (connectionPool){
            // Hier wird die Verbindung in die Verbindungsliste zurückgegeben
            connectionPool.add(connection);
            // Hier werden alle Threads benachrichtigt, die auf eine Verbindung warten
            connectionPool.notifyAll();
        }

    }
}

