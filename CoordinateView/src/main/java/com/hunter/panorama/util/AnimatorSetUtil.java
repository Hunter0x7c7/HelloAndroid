package com.hunter.panorama.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;


/**
 * 动画集合工具类
 *
 * @author Zihang Huang
 *         create date 2017/11/17 16:54
 */
public class AnimatorSetUtil {

    /**
     * 开启一个警告抖动的动画
     *
     * @param view 需要开启动画的View
     */
    public static void startWarningAnimatorSet(View view) {
        if (view == null) {
            return;
        }
        float max = 3;
        float normal = 0;
        float min = -3;
        float maxScale = 1.0f;
        float normalScale = 0.98f;
        float minScale = 0.95f;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "alpha", normal, 1, 1, 1, 1, 1),
                ObjectAnimator.ofFloat(view, "scaleX", normalScale, minScale, minScale, maxScale,
                        maxScale, maxScale, maxScale, maxScale, maxScale, normalScale),
                ObjectAnimator.ofFloat(view, "scaleY", normalScale, minScale, minScale, maxScale,
                        maxScale, maxScale, maxScale, maxScale, maxScale, normalScale),
                ObjectAnimator.ofFloat(view, "rotation", normal, min, min, max, min, max, min,
                        max, min, normal)
        );
        animatorSet.setDuration(700);
        animatorSet.start();
    }

    /**
     * 开启一个提示的动画
     *
     * @param view 需要开启动画的View
     */
    public static void startPromptAnimatorSet(View view) {
        if (view == null) {
            return;
        }
        float max = 1.0f;
        float normal = 0.98f;
        float min = 0.95f;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "alpha", 0, 0.5f, 1, 1, 1, 1),
                ObjectAnimator.ofFloat(view, "scaleX", 0, max, max, min, min, normal),
                ObjectAnimator.ofFloat(view, "scaleY", 0, max, max, min, min, normal)
        );
        animatorSet.setDuration(700);
        animatorSet.start();
    }

    /**
     * 设置布局动画:addView时依次显示弹跳效果动画
     */
    public static void setupLayoutAnimation(ViewGroup viewGroup) {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new TranslateAnimation(0, 0, -100, 0));
        animationSet.addAnimation(new AlphaAnimation(0, 1));
        animationSet.setDuration(650);
        // 设置弹跳效果插值器
        animationSet.setInterpolator(new BounceInterpolator());
        //得到一个LayoutAnimationController对象；
        LayoutAnimationController lac = new LayoutAnimationController(animationSet);
        //设置控件显示的顺序；
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        //设置控件显示间隔时间；
        lac.setDelay(0.65f);
        //为ListView设置LayoutAnimationController属性；
        viewGroup.setLayoutAnimation(lac);
    }

}

