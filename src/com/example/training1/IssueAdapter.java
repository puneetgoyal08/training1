package com.example.training1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

//import android.widget.GridView;

public class IssueAdapter extends BaseAdapter implements ManageIssueList {
	private Context mContext;
	private int[] queueList;

	// Keep all magazines in array
	public String[] magazines;

	// Constructor
	public IssueAdapter(Context c) {
		mContext = c;
		magazines = mContext.getResources().getStringArray(
				R.array.magazine_array);
		queueList = new int[magazines.length];
	}

	@Override
	public int getCount() {
		return magazines.length;
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String magazineURL = magazines[position];
		FlipperView fv1 = new FlipperView(mContext, null, this, position);
		int imgId = mContext.getResources().getIdentifier(magazineURL,
				"drawable", "com.example.training1");
		fv1.setImage(imgId);
		return fv1;
	}

	public int[] getQueueList() {
		return queueList;
	}

	public void setQueueList(int[] queueList) {
		this.queueList = queueList;
	}

	@Override
	public void toggleIssueStatus(int index, int status) {
		// TODO Auto-generated method stub

	}

	// public View setupFlipperView(int imgId, ViewGroup parent){
	// LayoutInflater inflater = (LayoutInflater)
	// mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// View fv1 = new FlipperView(mContext, null);
	// View fv = inflater.inflate( R.layout.flipper_layout,parent, false);
	// ImageView issueImage = (ImageView) fv.findViewById(R.id.issueImage);
	//
	// issueImage.setImageResource(imgId);
	// final ViewFlipper vf=(ViewFlipper)fv.findViewById(R.id.ViewFlipper01);
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
	// final DownloadIssue di = new DownloadIssue(mContext, vf, cancel);
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
	// Intent i = new Intent(mContext, TabsFragmentActivity.class);
	// mContext.startActivity(i);
	// // TODO Auto-generated method stub
	// }
	// });
	// // TODO Auto-generated method stub
	// }
	// });
	//
	//
	// fv.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// Log.d("error", "image clicked");
	// }
	// });
	// return fv1;
	// }

}
