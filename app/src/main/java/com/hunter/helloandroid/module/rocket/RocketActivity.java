package com.hunter.helloandroid.module.rocket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.hunter.helloandroid.R;
import com.hunter.panorama.util.DensityUtil;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/12/1 13:44
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class RocketActivity extends AppCompatActivity implements SpeechPanel.OnToggleChangeListener {

    //        private View itemRocket;
    private SpeechPanel itemRocket;
    private SpeechPanel hvHaul;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket);

//        itemRocket = View.inflate(this, R.layout.item_rocket, null);
        itemRocket = new SpeechPanel(this);
        itemRocket.setOnToggleChangeListener(this);

//        hvHaul = (SpeechPanel) findViewById(R.id.hv_haul);
        hvHaul = new SpeechPanel(this);
        hvHaul.setOnToggleChangeListener(this);
        hvHaul.setTag(false);

        hvHaul.setOnChildTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                WinManagerUtil.getInstance().addOnTouchListener(hvHaul,event);
                return false;
            }
        });

        WinManagerUtil.getInstance().setup(this, hvHaul);

        final View v_view = findViewById(R.id.v_view);
        SeekBar sb_progress = (SeekBar) findViewById(R.id.sb_progress);

        sb_progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                ViewGroup.LayoutParams params = v_view.getLayoutParams();
                params.height = dip2px(30 -progress);
                v_view.setLayoutParams(params);

                hvHaul.updateVolume(progress);
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private int dip2px(int progress) {
        return DensityUtil.dip2px(this,  progress);
    }

    public void onClickButton(View view) {
        WinManagerUtil.getInstance().setup(this, itemRocket);
    }

    public void onClickButton2(View view) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        WinManagerUtil.getInstance().remove(itemRocket);
        itemRocket = null;
    }

    @Override
    public void onToggleChanged(View view, boolean isChecked) {
        switch (view.getId()) {
            default:
                updatePanelStatus(isChecked ? 1 : 0);
                break;
        }
    }

    private void updatePanelStatus(int status) {

        SpeechPanel.PanelStatusEnum statusEnum;
        switch (status) {
            case 1:
                statusEnum = SpeechPanel.PanelStatusEnum.OPEN;
                break;
            case 2:
                statusEnum = SpeechPanel.PanelStatusEnum.SPEAK;
                break;
            case 3:
                statusEnum = SpeechPanel.PanelStatusEnum.DISCERN;
                break;
            default:
            case 0:
                statusEnum = SpeechPanel.PanelStatusEnum.CLOSE;
                break;
        }
        itemRocket.setCurrentStatus(statusEnum);
    }


}
