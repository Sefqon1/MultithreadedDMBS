package akad.multithreadeddbms.controller.applicationlayer;

import akad.multithreadeddbms.MainView;
import akad.multithreadeddbms.model.dataaccesslayer.TeacherDAO;
import akad.multithreadeddbms.model.domainmodels.TeacherEntryObject;

import java.sql.Connection;
import java.sql.SQLException;

public class QueryExecutor {

    public static boolean insertTeacherIntoDatabase(TeacherEntryObject newTeacherObject, Thread insertTeacherThread, Connection connection) throws InterruptedException, SQLException {

        TeacherDAO insertTeacherDao = new TeacherDAO(connection);
        try {
        insertTeacherThread = new Thread(() -> {
            insertTeacherDao.insertTeacher(newTeacherObject);
        });
        insertTeacherThread.start();
        insertTeacherThread.join();
        } finally {
                MainView.getNewThreadPool().returnThreadToPool(insertTeacherThread);
        }

        return insertTeacherDao.getInsertionStatus();
    }


    public static TeacherEntryObject retrieveTeacherById(int teacherId, Thread retrieveTeacherThread, Connection connection) throws InterruptedException, SQLException {

        TeacherDAO retrieveTeacherDao = new TeacherDAO(connection);

        try {
            retrieveTeacherThread = new Thread(() -> {
                retrieveTeacherDao.retrieveTeacherById(teacherId);
            });
            retrieveTeacherThread.start();
            retrieveTeacherThread.join();
        } finally {
            MainView.getNewThreadPool().returnThreadToPool(retrieveTeacherThread);
        }

        return retrieveTeacherDao.getRetrievedTeacher();
    }

   public static TeacherEntryObject retrieveTeacherByName(String teacherName, Thread retrieveTeacherThread, Connection connection) throws InterruptedException, SQLException {

        TeacherDAO retrieveTeacherDao = new TeacherDAO(connection);

        try {
            retrieveTeacherThread = new Thread(() -> {
                retrieveTeacherDao.retrieveTeacherByName(teacherName);
            });
            retrieveTeacherThread.start();
            retrieveTeacherThread.join();
        } finally {
            MainView.getNewThreadPool().returnThreadToPool(retrieveTeacherThread);
        }

        return retrieveTeacherDao.getRetrievedTeacher();
    }
}
