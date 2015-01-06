package com.shihx.index;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author shihx1
 * @date 4:19:06 PM Jan 6, 2015
 * @todo TODO 验证listview 选中混乱和多选
 * 参考 http://www.cnblogs.com/wujd/archive/2012/08/17/2635309.html
 * 原理：只要把添加监听器的方法加到初始化view中checkbox状态代码即可GridView 效果一样
 */
public class ListViewCheckBox extends Activity {
	private String TAG = "ListViewCheckBox";
	private ListView listview;
	private List<A> list;
	private ListViewAdapter adapter;
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this;
		listview = new ListView(this);
		setContentView(listview);
		initDate();
		adapter  = new ListViewAdapter();
		listview.setAdapter(adapter);
	}
	
	private void initDate(){
		list = new ArrayList<A>();
		A a;
		for(int i=0;i<40;i++){
			/*if(i%2==0){
				a = new A(i+"号位",A.TYPE_NOCHECKED);
			}else{
				a = new A(i+"号位",A.TYPE_CHECKED);
			}*/
			a = new A(i+"号位",A.TYPE_NOCHECKED);
			list.add(a);
		}
	}
	
	class ListViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final int index = position;
			Log.i(TAG, "index is:"+index);
			ViewHolder viewHolder;
			if(convertView==null){
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_mess_lay, null);
				viewHolder.layout = (LinearLayout)convertView.findViewById(R.id.layout);
				viewHolder.textView = (TextView)convertView.findViewById(R.id.textView);
				viewHolder.checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder)convertView.getTag();
			}
			
			viewHolder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					Log.i(TAG, "--isChecked");
					if(isChecked){
						list.get(index).type = A.TYPE_CHECKED;
					}else{
						list.get(index).type = A.TYPE_NOCHECKED;
					}
				}
			});
			
			viewHolder.textView.setText(list.get(position).name);
			if(list.get(position).type==A.TYPE_CHECKED){
				viewHolder.checkBox.setChecked(true);
			}else{
				viewHolder.checkBox.setChecked(false);
			}
			
			return convertView;
		}
	} 
	
	class ViewHolder{
		LinearLayout layout;
		TextView textView;
		CheckBox checkBox;
	}
	
	class A {
		public static final int TYPE_CHECKED = 1;
		public static final int TYPE_NOCHECKED = 0;
		
		String name;
		int type;
		
		public A(String name,int type) {
			// TODO Auto-generated constructor stub
			this.name = name;
			this.type = type;
		}
	}
}
