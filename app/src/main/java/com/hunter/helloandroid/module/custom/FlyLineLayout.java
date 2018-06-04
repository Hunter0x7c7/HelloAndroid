package com.hunter.helloandroid.module.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PointF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.module.update.util.StringUtil;


/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2018
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/5/28 14:12
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class FlyLineLayout extends ViewGroup {


    public FlyLineLayout(Context context) {
        super(context);
        init(null);
    }

    public FlyLineLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FlyLineLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private double mStartPercentX, mStartPercentY;

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FlyLine);

            String startPoint = typedArray.getString(R.styleable.FlyLine_startPoint);
            String[] startPointArray = StringUtil.splitString(startPoint, ",");

            if (startPointArray != null && startPointArray.length >= 2) {
                double startPercentX = getPercent(startPointArray[0]);
                double startPercentY = getPercent(startPointArray[1]);
                setStartPercentX(startPercentX);
                setStartPercentY(startPercentY);
            }
            typedArray.recycle();
        }

    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
    }

    @Override
    public void addView(View child, LayoutParams params) {
        //取出自定义属性
        View view = getFlyLineChild(child, params);
        super.addView(view, params);
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        //取出自定义属性
        View view = getFlyLineChild(child, params);
        super.addView(view, index, params);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyLayoutParams(getContext(), attrs);
    }

    private View getFlyLineChild(View child, LayoutParams params) {
        View result = child;
        //取出自定义属性
        MyLayoutParams p = (MyLayoutParams) params;
        //判断是否包含自定义属性
        if (isDiscrollvable(p)) {
            //包含自定义属性
            FlyLineChild flyLineChild = new FlyLineChild(getContext(), null);
//            flyLineChild.setStartPercentX(p.getStartPercentX());
//            flyLineChild.setStartPercentY(p.getStartPercentY());
            flyLineChild.setStopPercentX(p.getStopPercentX());
            flyLineChild.setStopPercentY(p.getStopPercentY());
            flyLineChild.addView(child);

            result = flyLineChild;
        }
        return result;
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

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int width = getWidth();
        int height = getHeight();
        System.out.println("..........width:" + width + " height:" + height);

        double startPercentX, startPercentY, stopPercentX, stopPercentY;
        int left, top, right, bottom;
        int childCount = getChildCount();
        startPercentX = getStartPercentX();
        startPercentY = getStartPercentY();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                if (child instanceof FlyLineChild) {
                    FlyLineChild flyLine = (FlyLineChild) child;
                    stopPercentX = flyLine.getStopPercentX();
                    stopPercentY = flyLine.getStopPercentY();
                    left = (int) (width * Math.min(startPercentX, stopPercentX));
                    top = (int) (height * Math.min(startPercentY, stopPercentY));
//                    right = (int) (Math.max(startPercentX, stopPercentX));
//                    bottom = (int) (height * Math.max(startPercentY, stopPercentY));
//                    System.out.println("..........left:" + left + " top:" + top + " right:" + right + " bottom:" + bottom);
//                child.layout(left, top, right, bottom);
                } else {
                    left = child.getLeft();
                    top = child.getTop();
                }
                child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof FlyLineLayout.FlyLine) {
                FlyLine flyLine = (FlyLine) child;
                flyLine.setParentWidth(w);
                flyLine.setParentHeight(h);
            }
        }
    }

    private boolean isDiscrollvable(MyLayoutParams p) {
        return p != null
                && (p.getStartPercentX() > 0
                || p.getStartPercentY() > 0
                || p.getStopPercentX() > 0
                || p.getStopPercentY() > 0);
    }

    public class MyLayoutParams extends LayoutParams {
        private int mStartX = 0;
        private int mStartY = 0;
        private int mStopX = 0;
        private int mStopY = 0;
        private int mLineColor = Color.WHITE;
        private int mPointColor = Color.BLACK;
        private String mPointName;
        private int mNameSize = 12;
        private double mStartPercentX, mStartPercentY, mStopPercentX, mStopPercentY;

        public MyLayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context, attrs);
        }

        private void init(Context context, AttributeSet attrs) {

            if (context != null && attrs != null) {
                TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlyLine);
//                mStartX = typedArray.getDimensionPixelSize(R.styleable.FlyLine_startX, mStartX);
//                mStartY = typedArray.getDimensionPixelSize(R.styleable.FlyLine_startY, mStartY);
//                mStopX = typedArray.getDimensionPixelSize(R.styleable.FlyLine_stopX, mStopX);
//                mStopY = typedArray.getDimensionPixelSize(R.styleable.FlyLine_stopY, mStopY);

                String startPoint = typedArray.getString(R.styleable.FlyLine_startPoint);
                String stopPoint = typedArray.getString(R.styleable.FlyLine_stopPoint);

                mLineColor = typedArray.getColor(R.styleable.FlyLine_lineColor, mLineColor);
                mPointColor = typedArray.getColor(R.styleable.FlyLine_pointColor, mPointColor);

                String pointName = typedArray.getString(R.styleable.FlyLine_pointName);
                int nameSize = typedArray.getDimensionPixelSize(R.styleable.FlyLine_nameSize, -1);

                String[] startPointArray = StringUtil.splitString(startPoint, ",");
                String[] stopPointArray = StringUtil.splitString(stopPoint, ",");

                if (startPointArray != null && startPointArray.length >= 2) {
                    double startPercentX = getPercent(startPointArray[0]);
                    double startPercentY = getPercent(startPointArray[1]);
                    mStartPercentX = startPercentX;
                    mStartPercentY = startPercentY;
                }
                if (stopPointArray != null && stopPointArray.length >= 2) {
                    double stopPercentX = getPercent(stopPointArray[0]);
                    double stopPercentY = getPercent(stopPointArray[1]);
                    mStopPercentX = stopPercentX;
                    mStopPercentY = stopPercentY;
                }
                if (!TextUtils.isEmpty(pointName)) {
                    setPointName(pointName);
                }
                if (nameSize > 0) {
                    setNameSize(nameSize);
                }
                typedArray.recycle();
            }

        }

        public PointF getStartPoint() {
            return getStartPoint(getWidth(), getHeight());
        }

        public PointF getStartPoint(float width, float height) {
            return new PointF((float) (width * mStartPercentX), (float) (height * mStartPercentY));
        }

        public PointF getStopPoint() {
            return getStopPoint(getWidth(), getHeight());
        }

        public PointF getStopPoint(float width, float height) {
            return new PointF((float) (width * mStopPercentX), (float) (height * mStopPercentY));
        }

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

        public int getLineColor() {
            return mLineColor;
        }

        public void setLineColor(int mLineColor) {
            this.mLineColor = mLineColor;
        }

        public int getPointColor() {
            return mPointColor;
        }

        public void setPointColor(int mPointColor) {
            this.mPointColor = mPointColor;
        }

        public String getPointName() {
            return mPointName;
        }

        public void setPointName(String mPointName) {
            this.mPointName = mPointName;
        }

        public int getNameSize() {
            return mNameSize;
        }

        public void setNameSize(int mNameSize) {
            this.mNameSize = mNameSize;
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

    public interface FlyLine {
        int getParentWidth();

        void setParentWidth(int parentWidth);

        int getParentHeight();

        void setParentHeight(int parentHeight);
    }
}
