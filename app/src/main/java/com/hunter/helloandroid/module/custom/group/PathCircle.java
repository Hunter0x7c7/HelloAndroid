package com.hunter.helloandroid.module.custom.group;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.panorama.util.DensityUtil;

import java.util.Random;

/**
 * ================================================================
 * <p>
 * 版    权： HunterHuang(c)2018
 * <p>
 * 作    者： Hunter_1125607007@QQ.COM
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/5/16 13:17
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class PathCircle extends View implements PathContainer {

    public PathCircle(Context context) {
        super(context);
        init();
    }

    public PathCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        initPath(600, 600);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            int width = parent.getWidth();
            int height = parent.getHeight();
            initPath(width, height);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);//分别拿到宽高的大小和测量模式

        int mWidth, mHeight;//最终宽度高度
        int yy_width = widthSize;//预测宽度，先假设它等于指定大小或填充窗体
        int yy_height = heightSize;//预测宽度，先假设它等于指定大小或填充窗体
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;//如果是指定大小或填充窗体（以后直接说成指定大小）,直接设置最终宽度
        } else {
//            yy_width=tempImage.getWidth();//如果是包裹内容，则预测宽度等于图片宽度
            yy_width = DensityUtil.dip2px(getContext(), 20);
            mWidth = yy_width + getPaddingLeft() + getPaddingRight();//最终宽度等于预测宽度加 左右Padding宽度
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;//同上
        } else {
            yy_height = DensityUtil.dip2px(getContext(), 20);
            mHeight = getPaddingTop() + getPaddingBottom() + yy_height;//最终高度等于预测宽度加 上下Padding宽度
            //目的是让控件的宽高相等，但Padding是可以由用户自由指定的，所以再加上padding
        }
        setMeasuredDimension(mWidth, mHeight);//设置View的宽高，测量结束
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private Path mPath;
    private PathMeasure mPathMeasure;
    private float[] mPosition;
    private float[] mTan;

    @Override
    public Path getPath() {
        return mPath;
    }

    @Override
    public float getPositionX() {
        return mPosition[0];
    }

    @Override
    public float getPositionY() {
        return mPosition[1];
    }

    @Override
    public float getTangentX() {
        return mTan[0];
    }

    @Override
    public float getTangentY() {
        return mTan[1];
    }

    public void setPath(Path path) {
        mPath = path;
        mPathMeasure = new PathMeasure(path, false);
        mPosition = new float[2];
        mTan = new float[2];

        int length = (int) mPathMeasure.getLength();
        if (length > 0){
            mCurrentValue = new Random().nextInt(length);
        }
        mSpeedFactor = 0.2f + new Random().nextInt((int) (mSpeedFactor * 100)) / 100;
    }

    private void initPath(int mWidth, int mHeight) {
        int offset = DensityUtil.dip2px(getContext(), 5 + new Random().nextInt(40));

        Path path = new Path();
        float radius = Math.min(mWidth, mHeight) / 2;
        radius = (radius - offset) <= 0 ? radius : (radius - offset);
        path.addCircle(mWidth * 17 / 30, mHeight * 17 / 30, radius, Path.Direction.CW);
        setPath(path);
    }

    // 用于纪录当前的位置,取值范围[0,length]映射Path的整个长度
    private float mCurrentValue = 0f;
    //速度系数
    private float mSpeedFactor = 2.0f;

    @Override
    public void getPosTan() {

        if (mPathMeasure == null) {
            return;
        }
        float length = mPathMeasure.getLength();

        // 计算当前的位置在总长度上的距离
        mCurrentValue += mSpeedFactor;
        if (mCurrentValue >= length) {
            // 跳转到下一条路径
            if (!mPathMeasure.nextContour()) {
                resetPath();//重置路径
            }
            mCurrentValue = 0;
        }
        // 获取当前位置的坐标以及趋势
        mPathMeasure.getPosTan(mCurrentValue, mPosition, mTan);
    }

    /**
     * 重置路径
     */
    private void resetPath() {
        mPathMeasure.setPath(mPath, false);
    }

}
