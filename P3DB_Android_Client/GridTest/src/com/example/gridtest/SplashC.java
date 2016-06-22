package com.example.gridtest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link SplashC.OnFragmentInteractionListener} interface to handle interaction
 * events. Use the {@link SplashC#newInstance} factory method to create an
 * instance of this fragment.
 * 
 */
public class SplashC extends Fragment implements OnClickListener{

	Button btn;
	
	public SplashC() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_splash_c, container, false);
		btn = (Button) view.findViewById(R.id.beginButton);
		btn.setOnClickListener(this);
		return view;
	}
	
	public void onClick(View v){
		if(v.getId()==R.id.beginButton){
			Intent ourIntent = new Intent("android.intent.action.MAINACTIVITY");
			startActivity(ourIntent);
		}
	}

}
