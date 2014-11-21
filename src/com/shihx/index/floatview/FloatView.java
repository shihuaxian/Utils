package com.shihx.index.floatview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

public class FloatView extends TextView {
	private final String TAG = FloatView.class.getSimpleName();  
    
    public static int TOOL_BAR_HIGH = 0;  
    public static WindowManager.LayoutParams params = new WindowManager.LayoutParams(); 
    
    private float startX,startY;
    private float x,y;
    
    private String text;
    
    WindowManager wm;
    
	public FloatView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		wm = (WindowManager)context.getApplicationContext().getSystemService(context.WINDOW_SERVICE);
		text = "测试文字，测试悬浮歌词效果--";
		setBackgroundColor(Color.argb(90, 150, 150, 150));
		
		setTextSize(60f);
		handler = new Handler();  
        handler.post(update);  
        Log.i(TAG, "main thread id is:"+Thread.currentThread().getId());
	}
	
	private Runnable update = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			postInvalidate();
			//Log.i(TAG, "thread id is:"+Thread.currentThread().getId());
			handler.postDelayed(update, 30);
		}
	};
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		x = event.getRawX();
		y = event.getRawY();
		Log.d(TAG, "------X: "+ x +"------Y:" + y);  
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			startY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			updatePosition();  
			break;
		case MotionEvent.ACTION_UP:
			updatePosition();  
			startX = startY = 0; 
			break;
		}
		return false;
	}
	private Handler handler; 
	private void updatePosition(){
		//view 的当前位置
		params.x = (int)(x - startX);
		params.y = (int)(y - startY);
		wm.updateViewLayout(this, params);
	}
	
	private float float1 = 0.0f;  
	private float float2 = 0.01f;  
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		float1 += 0.001f;  
        float2 += 0.001f;   
        if(float2 > 1.0){  
            float1 = 0.0f;  
            float2 = 0.01f;  
        }  
        this.setText("");  
        float len = this.getTextSize() * text.length();
        Shader shader = new LinearGradient(0, 0, len, 0, new int[] { Color.YELLOW, Color.RED },
        		new float[]{float1, float2}, TileMode.CLAMP);
        Paint p= new Paint();
        p.setShader(shader);
        p.setTextSize(60f);
        p.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(text, 0, 60, p);
	}
}
