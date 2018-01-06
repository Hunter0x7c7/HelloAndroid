package com.hunter.helloandroid.module.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.hunter.helloandroid.R;
import com.hunter.helloandroid.util.ToastUtil;
import com.hunter.helloandroid.view.LodingCircleView;
import com.hunter.helloandroid.view.ScrollTextView;
import com.hunter.helloandroid.view.TimerShaftView;
import com.hunter.helloandroid.view.ZHMarqueeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期： 2017/12/11 9:37
 */
public class ViewActivity extends AppCompatActivity
        implements TimerShaftView.OnTimeChangeListener  {

    private List<String> infoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        initWebView();
        initTimerShaftView();

        initData();

        initZHMarqueeView();
        initMarqueeView();
        initScrollTextView();
        initLodingCircleView();

//        ZHSnackbar.make(srlRefreshLayout, "TEST", ZHSnackbar.LENGTH_SHORT, 0).show();
//        ZHSnackbar.make(srlRefreshLayout, "TEST", -1, 0).show();
    }


    private void initLodingCircleView() {
        final LodingCircleView lcv_loding = (LodingCircleView) findViewById(R.id.lcv_loding);
        SeekBar sb_seek = (SeekBar) findViewById(R.id.sb_seek);
        sb_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lcv_loding.setProgerss(progress, true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void initScrollTextView() {
        final ScrollTextView marqueeView3 = (ScrollTextView) findViewById(R.id.marqueeView3);
//        marqueeView3.setText("3. GitHub帐号：3. GitHub帐号：zhangyuanchong本的Android TextView的例子能够适应多行长");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, infoList);
        marqueeView3.setAdapter(adapter);
        marqueeView3.beginScroll();
    }

    private void initMarqueeView() {
        MarqueeView marqueeView2 = (MarqueeView) findViewById(R.id.marqueeView2);
        MarqueeFactory marqueeFactory = new NoticeMF(this);
        marqueeFactory.setData(infoList);

        marqueeView2.setMarqueeFactory(marqueeFactory);
    }

    private void initData() {
        infoList = new ArrayList<>();
        infoList.add("1.能够适应多行长文本的Android TextView的例子能够适应多行长文本的Android TextView的例子本的Android TextView的例子能oid TextView的例子能够适应多行长文本的Android TextView的例子本的Android TextView的例子能够适应多行长");
        infoList.add("2.\"科比，！本的Android TextView的例子能够适应多行长");
        infoList.add("3. GitHub帐号：zhangyuanchong本的Android TextView的例子能够适应多行长");
        infoList.add("4.\"理解的也很简单，本的Android TextView的例子能够适应多行长");
        infoList.add("5. 破解密钥本的Android TextView的例子能够适应多行长");
        infoList.add("6. 实现了两种方式本的Android TextView的例子能够适应多行长本的Android TextView的例子能够适应多行长");
    }

    private void initZHMarqueeView() {
        ZHMarqueeView verticalSwitchTextView1 = (ZHMarqueeView) findViewById(R.id.vertical_switch_textview1);
        verticalSwitchTextView1.startWithList(infoList);
        verticalSwitchTextView1.setOnItemClickListener(new ZHMarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                position = position + 1;
                ToastUtil.showPrompt("点击了" + position);
            }
        });
    }

    private void initWebView() {
        WebView webview = (WebView) findViewById(R.id.wv_web);
//        webview.loadUrl("http://124.160.184.108/live/5/45/3bfabc1fe16a4282b50ea095928c1f60.m3u8");
        webview.loadUrl("file:///android_asset/m3u8.html");
    }





    public void initTimerShaftView() {

        TimerShaftView timerShaftView = (TimerShaftView) findViewById(R.id.tsv_timershaft);
        timerShaftView.setOnTimeChangeListener(this);
    }


    @Override
    public void onTimeChange(TimerShaftView view, String time) {
        System.out.println("...........time:" + time);
    }


    public static class NoticeMF extends MarqueeFactory<TextView, String> {
        private Context mContext;

        public NoticeMF(Context mContext) {
            super(mContext);
            this.mContext = mContext;
        }

        @Override
        public TextView generateMarqueeItemView(String data) {

            return new TextView(mContext);
        }
    }


}
