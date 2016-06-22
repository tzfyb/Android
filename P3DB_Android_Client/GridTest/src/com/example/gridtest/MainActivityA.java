package com.example.gridtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class MainActivityA extends Fragment implements OnClickListener{

	Button signin;
	Button dna;
	Button search;
	Button question;
	Button tools;
	
	public MainActivityA() {
		// Required empty public constructor
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_main_activity_a, container, false);
		signin = (Button) view.findViewById(R.id.signin);
		signin.setOnClickListener(this);
		dna = (Button) view.findViewById(R.id.dna_icon);
		dna.setOnClickListener(this);
		search = (Button) view.findViewById(R.id.main_search);
		search.setOnClickListener(this);
		question = (Button) view.findViewById(R.id.question);
		question.setOnClickListener(this);
		tools = (Button) view.findViewById(R.id.tools);
		tools.setOnClickListener(this);
		
		return view;
	}
	
	public void onClick(View v){
		Intent ourIntent;
		if(v.getId()==R.id.signin){
				ourIntent = new Intent("android.intent.action.SIGNIN");
				startActivity(ourIntent);
		}
		if(v.getId()==R.id.dna_icon){
			ourIntent = new Intent("android.intent.action.WEB");
			startActivity(ourIntent);
		}
		if(v.getId()==R.id.main_search){
			ourIntent = new Intent("android.intent.action.SEARCHFILE");
			startActivity(ourIntent);
		}
		if(v.getId()==R.id.question){
			ourIntent = new Intent("android.intent.action.DRAGANDDROP");
			startActivity(ourIntent);
		}
		if(v.getId()==R.id.tools){
			ourIntent = new Intent("android.intent.action.TOOLS");
			startActivity(ourIntent);
		}
	}

}



