package com.hunter.helloandroid.module.gradient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2017
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/14 14:55
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class SwitchView extends View {

    public SwitchView(Context context) {
        super(context);
        init(context);
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context mContext;
    private Paint mBackgroundPaint = new Paint();

    void init(Context context) {
        mContext = context;
        mBackgroundPaint.setStrokeWidth(dip2px(context, 3));
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPaint(mBackgroundPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
