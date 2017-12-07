package com.hunter.helloandroid.module.beam_mvp;

import com.google.gson.Gson;
import com.hunter.helloandroid.module.beam_mvp.model.LoginModel;
import com.hunter.helloandroid.util.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/11/30 17:51
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class Model {


    public void userLogin(final String userName, final String password) {


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://shuihuo.tyuniot.com")
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


            LoginModel.RequestSerives apiService = retrofit.create(LoginModel.RequestSerives.class);
            apiService.login3(userName, password, "1")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Consumer<LoginModel.LoginBean>() {
                        @Override
                        public void accept(LoginModel.LoginBean loginBean) {
                            System.out.println("......doOnNext........:" + new Gson().toJson(loginBean.Data));
                            ToastUtil.showPrompt("....doOnNext........");
                        }
                    })
//                    .unsubscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<LoginModel.LoginBean>() {
                        @Override
                        public void accept(LoginModel.LoginBean loginBean) {

                            ToastUtil.showPrompt("....subscribe.......accept...:" + new Gson().toJson(loginBean.Data));
                            try {
                                System.out.println("......subscribe.....accept...:" + new Gson().toJson(loginBean.Data));
//                                if (callback != null) {
//                                    callback.success(new Gson().toJson(loginBean));
//                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });


    }
}
