package com.hunter.helloandroid.module.gradient;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.hunter.helloandroid.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2017
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/13 15:28
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class GradientActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.iv_color1)
    ImageView mImageView1;
    @BindView(R.id.iv_color2)
    ImageView mImageView2;
    @BindView(R.id.ll_content)
    LinearLayout mLinearLayout;
    @BindView(R.id.iv_result_color)
    ImageView mResultColor;
    @BindView(R.id.pb_progress)
    SeekBar mSeekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradient);

        init();
    }

    private int mColor1 = 0xff19b955, mColor2 = 0xff959698;

    private void init() {
        ButterKnife.bind(this);
        mSeekBar.setOnSeekBarChangeListener(this);

        try {
            mLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mLinearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    initGradienView();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void onClickRename(View view) {
        try {
            String path = "/sdcard/360/layout";
            File file = new File(path);
            String[] list = file.list();
            for (String fileName : list) {
                String oldname = new File(fileName).getName();

                int indexOf = oldname.indexOf("_");
                if (indexOf > 0) {
                    String newName = oldname.substring(0, indexOf) + "_old" + oldname.substring(indexOf, oldname.length());
                    renameFile(path, oldname, newName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件重命名
     *
     * @param path    文件目录
     * @param oldName 原来的文件名
     * @param newName 新文件名
     */
    public void renameFile(String path, String oldName, String newName) {
        System.out.println("......path:" + path + " oldName:" + oldName + " newName:" + newName);
        if (!oldName.equals(newName)) {//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldfile = new File(path + "/" + oldName);
            File newfile = new File(path + "/" + newName);
            if (!oldfile.exists()) {
                return;//重命名文件不存在
            }
            if (newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newName + " 已经存在！");
            else {
                oldfile.renameTo(newfile);
                System.out.println(newName + " 成功！");
            }
        }
    }

    private void initGradienView() {
        Drawable background1 = mImageView1.getBackground();
        Drawable background2 = mImageView2.getBackground();
        if (background1 instanceof ColorDrawable) {
            ColorDrawable color1 = (ColorDrawable) background1;
            ColorDrawable color2 = (ColorDrawable) background2;
            mColor1 = color1.getColor();
            mColor2 = color2.getColor();
        }
        System.out.println("......startColor:" + mColor1 + " endColor:" + mColor2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int width = mLinearLayout.getWidth();
                int total = 20;
                int wiewWidth = width / total;
                System.out.println("...........width:" + width + " wiewWidth:" + wiewWidth);
                final List<View> list = new ArrayList<>();
                for (int i = 0; i < total; i++) {

                    View child = new View(GradientActivity.this);
                    child.setLayoutParams(new LinearLayout.LayoutParams(wiewWidth, -1));
                    child.setBackgroundColor(getAlphaColor(mColor1, mColor2, total, i));
                    list.add(child);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLinearLayout.removeAllViews();
                        for (View view : list) {
                            mLinearLayout.addView(view);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int result = getAlphaColor(mColor1, mColor2, progress);
        mResultColor.setBackgroundColor(result);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    int getAlphaColor(int startColor, int endColor, int percent) {
        return getAlphaColor(startColor, endColor, 100, percent);
    }

    int getAlphaColor(int startColor, int endColor, int total, int percent) {
//        System.out.println("....getAlphaColor..percent:" + percent);
        int startAlpha = Color.alpha(startColor);
        int startRed = Color.red(startColor);
        int startGreen = Color.green(startColor);
        int startBlue = Color.blue(startColor);
//        System.out.println("......startAlpha:" + startAlpha + " startRed:" + startRed + " startGreen:" + startGreen + " startBlue:" + startBlue);

        int endAlpha = Color.alpha(endColor);
        int endRed = Color.red(endColor);
        int endGreen = Color.green(endColor);
        int endBlue = Color.blue(endColor);
//        System.out.println("......endAlpha:" + endAlpha + " endRed:" + endRed + " endGreen:" + endGreen + " endBlue:" + endBlue);

        int resultAlpha = getPercent(startAlpha, endAlpha, total, percent);
        int resultRed = getPercent(startRed, endRed, total, percent);
        int resultGreen = getPercent(startGreen, endGreen, total, percent);
        int resultBlue = getPercent(startBlue, endBlue, total, percent);
//        System.out.println("......resultAlpha:" + resultAlpha + " resultRed:" + resultRed + " resultGreen:" + resultGreen + " resultBlue:" + resultBlue);
        return Color.argb(resultAlpha, resultRed, resultGreen, resultBlue);
    }


    private int getPercent(int start, int end, int total, int percent) {
        int difference = Math.abs(start - end);
        int min = Math.min(start, end);
        percent = end < start ? total - percent : percent;
        return min + difference * percent / total;
    }

}
