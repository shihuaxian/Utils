package com.shihx.index;

import java.io.File;

import com.shihx.index.listview.CopyFile;
import com.shihx.index.listview.ExpressDbHelper;
import com.shihx.index.listview.LetterView;
import com.shihx.index.listview.LetterView.onLetterChangeListener;
import com.shihx.index.listview.MyAdapter;
import com.shihx.index.listview.ToastUtil;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.AlphabetIndexer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LetterListView extends Activity {
	
	private ListView listView;
	private MyAdapter myAdapter;
	
	private ExpressDbHelper dbHelper;
	private AlphabetIndexer alphabetIndexer;
	private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private LetterView letterListView;
	
	//覆盖在ListView上面的视图
	private View viewOverlay;
	private TextView tvOverlay;
	
	/**自定义Toast**/
	private Toast toast;
	/**Toast视图中的TextView**/
	private TextView tvToast;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sortchar_listview);
		if(isFirstRun()){
        	copyDb();
        }
		dbHelper = new ExpressDbHelper(this);
		initToast();
		initViews();
	}
	
	/**初始化 Toast**/
    private void initToast() {
		toast = new Toast(this);
		//设置Toast的显示时间
		toast.setDuration(Toast.LENGTH_SHORT);
		View view = LayoutInflater.from(this).inflate(R.layout.toast, null);
		//设置Toast的显示视图
		toast.setView(view);
		//设置toast的显示位置位于窗口正中间
		toast.setGravity(Gravity.CENTER, 0, 0);
		tvToast = (TextView) view.findViewById(R.id.tvToast);
	}
	
	private void initViews(){
		listView = (ListView)findViewById(R.id.listView);
		viewOverlay = (View)findViewById(R.id.viewOverlay);
		tvOverlay = (TextView)findViewById(R.id.tvOverlay);
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor  cursor = db.query(ExpressDbHelper.TABLE_COMPANY_NAME, null, null, null, null, null, ExpressDbHelper.TABLE_COMPANY_COMPANY_INITIAL);
		alphabetIndexer = new AlphabetIndexer(cursor, cursor.getColumnIndex(ExpressDbHelper.TABLE_COMPANY_COMPANY_INITIAL), alphabet);
		myAdapter = new MyAdapter(cursor, this, alphabetIndexer);
		listView.setAdapter(myAdapter);
		
		letterListView = (LetterView)findViewById(R.id.letterView);
		letterListView.setOnLetterChangeListener(changeListener);
		
		listView.setOnScrollListener(onScrollListener);
	}
	
	private OnScrollListener onScrollListener = new OnScrollListener() {
		
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			int section = alphabetIndexer.getSectionForPosition(firstVisibleItem);
			letterListView.setSelectedIndex(section);
			//设置挤压效果
			int nextsection = section + 1;
			int pos = alphabetIndexer.getSectionForPosition(nextsection);
			//当当前第一个可见的item的下一个 正好是当前第一个可见item所在分组的下一个分组的第一个元素的时候
			if(pos == firstVisibleItem+1){
				View v = listView.getChildAt(0);
				if(v==null){
					return;
				}
				int dex = v.getBottom() - tvOverlay.getHeight();
				if(dex <=0){
					viewOverlay.setPadding(0, dex, 0, 0);
					tvOverlay.setText(alphabet.charAt(section));
				}else{
					viewOverlay.setPadding(0, 0, 0, 0);
					tvOverlay.setText(alphabet.charAt(section));
				}
			}else{
				viewOverlay.setPadding(0, 0, 0, 0);
				tvOverlay.setText(alphabet.charAt(section)+"");
			}
		}
	};
	
	private onLetterChangeListener changeListener = new onLetterChangeListener() {
		@Override
		public void onLetterChange(int selectedIndex) {
			// TODO Auto-generated method stub
			int position = alphabetIndexer.getPositionForSection(selectedIndex);
			listView.setSelection(position);
			tvToast.setText(alphabet.charAt(selectedIndex)+"");
			toast.show();
		}
	};	

	/**程序是否是第一次运行**/
	private boolean isFirstRun(){
		SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
		boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
		Editor editor = sharedPreferences.edit();
		if(isFirstRun){
			editor.putBoolean("isFirstRun", false);
			editor.commit();
		}
		return isFirstRun;
	}
	/**复制数据库**/
	private void copyDb(){
		File dbFile = getDatabasePath("express.db");
		CopyFile.copyFileFromResToPhone(dbFile.getParent(),
				dbFile.getName(),
				getResources().openRawResource(R.raw.express));
	}
}
