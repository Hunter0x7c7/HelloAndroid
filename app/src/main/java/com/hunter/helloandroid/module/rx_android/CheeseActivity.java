/*
 * Copyright (c) 2016 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.hunter.helloandroid.module.rx_android;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hunter.helloandroid.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * RxAndroid-CheeseFinder-final
 * <p>
 * just: 获取输入数据, 直接分发数据就是数组, 更加简洁, 省略其他回调.
 * from: 获取输入数组, 转变单个元素分发.
 * map: 映射, 对输入数据进行转换, 如大写.
 * flatMap: 增大, 本意就是增肥, 把输入数组映射多个值, 依次分发.
 * reduce: 简化, 正好相反, 把多个数组的值, 组合成一个数据.
 */
public class CheeseActivity extends BaseSearchActivity {

    private Disposable mDisposable;

    @Override
    protected void onStart() {
        super.onStart();

//        if (test()) return;
//        if (testObservable()) return;


        Observable<String> buttonClickStream = createButtonClickObservable();
        Observable<String> textChangeStream = createTextChangeObservable();

        Observable<String> searchTextObservable = Observable.merge(textChangeStream, buttonClickStream);

        mDisposable = searchTextObservable // change this line
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        showProgressBar(s);
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<String, List<String>>() {
                    @Override
                    public List<String> apply(String query) {
                        return mCheeseSearchEngine.search(query);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> result) {
                        hideProgressBar();
                        showResult(result);
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    private String tag = getClass().getSimpleName();

    // 1
    private Observable<String> createButtonClickObservable() {

        // 2
        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {

            // 3
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                // 4
                mSearchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 5
                        emitter.onNext(mQueryEditText.getText().toString());
                    }
                });

                // 6
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        // 7
                        mSearchButton.setOnClickListener(null);
                    }
                });
            }
        });
        return stringObservable;
    }

    //1
    private Observable<String> createTextChangeObservable() {
        //2
        Observable<String> textChangeObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                //3
                final TextWatcher watcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                    //4
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        emitter.onNext(s.toString());
                    }
                };

                //5
                mQueryEditText.addTextChangedListener(watcher);

                //6
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        mQueryEditText.removeTextChangedListener(watcher);
                    }
                });
            }
        });

        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String query) throws Exception {
                return query.length() >= 2;
            }
        };
        return textChangeObservable.filter(predicate).debounce(1000, TimeUnit.MILLISECONDS);
    }

    private boolean testObservable() {
        //--1
        Observable<String> textChangeObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                //3
                final TextWatcher watcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }

                    //4
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        emitter.onNext(s.toString());
                    }
                };

                //5
                mQueryEditText.addTextChangedListener(watcher);

                //6
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        mQueryEditText.removeTextChangedListener(watcher);
                    }
                });
            }
        });

        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String query) throws Exception {
                return query.length() >= 2;
            }
        };
        Observable<String> textChangeStream = textChangeObservable.filter(predicate).debounce(1000, TimeUnit.MILLISECONDS);

        //--2
        Observable<String> buttonClickStream = Observable.create(new ObservableOnSubscribe<String>() {

            // 3
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                // 4
                mSearchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 5
                        emitter.onNext(mQueryEditText.getText().toString());
                    }
                });

                // 6
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        // 7
                        mSearchButton.setOnClickListener(null);
                    }
                });
            }
        });

        //---3
        Observable<String> searchTextObservable = Observable.merge(textChangeObservable, buttonClickStream);

        mDisposable = searchTextObservable // change this line
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        showProgressBar(s);
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<String, List<String>>() {
                    @Override
                    public List<String> apply(String query) {
                        return mCheeseSearchEngine.search(query);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> result) {
                        hideProgressBar();
                        showResult(result);
                    }
                });
        return false;
    }

    private boolean test() {

        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> subscriber) throws Exception {
                subscriber.onNext("Hello1");
                subscriber.onNext("Hi1");
                subscriber.onNext("Aloha1");
                subscriber.onComplete();
            }
        });
        Observable<String> observable2 = Observable.just("Hello2", "Hi2", "Aloha2");
        String[] words = {"Hello3", "Hi3", "Aloha3"};
        Observable.just(words);
        Observable<String> observable3 = Observable.fromArray(words);

        observable3 // change this line
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        showProgressBar("........doOnNext:" + s);
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<String, List<String>>() {
                    @Override
                    public List<String> apply(String text) {
                        log(".....Function1........text:" + text);
                        return Arrays.asList(text);
                    }
                })
                .map(new Function<List<String>, List<String>>() {
                    @Override
                    public List<String> apply(List<String> text) throws Exception {
                        log(".....Function2........text:" + text);
                        List<String> lists = new ArrayList<>(text);
                        lists.addAll(text);
                        lists.addAll(text);
                        return lists;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> result) {
                        log(".....accept3........text:" + result);

                        hideProgressBar();

                        if (result != null && result.size() > 0)
                            showResult(result);
                    }
                });

        final int drawableRes = R.mipmap.ic_launcher;
        final Context context = getBaseContext();
        final ImageView imageView = new ImageView(context);

        Observable.create(new ObservableOnSubscribe<Drawable>() {

            @Override
            public void subscribe(ObservableEmitter<Drawable> subscriber) throws Exception {

                Drawable drawable = getResources().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onComplete();
            }
        }).subscribe(new Observer<Drawable>() {


            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Drawable value) {
                imageView.setImageDrawable(value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        });


        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> subscriber) {
                subscriber.onNext(R.mipmap.ic_launcher);
                subscriber.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer drawable) {

                    }
                })
                .map(new Function<Integer, Drawable>() {
                    @Override
                    public Drawable apply(Integer drawable) {
                        return getResources().getDrawable(drawableRes);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Drawable>() {
                    @Override
                    public void accept(Drawable drawable) {
                        imageView.setImageDrawable(drawable);
                    }
                });


        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(tag, "number:" + integer);

                        System.out.println("................number:" + integer);
                    }
                });

        return false;
    }

    private void log(String msg) {
        log(tag, msg);
    }

    private void log(String tag, String msg) {
        Log.d(tag, msg);
        System.out.println("..........tag:" + tag + " msg:" + msg);
    }
}
