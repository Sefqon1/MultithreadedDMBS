package akad.multithreadeddbms.controller.applicationlayer;

import akad.multithreadeddbms.model.dataaccesslayer.TeacherDAO;
import akad.multithreadeddbms.model.domainmodels.TeacherEntryObject;
import akad.multithreadeddbms.model.persistencelayer.DatabaseConnection;
import akad.multithreadeddbms.model.persistencelayer.DatabaseConnectionPool;
import akad.multithreadeddbms.model.persistencelayer.ThreadPool;

import java.sql.Connection;
import java.sql.SQLException;

public class QueryExecutor {

    static ThreadPool newThreadPool;
    static DatabaseConnection newDbConnection;
    static DatabaseConnectionPool newDbPool;
    
    static TeacherEntryObject newTeacherObject;
    
    public static void insertTeacherIntoDatabase(TeacherEntryObject newTeacherObject) throws InterruptedException, SQLException {

        Thread insertTeacherThread = newThreadPool.getThreadFromPool();
        TeacherDAO insertTeacherDao = new TeacherDAO(newDbPool.getConnectionFromPool());
        try {
        insertTeacherThread = new Thread(() -> {
            insertTeacherDao.insertTeacher(newTeacherObject);
        });
        insertTeacherThread.start();
        } finally {
                newThreadPool.returnThreadToPool(insertTeacherThread);
            }

        if (insertTeacherThread.isAlive()) {
            System.out.println("Insert teacher thread is running.");
        } else {
            System.out.println("Insert teacher thread is not running.");
        }
    }


    
    
    public static void main(String[] args) throws SQLException, InterruptedException {
        newThreadPool = new ThreadPool();
        newDbConnection = new DatabaseConnection();
        newDbPool = new DatabaseConnectionPool(newDbConnection);
        newTeacherObject = new TeacherEntryObject("Peter Zwegat Test!!", "Mathematik");
        insertTeacherIntoDatabase(newTeacherObject);
        
        
    }


}
