package com.shihx.index;

import com.shihx.index.widget.DIYRoundProgressView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class CircleActivity extends Activity {
	private DIYRoundProgressView progress1,progress2,progress3;
	private int progress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.circle_lay);
		
		progress1 = (DIYRoundProgressView)findViewById(R.id.circle_one);
		progress2 = (DIYRoundProgressView)findViewById(R.id.circle_two);
		progress3 = (DIYRoundProgressView)findViewById(R.id.circle_three);
	}
	
	public void startProgress(View v){
		progress = 0;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(progress <= 100){
					progress += 3;
					progress1.setProgress(progress);
					progress2.setProgress(progress);
					progress3.setProgress(progress);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
