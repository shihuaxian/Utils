package com.shihx.index.listview;

import com.shihx.index.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil extends Toast {

	public ToastUtil(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public static Toast makeText(Context context, CharSequence text,
			int duration) {
		Toast result = new Toast(context);

		LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.toast, null);
		TextView textView = (TextView)v.findViewById(R.id.tvToast);
		textView.setText(text);
		result.setView(v);
		result.setGravity(Gravity.CENTER, 0, 0);
		return result;
	}
}
