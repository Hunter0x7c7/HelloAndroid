package com.hunter.helloandroid.module.rotation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

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
 * 创建日期： 2017/12/12 16:16
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class RotationActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private AngleViewGroup avgAngle1, avgAngle2, avgAngle3, avgAngle4;
    private LinearLayout ll_button;
    private Button btnButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation);


        avgAngle1 = (AngleViewGroup) findViewById(R.id.avg_angle1);
        avgAngle2 = (AngleViewGroup) findViewById(R.id.avg_angle2);
        avgAngle3 = (AngleViewGroup) findViewById(R.id.avg_angle3);
        avgAngle4 = (AngleViewGroup) findViewById(R.id.avg_angle4);

        ll_button = (LinearLayout) findViewById(R.id.ll_button);
        btnButton = (Button) findViewById(R.id.btn_button);


        SeekBar sb_seek = (SeekBar) findViewById(R.id.sb_seek);
        SeekBar sb_width = (SeekBar) findViewById(R.id.sb_width);
        SeekBar sb_height = (SeekBar) findViewById(R.id.sb_height);
        sb_seek.setOnSeekBarChangeListener(this);
        sb_width.setOnSeekBarChangeListener(this);
        sb_height.setOnSeekBarChangeListener(this);
    }


    public void onClickAngleViewGroup(View view) {
        avgAngle1.rotationLayout();
//        avgAngle2.rotationLayout();
//        avgAngle3.rotationLayout();
//        avgAngle4.rotationLayout();

        setChildEnabled(ll_button, false);
        ll_button.postDelayed(new Runnable() {
            @Override
            public void run() {
                setChildEnabled(ll_button, true);
            }
        }, 5000);
    }

    /**
     * 遍历布局，并禁用所有子控件
     *
     * @param viewGroup 布局对象
     */
    public void setChildEnabled(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                setChildEnabled((ViewGroup) view, enabled);
            } else {
                view.setEnabled(enabled);
            }
        }
    }

    int width = 0;
    int height = 0;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        switch (seekBar.getId()) {
            case R.id.sb_seek:
                break;
            case R.id.sb_width:

                if (width <= 0 && height <= 0) {
                    width = avgAngle1.getWidth();
                    height = avgAngle1.getHeight();
                }

                int diff = height - width;
                int diffProgress = progress * diff / 100;

                ViewGroup.LayoutParams params = avgAngle1.getLayoutParams();
                params.width = width + diffProgress;
                avgAngle1.setLayoutParams(params);
                System.out.println("...........diff:" + diff);
                System.out.println("...........+++ :" + diffProgress);
                System.out.println("...........params.width :" + params.width + " width:" + width);

                ViewGroup parent = (ViewGroup) avgAngle1.getParent();
//                parent.layout(avgAngle1.getLeft() + diffProgress / 2, avgAngle1.getTop(), width + avgAngle1.getLeft(), height + avgAngle1.getTop());
                parent.layout(100, 100, avgAngle1.getRight(), avgAngle1.getBottom());
                break;
            case R.id.sb_height:

                if (width <= 0 && height <= 0) {
                    width = avgAngle1.getWidth();
                    height = avgAngle1.getHeight();
                }

                diff = width - height;

                params = avgAngle1.getLayoutParams();
                params.height = height + (progress * diff / 100);
                avgAngle1.setLayoutParams(params);
                System.out.println("...........diff:" + diff);
                System.out.println("...........+++ :" + (progress * diff / 100));
                System.out.println("...........params.width :" + params.height + " width:" + height);

                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
