package com.example.training1;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class DownloadIssue extends AsyncTask<Void, Void, Boolean> {

	boolean running = true;
	ImageView cancel;
	ViewFlipper fv;

	public DownloadIssue(Context context, ViewFlipper fv, ImageView cancel) {
		super();
		this.cancel = cancel;
		this.fv = fv;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub
		int progress_status = 0;
		while (progress_status < 100 && running) {
			Log.i("progress", String.format("%d", progress_status));
			progress_status += 2;
			SystemClock.sleep(100);
		}
		if (progress_status == 100){
			return true;
		}
		else
			return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Log.i("result", "executed");
		fv.showNext();
		cancel.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void onCancelled() {
		running = false;
	}

}
