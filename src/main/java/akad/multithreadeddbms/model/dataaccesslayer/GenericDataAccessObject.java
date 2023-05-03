package akad.multithreadeddbms.model.dataaccesslayer;

import akad.multithreadeddbms.model.domainmodels.*;
import akad.multithreadeddbms.model.persistancelayer.DatabaseConnectionPool;

import java.sql.*;
import java.util.*;

public class GenericDataAccessObject {
    Connection dbconnection;
    GenericEntryObject newObject;
    public GenericDataAccessObject(Connection connection) {
        this.dbconnection = connection;
    }

    public void insertObject(GenericEntryObject genericObject) throws SQLException {
        String query = "INSERT INTO genericobject (name, class) VALUES (?, ?)";
        PreparedStatement statement = dbconnection.prepareStatement(query);

        String name = genericObject.name; //create getter/setter

        statement.setString(1, name);
        statement.executeUpdate();
        statement.close();
        DatabaseConnectionPool.releaseConnection(dbconnection);
    }

    public GenericEntryObject retrieveById(int id) throws SQLException {
        String query = "SELECT * FROM EduDB WHERE id = ?";
        PreparedStatement statement = dbconnection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
          String retrievedName = resultSet.getString("name");


        resultSet.close();
        statement.close();

        GenericEntryObject genericObject = new GenericEntryObject(retrievedName);

        DatabaseConnectionPool.releaseConnection(dbconnection);
        
        return genericObject;
    }

    public GenericEntryObject retrieveByName(String name) throws SQLException {
        String query = "SELECT * FROM EduDB WHERE name = ?";
        PreparedStatement statement = dbconnection.prepareStatement(query);
        statement.setInt(1, name);
        ResultSet resultSet = statement.executeQuery();
        int retrievedId = resultSet.getInt("id");


        resultSet.close();
        statement.close();

        GenericEntryObject genericObject = new GenericEntryObject(name);


        DatabaseConnectionPool.releaseConnection(dbconnection);

        return genericObject;
    }
}
