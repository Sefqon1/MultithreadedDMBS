package akad.multithreadeddbms.model.persistancelayer;
import java.sql.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class DatabaseConnectionPool {
    private static final int MAX_POOL_SIZE = 10;
    private static final long DELAY = 6000; // Zeitintervall für die Überprüfung von Verbindungen
    private static final long TIMEOUT = 6000; // Zeitintervall, nachdem eine Verbindung als "abgelaufen" gilt
    private List<Connection> connectionPool; // Liste der Verbindungen zur Datenbank
    private ScheduledExecutorService executorService; // ScheduledExecutorService für die periodische Überprüfung von Verbindungen

    public DatabaseConnectionPool() throws SQLException {
        connectionPool = new ArrayList<>(MAX_POOL_SIZE);
        // Hier werden die Verbindungen zur Datenbank erstellt und der Verbindungsliste hinzugefügt
        populateConnectionPool(connectionPool);
        executorService = Executors.newScheduledThreadPool(MAX_POOL_SIZE);
        executorService.scheduleAtFixedRate(this::validateConnections , DELAY, TIMEOUT);
        // Hier wird die periodische Überprüfung von Verbindungen gestartet
    }

    private void populateConnectionPool(List<Connection> connectionPool){
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            try {
                // Hier wird eine neue Verbindung zur Datenbank erstellt und der Verbindungsliste hinzugefügt
                this.connectionPool.add(DatabaseConnection.getDatabaseConnection());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void validateConnections() {
        List<Connection> expiredConnections = new ArrayList<>();
        // Hier werden alle Verbindungen in der Verbindungsliste überprüft
        for (Connection connection : connectionPool) {
            try {
                // Wenn die Verbindung geschlossen ist, wird sie der Liste abgelaufener Verbindungen hinzugefügt
                if (connection.isClosed()) {
                    expiredConnections.add(connection);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Wenn es mehr abgelaufene Verbindungen als verfügbare Verbindungen gibt, wird der Verbindungspool zurückgesetzt
        if (expiredConnections.size() >= MAX_POOL_SIZE) {
            connectionPool.clear();
            populateConnectionPool(connectionPool);
            expiredConnections.clear();
        } else {
            // Andernfalls werden alle abgelaufenen Verbindungen aus der Verbindungsliste entfernt und geschlossen
            for (Connection connection : expiredConnections) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                connectionPool.remove(connection);
            }
        }
    }

    // Diese Methode gibt eine Verbindung zur Datenbank aus dem Pool zurück
    public synchronized Connection getConnectionFromPool() throws SQLException {
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
        // Hier wird die letzte Verbindung aus der Verbindungsliste entfernt und zurückgegeben
        return connectionPool.remove(connectionPool.size() - 1);
    }


    // Diese Methode gibt eine Verbindung zurück in den Pool
    public synchronized void releaseConnection(Connection connection) {
        // Hier wird die Verbindung in die Verbindungsliste zurückgegeben
        connectionPool.add(connection);
        // Hier werden alle Threads benachrichtigt, die auf eine Verbindung warten
        notifyAll();
    }
}

/*
To Do:

    Überprüfen, ob die Konstanten DELAY und TIMEOUT geeignete Werte haben. Die aktuellen Werte sind 6000 Millisekunden oder 6 Sekunden. Diese Werte könnten möglicherweise je nach Anwendung angepasst werden.

    Überprüfen, ob die Konstante MAX_POOL_SIZE eine geeignete Größe für die Anzahl der Verbindungen zum Datenbankserver hat. Die aktuelle Größe beträgt 10. Möglicherweise müssen Sie diese Anzahl basierend auf der Anwendung und der Datenbankserver-Verfügbarkeit anpassen.

    Überprüfen, ob der ScheduledExecutorService ordnungsgemäß verwendet wird und ob die Implementierung der validateConnections()-Methode korrekt ist. Die Methode scheint derzeit Verbindungen zu entfernen, die bereits geschlossen sind, anstatt Verbindungen zu schließen, die seit dem letzten Validierungszeitraum nicht verwendet wurden.

    Überprüfen Sie, ob der populateConnectionPool()-Methode möglicherweise eine Ausnahme auslöst, die die Verbindungsliste nicht vollständig bevölkert. Sie sollten überlegen, wie Sie damit umgehen können, wenn nicht alle Verbindungen erfolgreich erstellt werden können.

    Überprüfen Sie, ob die getConnectionFromPool()-Methode ordnungsgemäß blockiert, wenn keine Verbindungen verfügbar sind, und ob sie ordnungsgemäß eine Verbindung aus dem Pool zurückgibt. Sie sollten sicherstellen, dass alle Threads, die auf eine Verbindung warten, ordnungsgemäß benachrichtigt werden.

    Überprüfen Sie, ob die releaseConnection()-Methode ordnungsgemäß funktioniert und ob sie alle Threads benachrichtigt, die auf eine Verbindung warten. Sie sollten auch sicherstellen, dass freigegebene Verbindungen ordnungsgemäß wieder in den Pool aufgenommen werden.

    Überprüfen Sie, ob alle Ausnahmen in der Implementierung ordnungsgemäß abgefangen und behandelt werden.
 */