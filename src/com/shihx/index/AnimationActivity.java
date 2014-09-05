package com.shihx.index;

import com.shihx.index.animation.MyAnimationView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AnimationActivity extends Activity implements OnClickListener{
	public static final int DURATION = 1500;
	
	private LinearLayout animation_container;
	private MyAnimationView animationView;
	private TextView animation_txt;
	private Button startBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animation_lay);
		
		animation_container = (LinearLayout)findViewById(R.id.animation_container);
		animation_txt = (TextView)findViewById(R.id.animation_txt);
		startBtn = (Button)findViewById(R.id.animation_startButton);
		startBtn.setOnClickListener(this);
		initAnimationView();
		
	}
	
	private void initAnimationView(){
		animationView = new MyAnimationView(this);
		animation_container.addView(animationView);
		//animationView.setBackgroundColor(Color.RED);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.animation_startButton:
			animationView.startAnimation();
			break;
		}
	}
}
