package com.hunter.helloandroid.module.update;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.module.update.bean.VersionBean;
import com.hunter.helloandroid.module.update.util.UpdateVersionUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2018
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/5/14 15:20
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class UpdateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_update)
    void onClickUpdates() {
        System.out.println(".........1111...........");
    }

    UpdateActivity getView() {
        return this;
    }

    private void getVersionResult(VersionBean result) {
        new UpdateVersionUtil(new MyUpdateVersion(), this).getVersionResult(result);
    }


    private class MyUpdateVersion implements UpdateVersionUtil.UpdateVersion {

        @Override
        public void dismissLoadingLayout() {
            getView().dismissLoadingLayout();
        }

        @Override
        public void updateLoadProcess(int process) {
            getView().updateLoadProcess(process);
        }

        @Override
        public void downloadFinish(Dialog dialog) {
            getView().downloadFinish(dialog);
        }

        @Override
        public void setupFailure() {
            getView().setupFailure();
        }

        @Override
        public void loadFailure(Dialog dialog) {
            getView().loadFailure(dialog);
        }

        @Override
        public Dialog onClickUpdate(View.OnClickListener onClickListener) {
            return getView().onClickUpdate(onClickListener);
        }

        @Override
        public void hasNewVersion(String msg, View.OnClickListener onClickListener) {
            getView().hasNewVersion(msg, onClickListener);
        }

        @Override
        public void latestVersion() {
        }
    }

    private void loadFailure(Dialog dialog) {

    }

    private Dialog onClickUpdate(View.OnClickListener onClickListener) {
        return null;
    }

    private void hasNewVersion(String msg, View.OnClickListener onClickListener) {

    }

    private void setupFailure() {

    }

    private void updateLoadProcess(int process) {

    }

    private void downloadFinish(Dialog dialog) {

    }

    private void dismissLoadingLayout() {

    }

}
