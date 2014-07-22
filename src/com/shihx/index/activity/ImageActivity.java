package com.shihx.index.activity;

import com.shihx.index.R;
import com.shihx.index.utils.ImageUtil;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends Activity {
    ImageView mImageView,mReflectionImg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_util_lay);
		mImageView = (ImageView)findViewById(R.id.image_test);
		mReflectionImg = (ImageView)findViewById(R.id.image_reflect);
		Bitmap bitmap = ImageUtil.drawableToBitmap(getResources().getDrawable(R.drawable.head_icon));
		mImageView.setImageBitmap(ImageUtil.getRoundedCornerBitmap(bitmap));
		
		mReflectionImg.setImageBitmap(ImageUtil.getReflectionBitmap(bitmap));
	}
	
}
