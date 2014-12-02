package com.shihx.index.ui;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;

public final class ActivityUtil {
	private static final String TAG =  "ActivityUtil";
	private static final String FLAG_TRANSLUCENT_STATUS = "FLAG_TRANSLUCENT_STATUS";
	private static final String FLAG_TRANSLUCENT_NAVIGATION = "FLAG_TRANSLUCENT_NAVIGATION";
	
	private ActivityUtil() {}
	
	/**
     * Set statusbar the same color as activity shown. 
     * The following attributes of the window belongs to the activity have to be set.
     *
     * <item name="android:windowNoTitle">true</item>
     * <item name="android:windowTranslucentStatus">true</item>
     * <item name="android:windowTranslucentNavigation">true</item>
     *
     * Note that this feature only available above KitKat, namely api level 19.
     */
	public static void configureStatusBarStyle(final Activity activity){
		// promise all activities have no title
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		final StatusBarUtil util = StatusBarUtil.getInstance();
		if(!util.isSupport())
			return;
		
		Window window = activity.getWindow();
		Class<?> className = null;
		try {
			className = Class.forName("android.view.WindowManager$LayoutParams");
			Field field = className.getDeclaredField(FLAG_TRANSLUCENT_STATUS);
			int translucentValue = field.getInt(null);
			window.setFlags(translucentValue, translucentValue);
			
			field = className.getDeclaredField(FLAG_TRANSLUCENT_NAVIGATION);
			int translucentNavigation = field.getInt(null);
			window.setFlags(translucentNavigation, translucentNavigation);
			
			util.setDarkStatus(window, true);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NoSuchFieldException e){
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean setPaddingAsStatusBarHeight(final Activity activity,int titleBarId){
		if(activity ==null)
			return false;
		
		final StatusBarUtil util = StatusBarUtil.getInstance();
		if(!util.isSupport())
			return false;
		
		View titleview = activity.findViewById(titleBarId);
		if(titleview == null)
			return false;
		
		titleview.setPadding(0, getStatusBarHeight(activity), 0, 0);
		return true;
	}
	
	public static boolean isStatusBarCustomizeSupported(){
		final StatusBarUtil util = StatusBarUtil.getInstance();
		return util.isSupport();
	}
	
	public static int getStatusBarHeight(final Context context){
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int dp = 0;
		int statusBarHeight = 50;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			dp = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(dp);
			Log.i(TAG, "Status height is: " + statusBarHeight);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return statusBarHeight;
	}
	
}
