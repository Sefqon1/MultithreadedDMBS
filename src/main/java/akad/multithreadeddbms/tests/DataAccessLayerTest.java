package akad.multithreadeddbms.tests;
import akad.multithreadeddbms.model.dataaccesslayer.TeacherDAO;
import akad.multithreadeddbms.model.domainmodels.TeacherEntryObject;
import akad.multithreadeddbms.model.persistencelayer.DatabaseConnection;

import java.sql.Connection;

public class DataAccessLayerTest {

    public static boolean testTeacherDAOSetUp() throws ClassNotFoundException {
        Connection connection = DatabaseConnection.getDatabaseConnection();
        TeacherDAO testDAO = new TeacherDAO(connection);

        return testDAO != null;
    }

    public static boolean testTeacherDAOInsertion() throws ClassNotFoundException {
        Connection connection = DatabaseConnection.getDatabaseConnection();
        TeacherDAO testDAO = new TeacherDAO(connection);
        TeacherEntryObject newTeacher = new TeacherEntryObject("Test Teacher", "Test Course");
        testDAO.insertTeacher(newTeacher);
        return testDAO.getInsertionStatus();
    }

    public static boolean testTeacherDAOGetTeacherById() throws ClassNotFoundException {
        Connection connection = DatabaseConnection.getDatabaseConnection();
        TeacherDAO testDAO = new TeacherDAO(connection);
        TeacherEntryObject retrievedTeacher = testDAO.retrieveTeacherById(1);
        return retrievedTeacher != null;
    }

    public static boolean testTeacherDAOGetTeacherByName() throws ClassNotFoundException {
        Connection connection = DatabaseConnection.getDatabaseConnection();
        TeacherDAO testDAO = new TeacherDAO(connection);
        TeacherEntryObject retrievedTeacher = testDAO.retrieveTeacherByName("Morgan");
        return retrievedTeacher != null;
    }

    public static boolean testGenericDAOSetUp() throws ClassNotFoundException {
        Connection connection = DatabaseConnection.getDatabaseConnection();
        TeacherDAO testDAO = new TeacherDAO(connection);
        return testDAO != null;
    }

}
