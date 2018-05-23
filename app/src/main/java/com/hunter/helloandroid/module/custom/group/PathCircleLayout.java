package com.hunter.helloandroid.module.custom.group;

import android.content.Context;
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
 * 版    权： 上海田韵物联网科技有限公司(c)2018
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/5/16 13:17
 * <p>
 * 描    述：圆形Path布局
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class PathCircleLayout extends ViewGroup implements PathContainer {

    public PathCircleLayout(Context context) {
        super(context);
        init();
    }

    public PathCircleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathCircleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
            System.out.println("............w:" + w + " h:" + h + " width:" + width + " height:" + height);
        }
    }

    /**
     * 要求所有的孩子测量自己的大小，然后根据这些孩子的大小完成自己的尺寸测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int layoutWidth = 0;
        int layoutHeight = 0;
        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int cWidth, cHeight;
        int count = getChildCount();

        if (widthMode == MeasureSpec.EXACTLY) {
            //如果布局容器的宽度模式是确定的（具体的size或者match_parent），直接使用父窗体建议的宽度
            layoutWidth = sizeWidth;
        } else {
            //如果是未指定或者wrap_content，我们都按照包裹内容做，宽度方向上只需要拿到所有子控件中宽度做大的作为布局宽度
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                cWidth = child.getMeasuredWidth();
                //获取子控件最大宽度
                layoutWidth = cWidth > layoutWidth ? cWidth : layoutWidth;
            }
        }
        //高度很宽度处理思想一样
        if (heightMode == MeasureSpec.EXACTLY) {
            layoutHeight = sizeHeight;
        } else {
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                cHeight = child.getMeasuredHeight();
                layoutHeight = cHeight > layoutHeight ? cHeight : layoutHeight;
            }
        }

        // 测量并保存layout的宽高
        setMeasuredDimension(layoutWidth, layoutHeight);
    }

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
        int offset = DensityUtil.dip2px(getContext(), 10 + new Random().nextInt(80));

        Path path = new Path();
        float radius = Math.min(mWidth, mHeight) / 2;
        radius = (radius - offset) <= 0 ? radius : (radius - offset);
        path.addCircle(mWidth * 17 / 30, mHeight * 17 / 30, radius, Path.Direction.CW);
        setPath(path);
    }


    // 用于纪录当前的位置,取值范围[0,length]映射Path的整个长度
    private float mCurrentValue = 0f;
    //速度系数
    private float mSpeedFactor = 1.5f;

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
