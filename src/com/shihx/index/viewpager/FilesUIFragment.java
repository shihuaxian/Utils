package com.shihx.index.viewpager;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.shihx.index.R;
import com.shihx.index.adapter.FileAdapter;
import com.shihx.index.model.Item;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class FilesUIFragment extends Fragment implements OnKeyListener{
	final String TAG = "FilesUIFragment";
	
	private View rootView;
	private TextView indexTxt;
	private ListView mListView;
	private ArrayList<String> pathDirsList;
	private FileAdapter mFileAdapter;
	private File path;
	private String chosenFile;
	private List<Item> fileList;
	
	private static int currentAction = -1;
	private static final int SELECT_DIRECTORY = 1;
	private static final int SELECT_FILE = 2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "enter onCreateView this.getId() is:"+this.getId());
		rootView = inflater.inflate(R.layout.file_browser, container, false);
		return rootView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG, "enter onActivityCreated");
		initViews();
		initData();
		parseDirectoryPath();
		handleDataAndShow();
		initListener();
	}
	
	private void handleDataAndShow(){
		loadFileList();
		if(mFileAdapter==null){
			mFileAdapter = new FileAdapter(getActivity(), fileList);
			mListView.setAdapter(mFileAdapter);
		}else{
			mFileAdapter.freshData(fileList);
			mFileAdapter.notifyDataSetChanged();
		}
		updateCurrentDirTextView();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i(TAG, "enter onResume");
	}
	
	private void initData(){
		pathDirsList = new ArrayList<String>();
		fileList  = new ArrayList<Item>();
		if (Environment.getExternalStorageDirectory().isDirectory()
				&& Environment.getExternalStorageDirectory().canRead())
			path = Environment.getExternalStorageDirectory();
		else
			path = new File("/");
	}
	
	
	private void initViews(){
		mListView = (ListView)rootView.findViewById(R.id.dt_file_listview);
		indexTxt = (TextView)rootView.findViewById(R.id.dt_file_index_txt);
	}
	private void showToast(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
	}
	
	private void initListener(){
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				chosenFile = fileList.get(position).file;
				File sel = new File(path + "/" + chosenFile);
				if(position==0){
					loadDirectoryUp();
					return;
				}
				if(sel.isDirectory()){
					if (sel.canRead()) {
						// Adds chosen directory to list
						pathDirsList.add(chosenFile);
						path = new File(sel + "");
						//loadFileList();
						//startDataTask("child");
						handleDataAndShow();
						Log.d(TAG, path.getAbsolutePath());
					} else {// if(sel.canRead()) {
						showToast("Path does not exist or cannot be read");
					}
				}else{
					startAudioPlayer(sel.getAbsolutePath());
				}
			}
		});
	}
	
	private void startAudioPlayer(String uri){
		/*Intent retIntent = new Intent();
		retIntent.setClass(getActivity(), AudioPlayerActivity.class);
		retIntent.putExtra(Constant.FILE_MSG, uri);
		startActivity(retIntent);*/
	}
	
	private void parseDirectoryPath() {
		pathDirsList.clear();
		String pathString = path.getAbsolutePath();
		String[] parts = pathString.split("/");
		int i = 0;
		while (i < parts.length) {
			pathDirsList.add(parts[i]);
			i++;
		}
	}
	
	private void updateCurrentDirTextView(){
		int i = 0;
		String curDirString = "";
		while (i < pathDirsList.size()) {
			curDirString += pathDirsList.get(i) + "/";
			i++;
		}
		if (pathDirsList.size() == 0) {
			/*((Button) this.findViewById(R.id.upDirectoryButton))
					.setEnabled(false);*/
			curDirString = "/";
		}
		indexTxt.setText(curDirString);
	}
	
	/**
	 * for key back
	 */
	private void loadDirectoryUp() {
		// present directory removed from list
		String s = pathDirsList.remove(pathDirsList.size() - 1);
		// path modified to exclude present directory
		path = new File(path.toString().substring(0,
				path.toString().lastIndexOf(s)));
		fileList.clear();
		handleDataAndShow();
	}
	
	private void loadFileList() {
		try {
			path.mkdirs();
		} catch (SecurityException e) {
			Log.e(TAG, "unable to write on the sd card ");
		}
		fileList.clear();
		if(path.exists() && path.canRead()){
			FilenameFilter filter = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String filename) {
					// TODO Auto-generated method stub
					File sel = new File(dir, filename);
					boolean showReadableFile = sel.canRead();
					if (currentAction == SELECT_DIRECTORY) {
						return (sel.isDirectory() && showReadableFile);
					}
					return true;
				}
			};
			String[] fList = path.list(filter);
			//fileList.add(new Item(path, ".."));
			for(int i= 0; i<fList.length; i++){
				Item item = new Item(path, fList[i]);
				if(item.isDirectory()){
					item.setIcon(R.drawable.dt_browser_folder);
				}else{
					item.setIcon(R.drawable.dt_browser_file);
				}
				fileList.add(item);
			}
			if(fileList.size() == 0){
			}else{
				Collections.sort(fileList, new ItemFileNameComparator());
			}
		}
	}
	
	public class ItemFileNameComparator implements Comparator<Item> {
		public int compare(Item lhs, Item rhs) {
			return lhs.file.toLowerCase().compareTo(rhs.file.toLowerCase());
		}
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i(TAG, "enter onPause");
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i(TAG, "enter onStop");
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.i(TAG, "enter onDestroyView");
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mFileAdapter = null;
		pathDirsList.clear();
		pathDirsList = null;
		fileList.clear();
		fileList = null;
		super.onDestroy();
	}
	

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction() == KeyEvent.ACTION_DOWN){
			if(keyCode == KeyEvent.KEYCODE_BACK){
				loadDirectoryUp();
				return true;
			}
		}
		return false;
	}
	
}
