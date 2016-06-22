package com.example.biosearchengineclient;

import org.json.JSONException;

public interface AsyncTaskCompleteListener<T> {
	public void onTaskComplete(T result);
}
