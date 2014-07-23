package com.shihx.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockListActivity;

/**
 * index for other
 * ui demos
 * @author shihx1
 *
 */
public class IndexActivity extends SherlockListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setListAdapter(new SimpleAdapter(this, getData(""), android.R.layout.simple_list_item_1, new String[]{"title"}, new int[]{android.R.id.text1}));
	}
	
	protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }
	
	protected List<Map<String, Object>> getData(String prefix){
		List<Map<String,Object>> myData = new ArrayList<Map<String,Object>>();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
		mainIntent.addCategory("com.shihx.index.demos");
		
		PackageManager pm = getPackageManager();
		List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);
		if(list == null)
			return myData;
		
		String[] prefixPath;
		String prefixWithSlash = prefix;
		if(prefix.equals("")){
			prefixPath = null;
		}else{
			prefixPath = prefix.split("/");
            prefixWithSlash = prefix + "/";
		}
		int len = list.size();
		Map<String,Boolean> entries = new HashMap<String, Boolean>(); 
		for(int i=0;i<len;i++){
			ResolveInfo info = list.get(i);
			CharSequence labelSeq = info.loadLabel(pm);
			String label = labelSeq != null ? labelSeq.toString() : info.activityInfo.name;
			if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {
				String[] labelPath = label.split("/");
				String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];
				if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    addItem(myData, nextLabel, activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
                } else {
                    if (entries.get(nextLabel) == null) {
                        addItem(myData, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }
			}
		}
		return myData;
	}
	
	protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, IndexActivity.class);
        result.putExtra("com.example.android.apis.Path", path);
        return result;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	// TODO Auto-generated method stub
    	//super.onListItemClick(l, v, position, id);
    	
		Map<String,Object> map = (Map<String,Object>)l.getItemAtPosition(position);
    	Intent intent = (Intent) map.get("intent");
        startActivity(intent);
    }
}
