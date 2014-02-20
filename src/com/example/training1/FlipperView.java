package com.example.training1;


import com.example.training1.tabs.TabsFragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;
import com.example.training1.ManageIssueList;

public class FlipperView extends LinearLayout{

	ViewFlipper vf;
	private ImageView download, cancel, issueImage, play;
	private int status;

	public FlipperView(Context context, AttributeSet attrs, final ManageIssueList qbc, final int index) {
		super(context, attrs);
		setOrientation(LinearLayout.HORIZONTAL);
		setGravity(Gravity.CENTER_VERTICAL);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.flipper_layout, this, true);
		vf = (ViewFlipper) findViewById(R.id.ViewFlipper01);
		issueImage = (ImageView) findViewById(R.id.issueImage);
		download = (ImageView) findViewById(R.id.download);
		cancel = (ImageView) findViewById(R.id.cancel);
		cancel.setVisibility(INVISIBLE);
		play = (ImageView) findViewById(R.id.play);
		final Context m_context = context;
		download.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setStatus(1);
				final DownloadIssue di = new DownloadIssue(m_context, vf, cancel);
				cancel.setVisibility(View.VISIBLE);
				vf.showNext();
				di.execute();
				cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						setStatus(0);
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
						Intent i = new Intent(m_context, TabsFragmentActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						m_context.startActivity(i);
						// TODO Auto-generated method stub
					}
				});
			}
		});
	}

	public void showNext(){
		vf.showNext();
	}
	public int getStatus(){
		return this.status;
	}
	public void setStatus(int status){
		this.status = status;
	}
	public void setImage(int imgID) {
		issueImage.setImageResource(imgID);
	}
}
