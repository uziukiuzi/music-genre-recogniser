package com.musicplayer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.util.Log;

import com.musicplayer.TPE.ExecutorCallback;

public class ThreadManager {

	public static ThreadManager sInstance;
	
	private final BlockingQueue<Runnable> mWorkQueue;
	// Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    /*
     * Gets the number of available cores
     * (not always the same as the maximum number of cores)
     */
    private static int NUMBER_OF_CORES =
            Runtime.getRuntime().availableProcessors();
    
    
    TPE mThreadPoolExecutor;

	
    static  {
        // Creates a single static instance of ThreadManager
        sInstance = new ThreadManager();
    }
	
	
    private ThreadManager(){
    	Log.v("", String.valueOf(NUMBER_OF_CORES));
        // Instantiates the queue of Runnables as a LinkedBlockingQueue
        mWorkQueue = new LinkedBlockingQueue<Runnable>();
        mThreadPoolExecutor = new TPE(
                NUMBER_OF_CORES,       // Initial pool size
                NUMBER_OF_CORES,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mWorkQueue);
    }
    
    public void addTask(Runnable task){
    	sInstance.mThreadPoolExecutor.execute(task);
    }
    
    public void setExecutorCallback(ExecutorCallback callback){
    	sInstance.mThreadPoolExecutor.setExecutorCallback(callback);
    }
    
    
}
