package com.rittik.kritanj;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;

import com.rittik.kritanj.adapter.NavDrawerListAdapter;
import com.rittik.kritanj.model.NavDrawerItem;

public class MainActivity extends Activity {
	
	    private DrawerLayout mDrawerLayout;
	    private ListView mDrawerList;
	    private ActionBarDrawerToggle mDrawerToggle;
	 
	    // nav drawer title
	    private CharSequence mDrawerTitle;
	 
	    // used to store app title
	    private CharSequence mTitle;
	 
	    // slide menu items
	    private String[] navMenuTitles;
	    private TypedArray navMenuIcons;
	 
	    private ArrayList<NavDrawerItem> navDrawerItems;
	    private NavDrawerListAdapter adapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTitle = mDrawerTitle = getTitle();
		 
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
 
        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
 
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
 
        navDrawerItems = new ArrayList<NavDrawerItem>();
 
        // adding nav drawer items to array
        // Kritanj
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Navodaya
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Gameathlon
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Bridge-it, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        // iCoding
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // electrocution, We  will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));
         
 
        // Recycle the typed array
        navMenuIcons.recycle();
 
        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
 
        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
 
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
                ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
 
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
        
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
	}
    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
 
     /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
        case 0:
            fragment = new KritanjFragment();
            break;
        case 1:
            fragment = new NavodayaFragment();
            break;
        case 2:
            fragment = new GameathlonFragment();
            break;
        case 3:
            fragment = new icodingFragment();
            break;
        case 4:
            fragment = new BuilditFragment();
            break;
        case 5:
            fragment = new electrocutionFragment();
            break;
 
        default:
            break;
        }
 
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
 
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }       
    
 
	
	//Forcing Menu Button to appear on all Devices.
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
			.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
			}
			} catch (Exception e) {
			e.printStackTrace();
			}
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) 
		{
	        // toggle nav drawer on selecting action bar app icon/title
	        if (mDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
		
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_help:
            // help action
        	Intent emailIntent1 = new Intent(android.content.Intent.ACTION_SEND);
			emailIntent1.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] 
					{getResources().getString(R.string.email_address)});
			emailIntent1.putExtra(android.content.Intent.EXTRA_SUBJECT, 
					getResources().getText(R.string.email_subject_help));
			emailIntent1.setType("plain/text");
			startActivity(Intent.createChooser(emailIntent1, "Contact Developer"));
            return true;
        default:
            return super.onOptionsItemSelected(item);
         // toggle nav drawer on selecting action bar app icon/title
            
        
        }
   }
	/**
     * Launching new activity
     * */
    private void LocationFound() {
        Intent i = new Intent(MainActivity.this, Location.class);
        startActivity(i);
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
 
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
