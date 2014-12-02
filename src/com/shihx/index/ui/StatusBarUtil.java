package com.shihx.index.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.Window;

public final class StatusBarUtil {
	private static final String TAG = "StatusBarUtil";
	private Method methodSetDarkStatusIcon;
    private static StatusBarUtil instance;;
    
    public static synchronized StatusBarUtil getInstance(){
    	if(instance==null){
    		instance = new StatusBarUtil();
    	}
    	return instance;
    }
    
    private StatusBarUtil(){
    	try {
			Class<?> classWindow = Class.forName("android.view.Window");
			methodSetDarkStatusIcon = classWindow.getMethod("romUI_setDarkStatusIcon", boolean.class);
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch(NoSuchMethodException e1){
			e1.printStackTrace();
		}
    }
    
    public void setDarkStatus(Window window,boolean dark){
    	Log.i(TAG, "setDarkStatus >> window : " + window + " ; dark : " + dark + " ; methodSetDarkStatusIcon : "
                        + methodSetDarkStatusIcon);
    	if(methodSetDarkStatusIcon==null)
    		return;
    	try {
			methodSetDarkStatusIcon.invoke(window, dark);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Only KitKat(19) and above version support.
     */
    @SuppressLint("NewApi")
    public boolean isSupport(){
    	return (Build.VERSION.SDK_INT >= 19 && methodSetDarkStatusIcon!=null);
    }
}
