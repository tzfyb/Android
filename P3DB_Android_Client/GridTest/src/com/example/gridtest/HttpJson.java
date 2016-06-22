package com.example.gridtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class HttpJson {
	private String uri;
	private JSONObject jsonobj;
	private Context context;
	private AsyncTaskCompleteListener<JSONObject> callback;
	private Boolean needProgressDialog;
	
	HttpJson(String uri, JSONObject jsonobj,Context context, AsyncTaskCompleteListener<JSONObject> cb){
		this.uri = uri;
		this.jsonobj = jsonobj;
		this.context = context;	
		this.callback = cb;
		needProgressDialog = true;
	}

	public void setNeedProgressDialog(Boolean needPD){
		needProgressDialog = needPD;
	}
	public void jsonRequest(){
		new Connection().execute();
	}
	
	private class Connection extends AsyncTask<Void, Void, String> {
		ProgressDialog progressDialog = new ProgressDialog(context);
		
		@Override
		protected String doInBackground(Void... params){
			HttpResponse response = null;
			HttpClient httpclient = new DefaultHttpClient();
			//HttpConnectionParams.setConnectionTimeout(httpclient.getParams(), 100000);
			
			String result = null;
			HttpPost post = new HttpPost(uri);
			
			 
			
			try {
				String content = jsonobj.toString();

	            post.setEntity(new StringEntity(content));
	            post.setHeader("Accept", "application/json");
	            post.setHeader("Content-type", "application/json");
	 
	            
	            response = httpclient.execute(post);
	            
	            if (response != null) {
	                InputStream in = response.getEntity().getContent(); // Get the data in the entity

	                result = convertStreamToString(in);
	                //Log.i("Read from Server", a);
	            }  

	        } catch (Exception e) { e.printStackTrace();}

			return result;
		}	
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if(needProgressDialog == true){
				progressDialog.setMessage("Wait...");
				progressDialog.show();
			}
			
		}

		@Override
		protected void onPostExecute(String result){
			JSONObject prepareJson = null;
			try {
				prepareJson = new JSONObject(result);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(needProgressDialog == true){
				progressDialog.dismiss();
			}
			callback.onTaskComplete(prepareJson);			
		}
	}
	
	private static String convertStreamToString(InputStream is) {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
}
