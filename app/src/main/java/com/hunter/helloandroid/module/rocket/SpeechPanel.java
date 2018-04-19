package com.hunter.helloandroid.module.rocket;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.util.ClickUtil;
import com.hunter.helloandroid.view.CircleView;
import com.hunter.panorama.util.DensityUtil;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2017
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/12/1 16:48
 * <p>
 * 描    述：语音操作面板
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class SpeechPanel extends RelativeLayout implements CompoundButton.OnCheckedChangeListener
        , View.OnTouchListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private Context mContext;
    private LinearLayout progressGroup;
    private CheckBox cbMic  ;
    private SeekBar sbProgress;
    private View progressView1,vBackground , mDiscernView;
    private AnimatorSet mRotationAnimator;

    public SpeechPanel(Context context) {
        super(context);
        init(context);
    }

    public SpeechPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SpeechPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    private void init(Context context) {
        mContext = context;
        dip2 = dip2px(2);

        RelativeLayout groud = new RelativeLayout(context);
        groud.setLayoutParams(new LayoutParams(dip2px(40), dip2px(40)));

        //背景
        CircleView background = new CircleView(context);
        LayoutParams params = new LayoutParams(-1, -1);
        params.addRule(CENTER_IN_PARENT);
        background.setLayoutParams(params);
        background.setColor(Color.parseColor("#48AAAAAA"));

        //识别中
        mDiscernView = new View(context);
        params = new LayoutParams(-1, -1);
        params.addRule(CENTER_IN_PARENT);
        int dip4 = dip2px(2);
        params.setMargins(dip4, dip4, dip4, dip4);
        mDiscernView.setLayoutParams(params);
        mDiscernView.setBackgroundResource(R.mipmap.ic_discern);

        //白色圆形背景
        vBackground = new View(context);
        vBackground.setId(R.id.v_background);
        vBackground.setOnClickListener(this);
        vBackground.setOnTouchListener(this);
        params = new LayoutParams(dip2px(32), dip2px(32));
        params.addRule(CENTER_IN_PARENT);
        vBackground.setLayoutParams(params);
        vBackground.setBackgroundResource(R.drawable.btn_speech_switch_selector);

        //mic+开关状态
        cbMic = new CheckBox(context);
        cbMic.setId(R.id.cb_mic);
        cbMic.setOnClickListener(this);
        cbMic.setOnTouchListener(this);
        params = new LayoutParams(dip2px(25), dip2px(25));
        params.addRule(CENTER_IN_PARENT);
        cbMic.setLayoutParams(params);
        cbMic.setBackgroundResource(R.drawable.btn_mic_selector);
        cbMic.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        cbMic.setOnCheckedChangeListener(this);

        groud.addView(background);
        groud.addView(mDiscernView);
        groud.addView(vBackground);
        groud.addView(cbMic);
        addView(groud);

        /*
       //这里实现音量变化的功能
        < LinearLayout
            android:layout_width="8dp"
            android:layout_height="15dp"
            android:layout_alignTop="@id/tv_mic"
            android:layout_marginLeft="2dp"
            android:layout_centerHorizontal="true"
            android:orientation="vertical">

            <View
                android:id="@+id/v_view"
                android:layout_width="match_parent"
                android:layout_height="0dp" />

            <View
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="@drawable/ic_mic" />
        </LinearLayout>
         */

        progressGroup = new LinearLayout(context);
        LayoutParams layoutParams = new LayoutParams(dip2px(8), dip2px(12));
        layoutParams.addRule(CENTER_HORIZONTAL);
        layoutParams.addRule(ALIGN_TOP, R.id.cb_mic);
        layoutParams.setMargins(dip2, dip2, 0, 0);
        progressGroup.setLayoutParams(layoutParams);
        progressGroup.setOrientation(LinearLayout.VERTICAL);

        progressView1 = new View(context);
        progressView1.setLayoutParams(new LinearLayout.LayoutParams(-1, dip2px(8)));

        View progressView2 = new View(context);
        progressView2.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        progressView2.setBackgroundResource(R.drawable.ic_mic);

        sbProgress = new SeekBar(context);
        sbProgress.setOnSeekBarChangeListener(this);
        sbProgress.setMax(100);

        progressGroup.addView(progressView1);
        progressGroup.addView(progressView2);

        groud.addView(progressGroup);

        resetStatus();//初始化状态
    }

    public void stopSpeech() {
        if (cbMic.isChecked()) {
            ClickUtil.simulateClick(cbMic);
        }
    }

    private int maxVolume = 30;

    /**
     * 更新音量
     */
    public void updateVolume(int volume) {

        int percent;//百分之几
        if (getCurrentStatus() == PanelStatusEnum.CLOSE) {//如果状态已经关闭，不显示音量
            percent = 100;
        } else {
            int diff = maxVolume - volume;
            diff = diff < 0 ? 0 : diff;
            diff = diff > maxVolume ? maxVolume : diff;
            percent = diff * 100 / maxVolume;//百分之几

            //如果状态不是PanelStatusEnum.SPEAK则改为PanelStatusEnum.SPEAK
            if (getCurrentStatus() != PanelStatusEnum.SPEAK)
                setCurrentStatus(PanelStatusEnum.SPEAK);
        }
        sbProgress.setProgress(percent);
    }

    /**
     * 面板状态枚举
     */
    public enum PanelStatusEnum {
        //关
        CLOSE,
        //开
        OPEN,
        //说话
        SPEAK,
        //识别
        DISCERN
    }

    private PanelStatusEnum mCurrentStatus = PanelStatusEnum.CLOSE;

    public PanelStatusEnum getCurrentStatus() {
        return mCurrentStatus;
    }

    public void setCurrentStatus(PanelStatusEnum mCurrentStatus) {
        this.mCurrentStatus = mCurrentStatus;

        switch (mCurrentStatus) {
            case OPEN:
            case CLOSE:
                resetStatus();//初始化状态
                break;
            case SPEAK:
                break;
            case DISCERN:
                doDiscern();
                break;
        }
    }

    /**
     * 识别状态结束时
     */
    private void discernEnd() {
        mDiscernView.setVisibility(View.GONE);
        if (mRotationAnimator != null)
            mRotationAnimator.cancel();
    }

    /**
     * 进入识别状态时
     */
    private void doDiscern() {
        mDiscernView.setVisibility(View.VISIBLE);
        if (mRotationAnimator == null) {
            ObjectAnimator rotation = ObjectAnimator.ofFloat(mDiscernView, "rotation", 0, 360);
            rotation.setRepeatCount(Animation.INFINITE);
            rotation.setRepeatMode(ValueAnimator.RESTART);

            mRotationAnimator = new AnimatorSet();
            mRotationAnimator.play(rotation);
            mRotationAnimator.setInterpolator(new LinearInterpolator());//线性插值器： 匀速动画
            mRotationAnimator.setDuration(800).start();
        } else {
            mRotationAnimator.start();
        }
    }

    /**
     * 初始化状态
     */
    public void resetStatus() {
        discernEnd();
        updateVolume(0);
    }

    private OnTouchListener onTouchListener;

    public void setOnChildTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    private float startX, startY;
    private int dip2;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()) {
            case R.id.cb_mic:
            case R.id.v_background:

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();

                        view.setTag(true);//标记是否是点击事件
                        view.setPressed(true);
                        cbMic.setPressed(true);//如果是点击的mic 白色背景也显示按压效果
                        break;
                    case MotionEvent.ACTION_MOVE:

                        //在2dp范围内不算移动
                        if (Math.abs(startX - event.getX()) > dip2 || Math.abs(startY - event.getY()) > dip2) {
                            view.setTag(false);//标记是否是点击事件
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        startX = view.getX();
                        startY = view.getY();

                        //如果是点击事件
                        if ((view.getTag() instanceof Boolean) && (boolean) view.getTag())
                            onClick(view);

                        view.setTag(false);//标记是否是点击事件
                        view.setPressed(false);
                        cbMic.setPressed(false);//如果是点击的mic 白色背景也显示按压效果

                        break;
                }
                if (onTouchListener != null) {
                    onTouchListener.onTouch(view, event);
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_mic:
            case R.id.v_background:
                cbMic.setChecked(!cbMic.isChecked());
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (progressGroup == null || progressView1 == null) {
            return;
        }
        if (progressGroup.getLayoutParams() == null) {
            return;
        }
        int totalHeight = progressGroup.getLayoutParams().height;//总的高度
        int height = totalHeight;
        switch (getCurrentStatus()) {
            case OPEN:
            case SPEAK:

                height = progress * totalHeight / 100;
                break;
        }
        ViewGroup.LayoutParams params = progressView1.getLayoutParams();
        if (params == null) {
            return;
        }
        //改变进度上面的View，间接改变进度的高度
        params.height = height;
        progressView1.setLayoutParams(params);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public interface OnToggleChangeListener {
        void onToggleChanged(View view, boolean isChecked);
    }

    private OnToggleChangeListener onToggleChangeListener;

    public void setOnToggleChangeListener(OnToggleChangeListener onToggleChangeListener) {
        this.onToggleChangeListener = onToggleChangeListener;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked) updateVolume(0);//关闭时先清空音量进度

        setCurrentStatus(isChecked ? PanelStatusEnum.OPEN : PanelStatusEnum.CLOSE);

        if (onToggleChangeListener != null) {
            onToggleChangeListener.onToggleChanged(this, isChecked);
        }
    }

    private int dip2px(int dpValue) {
        return DensityUtil.dip2px(mContext, dpValue);
    }

    public void setPanelIcon(@DrawableRes int resid) {
        cbMic.setBackgroundResource(resid);
    }

}
