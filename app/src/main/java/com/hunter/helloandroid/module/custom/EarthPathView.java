package com.hunter.helloandroid.module.custom;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.hunter.helloandroid.R;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2018
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/4/10 16:28
 * <p>
 * 描    述：根据路径旋转View
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class EarthPathView extends View {

    private Path mPath;
    private Paint mPaint;
    private Bitmap mBitmap;
    private PathMeasure mPathMeasure;
    private float[] mPoint;
    private float[] mTan;
    private float mDdegrees;

    public EarthPathView(Context context) {
        this(context, null);
        init(context);
    }

    public EarthPathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public EarthPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);

//        InputStream is = getResources().openRawResource(R.mipmap.head);
//        mBitmap = BitmapFactory.decodeStream(is);
//        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;       // 缩放图片
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.arrow, options);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPath == null) {
            return;
        }
//        canvas.rotate(mDdegrees += 2, getWidth() / 2, getHeight() / 2);
        canvas.drawPath(mPath, mPaint);

        float degress = (float) Math.toDegrees(Math.atan2(mTan[1], mTan[0]));
        Matrix matrix = new Matrix();
        matrix.postRotate(degress, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        matrix.postTranslate(mPoint[0] - mBitmap.getWidth() / 2, mPoint[1] - mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, matrix, null);
    }

    private int mWidth, mHeight;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        initPath();
    }

    private void initPath() {
        Path path = new Path();
        float radius = Math.min(mWidth, mHeight) / 2;
        path.addCircle(mWidth / 2, mHeight / 2, radius - 20, Path.Direction.CW);
        setPath(path);
    }

    public void setPath(Path path) {
        mPath = path;
        mPathMeasure = new PathMeasure(path, false);
        mPoint = new float[2];
        mTan = new float[2];
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void startAnim() {

        if (mPathMeasure == null) {
            initPath();
        }
        ValueAnimator animator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        animator.setDuration(5000);
        animator.setInterpolator(new LinearInterpolator()); //插值器
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float distance = (float) animation.getAnimatedValue();
                mPathMeasure.getPosTan(distance, mPoint, mTan);
                invalidate();
            }
        });
        animator.start();
//        animator.cancel();
    }
} 
