package com.example.gridtest;

import java.lang.reflect.Method;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements TabListener,
		OnItemClickListener, AsyncTaskCompleteListener<JSONObject> {
	

	
	private Button updateButton;
	private static final int NUM_PAGES = 3;

	private ViewPager mainPager;
	private PagerAdapter mainPagerAdapter;
	private ActionBar actionBar;
	private ListView navigationListView;
	private DrawerLayout drawerLayout;
	private String[] options;
	private ActionBarDrawerToggle drawerListener;
	private MyNavigationAdapter myNAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//check Version
		checkVersion();

		// for navigation list view
		options = getResources().getStringArray(R.array.options);
		myNAdapter = new MyNavigationAdapter(this);
		navigationListView = (ListView) findViewById(R.id.drawerList);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		navigationListView.setAdapter(myNAdapter);
		navigationListView.setOnItemClickListener(this);
		drawerListener = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer_toggle, R.string.drawer_open,
				R.string.drawer_close) {

			@Override
			public void onDrawerClosed(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				super.onDrawerOpened(drawerView);
			}

		};
		drawerLayout.setDrawerListener(drawerListener);

		// for customed action bar
		LinearLayout main_ll = (LinearLayout) findViewById(R.id.mainpage_titlebar);
		getSupportActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
		getSupportActionBar().setCustomView(R.layout.mainpage_title_bar);
		// getSupportActionBar().setHomeButtonEnabled(true);

		// badge drawable
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		updateButton = (Button) findViewById(R.id.update);
		LayerDrawable icon = (LayerDrawable) updateButton.getBackground();
		Utils.setBadgeCount(this, icon, 3);

		updateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkVersion();
				Intent update = new Intent("android.intent.action.UPDATE");
				startActivity(update);
			}
		});

		mainPager = (ViewPager) findViewById(R.id.mainPager);
		mainPagerAdapter = new MainSlideAdapter(getSupportFragmentManager());
		mainPager.setAdapter(mainPagerAdapter);
		mainPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(arg0);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ActionBar.Tab tab1 = actionBar.newTab();
		tab1.setText("Home");
		tab1.setTabListener(this);

		ActionBar.Tab tab2 = actionBar.newTab();
		tab2.setText("Tool Box");
		tab2.setTabListener(this);

		ActionBar.Tab tab3 = actionBar.newTab();
		tab3.setText("Wegit");
		tab3.setTabListener(this);

		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
		actionBar.addTab(tab3);

	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        getMenuInflater().inflate(R.menu.main, menu);
      
        return true;
    }
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu)
	{
	    if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
	        if(menu.getClass().getSimpleName().equals("MenuBuilder")){
	            try{
	                Method m = menu.getClass().getDeclaredMethod(
	                    "setOptionalIconsVisible", Boolean.TYPE);
	                m.setAccessible(true);
	                m.invoke(menu, true);
	            }
	            catch(NoSuchMethodException e){
	                //Log.e(TAG, "onMenuOpened", e);
	            }
	            catch(Exception e){
	                throw new RuntimeException(e);
	            }
	        }
	    }
	    return super.onMenuOpened(featureId, menu);
	}


	private class MainSlideAdapter extends FragmentPagerAdapter {
		public MainSlideAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			if (position == 0) {
				fragment = new MainActivityA();
			}
			if (position == 1) {
				fragment = new MainActivityB();
			}
			if (position == 2) {
				fragment = new MainActivityC();
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		mainPager.setCurrentItem(arg0.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (drawerListener.onOptionsItemSelected(item)) {
			return true;
		}
		
		switch (item.getItemId()){
    	/*case R.id.home: 
    		Intent intentHome = new Intent(this, MainActivity.class);
    		startActivity(intentHome);
    		return true;*/
    
    		
    	case R.id.mail:
    		Toast.makeText(this, "mail selected", Toast.LENGTH_SHORT).show();
    		return true;
    	case R.id.tools:
    		Toast.makeText(this, "tools selected", Toast.LENGTH_SHORT).show();
    		return true;
    	case R.id.update:
    		Intent update = new Intent("android.intent.action.UPDATE");
			startActivity(update);
    		return true; 
    		
    		
    	default:
    		return super.onOptionsItemSelected(item);
    			
    	}
		
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		drawerListener.syncState();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Toast.makeText(this, options[position] + " was selected ",
				Toast.LENGTH_LONG).show();
		if (position == 0) {
			Intent web = new Intent("android.intent.action.WEB");
			startActivity(web);
		}

		if (position == 2) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("https://www.youtube.com/watch?v=moSFlvxnbgk"));
			startActivity(intent);
		}

	}
	
	public void checkVersion(){
	
		//check update everytime when start this activity

		
		JSONObject requestJson = new JSONObject();
		try {
			requestJson.put("command", "versionNum");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String uri = "http://babbage.cs.missouri.edu/~tzfyb/underGradResearch/littleToolsUpdate.php";
		HttpJson checkVersionJson = new HttpJson(uri, requestJson, MainActivity.this, this);
		checkVersionJson.setNeedProgressDialog(false);

		checkVersionJson.jsonRequest();
		
	}

	@Override
	public void onTaskComplete(JSONObject result) {
		// TODO Auto-generated method stub
		
		Context context = getApplicationContext(); 
		PackageManager packageManager = context.getPackageManager();
		String packageName = context.getPackageName();
		int myVersionNum = 0;
		try {
			PackageInfo pinfo = packageManager.getPackageInfo(
					packageName, 0);
			myVersionNum = pinfo.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		int newVersionNum = 0;
		try {
			newVersionNum = Integer.parseInt(result.getString("versionNum"));
		} catch (NumberFormatException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		updateButton = (Button) findViewById(R.id.update);
		if(myVersionNum >= newVersionNum){
			LayerDrawable icon = (LayerDrawable) updateButton.getBackground();
			Utils.setBadgeCount(MainActivity.this, icon, 0);
		}
		else{
			LayerDrawable icon = (LayerDrawable) updateButton.getBackground();
			Utils.setBadgeCount(MainActivity.this, icon, 3);
		}
	}
}

class MyNavigationAdapter extends BaseAdapter {

	private Context context;

	String[] options;
	int[] images = { R.drawable.ic_facebook, R.drawable.ic_twitter,
			R.drawable.ic_youtube, R.drawable.ic_weixin };

	public MyNavigationAdapter(Context context) {
		this.context = context;
		options = context.getResources().getStringArray(R.array.options);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return options.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return options[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.drawer_list_row, parent, false);

		} else {
			row = convertView;
		}
		TextView drawerRowTextView = (TextView) row
				.findViewById(R.id.drawer_list_row_text);
		ImageView drawerRowImageView = (ImageView) row
				.findViewById(R.id.drawer_list_row_icon);
		drawerRowTextView.setText(options[position]);
		drawerRowImageView.setImageResource(images[position]);

		return row;
	}

}
