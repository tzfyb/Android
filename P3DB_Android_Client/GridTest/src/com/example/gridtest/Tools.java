package com.example.gridtest;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.gridtest.DragAndDrop.MyDragListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Tools extends Activity implements
		AsyncTaskCompleteListener<JSONObject> {
	String folderPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + File.separatorChar + "littleTools";
	private ImageView toolA, toolB, toolC, toolD, toolE, toolF;
	private static final String TAG = "MyActivity";
	private int[] coorA = new int[2];
	private int[] coorB = new int[2];
	private int[] coorC = new int[2];
	private int[] coorD = new int[2];
	private int[] coorE = new int[2];
	private int[] coorF = new int[2];

	public static final String MyPREFERENCES = "MyPrefs" ;
	SharedPreferences sharedpreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tools);

		toolA = (ImageView) findViewById(R.id.toolA);
		toolB = (ImageView) findViewById(R.id.toolB);
		toolC = (ImageView) findViewById(R.id.toolC);
		toolD = (ImageView) findViewById(R.id.toolD);
		toolE = (ImageView) findViewById(R.id.toolE);
		toolF = (ImageView) findViewById(R.id.toolF);

		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		
		 
		//getImageViewCor();

		//getImageViewCor(toolB, toolACor);
		
		//Log.v(TAG, "toolB position" + Integer.toString(toolB.getTop()) + "," + Integer.toString(toolB.getLeft()));
		/*Log.v(TAG, "toolC position" + Integer.toString(toolC.getTop()) + "," + Integer.toString(toolC.getLeft()));
		Log.v(TAG, "toolD position" + Integer.toString(toolD.getTop()) + "," + Integer.toString(toolD.getLeft()));
		Log.v(TAG, "toolE position" + Integer.toString(toolE.getTop()) + "," + Integer.toString(toolE.getLeft()));
		Log.v(TAG, "toolF position" + Integer.toString(toolF.getTop()) + "," + Integer.toString(toolF.getLeft()));*/
		
		if (!checkToolValid("toolA")) {
			toolA.setImageBitmap(toGrayscale(((BitmapDrawable) toolA
					.getDrawable()).getBitmap()));
		}

		if (!checkToolValid("toolB")) {
			toolB.setImageBitmap(toGrayscale(((BitmapDrawable) toolB
					.getDrawable()).getBitmap()));
		}

		if (!checkToolValid("toolC")) {
			toolC.setImageBitmap(toGrayscale(((BitmapDrawable) toolC
					.getDrawable()).getBitmap()));
		}

		if (!checkToolValid("toolD")) {
			toolD.setImageBitmap(toGrayscale(((BitmapDrawable) toolD
					.getDrawable()).getBitmap()));
		}

		if (!checkToolValid("toolE")) {
			toolE.setImageBitmap(toGrayscale(((BitmapDrawable) toolE
					.getDrawable()).getBitmap()));
		}

		if (!checkToolValid("toolF")) {
			toolF.setImageBitmap(toGrayscale(((BitmapDrawable) toolF
					.getDrawable()).getBitmap()));
		}
		
		setImagePos();
		
		toolA.setOnLongClickListener(longListen);
		toolB.setOnLongClickListener(longListen);
		toolC.setOnLongClickListener(longListen);
		toolD.setOnLongClickListener(longListen);
		toolE.setOnLongClickListener(longListen);
		toolF.setOnLongClickListener(longListen);
		
		toolA.setOnDragListener(new MyDragListener());
		toolB.setOnDragListener(new MyDragListener());
		toolC.setOnDragListener(new MyDragListener());
		toolD.setOnDragListener(new MyDragListener());
		toolE.setOnDragListener(new MyDragListener());
		toolF.setOnDragListener(new MyDragListener());
		
		toolA.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				launchTask("toolA");

			}
		});
		toolB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				launchTask("toolB");

			}
		});
		toolC.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				launchTask("toolC");

			}
		});
		toolD.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				launchTask("toolD");

			}
		});
		toolE.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				launchTask("toolE");

			}
		});
		toolF.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				launchTask("toolF");

			}
		});
	}
	
	OnLongClickListener longListen = new OnLongClickListener() {
		
		@Override
		public boolean onLongClick(View v) {
			Log.v(TAG, v.getId() + "Click!");
			DragShadowBuilder dragshadow = new DragShadowBuilder(v);
			ClipData data = ClipData.newPlainText("", "");
			v.startDrag(data, dragshadow, v, 0);
			v.setVisibility(View.INVISIBLE);
			
			return false;
		}
	};

	class MyDragListener implements OnDragListener {

		@Override
		public boolean onDrag(View v, DragEvent event) {
			ImageView dragAction = (ImageView) event.getLocalState();
			ImageView target = (ImageView) v;
			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				// do nothing
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
				Log.v(TAG, "Enter!");
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				Log.v(TAG, "Exit!");
				break;
			case DragEvent.ACTION_DROP:
				// Dropped, reassign View to ViewGroup
				Log.v(TAG, "Drop!");
				float[] dragLoc = new float [2];
				float[] targetLoc = new float [2];
				dragLoc[0] = dragAction.getX();
				dragLoc[1] = dragAction.getY();
				targetLoc[0] = target.getX();
				targetLoc[1] = target.getY();
				//dragAction.getLocationOnScreen(dragLoc);
				//target.getLocationOnScreen(targetLoc);
				Log.v(TAG, "DragLoc:" + Float.toString(dragLoc[0]) + "," + Float.toString(dragLoc[1]));
				Log.v(TAG, "TargetLoc:" + Float.toString(targetLoc[0]) + "," + Float.toString(targetLoc[1]));
				dragAction.setX(targetLoc[0]);
				dragAction.setY(targetLoc[1]);
				target.setX(dragLoc[0]);
				target.setY(dragLoc[1]);
				dragAction.setVisibility(View.VISIBLE);
				//dragAction.getLocationOnScreen(dragLoc);
				//target.getLocationOnScreen(targetLoc);
				dragLoc[0] = dragAction.getX();
				dragLoc[1] = dragAction.getY();
				targetLoc[0] = target.getX();
				targetLoc[1] = target.getY();
				Log.v(TAG, "DragLoc:" + Float.toString(dragLoc[0]) + "," + Float.toString(dragLoc[1]));
				Log.v(TAG, "TargetLoc:" + Float.toString(targetLoc[0]) + "," + Float.toString(targetLoc[1]));
				savePreferencePos();
//				Drawable tem = dragAction.getDrawable();
//				dragAction.setImageDrawable(target.getDrawable());
//				target.setImageDrawable(tem);
				break;
			case DragEvent.ACTION_DRAG_ENDED:
				Log.v(TAG, "END!");
				final ImageView droppedView = (ImageView) event.getLocalState();
			    droppedView.post(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						droppedView.setVisibility(View.VISIBLE);
					} 
			    });
			default:
				break;
			}
			return true;
		}

	}
	
	public Bitmap toGrayscale(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();

		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	public Boolean checkToolValid(String ToolName) {
		File[] files = (new File(folderPath)).listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().equals(ToolName)) {
				return true;
			}
		}
		return false;
	}
	
	public void onTaskComplete(JSONObject result) {
		String toolName = null, dataSize = null, dataInfo;
		String dataUrl = null;
		try {
			toolName = result.getString("toolName");
			dataSize = result.getString("dataSize");
			dataInfo = result.getString("dataInfo");
			dataUrl = result.getString("dataUrl");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final String toolNamef = toolName;
		final String dataUrlf = dataUrl;
		
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
				.getPath());
		long bytesAvailable = (long) stat.getBlockSize()
				* (long) stat.getAvailableBlocks();
		long megAvailable = bytesAvailable / (1024 * 1024);
		long dataSizeLong = Long.parseLong(dataSize);
		
		if(dataSizeLong >= megAvailable){
			Toast.makeText(this, "Not enough space for data file!", Toast.LENGTH_SHORT).show();
			return;
		}

		LittleToolsFileManager toolFile = new LittleToolsFileManager();
		toolFile.addFolder(toolNamef);

		AlertDialog updateAlert = new AlertDialog.Builder(Tools.this).create();
		updateAlert.setTitle("Data Download");
		updateAlert
				.setMessage("You will download the data of" + toolName + "!");

		updateAlert.setButton(-3, "Yes", new DialogInterface.OnClickListener() {

			@Override
 		public void onClick(DialogInterface dialog, int which) { //
				// TODO // Auto-generated // method // stub
				downloadData(dataUrlf, toolNamef);

			}
		});

		updateAlert.setButton(-2, "No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) { //
				// TODO // Auto-generated // method // stub
				dialog.cancel();
			}
		});
		updateAlert.show();
	}

	public void launchTask(String toolName) {
		JSONObject requestJson = new JSONObject();
		try {
			requestJson.put("command", toolName);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// END

		// Using HttpJson class
		String uri = "http://babbage.cs.missouri.edu/~tzfyb/underGradResearch/littleToolsUpdate.php";
		HttpJson updateJson = new HttpJson(uri, requestJson, Tools.this, this);

		updateJson.jsonRequest();
	}
	
	private void downloadData(String downloadUrl, String toolName) {

		DownloadManager.Request request = new DownloadManager.Request(
				Uri.parse(downloadUrl));
		request.setDescription(toolName + " Data Package");
		request.setTitle("Data Package");

		request.allowScanningByMediaScanner();
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

		request.setDestinationInExternalPublicDir(
				Environment.DIRECTORY_DOWNLOADS, Environment.getExternalStorageDirectory().getAbsolutePath()
				+File.separatorChar + "littleTools" + File.separatorChar + toolName);

		// get download service and enqueue file
		DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		manager.enqueue(request);

		registerReceiver(onComplete, new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}

	BroadcastReceiver onComplete = new BroadcastReceiver() {
		public void onReceive(Context ctxt, Intent intent) {
			Toast.makeText(Tools.this, "Data downloaded", Toast.LENGTH_SHORT).show();
		}
	};
	
	private float getPreferencePos(String posName){
		 if (sharedpreferences.contains(posName))
	      {
	         return Float.valueOf(sharedpreferences.getString("toolAPosX", "-1"));
	      }
		 else
			 return -1;
	}
	
	private void setImagePos(){
		if(getPreferencePos("toolAX") != -1 && getPreferencePos("toolAY") != -1 && getPreferencePos("toolBX") != -1 ||
				getPreferencePos("toolBY") != -1 && getPreferencePos("toolCX") != -1 && getPreferencePos("toolCY") != -1 &&
				getPreferencePos("toolDX") != -1 && getPreferencePos("toolDY") != -1 && getPreferencePos("toolEX") != -1 &&
				getPreferencePos("toolEY") != -1 && getPreferencePos("toolFX") != -1 && getPreferencePos("toolFY") != -1){
			toolA.setX(getPreferencePos("toolAX"));
			toolA.setY(getPreferencePos("toolAY"));
			toolB.setX(getPreferencePos("toolBX"));
			toolB.setY(getPreferencePos("toolBY"));
			toolC.setX(getPreferencePos("toolCX"));
			toolC.setY(getPreferencePos("toolCY"));
			toolD.setX(getPreferencePos("toolDX"));
			toolD.setY(getPreferencePos("toolDY"));
			toolE.setX(getPreferencePos("toolEX"));
			toolE.setY(getPreferencePos("toolEY"));
			toolF.setX(getPreferencePos("toolFX"));
			toolF.setY(getPreferencePos("toolFY"));
		}
	}
	
	private void savePreferencePos(){
		Editor editor = sharedpreferences.edit();
		editor.putString("toolAX", Float.toString(toolA.getX()));
		editor.putString("toolAY", Float.toString(toolA.getY()));
		editor.putString("toolBX", Float.toString(toolB.getX()));
		editor.putString("toolBY", Float.toString(toolB.getY()));
		editor.putString("toolCX", Float.toString(toolC.getX()));
		editor.putString("toolCY", Float.toString(toolC.getY()));
		editor.putString("toolDX", Float.toString(toolD.getX()));
		editor.putString("toolDY", Float.toString(toolD.getY()));
		editor.putString("toolEX", Float.toString(toolE.getX()));
		editor.putString("toolEY", Float.toString(toolE.getY()));
		editor.putString("toolFX", Float.toString(toolF.getX()));
		editor.putString("toolFY", Float.toString(toolF.getY()));
	}
}