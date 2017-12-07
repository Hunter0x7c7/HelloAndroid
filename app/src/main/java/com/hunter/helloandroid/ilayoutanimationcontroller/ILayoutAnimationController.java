package com.hunter.helloandroid.ilayoutanimationcontroller;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

public class ILayoutAnimationController extends LayoutAnimationController {
    private Callback onIndexListener;

    public void setOnIndexListener(Callback onIndexListener) {
        this.onIndexListener = onIndexListener;
    }

    public ILayoutAnimationController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ILayoutAnimationController(Animation animation) {
        super(animation);
    }

    public ILayoutAnimationController(Animation animation, float delay) {
        super(animation, delay);
    }

    @Override
    protected int getTransformedIndex(AnimationParameters params) {
        if (onIndexListener != null) {
            return onIndexListener.getTransformedIndex(
                    this,
                    params.count,
                    params.index);
        } else {
            return super.getTransformedIndex(params);
        }
    }

    /**
     * callback for get play animation order
     */
    public interface Callback {
        public int getTransformedIndex(
                ILayoutAnimationController controller,
                int count,
                int index);
    }

    /**
     * 根据当前枚举类型的值，确定在setOnIndexListener方法中的
     * CustomLayoutAnimationController.Callback
     * 实例的getTransformedIndex方法调用GetTransformedIndexUtils中的那种方法
     */
    public enum IndexAlgorithm {
        INDEX1325476,
        INDEX135246,
        INDEX246135,
        INDEXSIMPLEPENDULUM,
        MIDDLETOEDGE,
        INDEX15263748,
        INDEX1325476REVERSE,
        INDEX135246REVERSE,
        INDEX246135REVERSE,
        INDEXSIMPLEPENDULUMREVERSE,
        INDEXMIDDLETOEDGEREVERSE,
        INDEX15263748REVERSE
    }

    public static ILayoutAnimationController generateController(
            Animation animation, float delay) {
        return generateController(animation, delay, null);
    }

    /**
     * 根据指定的动画、单个子View动画延时、
     * 子View动画执行顺序算法枚举值，
     * 创建一个新的CustomLayoutAnimationController实例
     *
     * @param animation      the animation to use on each child of the view group
     * @param delay          the delay by which each child's animation must be offset
     * @param indexAlgorithm 子View动画执行顺序算法枚举值
     * @return
     */
    public static ILayoutAnimationController generateController(
            @NonNull Animation animation,
            float delay,
            @Nullable final IndexAlgorithm indexAlgorithm) {
        ILayoutAnimationController controller =
                new ILayoutAnimationController(animation, delay);
        controller.setOnIndexListener(new Callback() {
            @Override
            public int getTransformedIndex(
                    ILayoutAnimationController controller, int count, int index) {
                if (indexAlgorithm != null) {
                    switch (indexAlgorithm) {
                        case INDEX1325476:
                            return GetTransformedIndexUtils
                                    .getTransformedIndex1325476(count, index);
                        case INDEX135246:
                            return GetTransformedIndexUtils
                                    .getTransformedIndex135246(count, index);
                        case INDEX246135:
                            return GetTransformedIndexUtils
                                    .getTransformedIndex246135(count, index);
                        case INDEXSIMPLEPENDULUM:
                            return GetTransformedIndexUtils
                                    .getTransformedIndexSimplePendulum(
                                            count, index);
                        case MIDDLETOEDGE:
                            return GetTransformedIndexUtils
                                    .getTransformedIndexMiddleToEdge(
                                            count, index);
                        case INDEX15263748:
                            return GetTransformedIndexUtils
                                    .getTransformedIndex15263748(count, index);
                        case INDEX1325476REVERSE:
                            return GetTransformedIndexUtils
                                    .getTransformedIndex1325476REVERSE(
                                            count, index);
                        case INDEX135246REVERSE:
                            return GetTransformedIndexUtils
                                    .getTransformedIndex135246REVERSE(
                                            count, index);
                        case INDEX246135REVERSE:
                            return GetTransformedIndexUtils
                                    .getTransformedIndex246135REVERSE(
                                            count, index);
                        case INDEXSIMPLEPENDULUMREVERSE:
                            return GetTransformedIndexUtils
                                    .getTransformedIndexSimplePendulumREVERSE(
                                            count, index);
                        case INDEXMIDDLETOEDGEREVERSE:
                            return GetTransformedIndexUtils
                                    .getTransformedIndexMiddleToEdgeREVERSE(
                                            count, index);
                        case INDEX15263748REVERSE:
                            return GetTransformedIndexUtils
                                    .getTransformedIndex15263748REVERSE(
                                            count, index);
                        default:
                            break;
                    }
                }
                return index;
            }
        });
        return controller;
    }

    /**
     * 根据指定的动画、单个子View动画延时、
     * 子View动画执行顺序算法枚举值，
     * 创建一个新的CustomLayoutAnimationController实例，
     * 将此实例作为参数为viewGroup设置布局动画
     *
     * @param viewGroup
     * @param animation
     * @param delay
     * @param indexAlgorithm
     */
    public static void setLayoutAnimation(
            @NonNull ViewGroup viewGroup,
            @NonNull Animation animation,
            float delay,
            @Nullable final IndexAlgorithm indexAlgorithm) {
        ILayoutAnimationController controller =
                generateController(animation, delay, indexAlgorithm);
        viewGroup.setLayoutAnimation(controller);
    }

    /**
     * 根据传入的动画资源ID、单个子View动画延时、
     * 子View动画执行顺序算法枚举值，
     * 创建一个新的CustomLayoutAnimationController实例，
     * 将此实例作为参数为viewGroup设置布局动画
     *
     * @param viewGroup
     * @param animResId
     * @param delay
     * @param indexAlgorithm
     */
    public static void setLayoutAnimation(
            @NonNull ViewGroup viewGroup,
            @AnimRes int animResId,
            float delay,
            @Nullable final IndexAlgorithm indexAlgorithm) {
        Animation animation = AnimationUtils.loadAnimation(
                viewGroup.getContext(), animResId);
        setLayoutAnimation(viewGroup, animation, delay, indexAlgorithm);
    }
}
