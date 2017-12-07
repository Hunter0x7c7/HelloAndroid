package com.hunter.helloandroid.module.matrix;

import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.chrisbanes.photoview.OnCoordinateChangeListener;
import com.github.chrisbanes.photoview.PhotoView;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/5/8 14:55
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class MatrixActivity extends AppCompatActivity implements OnCoordinateChangeListener {

    private PhotoView mImageView;
    private ViewGroup rlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int mask = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(mask, mask);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_matrix);

        rlRoot = (ViewGroup) findViewById(R.id.rl_root);
        mImageView = (PhotoView) findViewById(R.id.iv_image);
        mImageView.setImageResource(R.mipmap.bg_huayu);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mImageView.setOnCoordinateChangeListener(this);

        mPointList.add(new PointF(29.39f, 33.00f));
        mPointList.add(new PointF(37.09f, 38.34f));
        mPointList.add(new PointF(48.56f, 28.34f));
        mPointList.add(new PointF(49.06f, 38.34f));
        mPointList.add(new PointF(57.37f, 35.38f));


        dip2px = dip2px(5);
        int size = mPointList.size();
        for (int i = 0; i < size; i++) {

            ImageButton child = new ImageButton(this);
            child.setImageResource(R.drawable.btn_blws_selector);
            child.setBackgroundDrawable(new BitmapDrawable());
            child.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));

            child.setOnClickListener(new MyClickListener(i));
            mButtonList.add(child);
        }


        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                ToastUtil.showPrompt("第" + (position + 1) + "个被点击");
            }
        });
    }

    private List<PointF> mPointList = new ArrayList<>();
    private List<ImageButton> mButtonList = new ArrayList<>();
    private int dip2px;
    private OnItemClickListener onItemClickListener;

    @Override
    public void onCoordinateChange(float width, float height, float left, float top, float right, float bottom) {

        rlRoot.removeAllViews();

        int size = mButtonList.size();
        for (int i = 0; i < size; i++) {
            final ImageButton child = mButtonList.get(i);
            PointF p = mPointList.get(i);

            int x = (int) (width * p.x / 100 + left);
            int y = (int) (height * p.y / 100 + top);

            child.measure(0, 0);
            setLayout(child, x - child.getMeasuredWidth() / 2, y - child.getMeasuredHeight() + dip2px);

            rlRoot.addView(child);
        }
    }

private     class MyClickListener implements View.OnClickListener {

        int position;

        public MyClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, position);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 设置控件所在的位置YY，并且不改变宽高，
     * XY为绝对位置
     */
    public static void setLayout(final View view, final int x, final int y) {

        view.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams lp = view.getLayoutParams();

                if (lp == null) {
                    return;
                }
                RelativeLayout.MarginLayoutParams margin = new RelativeLayout.MarginLayoutParams(lp);
                margin.setMargins(x, y, x + margin.width, y + margin.height);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
                view.setLayoutParams(layoutParams);
            }
        });

    }


    public int dip2px(float dpValue) {
        return (int) (dpValue * getResources().getDisplayMetrics().density + 0.5F);
    }

    public int px2dip(float pxValue) {
        return (int) (pxValue / getResources().getDisplayMetrics().density + 0.5F);
    }

}
