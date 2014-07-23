package com.shihx.index;

import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;


public class ActionItemActivity extends SherlockActivity{
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		 boolean isLight = R.style.Theme_Sherlock == R.style.Theme_Sherlock_Light;
		 menu.add("Save")
         .setIcon(isLight ? R.drawable.ic_compose_inverse : R.drawable.ic_compose)
         .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		 menu.add("Search")
         .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		 menu.add("Refresh")
         .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
         .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //This uses the imported MenuItem from ActionBarSherlock
        Toast.makeText(this, "Got click: " + item.toString(), Toast.LENGTH_SHORT).show();
        return true;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
	}
}
