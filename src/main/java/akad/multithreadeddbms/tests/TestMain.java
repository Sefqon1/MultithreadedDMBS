package akad.multithreadeddbms.tests;

import java.sql.SQLException;

import static akad.multithreadeddbms.tests.PersistenceLayerTests.*;
import static akad.multithreadeddbms.tests.DomainModelsTest.*;
import static akad.multithreadeddbms.tests.DataAccessLayerTest.*;
import static akad.multithreadeddbms.tests.ApplicationLayerTest.*;
public class TestMain {

    public static void main(String[] args) throws SQLException, InterruptedException {
        System.out.println("Starting Test");
        boolean testDbConnTestResult = testGetDatabaseConnection();
        boolean testDbPoolTestResult = testDatabaseConnectionPool();
        boolean testThreadPoolTestResult = testThreadPool();
        boolean testTeacherEntryObjectResult = testTeacherEntryObject();
        boolean testGenericEntryObjectResult = testGenericEntryObject();
        boolean testTeacherDAOSetUpResult = testTeacherDAOSetUp();
        boolean testTeacherDAOInsertionResult = testTeacherDAOInsertion();
        boolean testTeacherDAOGetTeacherByIdResult = testTeacherDAOGetTeacherById();
        boolean testTeacherDAOGetTeacherByNameResult = testTeacherDAOGetTeacherByName();
        boolean testGenericDAOSetUpResult = testGenericDAOSetUp();
        boolean testInputHandlerResult = testInputHandler();
        boolean testOutputHandlerResult = testOutputHandler();
        //boolean testInsertTeacherIntoDatabaseResult = testInsertTeacherIntoDatabase();
        //boolean testRetrieveTeacherByIdResult = testRetrieveTeacherById();
        //boolean testRetrieveTeacherByNameResult = testRetrieveTeacherByName();

        printTestResult("DbConnection Test", testDbConnTestResult);
        printTestResult("DbConnectionPool Test", testDbPoolTestResult);
        printTestResult("Thread Pool Test", testThreadPoolTestResult);
        printTestResult("TeacherEntryObject Test", testTeacherEntryObjectResult);
        printTestResult("GenericEntryObject Test", testGenericEntryObjectResult);
        printTestResult("TeacherDAO SetUp Test", testTeacherDAOSetUpResult);
        printTestResult("TeacherDAO Insertion Test", testTeacherDAOInsertionResult);
        printTestResult("TeacherDAO GetTeacherById Test", testTeacherDAOGetTeacherByIdResult);
        printTestResult("TeacherDAO GetTeacherByName Test", testTeacherDAOGetTeacherByNameResult);
        printTestResult("GenericDAO SetUp Test", testGenericDAOSetUpResult);
        printTestResult("InputHandler Test", testInputHandlerResult);
        printTestResult("OutputHandler Test", testOutputHandlerResult);
        //printTestResult("InsertTeacherIntoDatabase Test", testInsertTeacherIntoDatabaseResult);
        //printTestResult("RetrieveTeacherById Test", testRetrieveTeacherByIdResult);
        //printTestResult("RetrieveTeacherByName Test", testRetrieveTeacherByNameResult);

    }

    private static void printTestResult(String testName, boolean success) {
        String resultSymbol = success ? "\u2714" : "\u2718";
        String resultColor = success ? "\u001B[32m" : "\u001B[31m";
        System.out.println(resultColor + resultSymbol + " " + testName);
    }
}
