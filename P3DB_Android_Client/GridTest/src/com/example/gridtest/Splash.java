package com.example.gridtest;

import com.viewpagerindicator.LinePageIndicator;
import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;



public class Splash extends FragmentActivity{

	private static final int NUM_PAGES=3;
	
	private ViewPager splashPager;
	
	private PagerAdapter splashPagerAdapter;
	
	MediaPlayer welcomeSong;
	
	LinePageIndicator splashIndicator;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		welcomeSong = MediaPlayer.create(Splash.this, R.raw.moonlight);
		welcomeSong.start();
		
		splashPager = (ViewPager) findViewById(R.id.splashPager);
        splashPagerAdapter = new SplashSlideAdapter(getSupportFragmentManager());
        splashPager.setAdapter(splashPagerAdapter);
        
        splashIndicator = (LinePageIndicator) findViewById(R.id.splashIndicator);
        //splashIndicator.setFades(false);
        splashIndicator.setViewPager(splashPager); 
        LittleToolsFileManager ini = new LittleToolsFileManager();
	}
	
	private class SplashSlideAdapter extends FragmentPagerAdapter {
        public SplashSlideAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
        	if(position == 0){
        		fragment = new SplashA();
        	}
        	if(position == 1){
        		fragment = new SplashB();
        	}
        	if(position == 2){
        		fragment = new SplashC();
        	}
        	return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	
}
