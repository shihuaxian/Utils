package com.shihx.index.animation;

import java.util.ArrayList;
import java.util.List;



import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

@SuppressLint("NewApi")
public class MyAnimationView extends View implements AnimatorUpdateListener{
	
	private static final float BALL_SIZE = 100f;
	private final List<ShapeHolder> balls = new ArrayList<ShapeHolder>();
	//总的动画集合
	AnimatorSet animation = null;
	//屏幕的密度
	private float mDensity;
	
	public MyAnimationView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mDensity = getResources().getDisplayMetrics().density;
		addBall(50f, 25f);
        addBall(150f, 25f);
        addBall(250f, 25f);
        addBall(350f, 25f);
        addBall(450f, 25f);
	}
	
	private ShapeHolder addBall(float x,float y){
		OvalShape circle = new OvalShape();
		circle.resize(50f * mDensity, 50f *mDensity);
		ShapeDrawable drawable = new ShapeDrawable(circle);
		ShapeHolder shapeHolder = new ShapeHolder(drawable);
		shapeHolder.setX(x-25f);
		shapeHolder.setY(y-25f);
		int red = (int)(100+Math.random() * 155);
		int green = (int)(100 + Math.random() * 155);
		int blue = (int)(100 + Math.random() * 155);
		int color = 0xff000000 | red << 16 | green << 8 | blue;
		Paint paint = drawable.getPaint();
		int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
		
		RadialGradient gradient = new RadialGradient(37.5f, 12.5f, 50f, color, darkColor, Shader.TileMode.CLAMP);
		paint.setShader(gradient);
		shapeHolder.setPaint(paint);
		balls.add(shapeHolder);
		return shapeHolder;
	}
	
	private void createAnimation(){
		if(animation ==null){
			ObjectAnimator anim1 = ObjectAnimator.ofFloat(balls.get(0), "y", 
					0f,getHeight() - balls.get(0).getHeight()).setDuration(500);
			
			ObjectAnimator anim2 = anim1.clone();
			anim2.setTarget(balls.get(1));
			anim2.addUpdateListener(this);
			
			//第三个小球，先加速下降，再减速上升
			ShapeHolder ball2 = balls.get(2);
			//动画下落
			ObjectAnimator animDown = ObjectAnimator.ofFloat(ball2, "y", 0f,getHeight()-ball2.getHeight()).setDuration(500);
			//下落加速
			animDown.setInterpolator(new AccelerateInterpolator());
			//动画效果上升
			ObjectAnimator animUp = ObjectAnimator.ofFloat(ball2, "y",getHeight()-ball2.getHeight(),0f).setDuration(500);
			animUp.setInterpolator(new DecelerateInterpolator());
			
			AnimatorSet animatorSet = new AnimatorSet();
			animatorSet.playSequentially(animDown, animUp);
			//下落和上升都刷新
			animDown.addUpdateListener(this);
			animUp.addUpdateListener(this);
			
			//第四个球的效果
			AnimatorSet s2 = animatorSet.clone();
			s2.setTarget(balls.get(3));
			
			//第五个球的效果要自己监听
			final ShapeHolder ball5 = balls.get(4);
			ValueAnimator valueAnimator5 = ValueAnimator.ofFloat(0f,getHeight() - ball5.getHeight());
			valueAnimator5.setDuration(500);
			valueAnimator5.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					// TODO Auto-generated method stub
					ball5.setY((Float)animation.getAnimatedValue());
					invalidate();
				}
			});
			 // 用一个总的AnimatorSet对象管理以上所有动画
			animation = new AnimatorSet();
			animation.playTogether(anim1,anim2);//并行串行
			animation.playSequentially(animatorSet,s2,valueAnimator5);
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//super.onDraw(canvas);
		for(int i=0;i<balls.size();++i){
			ShapeHolder shapeHolder = balls.get(i);
			canvas.save();
			canvas.translate(shapeHolder.getX(), shapeHolder.getY());
			shapeHolder.getShape().draw(canvas);
			canvas.restore();
		}
	}
	
	public void startAnimation() {
        createAnimation();
        animation.start();// 这里开始播放动画
    }
	
	@Override
	public void onAnimationUpdate(ValueAnimator animation) {
		// TODO Auto-generated method stub
		invalidate();
	}
	
	
}
