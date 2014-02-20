/**
 * 
 */
package com.example.training1.tabs;

import com.example.training1.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 *
 */
public class Tab3Fragment extends Fragment {

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		if (container == null) {
//            return null;
//        }
		return (LinearLayout)inflater.inflate(R.layout.tab_frag3_layout, container, false);
	}

	

}
