/**
 *  Author :  hmg25
 *  Description :
 */
package com.shihx.index.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

/**
 * hmg25's android Type
 *
 *@author shihx1
 *
 */
public class ImageUtil {
	public static Bitmap drawableToBitmap(Drawable drawable){
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, 
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0,0,width,height);
		drawable.draw(canvas);
		return bitmap;
	}
	
	/**
	 *  circle corner
	 *  Author :  hmg25
	 *  Version:  1.0 
	 *  Description :
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap){
		Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(outBitmap);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPX = bitmap.getWidth()/2;
		paint.setAntiAlias(true);
		canvas.drawARGB(0,0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		
		return outBitmap;
	}
	
	/**
	 *  reflection
	 *  Author :  hmg25
	 *  Version:  1.0 
	 *  Description :
	 */
	public static Bitmap getReflectionBitmap(Bitmap bitmap){
		int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		Matrix matrix = new Matrix();
		matrix.postScale(1, -1);
		
		Bitmap reflectionBitmap = Bitmap.createBitmap(bitmap,0, height/2,width,height/2,matrix,false);
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width, height+height/2, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		
		Paint defaultPaint = new Paint();
		defaultPaint.setAntiAlias(true);
		canvas.drawRect(0, height, width, height+reflectionGap, defaultPaint);
		canvas.drawBitmap(reflectionBitmap, 0, height+reflectionGap, null);
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		//线性渐变
		LinearGradient shader = new LinearGradient(0, height, 0, bitmapWithReflection.getHeight()+reflectionGap,
				0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DARKEN));
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()+reflectionGap, paint);
		
		return bitmapWithReflection;
	}
	
}
