package akad.multithreadeddbms.model.persistencelayer;

import java.util.*;


public class ThreadPool {
    private final List<Thread> threadPool;
    private static final int MAX_POOL_SIZE = 10;

    //Hier wird die Liste mit Threads initialisiert
    public ThreadPool() {
        threadPool = new ArrayList<>(MAX_POOL_SIZE);
        populateThreadPool(threadPool);
    }

    //Hier wird die Liste mit Threads gefüllt
    private synchronized void populateThreadPool(List<Thread> threadPool) {
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            Thread thread = new Thread();
            threadPool.add(thread);
        }
    }

    //Hier wird der Threadpool gestoppt
    public synchronized void stopThreadPool() {
        for (Thread t : threadPool) t.interrupt();
    }

    //Hier wird ein Thread aus dem Pool geholt
    public Thread getThreadFromPool() throws InterruptedException {

        while (threadPool.isEmpty()) {
            wait();
        }
        return threadPool.remove(0);
    }

    //Hier wird ein Thread zurück in den Pool gelegt
    public void returnThreadToPool(Thread thread) {
        threadPool.add(thread);
        notifyAll();
    }

}

/*

- Consider adding timer to threads to check for possible deadlocks

*/

