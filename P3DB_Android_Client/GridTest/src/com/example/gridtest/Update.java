package com.example.gridtest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

public class Update extends Activity implements
		AsyncTaskCompleteListener<JSONObject> {

	Button updateBnt;
	private String myVersionName;
	private String myVersionNum;
	private String newVersionName;
	private String newVersionNum;
	private String updateInfo;
	private String newApkUrl;
	private Boolean needUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
		updateBnt = (Button) findViewById(R.id.updateButton);
		updateBnt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				launchTask();

				// Get current app version

				Context context = getApplicationContext();
				PackageManager packageManager = context.getPackageManager();
				String packageName = context.getPackageName();

				try {
					PackageInfo pinfo = packageManager.getPackageInfo(
							packageName, 0);
					myVersionNum = Integer.toString(pinfo.versionCode);
					myVersionName = pinfo.versionName;
				} catch (PackageManager.NameNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void onTaskComplete(JSONObject result) {

		try {
			newVersionName = result.getString("versionName");
			newVersionNum = result.getString("versionNum");
			updateInfo = result.getString("updateInfo");
			newApkUrl = result.getString("apkUrl");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (Integer.parseInt(newVersionNum) > Integer.parseInt(myVersionNum)) {

			AlertDialog updateAlert = new AlertDialog.Builder(Update.this)
					.create();
			updateAlert.setTitle("Update");
			updateAlert
					.setMessage("You will download the lattest version of the app!");

			updateAlert.setButton(-3, "Yes",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) { //
							// TODO // Auto-generated // method // stub
							downloadapk();

						}
					});

			updateAlert.setButton(-2, "No",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) { //
							// TODO // Auto-generated // method // stub
							dialog.cancel();
						}
					});
			updateAlert.show();
			needUpdate = true;
		} else {
			AlertDialog updateAlert = new AlertDialog.Builder(Update.this).create();
			updateAlert.setTitle("Update");
			updateAlert.setMessage("This is the latest version!");
			updateAlert.setButton(-3, "OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) { //
							// TODO // Auto-generated // method // stub
						}
					});
			updateAlert.show();
			needUpdate = false;
		}

	}

	private void downloadapk() {

		DownloadManager.Request request = new DownloadManager.Request(
				Uri.parse(newApkUrl));
		request.setDescription("GridTest Update Package");
		request.setTitle("GridTest1.0");

		request.allowScanningByMediaScanner();
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

		request.setDestinationInExternalPublicDir(
				Environment.DIRECTORY_DOWNLOADS, "GridTest1.apk");

		// get download service and enqueue file
		DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		manager.enqueue(request);

		registerReceiver(onComplete, new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}

	BroadcastReceiver onComplete = new BroadcastReceiver() {
		public void onReceive(Context ctxt, Intent intent) {
			Intent intent2 = new Intent(Intent.ACTION_VIEW);
			Uri uri = Uri.fromFile(new File(Environment.DIRECTORY_DOWNLOADS + File.separatorChar
					+ "GridTest1.apk"));
			intent2.setDataAndType(uri,
					"application/vnd.android.package-archive");
			startActivity(intent2);
		}
	};

	public void launchTask() {
		JSONObject requestJson = new JSONObject();
		try {
			requestJson.put("command", "update");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// END

		// Using HttpJson class
		String uri = "http://babbage.cs.missouri.edu/~tzfyb/underGradResearch/littleToolsUpdate.php";
		HttpJson updateJson = new HttpJson(uri, requestJson, Update.this, this);

		updateJson.jsonRequest();
	}
	
	public Boolean getUpdateNeed(){
		return needUpdate;
	}
}
