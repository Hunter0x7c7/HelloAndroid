package com.hunter.helloandroid.module.operate_log;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.util.SystemUtil;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2017
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/12/14 16:27
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class MyDialogActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏

//        setFinishOnTouchOutside(true);


        Window window = getWindow();

//        全屏覆盖状态栏显示加上以下代码：
        window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
//        window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);



        //设置一个布局
        setContentView(R.layout.dialog_operate_log);

        window.setGravity(Gravity.END);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.mystyle);  //添加动画
        //设置window背景，默认的背景会有Padding值，不能全屏。
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //一定要在setContentView之后调用，否则无效
        window.setLayout(SystemUtil.getWidth() * 2 / 3, ViewGroup.LayoutParams.MATCH_PARENT);
    }



}
