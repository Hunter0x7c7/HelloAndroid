package com.hunter.helloandroid.module.rocket;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/12/1 16:48
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class SpeechPanelView extends LinearLayout implements CompoundButton.OnCheckedChangeListener {

    private CheckBox cbToggle;
    private TextView tvStatus;

    public SpeechPanelView(Context context) {
        super(context);
        init(context);
    }

    public SpeechPanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SpeechPanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }


    private void init(Context context) {
        setOrientation(VERTICAL);

//        addView(View.inflate(context, R.layout.view_group_haul, null));
//        cbToggle = (CheckBox) findViewById(R.id.cb_toggle);
//        tvStatus = (TextView) findViewById(R.id.tv_status);

        cbToggle = new CheckBox(context);
        tvStatus = new TextView(context);

        tvStatus.setText("状态: .......");
        cbToggle.setText("语音助手");
        cbToggle.setOnCheckedChangeListener(this);

        addView(cbToggle);
        addView(tvStatus);
    }

    public void updateStatus(String status) {
        tvStatus.setText(status);
    }

    private OnToggleChangeListener onToggleChangeListener;

    public void setOnToggleChangeListener(OnToggleChangeListener onToggleChangeListener) {
        this.onToggleChangeListener = onToggleChangeListener;
    }


    public interface OnToggleChangeListener {
        void onToggleChanged(View view, boolean isChecked);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (onToggleChangeListener != null) {
            onToggleChangeListener.onToggleChanged(this, isChecked);
        }
    }
}
