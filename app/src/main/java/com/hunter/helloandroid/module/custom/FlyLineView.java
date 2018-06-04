package com.hunter.helloandroid.module.custom;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.module.update.util.StringUtil;
import com.hunter.helloandroid.widget.CirclePromptView;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2018
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/5/29 9:56
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class FlyLineView extends ViewGroup implements FlyLineLayout.FlyLine {


    public FlyLineView(Context context) {
        super(context);
        init(null);
    }

    public FlyLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FlyLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FlyLine);

            String startPoint = typedArray.getString(R.styleable.FlyLine_startPoint);
            String stopPoint = typedArray.getString(R.styleable.FlyLine_stopPoint);

            String[] startPointArray = StringUtil.splitString(startPoint, ",");
            String[] stopPointArray = StringUtil.splitString(stopPoint, ",");

            if (startPointArray != null && startPointArray.length >= 2) {
                double startPercentX = getPercent(startPointArray[0]);
                double startPercentY = getPercent(startPointArray[1]);
                setStartPercentX(startPercentX);
                setStartPercentY(startPercentY);
            }
            if (stopPointArray != null && stopPointArray.length >= 2) {
                double stopPercentX = getPercent(stopPointArray[0]);
                double stopPercentY = getPercent(stopPointArray[1]);
                setStopPercentX(stopPercentX);
                setStopPercentY(stopPercentY);
            }
            typedArray.recycle();
        }
        mCirclePromptView = new CirclePromptView(getContext(), attrs);
        mBezierView = new BezierView(getContext(), attrs);
        int dip20 = DensityUtil.dip2px(getContext(), 60);
        mCirclePromptView.setLayoutParams(new ViewGroup.LayoutParams(dip20, dip20));
        mCirclePromptView.setFullStyle(false);
//        mCirclePromptView.setColor(Color.YELLOW);
        mCirclePromptView.setPromptRadius(6);
        mCirclePromptView.startPrompt();

