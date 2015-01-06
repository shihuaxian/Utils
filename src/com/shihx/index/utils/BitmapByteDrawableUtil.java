package com.shihx.index.utils;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * @author shihx1
 * @date 11:02:56 AM Jan 6, 2015
 * @todo TODO
 */
public class BitmapByteDrawableUtil {
	
	/**
	 * 从资源中获取Bitmap
	 */
	public Bitmap getBitmapFromRes(Context context,int resID){
		Resources resources = context.getResources();
		Bitmap bmp = BitmapFactory.decodeResource(resources, resID);
		return bmp;
	}
	
	/**
	 * bitmap to byte
	 */
	public byte[] bitmap2Byte(Bitmap bitmap){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}
	
	/**
	 * data 转换成bitmap
	 */
	public Bitmap bytes2Bitmap(byte[] data){
		if(data.length !=0 ){
			return BitmapFactory.decodeByteArray(data, 0, data.length);
		}
		return null;
	}
	
	/**
	 * Bitmap缩放
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap,int width,int height){
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float)width/w);
		float scaleHeight = ((float)height/h);
		
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, w, h,matrix,true);
		return newBmp;
	}
	
	/**
	 * drawable to bitmap
	 */
	public static Bitmap drawableToBitmap(Drawable drawable){
		//取drawable 的长宽
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		
		//取drawable
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE 
				? Bitmap.Config.ALPHA_8:Bitmap.Config.RGB_565;
		//建立对应bitmap
		Bitmap bitmap = Bitmap.createBitmap(w,h,config);
		//建立对应画布
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0,0,w,h);
		//把drawable 内容到画布中
		drawable.draw(canvas);
		return bitmap;
	}
	
	/**
	 * 获取圆角图片
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		
		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0,0,w,h);
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap,rect,rect,paint);
		
		return output;
	}
	/**
	 * drawable 缩放
	 */
	public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        // drawable转换成bitmap
        Bitmap oldbmp = drawableToBitmap(drawable);
        // 创建操作图片用的Matrix对象
        Matrix matrix = new Matrix();
        // 计算缩放比例
        float sx = ((float) w / width);
        float sy = ((float) h / height);
        // 设置缩放比例
        matrix.postScale(sx, sy);
        // 建立新的bitmap，其内容是对原bitmap的缩放后的图
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        return new BitmapDrawable(newbmp);
    }
	
	/**
	 * 倒影
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
                h / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
                Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }
}
