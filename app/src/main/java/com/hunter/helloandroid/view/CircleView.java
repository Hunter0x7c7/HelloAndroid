package com.hunter.helloandroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

import com.hunter.helloandroid.R;


/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/8/10 13:23
 * <p>
 * 描    述：圆形View
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class CircleView extends View {

    private Paint mPaint;
    private
    @ColorInt
    int color = 0XFF000000;

    public CircleView(Context context) {
        super(context);
        init(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
            if (typedArray != null) {
                color = typedArray.getColor(R.styleable.CircleView_circleColor, color);
                typedArray.recycle();
            }
        }
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(false); //设置画笔为无锯齿
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.TRANSPARENT);
        int radius = Math.min(getWidth(), getHeight()) / 2;
        mPaint.setColor(color);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
    }

    public int getColor() {
        return color;
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
        invalidate();
    }
}
