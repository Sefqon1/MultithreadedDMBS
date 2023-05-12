package akad.multithreadeddbms.controller.applicationlayer;

import akad.multithreadeddbms.MainView;
import akad.multithreadeddbms.model.dataaccesslayer.TeacherDAO;
import akad.multithreadeddbms.model.domainmodels.TeacherEntryObject;

import java.sql.Connection;
import java.sql.SQLException;

public class QueryExecutor {

    // Diese Methode nimmt ein neues Objekt entgegen und eine Verbindung, führt die Insertion aus und gibt den Status zurück.
    public static boolean insertTeacherIntoDatabase(TeacherEntryObject newTeacherObject, Connection connection) throws InterruptedException, SQLException {

        TeacherDAO insertTeacherDao = new TeacherDAO(connection);
        insertTeacherDao.insertTeacher(newTeacherObject);
        return insertTeacherDao.getInsertionStatus();
    }

    // Diese Methode nimmt eine Id entgegen und eine Verbindung, führt die Suche aus und gibt das Objekt, wenn gefunden, zurück.
    public static TeacherEntryObject retrieveTeacherById(int teacherId, Connection connection) throws InterruptedException, SQLException {

        TeacherDAO retrieveTeacherDao = new TeacherDAO(connection);
        retrieveTeacherDao.retrieveTeacherById(teacherId);
        return retrieveTeacherDao.getRetrievedTeacher();
    }

    // Diese Methode nimmt einen Namen entgegen und eine Verbindung, führt die Suche aus und gibt das Objekt, wenn gefunden, zurück.
   public static TeacherEntryObject retrieveTeacherByName(String teacherName, Thread retrieveTeacherThread, Connection connection) throws InterruptedException, SQLException {

        TeacherDAO retrieveTeacherDao = new TeacherDAO(connection);
        return retrieveTeacherDao.getRetrievedTeacher();
    }
}
