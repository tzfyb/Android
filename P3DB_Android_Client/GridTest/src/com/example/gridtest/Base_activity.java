package com.example.gridtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;

public class Base_activity extends Activity{
	private int notification = 3;
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        getMenuInflater().inflate(R.menu.main, menu);
       
        //Get notifications MenuItem and its LayerDrawable (layer-list)
        MenuItem itemSettings = menu.findItem(R.id.action_settings);
        LayerDrawable iconSettings = (LayerDrawable) itemSettings.getIcon();
        Utils.setBadgeCount(this, iconSettings, notification);
        
      //Get notifications MenuItem and its LayerDrawable (layer-list)
        //MenuItem itemMail = menu.findItem(R.id.mail);
        //LayerDrawable iconMail = (LayerDrawable) itemMail.getIcon();
        //Utils.setBadgeCount(this, iconMail, notification);
        
        
        return true;
    }
	
	 /*
    Updates the count of notifications in the ActionBar.
     */
    private void updateNotificationsBadge(int count) {
        notification = count;

        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu(); 
    }

    /*
    Sample AsyncTask to fetch the notifications count
    */
    class FetchCountTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            // example count. This is where you'd 
            // query your data store for the actual count.
            return 5; 
        }

        @Override
        public void onPostExecute(Integer count) {
            updateNotificationsBadge(count);
        }
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
