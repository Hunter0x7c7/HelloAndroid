package com.hunter.helloandroid.module.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/12/12 13:55
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class RotateButton extends android.support.v7.widget.AppCompatButton {

    public RotateButton(Context context) {
        super(context);
    }

    public RotateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.rotate(5f);
        super.onDraw(canvas);
        canvas.restore();
    }

}
