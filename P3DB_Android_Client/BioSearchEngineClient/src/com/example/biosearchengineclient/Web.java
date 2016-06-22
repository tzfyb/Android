package com.example.biosearchengineclient;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web extends Activity{
	
	String url = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		
		url = getIntent().getStringExtra("url");
		WebView view = (WebView) this.findViewById(R.id.webView);
		view.getSettings().setBuiltInZoomControls(true);
		view.getSettings().setLoadWithOverviewMode(true);
	    view.getSettings().setUseWideViewPort(true);
		view.getSettings().setJavaScriptEnabled(true);
		view.loadUrl(url);
		
		view.setWebViewClient(new WebViewClient());
	}
	
	/*private int getScale(){
	    Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay(); 
	    int width = display.getWidth(); 
	    Double val = new Double(width)/new Double(PIC_WIDTH);
	    val = val * 100d;
	    return val.intValue();
	}*/
	

}
