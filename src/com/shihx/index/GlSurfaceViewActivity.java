package com.shihx.index;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.shihx.index.utils.ControlLightness;
import com.shihx.index.utils.VolumeUtil;
import com.shihx.index.widget.GlVideoView;
import com.shihx.index.widget.OnTouchMoveListener;

import android.app.Activity;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.ProgressBar;


/**
 * touch and move up and down
 * left size for brightness, right size for volume
 * for multimedia playing UI and show DIY PrgressBars
 * @author shihx1
 *
 */
public class GlSurfaceViewActivity extends Activity {
	private String TAG = "GlSurfaceViewActivity";
	
	private GlVideoView glVideoView;
	
	private final int HANDLE_UP = 0x0110;
	private final int HANDLE_DOWN = HANDLE_UP+1;
	private ProgressBar brightProgressBar,volumeProgressBar;
	private VolumeUtil volumeUtil;
	private int mScreenWidth;
	private int mScreenHeight;
	private int currentLightness;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.glsurfaceview_lay);
		
		glVideoView = (GlVideoView)findViewById(R.id.dt_gl_videoView);
		
		brightProgressBar = (ProgressBar)findViewById(R.id.bright_progressbar);
		volumeProgressBar = (ProgressBar)findViewById(R.id.volume_progress);
		
		volumeUtil = new VolumeUtil(this);
		volumeProgressBar.setMax(volumeUtil.getMaxVolume());
		volumeProgressBar.setProgress(volumeUtil.getCurrentVolume());
		
		currentLightness = ControlLightness.getInstance().getLightness(this);
		if(currentLightness>=255){
			currentLightness = 255;
		}else if(currentLightness<=0){
			currentLightness = 0;
		}
		
		initDisplay();
		initListener();
	}
	
	private void initListener(){
		glVideoView.setRenderer(new Renderer() {
			@Override
			public void onSurfaceCreated(GL10 gl, EGLConfig config) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSurfaceChanged(GL10 gl, int width, int height) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onDrawFrame(GL10 gl) {
				// TODO Auto-generated method stub
				
			}
		});
		
		glVideoView.setTouchMoveListener(new OnTouchMoveListener() {
			@Override
			public void onTouchMoveUp(float posX) {
				// TODO Auto-generated method stub
				if(posX<(mScreenWidth/2-10)){//left up handle audiovolume
					//Log.i(TAG, "left up handle audiovolume");
					handleAudioVolume(HANDLE_UP);
				}else if(posX>(mScreenWidth/2+10)){//right up handle lightless
					//Log.i(TAG, "right up handle lightless");
					handleLightless(HANDLE_UP);
				}
			}
			@Override
			public void onTouchMoveDown(float posX) {
				// TODO Auto-generated method stub
				if(posX<(mScreenWidth/2-10)){//left down handle audiovolume
					Log.i(TAG, "left down handle audiovolume");
					handleAudioVolume(HANDLE_DOWN);
				}else if(posX>(mScreenWidth/2+10)){//right down handle lightless
					//Log.i(TAG, "right down handle lightless");
					handleLightless(HANDLE_DOWN);
				}
			}
		});
	}
	
	private void initDisplay(){
		DisplayMetrics displayMetrics =  new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		mScreenWidth = displayMetrics.widthPixels;
		mScreenHeight = displayMetrics.heightPixels;
		if(mScreenWidth==0){
			Display display = getWindowManager().getDefaultDisplay();
			mScreenWidth = display.getWidth();
			mScreenHeight = display.getHeight();
		}
	}
	
	private void handleAudioVolume(int type){
		switch(type){
		case HANDLE_UP:
			volumeUtil.upVolume(0);
			break;
		case HANDLE_DOWN:
			volumeUtil.downVolume(0);
			break;
		}
		volumeProgressBar.setProgress(volumeUtil.getCurrentVolume());
	}
	
	private void handleLightless(int type){
		currentLightness = ControlLightness.getInstance().getLightness(this);
		Log.i(TAG, "currentLightness is:"+currentLightness);
		switch(type){
		case HANDLE_UP:
			currentLightness+=5;
			currentLightness = currentLightness >= 255 ? 255:currentLightness;
			break;
		case HANDLE_DOWN:
			currentLightness-=5;
			currentLightness = currentLightness <= 0 ? 0 : currentLightness;
			break;
		}
		ControlLightness.getInstance().setBrightness(this, currentLightness);
		brightProgressBar.setProgress(currentLightness);
	}
}
