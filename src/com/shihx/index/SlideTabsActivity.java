package com.shihx.index;

import java.util.Random;

import com.shihx.index.utils.Constant;
import com.shihx.index.viewpager.AudioUIFragment;
import com.shihx.index.viewpager.FilesUIFragment;
import com.shihx.index.viewpager.VideoUIFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TabWidget;
import android.widget.TextView;

public class SlideTabsActivity extends FragmentActivity {
	 private static final String TAG = "AndroidDemos.SlideTabs3";
	    private ViewPager mViewPager;
	    private PagerAdapter mPagerAdapter;
	    private TabWidget mTabWidget;
	    private String[] addresses = { "first", "second", "third" };
	    private TextView[] mTextTabs = new TextView[addresses.length];
	    @Override
	    protected void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.fragment_slidetabs3);
	        mTabWidget = (TabWidget) findViewById(R.id.tabWidget1);
	        mTabWidget.setStripEnabled(false);
	        mTextTabs[0] = new TextView(this);
	        mTextTabs[0].setFocusable(true);
	        mTextTabs[0].setText(addresses[0]);
	        mTextTabs[0].setGravity(Gravity.CENTER);
	        mTextTabs[0].setTextColor(getResources().getColorStateList(R.color.button_bg_color_selector));
	        mTabWidget.addView(mTextTabs[0]);
	        /* 
	         * Listener必须在mTabWidget.addView()之后再加入，用于覆盖默认的Listener，
	         * mTabWidget.addView()中默认的Listener没有NullPointer检测。
	         */
	        mTextTabs[0].setOnClickListener(mTabClickListener);
	        mTextTabs[1] = new TextView(this);
	        mTextTabs[1].setFocusable(true);
	        mTextTabs[1].setText(addresses[1]);
	        mTextTabs[1].setGravity(Gravity.CENTER);
	        mTextTabs[1].setTextColor(getResources().getColorStateList(R.color.button_bg_color_selector));
	        mTabWidget.addView(mTextTabs[1]);
	        mTextTabs[1].setOnClickListener(mTabClickListener);
	        mTextTabs[2] = new TextView(this);
	        mTextTabs[2].setFocusable(true);
	        mTextTabs[2].setText(addresses[2]);
	        mTextTabs[2].setGravity(Gravity.CENTER);
	        mTextTabs[2].setTextColor(getResources().getColorStateList(R.color.button_bg_color_selector));
	        mTabWidget.addView(mTextTabs[2]);
	        mTextTabs[2].setOnClickListener(mTabClickListener);

	        mViewPager = (ViewPager) findViewById(R.id.viewPager1);
	        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
	        mViewPager.setAdapter(mPagerAdapter);
	        mViewPager.setOnPageChangeListener(mPageChangeListener);

	        mTabWidget.setCurrentTab(0);
	    }
	    private OnClickListener mTabClickListener = new OnClickListener() {
	        @Override
	        public void onClick(View v)
	        {
	            if (v == mTextTabs[0])
	            {
	                mViewPager.setCurrentItem(0);
	            } else if (v == mTextTabs[1])
	            {
	                mViewPager.setCurrentItem(1);
	            } else if (v == mTextTabs[2])
	            {
	                mViewPager.setCurrentItem(2);
	            }
	        }
	    };

	    private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

	        @Override
	        public void onPageSelected(int arg0)
	        {
	            mTabWidget.setCurrentTab(arg0);
	        }

	        @Override
	        public void onPageScrolled(int arg0, float arg1, int arg2)
	        {

	        }

	        @Override
	        public void onPageScrollStateChanged(int arg0)
	        {

	        }
	    };
	    private class MyPagerAdapter extends FragmentStatePagerAdapter
	    {
	        public MyPagerAdapter(FragmentManager fm)
	        {
	            super(fm);
	        }
	        @Override
	        public Fragment getItem(int position)
	        {
	        	Fragment ft = null;
				switch (position) {
				case 0:
					ft = new VideoUIFragment();
					Bundle args = new Bundle();
					args.putString(Constant.ARGUMENTS_NAME, mTextTabs[position].getText().toString());
					ft.setArguments(args);
					break;
				case 1:
					ft = new AudioUIFragment();
					break;
				case 2:
					ft = new FilesUIFragment();
					break;
				default:
					break;
				}
				return ft;
	        }
	        @Override
	        public int getCount()
	        {
	            return addresses.length;
	        }
	    }
}
