package akad.multithreadeddbms.model.dataaccesslayer;

import akad.multithreadeddbms.model.domainmodels.*;
import akad.multithreadeddbms.model.persistancelayer.DatabaseConnectionPool;

import java.sql.*;
import java.util.*;

public class TeacherDAO extends GenericDataAccessObject {

    TeacherEntryObject newTeacherObject;
    public TeacherDAO(Connection connection, TeacherEntryObject newTeacherObject) {
        super(connection);
        this.newTeacherObject = newTeacherObject;
        /*
        implement validation ->
        validateObject(newStudentObject);
        validateConnection(newConnection); */
    }

    public void insertTeacher() throws SQLException {
        String query = "INSERT INTO teacher (name, course_name) VALUES (?, ?)";
        PreparedStatement statement = dbconnection.prepareStatement(query);

        String name = newTeacherObject.getName();
        String courseName = newTeacherObject.getCourseName();

        statement.setString(1, name);
        statement.setString(2, courseName);
        statement.executeUpdate();
        statement.close();
        DatabaseConnectionPool.releaseConnection(dbconnection);
    }

    public TeacherEntryObject retrieveTeacherById(int id) throws SQLException {
        String query = "SELECT * FROM teacher WHERE id = ?";
        PreparedStatement statement = dbconnection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        String retrievedName = resultSet.getString("name");
        String retrievedCourseName = resultSet.getString("course_name");
        resultSet.close();
        statement.close();

        TeacherEntryObject teacher = new TeacherEntryObject(retrievedName, retrievedCourseName);

        DatabaseConnectionPool.releaseConnection(dbconnection);

        return teacher;
    }

    public TeacherEntryObject retrieveTeacherByName(String name) throws SQLException {
        String query = "SELECT * FROM teacher WHERE name = ?";
        PreparedStatement statement = dbconnection.prepareStatement(query);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        int retrievedId = resultSet.getInt("id");
        String retrievedCourseName = resultSet.getString("course_name");
        resultSet.close();
        statement.close();

        TeacherEntryObject teacher = new TeacherEntryObject(name, retrievedCourseName);

        DatabaseConnectionPool.releaseConnection(dbconnection);

        return teacher;
    }
}

