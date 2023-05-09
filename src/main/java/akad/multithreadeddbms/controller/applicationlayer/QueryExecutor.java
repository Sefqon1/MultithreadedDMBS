package akad.multithreadeddbms.controller.applicationlayer;

import akad.multithreadeddbms.MainView;
import akad.multithreadeddbms.model.dataaccesslayer.TeacherDAO;
import akad.multithreadeddbms.model.domainmodels.TeacherEntryObject;

import java.sql.Connection;
import java.sql.SQLException;

public class QueryExecutor {

    //static ThreadPool newThreadPool;
    //static DatabaseConnection newDbConnection;
    //static DatabaseConnectionPool newDbPool;
    
    //static TeacherEntryObject newTeacherObject;
    
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

   /* public static void main(String[] args) throws SQLException, InterruptedException {
        newThreadPool = new ThreadPool();
        newDbConnection = new DatabaseConnection();
        newDbPool = new DatabaseConnectionPool(newDbConnection);
        //newTeacherObject = new TeacherEntryObject("Peter Zwegat Test!!", "Mathematik");
        //insertTeacherIntoDatabase(newTeacherObject, newThreadPool.getThreadFromPool(), newDbPool.getConnectionFromPool());

        TeacherEntryObject teacher = retrieveTeacherById(2, newThreadPool.getThreadFromPool(), newDbPool.getConnectionFromPool());
        System.out.println(teacher.getName() + " " + teacher.getSubject());
    } */


}
