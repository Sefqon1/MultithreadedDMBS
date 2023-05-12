package akad.multithreadeddbms.model.persistencelayer;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ThreadPool {
    private final List<Thread> threadPool;
    private final BlockingQueue<Runnable> taskQueue;
    private static final int MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors();


    //Hier wird die Liste mit Threads initialisiert
    public ThreadPool() {
        threadPool = new ArrayList<>(MAX_POOL_SIZE);
        taskQueue = new LinkedBlockingQueue<>();
        populateThreadPool(threadPool);
    }

    //Hier wird die Liste mit Threads gefüllt
    private synchronized void populateThreadPool(List<Thread> threadPool) {
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            WorkerThread thread = new WorkerThread(taskQueue);
            threadPool.add(thread);
            thread.start();
        }
    }

    //Hier wird die Größe der TaskQueue zurückgegeben
    public int getTaskQueueSize() {
        return taskQueue.size();
    }

    //Hier wird der Thread pool gestoppt
    public synchronized void stopThreadPool() {
        for (Thread t : threadPool) t.interrupt();
    }

    public void addTaskToPool(Runnable task) {
        taskQueue.add(task);
    }

    //Hier wird ein Thread zurück in den Pool gelegt
    public void returnThreadToPool(Thread thread) {
        threadPool.add(thread);
        notifyAll();
    }

    //WorkerThread, der die übergebenen Tasks ausführt
    private static class WorkerThread extends Thread {
        private final BlockingQueue<Runnable> taskQueue;

        public WorkerThread(BlockingQueue<Runnable> taskQueue) {
            this.taskQueue = taskQueue;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}

/*

- Consider adding timer to threads to check for possible deadlocks

*/

