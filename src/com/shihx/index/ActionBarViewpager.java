package com.shihx.index;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.shihx.index.viewpager.ViewPagerFragment;

@SuppressLint("NewApi")
public class ActionBarViewpager extends SherlockFragmentActivity implements SearchView.OnQueryTextListener,SearchView.OnSuggestionListener{
	final String TAG = "ActionBarViewpager";
	final int LOCAL_VIDEO = 10001;
	final int LOCAL_AUDIO = 10002;
	final int LOCAL_FILE = 10003;
	private int CURRENTACTION = LOCAL_VIDEO;
	ActionMode currentMode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actionbar_viewpager);
		//currentMode = startActionMode(new LocalVideoActionBar());
		fillFragment();
	}
	
	private void fillFragment(){
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.viewpager_content_frame, new ViewPagerFragment()).commit();
	}
	
	/*public void showVideoAction(View v){
		CURRENTACTION = LOCAL_VIDEO;
		//int i = CURRENTACTION == LOCAL_VIDEO ? 1 : 0;
		invalidateOptionsMenu();
	}
	
	public void showAudioAction(View v){
		CURRENTACTION = LOCAL_AUDIO;
		invalidateOptionsMenu();
	}
		
	
	public void showFileAction(View v){
		CURRENTACTION = LOCAL_FILE;
		invalidateOptionsMenu();
	}*/

	private void createActionMode(Menu menu){
		switch(CURRENTACTION){
		case LOCAL_VIDEO:
			SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
			searchView.setMaxWidth(600);
			searchView.setQueryHint("input words");
			searchView.setOnQueryTextListener(this);
			searchView.setOnSuggestionListener(this);
			
			menu.add("Search").setIcon(R.drawable.menu_search_icon)
			.setActionView(searchView)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			menu.add("Refresh").setIcon(R.drawable.action_refresh_icon)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM );
			menu.add("Setting").setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			break;
		case LOCAL_AUDIO:
			menu.add("Plus").setIcon(R.drawable.action_plus_icon)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			menu.add("Setting").setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			break;
		case LOCAL_FILE:
			menu.add("Refresh").setIcon(R.drawable.action_refresh_icon)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			menu.add("Setting").setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		Log.i(TAG, "enter onCreateOptionsMenu");
		menu.clear();
		createActionMode(menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onSuggestionSelect(int position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSuggestionClick(int position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		Toast.makeText(this, "You searched for: " + query, Toast.LENGTH_LONG).show();
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}
}
