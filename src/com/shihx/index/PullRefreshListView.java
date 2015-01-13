package com.shihx.index;

import com.shihx.index.listview.RefreshableView;
import com.shihx.index.listview.RefreshableView.PullToRefreshListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author shihx1
 * @date 5:34:35 PM Jan 13, 2015
 * @todo TODO
 */
public class PullRefreshListView extends Activity {
	
	RefreshableView refreshableView;
	ListView listView;
	ArrayAdapter<String> adapter;
	String[] items = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		setContentView(R.layout.ui_refresh_list_lay);
		refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);  
        listView = (ListView) findViewById(R.id.list_view);  
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);  
        listView.setAdapter(adapter);  
        refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void Refresh() {
				// TODO Auto-generated method stub
				try {  
                    Thread.sleep(1000);  
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
                refreshableView.finishRefreshing();  
			}
		}, 0);
	}
}
