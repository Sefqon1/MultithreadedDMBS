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
    public synchronized void stopThreadPool() throws InterruptedException {
        for (Thread t : threadPool) {
            ((WorkerThread) t).stopThread();
        }
        for (Thread t : threadPool) t.interrupt();
        for (Thread t : threadPool) t.join();

    }

    // Hier wird ein Task der TaskQueue hinzugefügt
    public void addTaskToPool(Runnable task) {
        taskQueue.add(task);
    }


    //WorkerThread, der die übergebenen Tasks ausführt
    private static class WorkerThread extends Thread {
        // Hier wird eine TaskQueue instanziiert
        private final BlockingQueue<Runnable> taskQueue;
        // Hier wird ein Flag gesetzt, ob der Thread gestoppt werden soll
        private volatile boolean stopRequested = false;

        public WorkerThread(BlockingQueue<Runnable> taskQueue) {
            this.taskQueue = taskQueue;
        }

        // Hier wird der Thread gestartet und die Tasks ausgeführt
        @Override
        public void run() {
            while (!stopRequested && !Thread.currentThread().isInterrupted()) {
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        // Hier wird der Thread gestoppt
        public void stopThread() {
            stopRequested = true;
        }
    }

}


