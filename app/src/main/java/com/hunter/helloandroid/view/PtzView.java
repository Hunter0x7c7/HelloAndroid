package com.hunter.helloandroid.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/12/5 16:26
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class PtzView extends View {

    private Paint mPaint;
    private int mRadius, mBackgroundRadius;
    private float mCenterX, mCenterY, mCurrentX, mCurrentY;
    private int mBackgroundColor = Color.argb(0XAA, 0xF4, 0xF4, 0xF4);
    private int mCenterColor = Color.rgb(0xF3, 0x7F, 0x4C);

    public PtzView(Context context) {
        super(context);
        init(context);
    }

    public PtzView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PtzView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true); //无锯齿
    }

    public static Bitmap getBackGroundBitmap() {
        return getBackGroundBitmap(Color.YELLOW);
    }

    public static Bitmap getBackGroundBitmap(int color) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        Bitmap bitmap = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawColor(color);
        return bitmap;
    }

    public static Bitmap getRoundedCornerBitmap(float roundPx) {
        return getRoundedCornerBitmap(getBackGroundBitmap(), roundPx);
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);

        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        Rect clipBounds = canvas.getClipBounds();
        return output;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int width = getWidth();
        int height = getHeight();
        mRadius = Math.min(width / 3, height / 3) / 2;
        mBackgroundRadius = Math.min(width, height) / 2;

        mCurrentX = mCenterX = width / 2;
        mCurrentY = mCenterY = height / 2;
    }

    private Rect mBounds = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.TRANSPARENT);
//        canvas.drawColor(Color.GRAY);
        mPaint.setColor(mBackgroundColor);
        canvas.drawCircle(mCenterX, mCenterY, mBackgroundRadius, mPaint);

        mPaint.setColor(mCenterColor);
        canvas.drawCircle(mCurrentX, mCurrentY, mRadius, mPaint);
    }

    public int getCenterColor() {
        return mCenterColor;
    }

    public void setCenterColor(@ColorInt int color) {
        this.mCenterColor = color;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                mCurrentX = (int) event.getX();
                mCurrentY = (int) event.getY();

                //如果当前坐标超出范围
                if (mCurrentX < mRadius) {
                    mCurrentX = mRadius;
                }
                if (mCurrentX > mCenterX + mRadius) {
                    mCurrentX = mCenterX + mRadius;
                }
                if (mCurrentY < mRadius) {
                    mCurrentY = mRadius;
                }
                if (mCurrentY > mCenterY + mRadius) {
                    mCurrentY = mCenterY + mRadius;
                }
                invalidate();

//                //点击位置x坐标与圆心的x坐标的距离
//                float distanceX = Math.abs(mCurrentX - mCenterX);
//                //点击位置y坐标与圆心的y坐标的距离
//                float distanceY = Math.abs(mCurrentY - mCenterY);
//                //点击位置与圆心的直线距离
//                int distanceZ = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
//
//                //如果点击位置与圆心的距离大于圆的半径，证明点击位置没有在圆内
//                int diff = mBackgroundRadius - mRadius;
//                if (distanceZ < diff) {
//                    invalidate();
//                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mCurrentX = mCenterX;
                mCurrentY = mCenterY;
                invalidate();
                break;
        }
        return true;
    }
}
