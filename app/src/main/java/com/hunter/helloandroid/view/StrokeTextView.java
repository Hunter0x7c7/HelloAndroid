package com.hunter.helloandroid.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * 一个带描边效果的TextView
 *
 * @author Zihang Huang
 *         create date 2017/6/8 9:49
 */
public class StrokeTextView extends TextView {
    private Context mContext;
    private Paint mTextPaint;
    private boolean drawSideLine = true; // false不采用描边
    private int strokeColor = Color.BLACK;
    private int textColor = Color.WHITE;
    private float strokeWidth = 1;

    public StrokeTextView(Context context) {
        super(context);
        init(context);
    }


    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mTextPaint = getPaint();
        strokeColor=  Color.parseColor("#aa000000");
        setStrokeWidth(strokeWidth);
    }

    /**
     * (non-Javadoc)
     *
     * @see TextView#onDraw(Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (drawSideLine) {
            // 描外层
            //super.setTextColor(Color.BLUE); // 不能直接这么设，如此会导致递归
            setTextColorUseReflection(strokeColor);
            mTextPaint.setStrokeWidth(strokeWidth);  // 描边宽度
            mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE); //描边种类
            mTextPaint.setFakeBoldText(true); // 外层text采用粗体
            mTextPaint.setShadowLayer(1, 0, 0, 0); //字体的阴影效果，可以忽略
            super.onDraw(canvas);


            // 描内层，恢复原先的画笔
            //super.setTextColor(Color.BLUE); // 不能直接这么设，如此会导致递归
            setTextColorUseReflection(textColor);
            mTextPaint.setStrokeWidth(0);
            mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mTextPaint.setFakeBoldText(false);
            mTextPaint.setShadowLayer(0, 0, 0, 0);
        }
        super.onDraw(canvas);
    }

    private void setTextColorUseReflection(int color) {
        Field textColorField;
        try {
            textColorField = TextView.class.getDeclaredField("mCurTextColor");
            textColorField.setAccessible(true);
            textColorField.set(this, color);
            textColorField.setAccessible(false);

//            ColorStateList textColorList = getTextColors();
//            int[] drawableState = getDrawableState();
//            int defaultColor = textColorList.getDefaultColor();
//            int colorForState = textColorList.getColorForState(drawableState, defaultColor);
//            textColorField.set(this, colorForState); //填充具体的color进去
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTextPaint.setColor(color);
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return pxValue / scale;
    }

    public boolean isDrawSideLine() {
        return drawSideLine;
    }

    public void setDrawSideLine(boolean drawSideLine) {
        this.drawSideLine = drawSideLine;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    /**
     * 设置描边宽度
     *
     * @param strokeWidth 线宽单位dip
     */
    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = dip2px(mContext, strokeWidth);
    }
}
