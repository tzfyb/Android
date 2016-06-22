package com.example.biosearchengineclient;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class InfoActivity extends ActionBarActivity {
	String content = "";
	String url = null;
	TextView tv;
	TextView clickUrl;
	JSONObject _json = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		tv = (TextView)findViewById(R.id.infoTv);
		clickUrl = (TextView)findViewById(R.id.clickUrl);
		
		if(!getIntent().hasExtra("json")){
			_json = ((MyApplication) this.getApplication()).getCurrentJson();
		}
		else{
			String jsonStr = getIntent().getStringExtra("json");
			try {
				_json = new JSONObject(jsonStr);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			((MyApplication) this.getApplication()).setCurrentJson(_json);
		}
		
		Iterator<String> iter = _json.keys();
	    while (iter.hasNext()) {
	        String key = iter.next();
	        String value = null;
	        try {
	            value = _json.getString(key);
	        } catch (JSONException e) {
	            // Something went wrong!
	        }
	        
	        if(key.equalsIgnoreCase("url")){
	        	try {
					url = _json.getString(key);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        else{
	        	content += key  + ": " + value + "\n\n";
	        }
	    }
	    tv.setText(content);
	    tv.setTextSize(15);
	    tv.setTextColor(Color.parseColor("#404040"));
	    
	    clickUrl.setText(url);
	    clickUrl.setBackgroundColor(Color.parseColor("#8ec4d0"));
	}

	public void onClick(View v) {
		Intent intent = new Intent(getBaseContext(), Web.class);
		intent.putExtra("url", url);
		startActivity(intent);
    } 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
