# Multithreaded JavaFX DBMS

## Einführung

Die Multithreaded JavaFX DBMS-Anwendung ist eine kleine und leichte Java-Anwendung, die JDBC und JavaFX verwendet, um auf eine SQLite-Datenbank zuzugreifen. Die Anwendung demonstriert die Verwendung und Implementierung von Multithreading in einer Datenbankanwendung und verwendet eine Model-View-Controller (MVC) Architektur, um die Anwendungsfunktionalität zu organisieren. Die Anwendung bietet eine Benutzeroberfläche, um Datenbankabfragen durchzuführen und ist für Schulprojekte oder andere kleine Anwendungen geeignet.

### Funktionsweise der Anwendung

Die Anwendung verwendet eine Model-View-Controller (MVC) Architektur. Die Model-Schicht besteht aus einer Persistence-Layer, Data Access Layer und Domain Models. Die View-Schicht besteht aus einer Presentation-Layer und die Controller-Schicht besteht aus einer Application-Layer.

Die Persistence-Layer enthält Klassen, um die Datenbankverbindung und Pooling zu verwalten. Die Data Access Layer enthält Klassen, um auf Daten in der Datenbank zuzugreifen und Daten auf Domain-Modelle abzubilden. Die Domain Models repräsentieren die Entitäten, die in der Anwendung verwendet werden.

Die Application-Layer enthält Klassen, die für die Ausführung von Abfragen, die Validierung von Benutzereingaben und die Formatierung von Ausgaben verantwortlich sind. Diese Klassen interagieren mit der Data Access Layer, um auf die Daten zuzugreifen und sie zu manipulieren.

Die Presentation-Layer enthält die MainView-Klasse, die eine Benutzeroberfläche bereitstellt, um mit der Anwendung zu interagieren.

### Verwendung der Anwendung

Die Anwendung kann verwendet werden, um Daten von einer SQLite-Datenbank abzurufen, zu aktualisieren oder zu löschen. Die Benutzeroberfläche stellt die erforderlichen Felder zur Verfügung, um die Datenbankabfragen durchzuführen. Die Anwendung verwendet Multithreading, um die Geschwindigkeit der Abfragen zu erhöhen und die Benutzerfreundlichkeit zu verbessern.

### Systemanforderungen

- Betriebssystem: Windows, Linux oder MacOS
- Java Development Kit (JDK) 20 oder höher
- SQLite-Datenbank

### Zusammenfassung

Die Multithreaded JavaFX DBMS-Anwendung ist eine kleine und leichte Java-Anwendung, die JDBC und JavaFX verwendet, um auf eine SQLite-Datenbank zuzugreifen. Die Anwendung demonstriert die Verwendung und Implementierung von Multithreading in einer Datenbankanwendung und verwendet eine Model-View-Controller (MVC) Architektur, um die Anwendungsfunktionalität zu organisieren. Die Anwendung bietet eine Benutzeroberfläche, um Datenbankabfragen durchzuführen und ist für Schulprojekte oder andere kleine Anwendungen geeignet.

## Model

### Persistence Layer

### DatabaseConnection - Klasse

Die Klasse "DatabaseConnection" dient als Schnittstelle zur Datenbank. Im Folgenden sind einige Anweisungen aufgelistet, die bei der Arbeit mit dieser Klasse hilfreich sein können:
Verbindung zur Datenbank herstellen

Zum Herstellen einer Verbindung zur Datenbank muss lediglich die Methode "getDatabaseConnection" aufgerufen werden:

```Java
Connection conn = DatabaseConnection.getDatabaseConnection();
```

Diese Methode gibt ein Connection-Objekt zurück, das für Datenbank-Operationen verwendet werden kann.
Verbindung zur Datenbank schließen

Um die Verbindung zur Datenbank zu schließen, kann die Methode "disconnectDatabaseConnection" verwendet werden:

```Java
DatabaseConnection.disconnectDatabaseConnection(conn);
```

Dabei muss das Connection-Objekt, das geschlossen werden soll, als Parameter übergeben werden.

Es ist wichtig, die Verbindung zur Datenbank nach jeder Verwendung zu schließen, um Speicherlecks und andere Probleme zu vermeiden.

### DatabaseConnectionPool - Klasse

Die Klasse DatabaseConnectionPool dient als Verbindungspool für Datenbankverbindungen. Ein Verbindungspool ist eine Ansammlung von Datenbankverbindungen, die wiederverwendet werden können, anstatt dass für jede Anfrage an die Datenbank eine neue Verbindung hergestellt wird. Dies kann dazu beitragen, die Leistung von Anwendungen zu verbessern, die häufig auf Datenbanken zugreifen.

Die Klasse DatabaseConnectionPool verfügt über die folgenden Eigenschaften und Methoden:

``` Java 
DatabaseConnectionPool(DatabaseConnection dbConnection)
```
: Ein Konstruktor, der eine Verbindung zur Datenbank als Argument erhält. Bei der Erstellung eines neuen DatabaseConnectionPool wird der Verbindungspool automatisch mit einer Anzahl von Verbindungen zur Datenbank gefüllt, die der MAX_POOL_SIZE-Konstante entspricht. Der Verbindungspool wird auch mit einem ScheduledExecutorService erstellt, der den Verbindungspool regelmäßig auf abgelaufene Verbindungen überprüft.

```Java 
populateConnectionPool(List<Connection> connectionPool, DatabaseConnection dbConnection)
```
: Eine private Methode, die den Verbindungspool mit Verbindungen zur Datenbank füllt. Die Methode wird beim Erstellen eines neuen DatabaseConnectionPool aufgerufen und füllt den Verbindungspool mit einer Anzahl von Verbindungen zur Datenbank, die der MAX_POOL_SIZE-Konstante entspricht.

``` Java 
validateConnectionPool(DatabaseConnection dbConnection):
```
 Eine private Methode, die den Verbindungspool auf abgelaufene Verbindungen überprüft und abgelaufene Verbindungen entfernt und durch neue Verbindungen zur Datenbank ersetzt.

``` Java
validateConnection(Connection connection): 
```

Eine öffentliche Methode, die überprüft, ob eine Verbindung zur Datenbank gültig ist oder nicht. Die Methode wird von validateConnectionPool aufgerufen, um abgelaufene Verbindungen zu identifizieren.

``` Java
connectionHealthCheck(Connection connection): 
```
Eine private Methode, die eine einfache SQL-Abfrage an die Datenbank sendet, um zu überprüfen, ob die Verbindung gültig ist.

``` Java
getConnectionFromPool(): 
```

Eine öffentliche Methode, die eine Verbindung zur Datenbank aus dem Verbindungspool zurückgibt. Wenn keine Verbindung im Verbindungspool verfügbar ist, wartet die Methode, bis eine Verbindung verfügbar wird.

### ThreadPool - Klasse 

### Domain Models 

### GenericEntryObject - Klasse

### TeacherEntryObject - Klasse

### Data Access Layer

### GenericDataAccessObject - Klasse

### TeacherDAO - Klasse 

## Controller 
