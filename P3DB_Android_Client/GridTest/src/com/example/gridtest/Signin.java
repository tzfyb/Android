package com.example.gridtest;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Signin extends Activity implements AsyncTaskCompleteListener<JSONObject>{
	
	private String username;
	private String password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		ImageButton followfb;
		ImageButton loginbtn;
		ImageButton register;
		
		followfb = (ImageButton)findViewById(R.id.followFb);
		loginbtn = (ImageButton)findViewById(R.id.loginButton);
		register = (ImageButton)findViewById(R.id.register);
		
		loginbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				checkUser();
			}
		});
	}

	
	private void checkUser(){
		//Toast.makeText(this, "show!", Toast.LENGTH_SHORT).show();
		username = ((EditText)findViewById(R.id.usernametv)).getText().toString();
		password = ((EditText)findViewById(R.id.pwtv)).getText().toString();
		
		JSONObject requestJson = new JSONObject();
		try {
			requestJson.put("command", "login");
			requestJson.put("username", username);
			requestJson.put("password", password);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String uri = "http://babbage.cs.missouri.edu/~tzfyb/underGradResearch/littleToolsUpdate.php";
		HttpJson checkUser = new HttpJson(uri, requestJson, Signin.this, this);
		checkUser.setNeedProgressDialog(true);

		checkUser.jsonRequest();
	}
	
	@Override
	public void onTaskComplete(JSONObject result) {
		
		// TODO Auto-generated method stub
		String valid = null; 
		String nickname = null;
		String regDate = null;
		try {
			valid = result.getString("validation");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Toast.makeText(Signin.this, "beforeIF", Toast.LENGTH_SHORT).show();
		if(valid.equals("badName")){
			Toast.makeText(Signin.this, "Bad User Name!", Toast.LENGTH_SHORT).show();
		}
		else if(valid.equals("badPw")){
			Toast.makeText(Signin.this, "Wrong Password!", Toast.LENGTH_SHORT).show();

		}
		else if(valid.equals("pass")){
			try {
				nickname = result.getString("nickname");
				regDate = result.getString("regDate");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Toast.makeText(Signin.this, "YO~", Toast.LENGTH_SHORT).show();
			Toast.makeText(Signin.this, nickname, Toast.LENGTH_SHORT).show();
			Toast.makeText(Signin.this, regDate, Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(Signin.this, "ÔüÔü", Toast.LENGTH_SHORT).show();

		}
	}
		
}
