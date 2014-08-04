package com.shihx.index.viewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.shihx.index.R;
import com.shihx.index.utils.MusicUtils;


@SuppressLint("NewApi")
public class AudioUIFragment extends Fragment {
	static final String TAG = "AudioUIFragment";
	View rootView;
	ListView audio_listview;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.audio_ui_fragment, container, false);
		
		return rootView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initViews();
		initFillData();
	}
	
	private void initViews(){
		audio_listview = (ListView)rootView.findViewById(R.id.audio_listview);
	}
	
	
	private Cursor readDataFromSD(Context context) {
		Log.d(TAG, "scanFile");
		String[] str = new String[]{MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.DISPLAY_NAME,
				MediaStore.Audio.Media.TITLE,
				MediaStore.Audio.Media.DURATION,
				MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.ALBUM,
				MediaStore.Audio.Media.YEAR,
				MediaStore.Audio.Media.MIME_TYPE,
				MediaStore.Audio.Media.SIZE,
				MediaStore.Audio.Media.DATA};
		Cursor c = MusicUtils.query(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				str, MediaStore.Audio.Media.IS_MUSIC + "=1",
                null, MediaStore.Audio.Media.TITLE_KEY);
		return c;
	}
	
	private void initFillData(){
		String[] fromColumns = new String[] {MediaStore.Audio.Media.TITLE, 
				MediaStore.Audio.Media.ARTIST};
		int[] toLayoutIDs = new int[]{R.id.media_row_name,R.id.media_row_artist};
		Cursor cursor = readDataFromSD(getActivity());
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.media_item, cursor, fromColumns, toLayoutIDs, 0);
		
		audio_listview.setAdapter(adapter);
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
