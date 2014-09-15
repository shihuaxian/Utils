package com.shihx.index.widget;

import com.shihx.index.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.View;

public class DIYRoundProgressView extends View {
	
	private Paint mPaint;
	
	/**
	 * 圆环的颜色
	 */
	private int roundColor;
	

	/**
	 * 进度条的颜色
	 */
	private int roundProgressColor;
	
	private int textColor;
	private float textSize;
	
	//圆环的宽度
	private float roundWidth;
	private int progressMax;
	private int currentProgress;
	private boolean textIsDisable;
	private int style;//进度的风格，实心还是空心
	
	public static final int STROKE = 0;
	public static final int FILL = 1;
	
	public DIYRoundProgressView(Context context) {
		// TODO Auto-generated constructor stub
		this(context,null);
	}

	public DIYRoundProgressView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}
	
	public DIYRoundProgressView(Context context, AttributeSet attrs,int defStyle) {
		// TODO Auto-generated constructor stub
		super(context, attrs, defStyle);
		mPaint = new Paint();
		
		TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.diy_progress_attr);
		//get property or default value
		roundColor = typedArray.getColor(R.styleable.diy_progress_attr_roundColor, Color.RED);
		roundProgressColor = typedArray.getColor(R.styleable.diy_progress_attr_roundProgressColor, Color.GREEN);
		textColor = typedArray.getColor(R.styleable.diy_progress_attr_textColor, Color.YELLOW);
		textSize = typedArray.getDimension(R.styleable.diy_progress_attr_textSize, 28);
		roundWidth = typedArray.getDimension(R.styleable.diy_progress_attr_roundWidth, 10);
		progressMax = typedArray.getInteger(R.styleable.diy_progress_attr_max, 100);
		textIsDisable = typedArray.getBoolean(R.styleable.diy_progress_attr_textIsDisplayable, true);
		style = typedArray.getInt(R.styleable.diy_progress_attr_style, 0);
		
		typedArray.recycle();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		//外层的圆环
		int centre = getWidth()/2;//圆心半径
		int radius = (int)(centre - roundWidth/2);//radio
		mPaint.setColor(roundColor);//圆环颜色
		mPaint.setStyle(Paint.Style.STROKE);//设置空心
		mPaint.setStrokeWidth(roundWidth);//设置圆环宽度
		mPaint.setAntiAlias(true);//消除锯齿
		canvas.drawCircle(centre, centre, radius, mPaint);
		
		/**
		 * 画度百分比
		 */
		mPaint.setStrokeWidth(0);
		mPaint.setColor(textColor);
		mPaint.setTextSize(textSize);
		mPaint.setTypeface(Typeface.DEFAULT_BOLD);
		int percent = (int)(((float)currentProgress/(float)progressMax)*100);
		float textWidth = mPaint.measureText(percent+"%");
		
		//为了字体可以垂直也居中
		FontMetrics fontMetrics = mPaint.getFontMetrics();
		float fontHeight = fontMetrics.bottom - fontMetrics.top;
		float textHeightY = getHeight() - (getHeight()-fontHeight)/2 -fontMetrics.bottom;
		if(textIsDisable && textWidth!=0 && style==STROKE){
			canvas.drawText(percent+"%", centre-textWidth/2, textHeightY, mPaint);
		}
		
		/**
		 * 画圆弧，圆环的进度
		 */
		//画实心还是空心
		mPaint.setStrokeWidth(roundWidth);
		mPaint.setColor(roundProgressColor);//进度的颜色
		RectF rectF = new RectF(centre-radius, centre-radius, centre+radius, centre+radius);
		switch(style){
		case STROKE:
			mPaint.setStyle(Paint.Style.STROKE);
			canvas.drawArc(rectF, 0, 360*currentProgress/progressMax, false, mPaint);
			break;
		case FILL:
			mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			canvas.drawArc(rectF, 0, 360*currentProgress/progressMax, true, mPaint);
			break;
		}
	}
	
	public int getRoundColor() {
		return roundColor;
	}

	public void setRoundColor(int roundColor) {
		this.roundColor = roundColor;
	}

	public int getRoundProgressColor() {
		return roundProgressColor;
	}

	public void setRoundProgressColor(int roundProgressColor) {
		this.roundProgressColor = roundProgressColor;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}


	public boolean isTextIsDisable() {
		return textIsDisable;
	}

	public void setTextIsDisable(boolean textIsDisable) {
		this.textIsDisable = textIsDisable;
	}
	
	public synchronized int getMax() {
		return progressMax;
	}

	/**
	 * 设置进度的最大值
	 * @param max
	 */
	public synchronized void setMax(int max) {
		if(max < 0){
			throw new IllegalArgumentException("max not less than 0");
		}
		this.progressMax = max;
	}

	/**
	 * 获取进度.需要同步
	 * @return
	 */
	public synchronized int getProgress() {
		return currentProgress;
	}

	/**
	 * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
	 * 刷新界面调用postInvalidate()能在非UI线程刷新
	 * @param progress
	 */
	public synchronized void setProgress(int progress) {
		if(progress < 0){
			throw new IllegalArgumentException("progress not less than 0");
		}
		if(progress > progressMax){
			progress = progressMax;
		}
		if(progress <= progressMax){
			this.currentProgress = progress;
			postInvalidate();
		}
		
	}
}
