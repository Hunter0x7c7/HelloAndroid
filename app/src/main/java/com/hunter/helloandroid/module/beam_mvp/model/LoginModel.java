package com.hunter.helloandroid.module.beam_mvp.model;

import android.content.Context;

import com.google.gson.Gson;
import com.hunter.helloandroid.module.beam_mvp.presenter.LoginPresenter;
import com.hunter.helloandroid.util.ToastUtil;
import com.jude.beam.model.AbsModel;

import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public class LoginModel extends AbsModel {

    public static LoginModel getInstance() {
        return getInstance(LoginModel.class);
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

        System.out.println(".userLogin......userName:" + userName + " password:" + password + " callback:" + callback);

        final Retrofit retrofitDlogin = new Retrofit.Builder()
                .baseUrl("http://my.12301.cc")
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RequestSerives requestSerives = retrofitDlogin.create(RequestSerives.class);//这里采用的是Java的动态代理模式
        Callback<ResponseBody> bodyCallback = new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    Headers headers = response.headers();
                    Set<String> names = headers.names();
                    for (String name : names) {
                        System.out.println(".............next:" + name + ":" + headers.values(name));
                    }
                    ToastUtil.showPrompt("....response:" + new Gson().toJson(response.message()));

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println(".......失败.......t:" + t.getMessage() + " call:" + call.toString());
            }
        };
        requestSerives.dlogin().enqueue(bodyCallback);
        requestSerives.terminal("1", "922190", "624159").enqueue(bodyCallback);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://shuihuo.tyuniot.com")
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RequestSerives apiService = retrofit.create(RequestSerives.class);
        apiService.login3(userName, password, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) {
                        System.out.println("......doOnNext........:" + new Gson().toJson(loginBean.Data));
                        ToastUtil.showPrompt("....doOnNext...........:" + new Gson().toJson(loginBean.Data));
                    }
                })
                //                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) {

                        System.out.println("......subscribe.....accept...:" + new Gson().toJson(loginBean.Data));
                        ToastUtil.showPrompt("....subscribe.....accept......:" + new Gson().toJson(loginBean.Data));
                        if (callback != null) {
                            callback.success(new Gson().toJson(loginBean));
                        }
//                        if (callback != null) {
//                            callback.result(1 + (int) (System.currentTimeMillis() % 2), "");
//                        }
                    }
                });


    }



    public interface RequestSerives {
        /**
         * 表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
         * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
         */
        @POST("/form")
        @FormUrlEncoded
        Call<ResponseBody> testFormUrlEncoded1(@Field("username") String name, @Field("age") int age);

        /**
         * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
         * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
         */
        @POST("/form")
        @Multipart
        Call<ResponseBody> testFileUpload1(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

        @POST("/login/Login")
        Call<LoginBean> login(@Query("param1") String userName, @Query("param2") String password, @Query("flag") String flag);

        @POST("/login/Login")
        Call<ResponseBody> login2(@Query("param1") String userName, @Query("param2") String password, @Query("flag") String flag);

        @POST("/login/Login")
        Observable<LoginBean> login3(@Query("param1") String userName, @Query("param2") String password, @Query("flag") String flag);

        @GET("/dlogin_n.html")
        Call<ResponseBody> dlogin();

        @GET("/call/terminal.php")
        Call<ResponseBody> terminal(@Query("query") String query, @Query("salerid") String salerid, @Query("voucher") String voucher);
    }

    public class LoginBean {
        public String Code;
        public String Msg;
        public DataBean Data;
    }

    public class DataBean {
        private int User_ID;
        private String User_Name;
        private String User_Phone;
        private String User_Type;
        private String User_Role;
        private int User_Region_ID;
        private String User_Region;
        private String Access_Token;
        private String User_Status;
    }


}