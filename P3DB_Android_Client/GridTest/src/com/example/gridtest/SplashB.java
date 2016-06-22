package com.example.gridtest;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link SplashB.OnFragmentInteractionListener} interface to handle interaction
 * events. Use the {@link SplashB#newInstance} factory method to create an
 * instance of this fragment.
 * 
 */
public class SplashB extends Fragment {
	public SplashB() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_splash_b, container, false);
	}

}