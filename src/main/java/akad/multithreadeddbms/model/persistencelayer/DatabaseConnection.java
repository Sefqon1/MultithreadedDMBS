// Import der notwendigen Java-SQL-Klassen
package akad.multithreadeddbms.model.persistencelayer;
import java.sql.*;

public class DatabaseConnection {

    // Hier wird die URL der Datenbank angegeben
    String url = "jdbc:sqlite:src/main/resources/EduDB.db";

    // Hier wird das Connection-Objekt definiert
    Connection connection;

    // Privater Konstruktor, um direkte Instanziierung zu verhindern
    public DatabaseConnection() {
        try {
            // Hier wird der JDBC-Treiber registriert
            Class.forName("org.sqlite.JDBC");
            // Hier wird eine Verbindung zur Datenbank hergestellt
            connection = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException ex) {
            // Hier wird eine RuntimeException ausgelöst, wenn eine Verbindung nicht hergestellt werden kann
            System.out.println("Verbindung nicht hergestellt");
            throw new RuntimeException(ex);
        }
    }

    // Diese Methode gibt die Verbindung zur Datenbank zurück
    public static Connection getDatabaseConnection() {
        // Hier wird eine neue Instanz der DatabaseConnection-Klasse erstellt
        DatabaseConnection dbConnection = new DatabaseConnection();
        // Hier wird die Verbindung zur Datenbank abgerufen
        Connection conn = dbConnection.connection;
        // Hier wird die Verbindung auf null gesetzt, damit sie nicht versehentlich geschlossen wird
        dbConnection.connection = null;
        // Hier wird die Verbindung zur Datenbank zurückgegeben
        return conn;
    }

    // Diese Methode schließt die Verbindung zur Datenbank
    public void disconnectDatabaseConnection(Connection conn) {
        try {
            // Hier wird die Verbindung zur Datenbank geschlossen
            conn.close();
        } catch (SQLException ex) {
            // Hier wird eine RuntimeException ausgelöst, wenn die Verbindung nicht geschlossen werden kann
            throw new RuntimeException(ex);
        }
    }
}