package com.hunter.helloandroid.module.beam_mvp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.module.beam_mvp.presenter.LoginPresenter;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/11/27 16:00
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
@RequiresPresenter(LoginPresenter.class)
public class BeamMvpLoginActivity extends BeamBaseActivity<LoginPresenter> implements View.OnClickListener {


    EditText tilNumber;
    EditText tilPassword;
    Button btnLogin;
    TextView tvPrompt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beam_mvp);

        System.out.println(".BeamMvpLoginActivity......onCreate..........");

        tilNumber = $(R.id.tilNumber);
        tilPassword = $(R.id.tilPassword);
        btnLogin = $(R.id.btn_login);
        tvPrompt = $(R.id.tv_prompt);

        btnLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                checkLogin();
                break;
        }
    }

    //view可以简单进行一些表单验证之类的逻辑
    private void checkLogin() {
        String userName = tilNumber.getText().toString();
        String password = tilPassword.getText().toString();
        if (userName.length() != 11) {
            tvPrompt.setText("手机号格式错误");
            return;
        } else {
            tvPrompt.setText("");
        }
        if (password.length() < 6 || password.length() > 12) {
            tvPrompt.setText("密码应为6-12位");
            return;
        } else {
            tvPrompt.setText("");
        }
        getPresenter().login(userName, password);
    }

    //下面几个方法是暴露给presenter使用的接口
    public void setPasswordError() {
        tvPrompt.setText("密码错误");
    }

    public void setError(String text) {
        tvPrompt.setText(text);
    }
    public void setNumberError() {
        tvPrompt.setText("手机号还没有注册");
    }

    public void setNumberAndPassword(String number, String password) {
        tilNumber.setText(number);
        tilPassword.setText(password);
    }

    public void showProgress(String showProgress) {
        tvPrompt.setText("showProgress:" + showProgress);
        System.out.println("..........showProgress:" + showProgress);
    }

    public void dismissProgress() {
        tvPrompt.setText("dismissProgress");
        System.out.println("..........dismissProgress.........");
    }

    public void success(String success) {
        tvPrompt.setText("success:"+success);
        System.out.println("..........dismissProgress.........");
    }

}
