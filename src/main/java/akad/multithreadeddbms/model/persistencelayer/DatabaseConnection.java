// Import der notwendigen Java-SQL-Klassen
package akad.multithreadeddbms.model.persistencelayer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.sql.*;

public class DatabaseConnection {

    // Hier wird die URL der Datenbank angegeben
    //String url = "jdbc:sqlite:src/main/resources/EduDB.db";

    // Hier wird das Connection-Objekt definiert
    Connection connection;

    // Privater Konstruktor, um direkte Instanziierung zu verhindern
    public DatabaseConnection() {
        try {
            // Hier wird der JDBC-Treiber registriert
            Class.forName("org.sqlite.JDBC");

            // Hier wird der InputStream für die Datenbankdatei aus dem Klassenpfad abgerufen
            InputStream inputStream = getClass().getResourceAsStream("/EduDB.db");

            // Hier wird eine temporäre Datei erstellt, um den Inhalt des InputStreams zu kopieren
            File tempFile = File.createTempFile("EduDB", ".db");
            tempFile.deleteOnExit();

            // Hier wird der InputStream in die temporäre Datei kopiert
            try (OutputStream outputStream = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            // Hier wird die URL der Datenbank mit dem Pfad zur temporären Datei erstellt
            String url = "jdbc:sqlite:" + tempFile.getAbsolutePath();

            // Hier wird eine Verbindung zur Datenbank hergestellt
            connection = DriverManager.getConnection(url);
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            // Hier wird eine RuntimeException ausgelöst, wenn eine Verbindung nicht hergestellt werden kann
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
