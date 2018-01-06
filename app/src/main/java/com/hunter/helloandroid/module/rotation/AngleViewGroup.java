package com.hunter.helloandroid.module.rotation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/12/12 16:38
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class AngleViewGroup extends FrameLayout {

    private int rotationAngle, startAngle, entAngle;
    private float startScaleX, entScaleX, startScaleY, entScaleY;

    public AngleViewGroup(Context context) {
        super(context);
        init();
    }

    public AngleViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AngleViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        rotationAngle = 90;
        startAngle = 0;
        entAngle = rotationAngle;

        startScaleX = 1;
        startScaleY = 1;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // onSizeChanged();
    }

    private void onSizeChanged() {
        float width = getWidth();
        float height = getHeight();

        entScaleX = height / width;
        entScaleY = width / height;
    }

    public void rotationLayout() {
        //updateViewLayout();
        //  onSizeChanged();

        clearAnimation();

        setPivotX(getWidth() / 2);
        setPivotY(getHeight() / 2);

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(
                ObjectAnimator.ofFloat(this, "rotation", startAngle, entAngle)
//                , ObjectAnimator.ofFloat(this, "scaleX", startScaleX, entScaleX)
//                , ObjectAnimator.ofFloat(this, "scaleY", startScaleY, entScaleY)
        );
        animatorSet.setDuration(1000);
        animatorSet.addListener(new AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
           //     updateViewLayout();
            }
        });
        animatorSet.start();

        int temp = startAngle;
        startAngle = entAngle;
        entAngle = temp;

//        float fTemp = startScaleX;
//        startScaleX = entScaleX;
//        entScaleX = fTemp;
//
//        fTemp = startScaleY;
//        startScaleY = entScaleY;
//        entScaleY = fTemp;

    }

    private void updateViewLayout() {
        int height = getHeight();
        int width = getWidth();
        System.out.println(".....1........getHeight():" + getHeight() + "  getWidth():" + getWidth());

        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = height;
        params.height = width;
        setLayoutParams(params);
        System.out.println(".....2........getHeight():" + getHeight() + "  getWidth():" + getWidth());
    }

    public void rotationLayout2() {

        clearAnimation();
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(
                ObjectAnimator.ofFloat(this, "rotation", startAngle, entAngle),
                ObjectAnimator.ofFloat(this, "scaleX", startScaleX, entScaleX),
                ObjectAnimator.ofFloat(this, "scaleY", startScaleY, entScaleY)
        );
        animatorSet.setDuration(1000);
        animatorSet.start();

        int temp = startAngle;
        startAngle = entAngle;
        entAngle = temp;

        float fTemp = startScaleX;
        startScaleX = entScaleX;
        entScaleX = fTemp;

        fTemp = startScaleY;
        startScaleY = entScaleY;
        entScaleY = fTemp;
    }

    public int getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(int rotationAngle) {
        this.rotationAngle = rotationAngle;
        startAngle = 0;
    }


    public static class AnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }
}
