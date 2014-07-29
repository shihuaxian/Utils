package com.shihx.index.listview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LetterView extends View {
	
	/**获取该字母分组中第一条数据的位置**/
	public static final int COLOR_BG = 0xaa876881;
	/**没触摸view的时候的背景颜色**/
	public static final int COLOR_NO_BG = 0x00ffff00;
	/**选中的字母的字体颜色**/
	public static final int COLOR_TEXT_SELECTED = 0xff386AB7;
	/**没选中的字母的字体颜色**/
	public static final int COLOR_TEXT_NORMAL = 0xff000000;
	/**字母的字体大小**/
	public static final int SIZE_TEXT = 28;
	/**字母表**/
	private static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private Paint paint;
	/**该View的宽**/
	private int width;
	/**该View的高**/
	private int height;
	/**单个字母的高**/
	private int singleHight;
	
	
	public LetterView(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}

	public LetterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextSize(SIZE_TEXT);
		paint.setFakeBoldText(true);
	}
	private int currentSelectedIndex = 0;
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if(width == 0 || height ==0){
			height = getHeight();
			width = getWidth();
			singleHight = height/letters.length();
		}
		
		for(int i=0;i<letters.length();i++){
			if(currentSelectedIndex == i){
				paint.setColor(COLOR_TEXT_SELECTED);
			}else{
				paint.setColor(COLOR_TEXT_NORMAL);
			}
			float xPos = (width - paint.measureText(letters.charAt(i)+""))/2;
			float yPos = singleHight*i+singleHight;
			canvas.drawText(letters.charAt(i)+"", xPos, yPos, paint);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		currentSelectedIndex = (int)(event.getY()/singleHight);
		if(currentSelectedIndex < 0 ){
			currentSelectedIndex = 0;
		}
		if(currentSelectedIndex > letters.length() -1){
			currentSelectedIndex = letters.length() - 1;
		}
		if(letterChangeListener!=null){
			letterChangeListener.onLetterChange(currentSelectedIndex);
		}
		invalidate();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setBackgroundColor(COLOR_BG);
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			setBackgroundColor(COLOR_NO_BG);
			break;
		}
		return true;
	}
	
	onLetterChangeListener letterChangeListener;
	
	public void setOnLetterChangeListener(onLetterChangeListener changeListener){
		this.letterChangeListener = changeListener;
	}
	
	public interface onLetterChangeListener{
		void onLetterChange(int selectedIndex);
	}
	
	public void setSelectedIndex(int index){
		currentSelectedIndex = index;
		invalidate();
	}
}
