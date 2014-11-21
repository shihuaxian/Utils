package com.shihx.index;


import com.shihx.index.floatview.FloatView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 分享sina微博
 * @author shihx1
 *
 */
public class FloatViewActivity extends Activity {
	//floatview 可扩展
	private FloatView fv;
	private Activity mActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LinearLayout ly = new LinearLayout(this);
		Button button = new Button(this);
		ly.addView(button);
		button.setText("click me") ;
		setContentView(ly);
		mActivity = this;
		fv = new FloatView(this);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showView(true);
			}
		});
		fv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mActivity, "--", 1).show();
				showView(false);
			}
		});
	}
	
	private void showView(boolean isNeedShow){
		final WindowManager wm = (WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);
		if(fv!=null && fv.isShown()){
			wm.removeView(fv);
		}else{
			WindowManager.LayoutParams params = FloatView.params;
			params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
			
			params.width = WindowManager.LayoutParams.MATCH_PARENT;
			params.height = WindowManager.LayoutParams.WRAP_CONTENT;
			params.alpha = 80;
			
			params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL|LayoutParams.FLAG_NOT_FOCUSABLE;
			params.gravity = Gravity.TOP | Gravity.LEFT;
			
			//以屏幕左上角为原点，设置x、y初始值  
	        params.x = 0;  
	        params.y = 0;
	        wm.addView(fv, params);
		}
	}

}
