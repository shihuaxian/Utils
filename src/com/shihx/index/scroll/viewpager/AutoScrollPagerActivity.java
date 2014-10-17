package com.shihx.index.scroll.viewpager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.shihx.index.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 根据网上大部分写的那样
 * 自动循环viewpager
 * 由于自己控制的循环切换，有卡顿，于是把count加大
 * 暂时没有调试性能是否有影响，稍后更新
 * @author Administrator
 *
 */
public class AutoScrollPagerActivity extends Activity implements OnPageChangeListener{
	String TAG = "AutoScrollPagerActivity";
	private ViewPager viewPager;
	private ImageView[] groupViews;
	private ImageView[] indicators;
	
	private int[] imgArray ;

	private Handler handler;
	private Message message;
	private int select=800;
	
	private boolean isAutoScroll = true;
	
	private ScheduledExecutorService executorService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scroll_viewpager_lay);
		viewPager = (ViewPager)findViewById(R.id.scroll_viewpager);
		LinearLayout indicatorLay = (LinearLayout)findViewById(R.id.scroll_viewgroup);
		imgArray = new int[]{R.drawable.scroll_pager_01,R.drawable.scroll_pager_02,R.drawable.scroll_pager_03,R.drawable.scroll_pager_04,
				R.drawable.scroll_pager_05,R.drawable.scroll_pager_06,R.drawable.scroll_pager_07,R.drawable.scroll_pager_08};
		indicators = new ImageView[imgArray.length];
		for(int i=0;i<indicators.length;i++){
			ImageView imageView = new ImageView(this);
			LayoutParams lp = new LayoutParams(10, 10);
			lp.leftMargin = 5;
			imageView.setLayoutParams(lp);
			indicators[i]= imageView;
			if(i==0){
				imageView.setBackgroundResource(R.drawable.page_indicator_focused);
			}else{
				imageView.setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
			indicatorLay.addView(imageView);
		}
		
		handler = new Handler(Looper.getMainLooper()){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(msg.arg1);
			}
		};
		
		groupViews = new ImageView[imgArray.length];
		for(int j = 0;j<imgArray.length;j++){
			ImageView imageView = new ImageView(this);
			
			imageView.setBackgroundResource(imgArray[j]);
			groupViews[j]= imageView;
		}
		
		viewPager.setAdapter(new MyAdapter());
		viewPager.setCurrentItem((groupViews.length) * 100);
		viewPager.setOnPageChangeListener(this);
		viewPager.setOnTouchListener(new PagerTouchListener());
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
		super.onStart();
	}
	
	private class ScrollTask implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(isAutoScroll){
				synchronized(viewPager){
					message = handler.obtainMessage();
					message.arg1 = select;
					//send
					handler.sendMessage(message);
					select++;
				}
			}
		}
	}
	
	private class PagerTouchListener implements OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				isAutoScroll = false;
				//Log.i(TAG, "enter action_down---");
				break;
			case MotionEvent.ACTION_UP:
				isAutoScroll = true;
				//Log.i(TAG, "enter action_up---");
				break;
			}
			return false;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		executorService.shutdown();
	}
	
	public class MyAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			//super.destroyItem(container, position, object);
			((ViewPager)container).removeView(groupViews[position%groupViews.length]);
		}
		
		LayoutParams lp;
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			if(lp==null)
				lp= new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			container.addView(groupViews[position%groupViews.length], 0,lp);
			//return super.instantiateItem(container, position);
			return groupViews[position%groupViews.length];
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		select = viewPager.getCurrentItem();
		setIndicatorBackground(arg0%groupViews.length);
	}
	
	private void setIndicatorBackground(int position){
		for(int i=0;i<indicators.length;i++){
			if(position==i){
				indicators[i].setBackgroundResource(R.drawable.page_indicator_focused);
			}else{
				indicators[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
		}
	}
}
