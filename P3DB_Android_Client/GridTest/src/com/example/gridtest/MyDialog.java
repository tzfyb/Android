package com.example.gridtest;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

public class MyDialog extends Activity {

	String name;
	private static final String TAG = "theName";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mydialog);
		Intent intent=getIntent();
		if(intent != null){
			int imageId=intent.getIntExtra("animalImage", R.drawable.cat_item);
			
			String animalName = intent.getStringExtra("animalName");
			ImageView myImage=(ImageView)findViewById(R.id.myDialogImage);
			myImage.setImageResource(imageId);
			TextView myText = (TextView) findViewById(R.id.myDialogText);
			myText.setText("I am a "+animalName+"!");
			name = animalName;
		}
	}

	public void closeDialog(View v){
		//finish();
		//try{
			//Class ourClass = Class.forName("com.example.grid.Cat");
		Log.v(TAG, name);
		if(name.equals("cat")){
			Intent ourIntent = new Intent(this, Cat.class);
			startActivity(ourIntent);
		}
		else if(name.equals("dog")){
			Intent ourIntent = new Intent(this, ScreenSlideActivity.class);
			startActivity(ourIntent);
		}
		else{
			Intent ourIntent = new Intent(this, Cat.class);
			startActivity(ourIntent);
		}
			//} catch(ClassNotFoundException e){
			//		e.printStackTrace();
			//}

	}
}
