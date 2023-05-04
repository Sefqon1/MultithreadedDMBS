package akad.multithreadeddbms.model.dataaccesslayer;

import akad.multithreadeddbms.model.domainmodels.*;
import akad.multithreadeddbms.model.persistancelayer.DatabaseConnectionPool;

import java.sql.*;
import java.util.*;

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

    public Runnable insertTeacher(TeacherEntryObject newTeacherObject) {
        return () -> {
            try {
                this.insertedTeacher = newTeacherObject;
                String query = "INSERT INTO teacher (name, course_name) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);

                String name = insertedTeacher.getName();
                String courseName = insertedTeacher.getCourseName();


                statement.setString(1, name);
                statement.setString(2, courseName);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DatabaseConnectionPool.releaseConnection(connection);
            }
        };
    }

    public Runnable retrieveTeacherById(int id) {
        return () -> {
            try {
                String query = "SELECT * FROM teacher WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                String retrievedName = resultSet.getString("name");
                String retrievedCourseName = resultSet.getString("course_name");
                resultSet.close();
                statement.close();

                retrievedTeacher = new TeacherEntryObject(retrievedName, retrievedCourseName);

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
                String query = "SELECT * FROM teacher WHERE name = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, name);
                ResultSet resultSet = statement.executeQuery();
                int retrievedId = resultSet.getInt("id");
                String retrievedCourseName = resultSet.getString("course_name");
                resultSet.close();
                statement.close();

                retrievedTeacher = new TeacherEntryObject(name, retrievedCourseName);

            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                DatabaseConnectionPool.releaseConnection(connection);
            }
        };
    }
}

/* To Do:
* Convert Methods into Runnable 
* */

