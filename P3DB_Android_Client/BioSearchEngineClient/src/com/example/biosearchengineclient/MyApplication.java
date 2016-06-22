package com.example.biosearchengineclient;

import org.json.JSONObject;

import android.app.Application;

public class MyApplication extends Application{
	private JSONObject finalResult = null;
	private int currentPage = -1;
	private JSONObject currentJson = null;
	
	public JSONObject getFinalResult(){
		return finalResult;
	}
	
	public void setFinalResult(JSONObject result){
		this.finalResult = result;
	}
	
	public int getCurrentPage(){
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage){
		this.currentPage = currentPage;
	}
	
	public JSONObject getCurrentJson(){
		return currentJson;
	}
	
	public void setCurrentJson(JSONObject currentJson){
		this.currentJson = currentJson;
	}
}
