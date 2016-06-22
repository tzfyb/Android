package com.example.gridtest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

public class LittleToolsFileManager {
	private static final String TAG = "LT_FileManager";
	private String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath()
			+File.separatorChar + "littleTools";

	
	LittleToolsFileManager(){
		
		/*Check if external storage mounted or not
		 * Check if root dir exists or not, if not
		 * create it.
		 */
		File f = null;
		if(Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)){
			f = new File(rootDir);
			if(!f.isDirectory()){
				f.mkdir();
				Log.v(TAG, "Root Dir Created!");
			}
			else{
				Log.v(TAG, "Root Dir Creating Failed!");
			}
		}
		else{
			Log.v(TAG, "External Storage Not Found!");
		}
	}
	
	public Boolean checkFolder(String folderRelativePath){
		File f = null;
		f = new File(rootDir + File.separatorChar + folderRelativePath);
		return f.isDirectory();
	}
	
	public void addFolder(String folderRelativePath){
		File f = null;
		f = new File(rootDir + File.separatorChar + folderRelativePath);
		if(!f.isDirectory()){
			f.mkdir();
		}
	}
	
	public void rmFolder(String folderRelativePath){
		if(checkFolder(folderRelativePath)){
			File file = new File(folderRelativePath);
			DeleteRecursive(file);
		}
	}
	
	private void DeleteRecursive(File fileOrDirectory){
		if(fileOrDirectory.isDirectory()){
			for (File child : fileOrDirectory.listFiles())
				DeleteRecursive(child);
		}
		
		fileOrDirectory.delete();
	}
	
	public Boolean findFile(String filename, String folderRelativePath){
		if(!checkFolder(folderRelativePath)){
			return false;
		}
		
		File[] files = (new File(folderRelativePath)).listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().equals(filename)) {
				return true;
			}
		}
		return false;
		
	}
	
	public Boolean addFile(String fileName, String folderRelativePath){
		if(fileName.isEmpty() || folderRelativePath.isEmpty()){
			return false;
		}
		else{
			File f = null;
			f = new File(rootDir + File.separatorChar + folderRelativePath);
			if(!checkFolder(folderRelativePath)){
				return false;
			}
			else{
				if(!(new File(rootDir + File.separatorChar + folderRelativePath + File.separatorChar + fileName)).isFile()){
					new File(f, fileName);
					return true;
				}
				return false;
			}
		}
	}
	
	public Boolean addFile(String fileName, String folderRelativePath, String content){
		if(!checkFolder(folderRelativePath)){
			return false;
		}
		else{
			File f = null;
			f = new File(rootDir + File.separatorChar + folderRelativePath);
			
			if(!checkFolder(folderRelativePath)){
				return false;
			}
			else{
				File outputFile = new File(rootDir + File.separatorChar + folderRelativePath + File.separatorChar + fileName);
				if(!outputFile.isFile()){
					new File(f, fileName);
				}
					FileOutputStream fos;
					try {
						fos = new FileOutputStream(outputFile);
						fos.write(content.getBytes());
						fos.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return true;
			}
		}
	}
	
	public Boolean rmFile(String fileName, String folderRelativePath){
		if(!checkFolder(folderRelativePath)){
			return false;
		}
		else{
			File f = null;
			f = new File(rootDir + File.separatorChar + folderRelativePath);
			
			if(!checkFolder(folderRelativePath)){
				return false;
			}
			else{
				File[] files = f.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].getName().equals(fileName)) {
						files[i].delete();
						return true;
					}
				}
				return false;
			}
		}
	}
		
	public String listFile(String folderRelativePath){
		String fileNameList = "";
		if(!checkFolder(folderRelativePath)){
			return null;
		}
		else{
			File[] files = (new File(rootDir + File.separatorChar + folderRelativePath))
					.listFiles();
			for(int i = 0; i < files.length; i++){
				fileNameList += files[i].getName() + ";";
			}
			return fileNameList;
		}
	}
	
	public String readFile(String fileName, String folderRelativePath){
		if(!checkFolder(folderRelativePath)){
			return null;
		}
		else{
			File[] files = (new File(rootDir + File.separatorChar + folderRelativePath))
					.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().equals(fileName)) {
					try {
						String content = getStringFromFile(files[i].getAbsolutePath());
						return content;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return null;
		}
	}
	
	private static String getStringFromFile(String filePath) throws Exception {
		File fl = new File(filePath);
		FileInputStream fin = new FileInputStream(fl);
		String ret = convertStreamToString(fin);
		// Make sure you close all streams.
		fin.close();
		return ret;
	}

	private static String convertStreamToString(InputStream is) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\n");
		}
		reader.close();
		return sb.toString();
	}

}
