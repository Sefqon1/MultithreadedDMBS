package akad.multithreadeddbms.tests;
import akad.multithreadeddbms.controller.applicationlayer.QueryExecutor;
import akad.multithreadeddbms.controller.applicationlayer.InputHandler;
import akad.multithreadeddbms.controller.applicationlayer.OutputHandler;
import akad.multithreadeddbms.model.domainmodels.TeacherEntryObject;
import akad.multithreadeddbms.model.persistencelayer.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

import static akad.multithreadeddbms.controller.applicationlayer.QueryExecutor.*;

public class ApplicationLayerTest {

    public static boolean testInputHandler() {
        String name = "John Doe";
        String course = "Math";
        TeacherEntryObject teacher = InputHandler.convertInputToTeacherObject(name, course);
        if (InputHandler.validateInput(name, course) && teacher != null) {
            return true;
        }
        return false;
    }

    public static boolean testOutputHandler() {
        String id = "10";
        if (OutputHandler.parseAndValidateQuery(id) == 10) {
            return true;
        }
        return false;
    }

    /*public static boolean testInsertTeacherIntoDatabase() throws SQLException, InterruptedException {
        TeacherEntryObject testTeacherObject = new TeacherEntryObject("Test Teacher", "Test Course");
        Thread testThread = new Thread();
        Connection connection = DatabaseConnection.getDatabaseConnection();

        return insertTeacherIntoDatabase(testTeacherObject, testThread, connection);
    }

    public static boolean testRetrieveTeacherById() throws SQLException, InterruptedException {
        Thread testThread = new Thread();
        Connection connection = DatabaseConnection.getDatabaseConnection();
        return retrieveTeacherById(1, testThread, connection) != null;
    }

    public static boolean testRetrieveTeacherByName() throws SQLException, InterruptedException {
        Thread testThread = new Thread();
        Connection connection = DatabaseConnection.getDatabaseConnection();
        return retrieveTeacherByName("Morgan", testThread, connection) != null;
    }*/
}
