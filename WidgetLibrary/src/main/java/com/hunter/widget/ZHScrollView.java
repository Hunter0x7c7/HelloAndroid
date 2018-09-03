package com.hunter.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ScrollView;


/**
 * 自定义ScrollView控件，支持设置maxHeight
 *
 * @author Zihang Huang
 *         create date： 2018/3/30 11:05
 */
public class ZHScrollView extends ScrollView {
    private int mMaxHeight;

    public ZHScrollView(Context context) {
        super(context);
        init(null);
    }

    public ZHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ZHScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        int maxHeight = -1;
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ZHScrollView);
            maxHeight = ta.getDimensionPixelSize(R.styleable.ZHScrollView_maxHeight, maxHeight);
            ta.recycle();
        }
        if (maxHeight > 0) {
            setMaxHeight(maxHeight);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //重新计算控件高、宽
        super.onMeasure(widthMeasureSpec, getHeightMeasureSpec(heightMeasureSpec));
    }

    private int getHeightMeasureSpec(int heightMeasureSpec) {
        //此处是关键，设置控件高度 （在此替换成自己需要的高度）
        int maxHeight = getMaxHeight();
        if (maxHeight > 0 && MeasureSpec.getSize(heightMeasureSpec) > maxHeight) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        }
        return heightMeasureSpec;
    }

    public int getMaxHeight() {
        return mMaxHeight;
    }

    public void setMaxHeight(int mMaxHeight) {
        this.mMaxHeight = mMaxHeight;
    }

}
