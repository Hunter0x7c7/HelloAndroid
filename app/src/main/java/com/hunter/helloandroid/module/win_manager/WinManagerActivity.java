package com.hunter.helloandroid.module.win_manager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.widget.CirclePromptView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2017
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/13 15:28
 * <p>
 * 描    述：颜色渐变
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class WinManagerActivity extends AppCompatActivity {

    @BindView(R.id.cpv_prompt)
    CirclePromptView mCirclePromptView;
    private Button mSpeechPanel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_manager);

        init();
    }

    private void init() {
        ButterKnife.bind(this);

        mSpeechPanel = new Button(this);
        mSpeechPanel.setAllCaps(false);
        mSpeechPanel.setText("SpeechPanel");
    }

    public void onClickAddView(View view) {
        WinManagerUtil.getInstance().setup(this, mSpeechPanel);

        mCirclePromptView.setFullStyle(true);
//        mCirclePromptView.setColor(Color.YELLOW);
        mCirclePromptView.setPromptRadius(3);
        mCirclePromptView.startPrompt();
    }

    public void onClickRemoveView(View view) {
        WinManagerUtil.getInstance().remove(mSpeechPanel);

        mCirclePromptView.stopPrompt();
    }

}
