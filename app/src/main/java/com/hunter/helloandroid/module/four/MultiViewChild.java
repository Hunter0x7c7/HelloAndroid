package com.hunter.helloandroid.module.four;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.hunter.helloandroid.R;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2017
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/12/21 17:00
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class MultiViewChild extends FrameLayout implements MultiViewGroup.OnDiscardListener {

    public MultiViewChild(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MultiViewChild(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MultiViewChild(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        addView(View.inflate(context, R.layout.item_multi_view_group, null));
    }


    @Override
    public void onDiscard() {
        System.out.println(".......onDiscard.......");
    }
}