//        addView(mCirclePromptView);
        addView(mBezierView);
    }

    private CirclePromptView mCirclePromptView;
    private BezierView mBezierView;

    /**
     * 要求所有的孩子测量自己的大小，然后根据这些孩子的大小完成自己的尺寸测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 对所有子view进行测量，触发所有子view的onMeasure函数
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // 宽度模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        // 测量宽度
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        // 高度模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        // 测量高度
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 子view数目
        int childCount = getChildCount();
        if (childCount == 0) {
            // 如果当前ViewGroup没有子View，就没有存在的意义，无需占空间
            setMeasuredDimension(5, 5);
        } else {
//            // 如果宽高都是包裹内容
//            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
//                // 宽度为所有子view宽度相加，高度取子view最大高度
////                int width = getTotalWidth();
////                int height = getMaxHeight();
////                setMeasuredDimension(width, height);
//                setMeasuredDimension(getMinimumWidth(), getMinimumHeight());
//            } else if (widthMode == MeasureSpec.AT_MOST) {
//                // 宽度为所有子View宽度相加，高度为测量高度
//                setMeasuredDimension(getMinimumWidth(), heightSize);
//            } else if (heightMode == MeasureSpec.AT_MOST) {
//                // 宽度为测量宽度，高度为子view最大高度
//                setMeasuredDimension(widthSize, getMinimumHeight());
//            } else {
//            }
            setMeasuredDimension(widthSize, heightSize);
            System.out.println("............minWidth:" + getMinimumWidth() + " minHeight:" + getMinimumHeight());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int parentWidth = getParentWidth();
        int parentHeight = getParentHeight();
        PointF startPoint = new PointF((float) (parentWidth * getStartPercentX()), (float) (parentHeight * getStartPercentY()));
        PointF stopPoint = new PointF((float) (parentWidth * getStopPercentX()), (float) (parentHeight * getStopPercentY()));

        float left = Math.min(startPoint.x, stopPoint.x);
        float top = Math.min(startPoint.y, stopPoint.y);

        System.out.println("...........onSizeChanged....left:" + left + " top:" + top
                + " parentWidth:" + parentWidth + " parentHeight:" + parentHeight);

        mBezierView.setStartPoint(new PointF(startPoint.x - left, startPoint.y - top));
        mBezierView.setStopPoint(new PointF(stopPoint.x - left, stopPoint.y - top));
    }

    public PointF getBezierSize(PointF point1, PointF point2) {
        PointF result = new PointF();
        if (point1 != null && point2 != null) {
            float width = (float) Math.abs(Math.ceil(point1.x - point2.x));
            float height = (float) Math.abs(Math.ceil(point1.y - point2.y));
            result.set(width, height);
        }
        return result;
    }

    /**
     * 获取子view最大高度
     */
    private int getMaxHeight() {
        // 最大高度
        int maxHeight = 0;
        // 子view数目
        int childCount = getChildCount();
        // 遍历子view拿取最大高度
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getMeasuredHeight() > maxHeight)
                maxHeight = childView.getMeasuredHeight();
        }

        return maxHeight;
    }

    /**
     * 所有子view宽度相加
     */
    private int getTotalWidth() {
        // 所有子view宽度之和
        int totalWidth = 0;
        // 子View数目
        int childCount = getChildCount();
        // 遍历所有子view拿取所有子view宽度之和
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            totalWidth += childView.getMeasuredWidth();
        }
        return totalWidth;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth();
        int height = getHeight();

        double startPercentX, startPercentY, stopPercentX, stopPercentY;
        int left, top, right, bottom;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {

                left = child.getLeft();
                top = child.getTop();
                child.layout(0, 0, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
            }
        }
    }


    private int mParentWidth, mParentHeight;

    @Override
    public int getParentWidth() {
        return mParentWidth;
    }

    @Override
    public void setParentWidth(int parentWidth) {
        mParentWidth = parentWidth;
    }

    @Override
    public int getParentHeight() {
        return mParentHeight;
    }

    @Override
    public void setParentHeight(int parentHeight) {
        mParentHeight = parentHeight;
    }

    public static class DensityUtil {
        /**
         * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
         */
        public static int dip2px(Context context, float dpValue) {
            return (int) (dpValue * getScale(context) + 0.5f);
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        public static int px2dip(Context context, float pxValue) {
            return (int) (pxValue / getScale(context) + 0.5f);
        }

        private static float getScale(Context context) {
            float density = 0;
            if (context != null) {
                Resources res = context.getResources();
                if (res != null) {
                    DisplayMetrics metrics = res.getDisplayMetrics();
                    if (metrics != null) {
                        density = metrics.density;
                    }
                }
            }
            return density;
        }
    }

    private double getPercent(String percent) {
        double result = 0;
        if (percent != null && percent.contains("%")) {
            try {
                result = Double.parseDouble(percent.replace("%", ""));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return (result / 100L) % 1;
    }

    private double mStartPercentX, mStartPercentY, mStopPercentX, mStopPercentY;

    public double getStartPercentX() {
        return mStartPercentX;
    }

    public void setStartPercentX(double mStartPercentX) {
        this.mStartPercentX = mStartPercentX;
    }

    public double getStartPercentY() {
        return mStartPercentY;
    }

    public void setStartPercentY(double mStartPercentY) {
        this.mStartPercentY = mStartPercentY;
    }

    public double getStopPercentX() {
        return mStopPercentX;
    }

    public void setStopPercentX(double mStopPercentX) {
        this.mStopPercentX = mStopPercentX;
    }

    public double getStopPercentY() {
        return mStopPercentY;
    }

    public void setStopPercentY(double mStopPercentY) {
        this.mStopPercentY = mStopPercentY;
    }

    private PointF mStartPoint, mStopPoint;

    public void setStartPoint(PointF point) {
        mStartPoint = point;
    }

    public void setStopPoint(PointF point) {
        mStopPoint = point;
    }

    public PointF getStartPoint() {
        return mStartPoint;
    }

    public PointF getStopPoint() {
        return mStopPoint;
    }

    @Override
    public int getMinimumWidth() {
        int bezierViewWidth = getBezierViewWidth();
        return (int) (getPromptSize().x / 2 + bezierViewWidth);
    }

    @Override
    public int getMinimumHeight() {
        int bezierViewHeight = getBezierViewHeight();
        return (int) (getPromptSize().y / 2 + bezierViewHeight);
    }

    public PointF getPromptSize() {
        int dip50 = DensityUtil.dip2px(getContext(), 50);
        return new PointF(dip50, dip50);
    }

    public int getBezierViewWidth() {
        int result = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof BezierView) {
                int measured = child.getMeasuredWidth();
                if (measured > result) {
                    result = measured;
                }
            }
        }
        return result;
    }

    public int getBezierViewHeight() {
        int result = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof BezierView) {
                int measured = child.getMeasuredHeight();
                if (measured > result) {
                    result = measured;
                }
            }
        }
        return result;
    }

}
