package akad.multithreadeddbms.model.dataaccesslayer;

import akad.multithreadeddbms.model.domainmodels.*;
import akad.multithreadeddbms.model.persistencelayer.DatabaseConnectionPool;

import java.sql.*;

public class TeacherDAO extends GenericDataAccessObject {

    private TeacherEntryObject insertedTeacher;
    private TeacherEntryObject retrievedTeacher;
    private boolean insertionStatus;

    public TeacherDAO(Connection connection){
        super(connection);
    }

    // Getters und Setter
    public synchronized void setInsertedTeacher(TeacherEntryObject teacher) {
        this.insertedTeacher = teacher;
    }
    public synchronized TeacherEntryObject getInsertedTeacher() {
        return this.insertedTeacher;
    }
    public synchronized void setRetrievedTeacher(TeacherEntryObject teacher) {
        this.retrievedTeacher = teacher;
    }
    public synchronized TeacherEntryObject getRetrievedTeacher() {
        return this.retrievedTeacher;
    }
    public synchronized boolean getInsertionStatus() {
        return insertionStatus;
    }
    private synchronized void setInsertionStatus(boolean insertionStatus) {
        this.insertionStatus = insertionStatus;
    }

    // Datenbankaktionen
    // Hier wird ein neuer Lehrer in die Datenbank eingefügt
    public void insertTeacher(TeacherEntryObject newTeacherObject) {
        boolean localInsertionStatus = false;
            try {
                setInsertedTeacher(newTeacherObject);
                String query = "INSERT INTO Teacher (name, subject) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);

                String name = insertedTeacher.getName();
                String course = insertedTeacher.getCourse();
                statement.setString(1, name);
                statement.setString(2, course);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    localInsertionStatus = true;
                }
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DatabaseConnectionPool.releaseConnection(connection);
            }
            setInsertionStatus(localInsertionStatus);
    }

    // Hier wird ein Eintrag in der Datenbank mit der Id gesucht.
    public TeacherEntryObject retrieveTeacherById(int id) {
        try {
            String query = "SELECT * FROM Teacher WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            String retrievedName = resultSet.getString("name");
            String retrievedSubject = resultSet.getString("subject");
            resultSet.close();
            statement.close();

            setRetrievedTeacher(new TeacherEntryObject(retrievedName, retrievedSubject));


        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DatabaseConnectionPool.releaseConnection(connection);
        }
        return retrievedTeacher;
    }

    // Hier wird ein Eintrag in der Datenbank mit dem Namen gesucht.
    public TeacherEntryObject retrieveTeacherByName(String name) {
        try {
            String query = "SELECT * FROM Teacher WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            int retrievedId = resultSet.getInt("id");
            String retrievedSubject = resultSet.getString("subject");
            resultSet.close();
            statement.close();

            setRetrievedTeacher(new TeacherEntryObject(name, retrievedSubject));

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DatabaseConnectionPool.releaseConnection(connection);
        }
        return retrievedTeacher;
    }

}


