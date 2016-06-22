package com.example.gridtest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchFile extends Activity {
	//private String folderName = "littleTools";
	//private String fileNameList;
	Button addfolder;
	Button deletefolder;
	Button search;
	Button addFile;
	Button showFileList;
	Button deleteFile;
	Button showFileContent;
	String fileTitle;
	String fileContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_file);
		
		addfolder = (Button)findViewById(R.id.searchFileAddFolder);
		deletefolder = (Button)findViewById(R.id.searchFileDeleteFolder);
		addFile = (Button)findViewById(R.id.searchFileAddFile);
		showFileList = (Button)findViewById(R.id.searchShowFilesButton);
		search = (Button)findViewById(R.id.searchFileSearch);
		deleteFile = (Button)findViewById(R.id.searchDeleteFile);
		showFileContent = (Button)findViewById(R.id.searchFileContent);
		
		final LittleToolsFileManager fileDemo = new LittleToolsFileManager();
		
		addfolder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//addFolder();
				fileDemo.addFolder("tool1");
			}
		});
		
		deletefolder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//deleteFolder();
				fileDemo.rmFolder("tool1");
			}
		});
		
		addFile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//addFile();
				fileDemo.addFile("tool1a.data", "tool1");
				fileDemo.addFile("tool1b.data", "tool1");
				fileDemo.addFile("tool1c.data", "tool1");
				fileTitle = ((EditText) findViewById(R.id.searchFileTitleInput))
						.getText().toString();
				fileContent = ((EditText) findViewById(R.id.searchFileContentInput))
						.getText().toString();
				fileDemo.addFolder("tool2");
				fileDemo.addFile(fileTitle, "tool2", fileContent);
			}
		});
		
		showFileList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String fileList = fileDemo.listFile("tool1");
				//getAllFiles(folderPath);
				TextView tv = (TextView)findViewById(R.id.searchShowFilesList);
				tv.setText(fileList);
			}
		});
		
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//String folderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + folderName;
				String filename = ((EditText)findViewById(R.id.searchFileSearchInput)).getText().toString();
				
				if(fileDemo.findFile(filename, "tool1")){
					Toast.makeText(SearchFile.this, "File " + filename + " exists!", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(SearchFile.this, "File " + filename + " NOT exists!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		deleteFile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//String folderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + folderName;
				String filename = ((EditText)findViewById(R.id.searchFileSearchInput)).getText().toString();
				if(fileDemo.rmFile(filename, "tool1")){
					
					Toast.makeText(SearchFile.this, "File " + filename + "has been deleted!", Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(SearchFile.this, "File " + filename + " NOT exists!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		showFileContent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//String folderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + folderName;
				String filename = ((EditText)findViewById(R.id.searchFileSearchInput)).getText().toString();
				String readContent = null;
//				if(showFileContent(filename, folderPath)){	
//					Toast.makeText(SearchFile.this, "File " + filename + "shown!", Toast.LENGTH_SHORT).show();
//				}
//				else{
//					Toast.makeText(SearchFile.this, "File " + filename + " NOT exists!", Toast.LENGTH_SHORT).show();
//				}
				String content = fileDemo.readFile(filename, "tool2");
				TextView tv = (TextView) findViewById(R.id.searchShowFilesList);
				tv.setText(content);
				
			}
		});
	}

//	public Boolean showFileContent(String filename, String folderPath) {
//		addFolder();
//		File[] files = (new File(folderPath)).listFiles();
//		for (int i = 0; i < files.length; i++) {
//			if (files[i].getName().equals(filename)) {
//				try {
//					String content = getStringFromFile(files[i].getAbsolutePath());
//					TextView tv = (TextView) findViewById(R.id.searchShowFilesList);
//					tv.setText(content);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public Boolean deleteFile(String filename, String folderPath) {
//		addFolder();
//		File[] files = (new File(folderPath)).listFiles();
//		for (int i = 0; i < files.length; i++) {
//			if (files[i].getName().equals(filename)) {
//				files[i].delete();
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public Boolean search(String filename, String folderPath) {
//		addFolder();
//		File[] files = (new File(folderPath)).listFiles();
//		for (int i = 0; i < files.length; i++) {
//			if (files[i].getName().equals(filename)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public void getAllFiles(String folderPath) {
//		fileNameList = "";
//		addFolder();
//		File[] files = (new File(folderPath)).listFiles();
//		for (int i = 0; i < files.length; i++) {
//			fileNameList += files[i].getName() + ";";
//		}
//	}
//
//	public void addFile() {
//		fileTitle = ((EditText) findViewById(R.id.searchFileTitleInput))
//				.getText().toString();
//		fileContent = ((EditText) findViewById(R.id.searchFileContentInput))
//				.getText().toString();
//
//		if (fileTitle.isEmpty()) {
//			Toast.makeText(SearchFile.this, "Please enter file title!",
//					Toast.LENGTH_SHORT).show();
//		} else {
//			File f = null;
//			if (Environment.getExternalStorageState().equals(
//					Environment.MEDIA_MOUNTED)) {
//				String baseDir = Environment.getExternalStorageDirectory()
//						.getAbsolutePath();
//				f = new File(baseDir + File.separatorChar + folderName);
//				if (!f.isDirectory()) {
//					addFolder();
//				} else {
//					File outputFile = new File(f, fileTitle);
//					try {
//						FileOutputStream fos = new FileOutputStream(outputFile);
//						fos.write(fileContent.getBytes());
//						fos.close();
//						Toast.makeText(SearchFile.this,
//								fileTitle + " created!", Toast.LENGTH_SHORT)
//								.show();
//					} catch (FileNotFoundException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//				}
//			} else {
//				Toast.makeText(SearchFile.this, "MEDIA NOT MOUNTED!",
//						Toast.LENGTH_SHORT).show();
//			}
//		}
//	}
//
//	public void addFolder() {
//		if (Environment.getExternalStorageState().equals(
//				Environment.MEDIA_MOUNTED)) {
//			String baseDir = Environment.getExternalStorageDirectory()
//					.getAbsolutePath();
//			File f = new File(baseDir + File.separatorChar + folderName);
//			if (!f.isDirectory()) {
//				f.mkdir();
//				Toast.makeText(SearchFile.this, "Make Directory!",
//						Toast.LENGTH_SHORT).show();
//			} else {
//				Toast.makeText(SearchFile.this, "Directory Exists!",
//						Toast.LENGTH_SHORT).show();
//			}
//
//		} else {
//			Toast.makeText(SearchFile.this, "MEDIA NOT MOUNTED!",
//					Toast.LENGTH_SHORT).show();
//		}
//
//	}
//
//	public void deleteFolder() {
//		File f = null;
//		if (Environment.getExternalStorageState().equals(
//				Environment.MEDIA_MOUNTED)) {
//			String baseDir = Environment.getExternalStorageDirectory()
//					.getAbsolutePath();
//			f = new File(baseDir + File.separatorChar + folderName);
//		} else {
//			Toast.makeText(SearchFile.this, "MEDIA NOT MOUNTED!",
//					Toast.LENGTH_SHORT).show();
//		}
//
//		DeleteRecursive(f);
//	}
//
//	public void DeleteRecursive(File fileOrDirectory) {
//		if (fileOrDirectory.isDirectory())
//			for (File child : fileOrDirectory.listFiles())
//				DeleteRecursive(child);
//
//		fileOrDirectory.delete();
//	}
//
//	public static String getStringFromFile(String filePath) throws Exception {
//		File fl = new File(filePath);
//		FileInputStream fin = new FileInputStream(fl);
//		String ret = convertStreamToString(fin);
//		// Make sure you close all streams.
//		fin.close();
//		return ret;
//	}
//
//	public static String convertStreamToString(InputStream is) throws Exception {
//		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//		StringBuilder sb = new StringBuilder();
//		String line = null;
//		while ((line = reader.readLine()) != null) {
//			sb.append(line).append("\n");
//		}
//		reader.close();
//		return sb.toString();
//	}
}
