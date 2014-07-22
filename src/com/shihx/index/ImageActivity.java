/**
 *  Author :  hmg25
 *  Description :
 */
package com.shihx.index;

import com.shihx.index.utils.ImageUtil;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * hmg25's android Type
 *
 *@author shihx1
 *
 */
public class ImageActivity extends Activity {
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	ImageView mImageView,mReflectionImg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_lay);
		mImageView = (ImageView)findViewById(R.id.image_test);
		mReflectionImg = (ImageView)findViewById(R.id.image_reflect);
		Bitmap bitmap = ImageUtil.drawableToBitmap(getResources().getDrawable(R.drawable.head_icon));
		mImageView.setImageBitmap(ImageUtil.getRoundedCornerBitmap(bitmap));
		
		mReflectionImg.setImageBitmap(ImageUtil.getReflectionBitmap(bitmap));
	}
	
}
