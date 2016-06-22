package com.example.biosearchengineclient;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
	Button searchBnt;
	CheckBox pubmedCheck, ieeeCheck, newsCheck, googlescholarCheck;
	String datasource = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        pubmedCheck = (CheckBox)findViewById(R.id.pubmedCheck);
        ieeeCheck = (CheckBox)findViewById(R.id.ieeeCheck);
        newsCheck = (CheckBox)findViewById(R.id.newsCheck);
        googlescholarCheck = (CheckBox)findViewById(R.id.googlescholarCheck);
        
        searchBnt = (Button) findViewById(R.id.searchButton);
        searchBnt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if(pubmedCheck.isChecked()){
            		datasource += "pubmed";
            	}
            	
            	if(ieeeCheck.isChecked() && datasource.isEmpty()){
            		datasource += "ieee";
            	}
            	else if(ieeeCheck.isChecked()){
            		datasource += "|ieee";
            	}
            	
            	if(newsCheck.isChecked() && datasource.isEmpty()){
            		datasource += "news";
            	}
            	else if(newsCheck.isChecked()){
            		datasource += "|news";
            	}
            	
            	if(googlescholarCheck.isChecked() && datasource.isEmpty()){
            		datasource += "googlescholar";
            	}
            	else if(googlescholarCheck.isChecked()){
            		datasource += "|googlescholar";
            	}
				Intent intent = new Intent(getBaseContext(), ResultPage.class);
				EditText eText = (EditText)findViewById(R.id.searchInput);
				String searchKeyWord = eText.getText().toString();
				intent.putExtra("keyWord", searchKeyWord);
				intent.putExtra("datasource", datasource);
				startActivity(intent);
            }
        });
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
