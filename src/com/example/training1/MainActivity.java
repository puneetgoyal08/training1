package com.example.training1;

import java.util.ArrayList;


import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	String[] magazines;
	ArrayList<FlipperView> flipperViewList;
	int queueList[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		flipperViewList = new ArrayList<FlipperView>();
		setContentView(R.layout.activity_main);

		magazines = getResources().getStringArray(R.array.magazine_array);
		Configuration config = getResources().getConfiguration();
		Boolean isLandscape = (config.orientation == Configuration.ORIENTATION_LANDSCAPE);
		if (isLandscape)
			setupLandscapeView();
		else if (((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
				|| ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE))
			setupPotraitViewForTablet();
		else
			setupPotraitViewForPhone();
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void setFlipperViewList(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			int count = magazines.length;
			for (int i = 0; i < count; i++) {
				String magazineURL = magazines[i];
				int imgId = getResources().getIdentifier(magazineURL,
						"drawable", "com.example.training1");
				FlipperView fv1 = new FlipperView(getApplicationContext(),
						null, null, imgId);
				fv1.setImage(imgId);
				flipperViewList.add(fv1);
			}
		} else {
			int count = magazines.length;
			for (int i = 0; i < count; i++) {
				String magazineURL = magazines[i];
				int imgId = getResources().getIdentifier(magazineURL,
						"drawable", "com.example.training1");
				FlipperView fv1 = new FlipperView(getApplicationContext(),
						null, null, imgId);
				if(queueList[i]==1)
				fv1.setImage(imgId);
				flipperViewList.add(fv1);
			}
		}

	}

	public void setupPotraitViewForPhone() {
		Log.i("phone", "phone detected");
		ListView listView = (ListView) findViewById(R.id.issuesList);
		listView.setAdapter(new IssueAdapter(this));
	}

	public void setupPotraitViewForTablet() {
		Log.i("potrait", "POTRAIT VIEW");
		GridView gridView = (GridView) findViewById(R.id.grid_view);
		gridView.setAdapter(new IssueAdapter(this));
	}

	public void setupLandscapeView() {
		setFlipperViewList(null);

		LinearLayout horizontal_layout = (LinearLayout) findViewById(R.id.linear1);
		for (FlipperView fv : flipperViewList) {
			horizontal_layout.addView(fv);
		}
		// for (int i = 0; i < count; i++) {
		// String magazineURL = magazines[i];
		// int imgId = getResources().getIdentifier(magazineURL, "drawable",
		// "com.example.training1");
		// FlipperView fv1 = new FlipperView(getApplicationContext(), null,
		// null, imgId);
		// fv1.setImage(imgId);
		// // View fv = setupFlipperView(imgId);
		// horizontal_layout.addView(fv1);
		// }
	}

	// public View setupFlipperView(int imgId) {
	// View fv = LayoutInflater.from(this).inflate(R.layout.flipper_layout,
	// null);
	// ImageView issueImage = (ImageView) fv.findViewById(R.id.issueImage);
	//
	// issueImage.setImageResource(imgId);
	// final ViewFlipper vf = (ViewFlipper) fv
	// .findViewById(R.id.ViewFlipper01);
	//
	// ImageView download = (ImageView) fv.findViewById(R.id.download);
	// final ImageView cancel = (ImageView) fv.findViewById(R.id.cancel);
	// final ImageView play = (ImageView) fv.findViewById(R.id.play);
	//
	// cancel.setVisibility(View.INVISIBLE);
	//
	// download.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// final DownloadIssue di = new DownloadIssue(
	// getApplicationContext(), vf, cancel);
	//
	// cancel.setVisibility(View.VISIBLE);
	// vf.showNext();
	// di.execute();
	// cancel.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// cancel.setVisibility(View.INVISIBLE);
	// vf.showPrevious();
	// di.cancel(true);
	// di.onCancelled();
	// // TODO Auto-generated method stub
	//
	// }
	// });
	// play.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// Intent i = new Intent(MainActivity.this,
	// TabsFragmentActivity.class);
	// startActivity(i);
	// finish();
	// // TODO Auto-generated method stub
	// }
	// });
	// // TODO Auto-generated method stub
	// }
	// });
	//
	// fv.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// Log.d("error", "image clicked");
	// }
	// });
	// return fv;
	// }

	public void setQueueList() {
		int count = flipperViewList.size();
		queueList = new int[count];
		int index = 0;
		for (FlipperView fv : flipperViewList) {
			queueList[index] = fv.getStatus();
			index++;
		}
	}

	public int[] getQueueList() {

		return queueList;
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		this.queueList = savedInstanceState.getIntArray("queueList");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putIntArray("queueList", queueList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (isFinishing()) {
			// do stuff
		} else {
			Toast.makeText(getApplicationContext(), "Orientation Changed",
					Toast.LENGTH_SHORT).show();
		}
	}

}
