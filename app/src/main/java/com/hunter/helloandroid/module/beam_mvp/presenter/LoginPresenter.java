package com.hunter.helloandroid.module.beam_mvp.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hunter.helloandroid.module.beam_mvp.model.LoginModel;
import com.hunter.helloandroid.module.beam_mvp.ui.BeamMvpLoginActivity;
import com.jude.beam.bijection.Presenter;

public class LoginPresenter extends Presenter<BeamMvpLoginActivity> {

    BeamMvpLoginActivity mActivity;

    @Override
    protected void onCreate(@NonNull BeamMvpLoginActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
        mActivity = getView();
        LoginModel.getInstance().onAppCreate(mActivity);
    }


    //处理登录事件。这里没有逻辑，直接将数据交由AccountModel去处理。接收回调，根据结果修改View
    public void login(String userName, String password) {
        mActivity.showProgress("登录中");

        StatusCallback<String> statusCallback = new StatusCallback<String>() {

            @Override
            public void result(final int status, String info) {
                System.out.println(".....result..........info:" + info + ":" + status);

                mActivity.dismissProgress();
                switch (status) {
                    case 1:
                        mActivity.setNumberError();
                        break;
                    case 2:
                        mActivity.setPasswordError();
                        break;
                }
            }

            @Override
            public void success(final String info) {
                System.out.println(".....success..........info:" + info);
//                getView().finish();
                mActivity.success(info);

            }
        };
        LoginModel.getInstance().userLogin(userName, password, statusCallback);
    }

    public interface StatusCallback<T> {
        void success(T info);

        void result(int status, T info);
    }

}
