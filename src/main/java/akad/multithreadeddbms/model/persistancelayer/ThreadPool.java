package akad.multithreadeddbms.model.persistancelayer;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadPool {
    private final List<Thread> threadPool;
    private static final int MAX_POOL_SIZE = 10;

    public ThreadPool() {
        threadPool = new ArrayList<>(MAX_POOL_SIZE);
        populateThreadPool(threadPool);
    }

    private synchronized void populateThreadPool(List<Thread> threadPool) {
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            Thread thread = new Thread();
            threadPool.add(thread);
        }
    }

    public Thread getThreadFromPool() throws InterruptedException {
        while (threadPool.isEmpty()) {
            wait();
        }
        return threadPool.remove(0);
    }

    public void returnThreadToPool(Thread thread) {
        threadPool.add(thread);
        notifyAll();
    }

}

/*
To Do:
- Add try/catch block + error handling

- Consider adding timer to threads to check for possible deadlocks

*/

