package com.example.training1;

import android.os.Handler;
import android.util.Log;

public class DownloadManager extends Thread {
	
	

	static private Handler messageHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Log.i("DownloadManager",
					"messageHandler's handle message method is called");
		};
	};

	public DownloadManager() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		synchronized (messageHandler) {
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {

			}
			messageHandler.notifyAll();
		}
		// TODO Auto-generated method stub
		super.run();
	}

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		super.start();
	}

	@Override
	public State getState() {
		// TODO Auto-generated method stub
		return super.getState();
	}

	public Handler getHandler() {
		return messageHandler;
	}
}
