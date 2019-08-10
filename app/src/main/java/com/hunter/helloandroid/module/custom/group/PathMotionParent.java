package com.hunter.helloandroid.module.custom.group;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * ================================================================
 * <p>
 * 版    权： HunterHuang(c)2018
 * <p>
 * 作    者： Hunter_1125607007@QQ.COM
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/5/15 17:01
 * <p>
 * 描    述：Path运动的父类
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class PathMotionParent extends ViewGroup {

    private Paint mPaint;

    public PathMotionParent(Context context) {
        super(context);
        init();
    }

    public PathMotionParent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathMotionParent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
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
//        test(canvas);

        final int count = getChildCount();
        int childMeasureWidth, childMeasureHeight;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            if (child instanceof PathContainer) {
                PathContainer container = (PathContainer) child;
                Path path = container.getPath();
                if (path != null) {
                    canvas.drawPath(path, mPaint);
                }
                container.getPosTan();


                //注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
                childMeasureWidth = child.getMeasuredWidth();
                childMeasureHeight = child.getMeasuredHeight();

                int left = (int) (container.getPositionX() - childMeasureWidth / 2);
                int top = (int) (container.getPositionY() - childMeasureHeight / 2);
                int right = left + childMeasureWidth;
                int bottom = top + childMeasureHeight;
                //确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
                child.layout(left, top, right, bottom);
            }
        }
        invalidate();
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
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();
        int childMeasureWidth, childMeasureHeight;
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

}
