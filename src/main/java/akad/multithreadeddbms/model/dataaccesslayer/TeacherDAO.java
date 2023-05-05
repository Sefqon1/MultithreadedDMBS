package akad.multithreadeddbms.model.dataaccesslayer;

import akad.multithreadeddbms.model.domainmodels.*;
import akad.multithreadeddbms.model.persistencelayer.DatabaseConnectionPool;

import java.sql.*;

public class TeacherDAO extends GenericDataAccessObject {

    private TeacherEntryObject insertedTeacher;
    private TeacherEntryObject retrievedTeacher;
    public TeacherDAO(Connection connection){
        super(connection);

        /* if (!DatabaseConnectionPool.validateConnection(this.connection)) {
            this.connection = DatabaseConnectionPool.getConnectionFromPool(); }*/
        /*
        implement validation ->
        validateObject(newStudentObject);
        validateConnection(newConnection); */
    }

    /*Getters and Setters*/
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

    /*Db actions*/

    public void insertTeacher(TeacherEntryObject newTeacherObject) {
            try {
                setInsertedTeacher(newTeacherObject);
                String query = "INSERT INTO Teacher (name, subject) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);

                String name = insertedTeacher.getName();
                String subject = insertedTeacher.getSubject();
                System.out.println(name + " " + subject);

                statement.setString(1, name);
                statement.setString(2, subject);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DatabaseConnectionPool.releaseConnection(connection);
            }
        }

    public Runnable retrieveTeacherById(int id) {
        return () -> {
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
        };
    }

    public Runnable retrieveTeacherByName(String name) {
        return () -> {
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
        };
    }
}


