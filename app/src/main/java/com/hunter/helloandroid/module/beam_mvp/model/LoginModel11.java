package com.hunter.helloandroid.module.beam_mvp.model;

import android.content.Context;

import com.hunter.helloandroid.module.beam_mvp.OkHttpUtil;
import com.hunter.helloandroid.module.beam_mvp.presenter.LoginPresenter;
import com.jude.beam.model.AbsModel;


public class LoginModel11 extends AbsModel {

    public static LoginModel11 getInstance() {
        return getInstance(LoginModel11.class);
    }

    private Context mContext;

    @Override
    public void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);

        mContext = ctx;
    }

    //以下全都是面向上层的接口。这里全都是回调形式的异步返回。
    public void userLogin(final String userName, final String password, final LoginPresenter.StatusCallback callback) {
        //这里是向网络请求数据，并接受返回数据，解析返回上层。这里我在Callback里完成数据解析。让代码更简洁。
//        RequestManager.getInstance().post(API.URL.ModifyName, new RequestMap("password", password), callback);

        new OkHttpUtil().testPostDlogin();
    }


    public class TerminalBean {
        //    {"status":"fail","msg":"请先登录后再操作","code":0}
        private String status;
        private String msg;
        private int code;
        private Orders orders;

        @Override
        public String toString() {
            return "TerminalBean{" +
                    "status='" + status + '\'' +
                    ", msg='" + msg + '\'' +
                    ", code=" + code +
                    ", orders=" + orders +
                    '}';
        }
    }

    public class Orders {
    }


}