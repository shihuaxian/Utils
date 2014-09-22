package com.shihx.index;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DevicesInfo extends Activity {
	private TextView info_txt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_info_lay);
		info_txt = (TextView)findViewById(R.id.device_info_txt);
		showDeviceInfo(info_txt);
	}
	
	private void showDeviceInfo(TextView textView){
		StringBuffer phoneInfo =  new StringBuffer();
		phoneInfo.append("BOARD:" + android.os.Build.BOARD+"\n");
		phoneInfo.append("BOOTLOADER:"+android.os.Build.BOOTLOADER+"\n");
		//运营商
		phoneInfo.append("BRAND:"+android.os.Build.BRAND+"\n");
		phoneInfo.append("CPU_ABI:"+android.os.Build.CPU_ABI+"\n");
		phoneInfo.append("CPU_ABI2:"+android.os.Build.CPU_ABI2+"\n");
		//驱动
		phoneInfo.append("DEVICE:"+android.os.Build.DEVICE+"\n");
		//显示
		phoneInfo.append("DISPLAY:"+android.os.Build.DISPLAY+"\n");
		//指纹
		phoneInfo.append("FINGERPRINT:"+android.os.Build.FINGERPRINT+"\n");
		//硬件
		phoneInfo.append("HARDWARE:"+android.os.Build.HARDWARE+"\n");
		phoneInfo.append("HOST:"+android.os.Build.HOST+"\n");
		phoneInfo.append("ID:"+android.os.Build.ID+"\n");
		//MANUFECTRURER 生产厂家
		phoneInfo.append("MANUFECTRUE:"+android.os.Build.MANUFACTURER+"\n\n");
		//机型
		phoneInfo.append("MODEL:"+android.os.Build.MODEL+"\n");
		phoneInfo.append("PRODUCT:"+android.os.Build.PRODUCT+"\n");
		phoneInfo.append("RADIO:"+android.os.Build.RADIO+"\n");
		phoneInfo.append("RADITAGS:"+android.os.Build.TAGS+"\n");
		phoneInfo.append("TIME:"+android.os.Build.TIME+"\n");
		phoneInfo.append("TYPE:"+android.os.Build.TYPE+"\n");
		phoneInfo.append("USER:"+android.os.Build.USER+"\n\n");
		//VERSION.RELEASE 固件版本
		phoneInfo.append("VERSION.RELEASE:"+android.os.Build.VERSION.RELEASE+"\n");
		phoneInfo.append("VERSION.CODENMAE"+android.os.Build.VERSION.CODENAME+"\n");
		//VERSION.INCREMENTAL 基带版本
		phoneInfo.append("VERSION.INCREMENTAL:"+android.os.Build.VERSION.INCREMENTAL+"\n");
		//VERSION.SDK SDK版本
		phoneInfo.append("VERSION.SDK:"+android.os.Build.VERSION.SDK+"\n");
		phoneInfo.append("VERSION.SDK_INI"+android.os.Build.VERSION.SDK_INT+"\n");
		
		
		info_txt.setText(phoneInfo);
	}
}
