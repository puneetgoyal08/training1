package com.example.training1;

import com.example.training1.tabs.TabsFragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class IssueListAdapter extends BaseAdapter {
	private Context mContext;
	
	// Keep all Images in array
	public String[] magazines;
	
	// Constructor
	public IssueListAdapter(Context c){
		mContext = c;
		magazines = mContext.getResources().getStringArray(R.array.magazine_array);
	}

	@Override
	public int getCount() {
		return magazines.length;
	}

	@Override
	public Object getItem(int position) {
		String magazineURL = magazines[position];
		int imgId = mContext.getResources().getIdentifier(magazineURL, "drawable","com.example.training1");
		return imgId;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {			
		String magazineURL = magazines[position];
		int imgId = mContext.getResources().getIdentifier(magazineURL, "drawable","com.example.training1");
		View v = setupFlipperView(imgId, parent);
        return v;
	}
	
	public View setupFlipperView(int imgId, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		View fv = inflater.inflate( R.layout.flipper_layout,parent, false);
		ImageView issueImage = (ImageView) fv.findViewById(R.id.issueImage);
		
		issueImage.setImageResource(imgId);
		final ViewFlipper vf=(ViewFlipper)fv.findViewById(R.id.ViewFlipper01);

		ImageView download = (ImageView) fv.findViewById(R.id.download);
		final ImageView cancel = (ImageView) fv.findViewById(R.id.cancel);
		final ImageView play = (ImageView) fv.findViewById(R.id.play);

		cancel.setVisibility(View.INVISIBLE);

		download.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final DownloadIssue di = new DownloadIssue(mContext, vf, cancel);

				cancel.setVisibility(View.VISIBLE);
				vf.showNext();
				di.execute();
				cancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						cancel.setVisibility(View.INVISIBLE);
						vf.showPrevious();
						di.cancel(true);
						di.onCancelled();
						// TODO Auto-generated method stub
						
					}
				});
				play.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent i = new Intent(mContext, TabsFragmentActivity.class);
						mContext.startActivity(i);
						// TODO Auto-generated method stub
					}
				});
				// TODO Auto-generated method stub
			}
		});


		fv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("error", "image clicked");
			}
		});
		return fv;
	}
}
