package com.hunter.helloandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2017
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/4/28 18:11
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class ZHButton extends Button {

    public ZHButton(Context context) {
        super(context);
    }

    public ZHButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZHButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

  /*      TypedArray a = context.obtainStyledAttributes(attrs, com.android.internal.R.styleable.View,
                defStyleAttr, 0);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case com.android.internal.R.styleable.View_background:
                    Drawable drawable = a.getDrawable(attr);
                    System.out.println(i + " drawable:" + drawable.toString());
                    break;
                case com.android.internal.R.styleable.View_padding:
                    int dimensionPixelSize = a.getDimensionPixelSize(attr, -1);
                    System.out.println(i + " dimensionPixelSize:" + dimensionPixelSize);
                    break;
            }
        }
        a.recycle();*/
    }


}
