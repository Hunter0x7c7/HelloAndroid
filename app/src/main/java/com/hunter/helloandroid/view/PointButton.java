package com.hunter.helloandroid.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.interfaces.OnNestItemClickListener;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2017
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/11/17 17:00
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class PointButton extends RelativeLayout implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private TextView tvNameRight;
    private ImageButton ibButton;
    private ViewGroup llControlBg;
    private View child;
    private int dip15 = dip2px(12);
    private int duration = 200;
    private LinearLayout llPointMenu;
    private RadioGroup rgPointMenu;


    public PointButton(Context context) {
        super(context);
        init(context);
    }

    public PointButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PointButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context mContext;

    private void init(Context context) {
        mContext = context;

        child = View.inflate(mContext, R.layout.point_button, null);
        tvNameRight = (TextView) child.findViewById(R.id.tv_name_right);
        ibButton = (ImageButton) child.findViewById(R.id.ib_button);
        llControlBg = (ViewGroup) child.findViewById(R.id.ll_control_bg);
        llPointMenu = (LinearLayout) child.findViewById(R.id.ll_point_menu);
        rgPointMenu = (RadioGroup) child.findViewById(R.id.rg_point_menu);
        TextView btn_sense = (TextView) child.findViewById(R.id.btn_sense);
        TextView btn_monitor = (TextView) child.findViewById(R.id.btn_monitor);
        TextView btn_run = (TextView) child.findViewById(R.id.btn_run);
        View ib_option_back = (View) child.findViewById(R.id.ib_option_back);
        ibButton.setOnClickListener(this);
        btn_sense.setOnClickListener(this);
        btn_monitor.setOnClickListener(this);
        btn_run.setOnClickListener(this);
        ib_option_back.setOnClickListener(this);
        addView(child);

        tvNameRight.setVisibility(View.INVISIBLE);
        llControlBg.setVisibility(View.INVISIBLE);
        rgPointMenu.setOnCheckedChangeListener(this);
    }

    public void setOnItemClickListener(int position, OnNestItemClickListener onItemClickListener) {
        this.position = position;
        this.onItemClickListener = onItemClickListener;
    }

    public static final int NORMAL = 0;
    private static final int SHOW_TEXT = 1;
    private static final int CLICK = 2;
    private int clickStatus = -1;
    private OnNestItemClickListener onItemClickListener;
    private int position;

    @Override
    public void onClick(View v) {

        int subPosition = -1;
        int i = v.getId();
        if (i == R.id.ib_button) {
            ObjectAnimator animator = null;
            switch (clickStatus) {
                case CLICK:

                    clickStatus = SHOW_TEXT;
                    animator = ObjectAnimator.ofFloat(tvNameRight, "translationX", -tvNameRight.getWidth() - dip15, 0);
                    startScaleRight2(llControlBg);

                    //关闭的时候取消选中
                    rgPointMenu.clearCheck();
                    break;
                case NORMAL:
                default:

                    tvNameRight.setVisibility(View.VISIBLE);
                case SHOW_TEXT:

                    clickStatus = CLICK;
                    animator = ObjectAnimator.ofFloat(tvNameRight, "translationX", 0, -tvNameRight.getWidth() - dip15);
                    startScaleRight(llControlBg);
                    break;

            }

            if (animator != null) {
                animator.setDuration(duration);
                animator.start();
            }

        } else if (i == R.id.btn_sense) {
            subPosition = 0;

        } else if (i == R.id.btn_run) {
            subPosition = 1;

        } else if (i == R.id.btn_monitor) {
            subPosition = 2;

        } else if (i == R.id.ib_option_back) {
            subPosition = 3;

        }
        if (onItemClickListener != null) {
            onItemClickListener.onNestItemClick(v, position, subPosition);
        }
    }


    private void startScaleRight2(final View view) {

        view.setVisibility(View.VISIBLE);
        view.setPivotX(0);

        view.setPivotY(view.getHeight() / 2);

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "scaleX", 1, 0);
        animator.setDuration(duration);
        animator.addListener(new MyAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    /**
     * 动画执行监听接口
     */
    private class MyAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }

    private void startScaleRight(View view) {

        view.setVisibility(View.VISIBLE);
        view.setPivotX(0);
        view.setPivotY(view.getHeight() / 2);

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "scaleX", 0, 1);
        animator.setDuration(duration);
        animator.start();
    }

    public void closeShow() {
        switch (clickStatus) {
            case NORMAL:

//                System.out.println("closeShow clickStatus:NORMAL");
                break;
            case SHOW_TEXT:

//                System.out.println("closeShow clickStatus:SHOW_TEXT");
                break;
            case CLICK:
//                System.out.println("closeShow clickStatus:CLICK");

                ObjectAnimator animator = ObjectAnimator.ofFloat(tvNameRight, "translationX", -tvNameRight.getWidth() - dip15, 0);
                animator.setDuration(duration);
                animator.start();
                startScaleRight2(llControlBg);

                break;
        }
        clickStatus = NORMAL;
        tvNameRight.setVisibility(View.INVISIBLE);
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private String[] pointMenuNameNormal = {"传感", "监控", "运行"};
    private String[] pointMenuNameChecked = {"传感数据", "监控视频", "运行情况"};

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
    }

    private void updatePointMenuNmae(RadioGroup group, int checkedId) {
        RadioButton view = (RadioButton) group.findViewById(checkedId);
        int index = group.indexOfChild(view);
        int count = rgPointMenu.getChildCount();
        for (int i = 0; i < count; i++) {
            RadioButton childAt = (RadioButton) rgPointMenu.getChildAt(i);
            childAt.setText(pointMenuNameNormal[i]);
        }
        view.setText(pointMenuNameChecked[index]);
    }

    public String getNameRight() {
        return tvNameRight.getText().toString();
    }

    public void setNameRight(String text) {
        tvNameRight.setText(text);
    }

    public int getClickStatus() {
        return clickStatus;
    }

    public void setClickStatus(int clickStatus) {
        this.clickStatus = clickStatus;
    }

    public boolean isOpen() {
        int clickStatus = getClickStatus();
        return clickStatus != NORMAL && clickStatus != -1;
    }
}
