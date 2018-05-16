package com.hunter.helloandroid.module.custom.group;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.hunter.panorama.util.DensityUtil;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2018
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/5/15 17:01
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class PathGroup extends ViewGroup {

    public PathGroup(Context context) {
        super(context);
        init();
    }

    public PathGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);

        //设置背景才会调用onDraw()
        setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPath == null) {
            return;
        }
        canvas.drawPath(mPath, mPaint);

        final int count = getChildCount();
        int childMeasureWidth, childMeasureHeight;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            //注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();

            float degress = (float) Math.toDegrees(Math.atan2(mTan[1], mTan[0]));
            Matrix matrix = new Matrix();
            matrix.postRotate(degress, 100, 100);
//            matrix.postRotate(degress, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
//            canvas.concat(matrix);
//            canvas.setMatrix(matrix);

            int left = (int) (mPoint[0] - childMeasureWidth / 2);
            int top = (int) (mPoint[1] - childMeasureHeight / 2);
            int right = left + childMeasureWidth;
            int bottom = top + childMeasureHeight;
            //确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
            child.layout(left, top, right, bottom);
        }
    }

    /**
     * 要求所有的孩子测量自己的大小，然后根据这些孩子的大小完成自己的尺寸测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //测量并保存layout的宽高(使用getDefaultSize时，wrap_content和match_perent都是填充屏幕)
        //稍后会重新写这个方法，能达到wrap_content的效果
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    /**
     * 为所有的子控件摆放位置.
     */
    protected void onLayout2(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();
        int childMeasureWidth = 0;
        int childMeasureHeight = 0;
        int layoutWidth = 0;    // 容器已经占据的宽度
        int layoutHeight = 0;   // 容器已经占据的宽度
        int maxChildHeight = 0; //一行中子控件最高的高度，用于决定下一行高度应该在目前基础上累加多少
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            //注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();
            if (layoutWidth + childMeasureWidth > getWidth()) {
                //排满后换行
                layoutWidth = 0;
                layoutHeight += maxChildHeight;
                maxChildHeight = 0;

            }
            //如果一行没有排满，继续往右排列
            left = layoutWidth;
            right = left + childMeasureWidth;
            top = layoutHeight;
            bottom = top + childMeasureHeight;


            layoutWidth += childMeasureWidth;  //宽度累加
            if (childMeasureHeight > maxChildHeight) {
                maxChildHeight = childMeasureHeight;
            }
            float pointX = mPoint[0] - childMeasureWidth / 2;
            float pointY = mPoint[1] - childMeasureHeight / 2;


            //确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
//            child.layout(left, top, right, bottom);
            child.layout((int) pointX, (int) pointY, (int) pointX + childMeasureWidth, (int) pointY + childMeasureHeight);
//            child.layout(0, 0, (int) pointX, (int) pointY);


        }
    }

    /**
     * 为所有的子控件摆放位置.
     */
//    @Override
    protected void onLayout3(boolean changed, int left, int top, int right, int bottom) {
        System.out.println("...........onLayout.........");
        final int count = getChildCount();
        int childMeasureWidth = 0;
        int childMeasureHeight = 0;
        int layoutWidth = 0;    // 容器已经占据的宽度
        int layoutHeight = 0;   // 容器已经占据的宽度
        int maxChildHeight = 0; //一行中子控件最高的高度，用于决定下一行高度应该在目前基础上累加多少
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            //注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();
            if (layoutWidth + childMeasureWidth > getWidth()) {
                //排满后换行
                layoutWidth = 0;
                layoutHeight += maxChildHeight;
                maxChildHeight = 0;

            }
            //如果一行没有排满，继续往右排列
            left = layoutWidth;
            right = left + childMeasureWidth;
            top = layoutHeight;
            bottom = top + childMeasureHeight;


            layoutWidth += childMeasureWidth;  //宽度累加
            if (childMeasureHeight > maxChildHeight) {
                maxChildHeight = childMeasureHeight;
            }

            //确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
            child.layout(left, top, right, bottom);
        }
    }

    private Path mPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float[] mPoint;
    private float[] mTan;

    public void startAnim() {
        System.out.println("........startAnim............");
//        invalidate();

        if (mPathMeasure == null) {
            initPath();
        }
        ValueAnimator animator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        animator.setDuration(20000);
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

    private int mWidth, mHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        System.out.println("...........onSizeChanged.........");
        mWidth = w;
        mHeight = h;

        initPath();
        startAnim();
    }

    private void initPath() {
        Path path = new Path();
        float radius = Math.min(mWidth, mHeight) / 2;
        path.addCircle(mWidth / 2, mHeight / 2, radius - DensityUtil.dip2px(getContext(), 55), Path.Direction.CW);
        setPath(path);
    }

    public void setPath(Path path) {
        mPath = path;
        mPathMeasure = new PathMeasure(path, false);
        mPoint = new float[2];
        mTan = new float[2];
    }


}
