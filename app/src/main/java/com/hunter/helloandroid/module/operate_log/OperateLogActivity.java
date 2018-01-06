package com.hunter.helloandroid.module.operate_log;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.util.SystemUtil;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
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
public class OperateLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate_log);
    }

    public void onClickFilter(View view) {
        startActivity(new Intent(this, MyDialogActivity.class));

//        View inflate = View.inflate(this, R.layout.dialog_operate_log, null);
//        int width = SystemUtil.getWidth() * 2 / 3;
//        PopupWindow popupWindow  = new PopupWindow(inflate, width, -1, true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.showAtLocation(view, Gravity.LEFT, 0, 0);
//        popupWindow.setClippingEnabled(false);

    }

    public void initDialog() {

        Dialog dialog = new Dialog(this, R.style.DialogFullscreen);
//        Dialog dialog = this;
        Window window = dialog.getWindow();
        window.setFlags(0x00000400, 0x00000400);
        //设置一个布局
        dialog.setContentView(R.layout.dialog_operate_log);
        dialog.setCanceledOnTouchOutside(true);

        window.setGravity(Gravity.RIGHT);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.mystyle);  //添加动画
        //设置window背景，默认的背景会有Padding值，不能全屏。
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //一定要在setContentView之后调用，否则无效
        window.setLayout(SystemUtil.getWidth() * 2 / 3, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.show();
    }

}
