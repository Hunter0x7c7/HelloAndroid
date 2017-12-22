package com.hunter.helloandroid.module.rx_android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hunter.helloandroid.R;

import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/12/18 13:42
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class RxAndroidTestActivity extends AppCompatActivity {

    @BindView(R.id.tv_text)
    TextView tvText;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_android);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_add_items)
    public void addItems() {
        append("\n.......开始.........\n");

        DisposableObserver<DataObject> disposableObserver = new DisposableObserver<DataObject>() {
            @Override
            public void onNext(@NonNull DataObject object) {

                append(".......数据:" + String.valueOf(object.a) + "\n");
                append(".......数据:" + String.valueOf(object.b) + "\n");
            }

            @Override
            public void onComplete() {
                append(".......结束.........\n");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                append(".......结束.........\n");
            }
        };

        Observable<String> observableA = getObservableA(new TempObject("123"));
        Observable<String> observableB = getObservableB(new TempObject("456"));
        BiFunction<String, String, DataObject> zipper = new BiFunction<String, String, DataObject>() {
            @Override
            public DataObject apply(String a, String b) {
                return new DataObject(a, b);
            }
        };
//        DisposableObserver<DataObject> observer = Observable
//                .zip(observableA, observableB, zipper)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith (disposableObserver);

        Observable.zip(observableA, observableB, zipper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

//        mCompositeDisposable.add(observer);
    }

    private void append(String s) {
        tvText.append(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 如果退出程序，就清除后台任务
        mCompositeDisposable.clear();
    }

    private Observable<String> getObservableA(@NonNull final TempObject tempObject) {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() {
                try {
                    Thread.sleep(2000); // 假设此处是耗时操作
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "Aaaaa " + tempObject;
            }
        });
    }

    private Observable<String> getObservableB(@NonNull final TempObject tempObject) {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() {
                try {
                    Thread.sleep(2000); // 假设此处是耗时操作
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "Bbbbbb " + tempObject;
            }
        });
    }

    private class DataObject {
        private String a = null;
        private String b = null;

        public DataObject() {
        }

        public DataObject(String a, String b) {
            this.a = a;
            this.b = b;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }
    }

    /**
     * 这是一个废弃无用的数据结构，用以演示如何在请求之间传递参数
     */
    private class TempObject {
        public String temp;

        public TempObject() {
        }

        public TempObject(String temp) {
            this.temp = temp;
        }

        @Override
        public String toString() {
            return "TempObject{" +
                    "temp='" + temp + '\'' +
                    '}';
        }
    }

}
