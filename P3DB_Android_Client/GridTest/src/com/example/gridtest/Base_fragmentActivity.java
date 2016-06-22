package com.example.gridtest;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Base_fragmentActivity extends FragmentActivity{
	private int mNotificationsCount = 3;
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        getMenuInflater().inflate(R.menu.main, menu);
        
        MenuItem item = menu.findItem(R.id.action_settings);
        LayerDrawable icon = (LayerDrawable) item.getIcon();
        
        Utils.setBadgeCount(this, icon, mNotificationsCount);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	/*case R.id.home: 
    		Intent intentHome = new Intent(this, MainActivity.class);
    		startActivity(intentHome);
    		return true;*/
    
    		
    	case R.id.action_settings:
    		return true;
    	
    	case R.id.update:
    		Intent intent_update = new Intent(this ,Update.class);
    		startActivity(intent_update);
    		return true; 
    		
    		
    	default:
    		return super.onOptionsItemSelected(item);
    			
    	}
    }
}
