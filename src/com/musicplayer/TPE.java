package com.musicplayer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.util.Log;

public class TPE extends ThreadPoolExecutor{

	private ExecutorCallback mExecutorCallback;
	
	public TPE(int corePoolSize, int maximumPoolSize, long keepAliveTime,
			TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	protected void terminated() {
		// TODO Auto-generated method stub
		super.terminated();
		if(mExecutorCallback != null){
		mExecutorCallback.onTerminated();
		}
	}
	
	public void setExecutorCallback(ExecutorCallback callback){
		mExecutorCallback = callback;
	}
	
	public interface ExecutorCallback{
		public void onTerminated();
	}

}
