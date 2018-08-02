package com.hunter.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * 根据可显示宽度调整字体大小
 *
 * @author Zihang Huang
 *         create date： 2018/7/3 14:31
 */
public class AutoAlignTextView extends AppCompatTextView {
    private float mMaxTextSize, mMinTextSize;   //最大字体和最小字体
    private float mDefMinSize = 8;

    public AutoAlignTextView(Context context) {
        super(context);
        init(null);
    }

    public AutoAlignTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AutoAlignTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mMaxTextSize = (int) getTextSize();
        mMinTextSize = DensityUtil.dip2px(getContext(), mDefMinSize);
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.AutoAlignTextView);
            mMaxTextSize = ta.getDimensionPixelSize(R.styleable.AutoAlignTextView_maxTextSize, (int) getMaxTextSize());
            mMinTextSize = ta.getDimensionPixelSize(R.styleable.AutoAlignTextView_minTextSize, (int) getMinTextSize());
            ta.recycle();
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        refresh();
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        adjustTvTextSize();
    }

    //动态修改字体大小
    private void adjustTvTextSize() {
        String text = getText().toString();
        int avaiWidth = getWidth() - getPaddingLeft() - getPaddingRight() - DensityUtil.dip2px(getContext(), 2);
        if (avaiWidth <= 0) {
            return;
        }
        TextPaint textPaintClone = new TextPaint(getPaint());
        float trySize = getMaxTextSize();
        textPaintClone.setTextSize(trySize);
        while (textPaintClone.measureText(text) > avaiWidth && trySize > mMinTextSize) {
            trySize--;
            textPaintClone.setTextSize(trySize);
        }
        setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
    }

    public void setMaxTextSize(float maxTextSize) {
        this.mMaxTextSize = maxTextSize;
        refresh();
    }

    public void setMinTextSize(float minTextSize) {
        this.mMinTextSize = minTextSize;
        refresh();
    }

    public float getMaxTextSize() {
        return mMaxTextSize;
    }

    public float getMinTextSize() {
        return mMinTextSize;
    }

    public void refresh() {
        adjustTvTextSize();
    }

    private static class DensityUtil {
        private static int dip2px(Context context, float dpValue) {
            return (int) (dpValue * getDensity(context) + 0.5F);
        }

        private static int px2dip(Context context, float pxValue) {
            return (int) (pxValue / getDensity(context) + 0.5F);
        }

        private static float getDensity(Context context) {
            return context.getResources().getDisplayMetrics().density;
        }

    }

}
