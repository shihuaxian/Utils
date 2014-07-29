package com.shihx.index.listview;

import com.shihx.index.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	private Cursor cursor;
	private Context context;
	private AlphabetIndexer indexer;
	
	
	/**
	 * @param cursor
	 * @param context
	 * @param indexer
	 */
	public MyAdapter(Cursor cursor, Context context, AlphabetIndexer indexer) {
		super();
		this.cursor = cursor;
		this.context = context;
		this.indexer = indexer;
	}

	@Override
	public int getCount() {
		return cursor.getCount();
	}

	@Override
	public Cursor getItem(int position) {
		cursor.moveToPosition(position);
		return cursor;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
			holder = new ViewHolder();
			holder.tvLetter = (TextView) convertView.findViewById(R.id.tvLetter);
			holder.tvCompanyName = (TextView) convertView.findViewById(R.id.tvCompanyName_item);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		cursor.moveToPosition(position);
		holder.tvCompanyName.setText(cursor.getString(cursor.getColumnIndex(ExpressDbHelper.TABLE_COMPANY_COMPANY_NAME)));
		//获取该item对应的字母在字母表中的位置
		int section = indexer.getSectionForPosition(position);
		//获取该字母分组中第一条数据的位置
		int pos = indexer.getPositionForSection(section);
		if(pos == position){
			holder.tvLetter.setVisibility(View.VISIBLE);
			holder.tvLetter.setText(cursor.getString(cursor.getColumnIndex(ExpressDbHelper.TABLE_COMPANY_COMPANY_INITIAL)));
		}else{
			holder.tvLetter.setVisibility(View.GONE);
		}
		return convertView;
	}

	class ViewHolder{
		TextView tvLetter;
		TextView tvCompanyName;
	}
}
