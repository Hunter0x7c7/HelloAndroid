package com.hunter.helloandroid.module.custom;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.module.custom.hexagon.HexgonActivity;
import com.hunter.helloandroid.module.custom.path.AnimatorPath.AnimatorPath;
import com.hunter.helloandroid.module.custom.path.AnimatorPath.PathEvaluator;
import com.hunter.helloandroid.module.custom.path.AnimatorPath.PathPoint;
import com.hunter.helloandroid.module.custom.path.PathView;
import com.hunter.helloandroid.module.custom.path.SvgPathParser;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2018
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/4/10 15:52
 * <p>
 * 描    述：自定义View
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class CustomViewActivity extends AppCompatActivity {
    @BindView(R.id.mpv_player)
    MusicPlayerView mMusicPlayerView;
    @BindView(R.id.rcv)
    RotateCircleImageView mRotateCircle;

    @BindView(R.id.epv_earth_path)
    EarthPathView mEarthPathView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        ButterKnife.bind(this);
        mMusicPlayerView.start();


//        int w = getResources().getDisplayMetrics().widthPixels;
//        int h = getResources().getDisplayMetrics().heightPixels;
//
//        int min = Math.min(w, h);
//        Path path = buildPath(w / 2 - 200, h / 2 - 200, min / 8);
//        mEarthPathView.setPath(path);
        mEarthPathView.startAnim();

        setPath();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startAnimatorPath(fab, "fab", mPathView.getPath());
                startAnimator(fab, mPathView.getPath());
            }
        });

    }


    public void startRotate(View v) {
        mRotateCircle.startRotate();
    }

    public void stopRotate(View v) {
        mRotateCircle.stopRotate();
    }


    private Path buildPath(float x, float y, float radius) {
        Path mPath = new Path();
        mPath.addCircle(x, y, radius, Path.Direction.CW);
        return mPath;
    }


    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.pv_fab)
    PathView mPathView;
    private AnimatorPath mAnimatorPath;//声明动画集合

    /**
     * 设置动画路径
     */
    public void setPath() {
        mAnimatorPath = new AnimatorPath();
//        mAnimatorPath.moveTo(0, 0);
//        mAnimatorPath.lineTo(400, 400);
//        mAnimatorPath.secondBesselCurveTo(600, 200, 800, 400); //订单
//        mAnimatorPath.thirdBesselCurveTo(100, 600, 900, 1000, 200, 1200);

        mAnimatorPath.moveTo(60, 60);
        mAnimatorPath.lineTo(460, 460);
        mAnimatorPath.secondBesselCurveTo(660, 260, 860, 460); //订单
        mAnimatorPath.thirdBesselCurveTo(160, 660, 960, 1060, 260, 1260);

    }

    /**
     * 设置动画
     *
     * @param view         使用动画的View
     * @param propertyName 属性名字
     * @param path         动画路径集合
     */
    private void startAnimatorPath(View view, String propertyName, AnimatorPath path) {
        ObjectAnimator anim = ObjectAnimator.ofObject(view, propertyName, new PathEvaluator(), path.getPoints().toArray());
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(3000);
        anim.start();
    }

    /**
     * 设置View的属性通过ObjectAnimator.ofObject()的反射机制来调用
     */
    public void setFab(PathPoint newLoc) {
        fab.setTranslationX(newLoc.mX);
        fab.setTranslationY(newLoc.mY);
    }

    /**
     * 给个路径（path）我送你个动画
     */
    private void startAnimator(View mView, Path path) {
        //mView 用来执行动画的View
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mView, View.X, View.Y, path);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(6000);
        objectAnimator.start();
    }

    /**
     * 矩形动画
     *
     * @return
     */
    private Path rectanglePath(View mView, int width) {
        Path path = new Path();
        //view本身的宽度，不减去就跑出屏幕外面了
        int v_width = mView.getWidth();
        path.moveTo(0, 0);
        path.lineTo(width - v_width, 0);
        path.lineTo(width - v_width, width);
        path.lineTo(0, width);
        path.lineTo(0, 0);
        return path;
    }

    /**
     * 圆弧动画 叶子型动画（两个弧形对接）
     *
     * @return
     */
    private Path arcPath(View mView, int width) {
        Path path = new Path();
        int v_width = mView.getWidth();
        int d_width = width - v_width;
        path.moveTo(0, 0);
        path.quadTo(0, d_width, d_width, d_width);
        path.moveTo(d_width, d_width);
        path.quadTo(d_width, 0, 0, 0);
        return path;
    }

    /**
     * 模拟正弦曲线
     */
    private Path sinPath(View mView, int width) {
        Path path = new Path();
        int vWidth = mView.getWidth();
        int dWidth = width - vWidth;
        path.moveTo(0, 0);
        path.quadTo(0, dWidth, dWidth / 4, dWidth);
        path.moveTo(dWidth / 4, dWidth);
        path.cubicTo(dWidth / 2, dWidth, dWidth / 2, 0, dWidth * 3 / 4, 0);
        path.moveTo(dWidth * 3 / 4, 0);
        path.quadTo(dWidth, 0, dWidth, dWidth);
        return path;
    }

    public void onClickHexagon(View view) {
        startActivity(new Intent(this, HexgonActivity.class));
    }

    public void onClickSVG(View view) throws ParseException {
        SvgPathParser svgPathParser = new SvgPathParser();
        String pathStr = "M 495.00,0.00\n" +
                "           C 495.00,0.00 495.00,417.00 495.00,417.00\n" +
                "             495.00,417.00 0.00,417.00 0.00,417.00\n" +
                "             0.00,417.00 0.00,0.00 0.00,0.00\n" +
                "             0.00,0.00 495.00,0.00 495.00,0.00 Z\n" +
                "           M 81.18,219.39\n" +
                "           C 75.28,224.66 73.26,233.55 71.61,241.00\n" +
                "             68.81,256.26 68.66,271.70 71.61,287.00\n" +
                "             72.76,294.37 74.47,301.98 79.68,307.70\n" +
                "             85.29,313.85 91.52,314.81 99.00,316.73\n" +
                "             99.00,316.73 126.00,321.96 126.00,321.96\n" +
                "             126.00,321.96 134.00,321.96 134.00,321.96\n" +
                "             134.00,321.96 144.00,323.00 144.00,323.00\n" +
                "             156.04,323.14 168.13,322.58 180.00,320.39\n" +
                "             187.27,319.04 193.58,317.17 198.20,310.91\n" +
                "             202.27,305.40 200.54,300.30 201.28,294.00\n" +
                "             201.28,294.00 202.00,244.00 202.00,244.00\n" +
                "             201.98,234.15 201.61,228.06 192.91,221.85\n" +
                "             187.58,218.04 176.56,216.51 170.00,215.41\n" +
                "             153.07,212.57 126.99,210.70 110.00,212.28\n" +
                "             101.11,213.56 88.05,213.25 81.18,219.39 Z";
        Path parsePath = svgPathParser.parsePath(pathStr);
        System.out.println(".........parsePath:" + parsePath);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
