package com.hunter.dialog;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * @author Hunter Huang
 *         自定义Dialog
 *         usage： new SimpleDialog(getContent()).setTitleName("标题").setPositiveButton("完成", null).show();
 */
public class SimpleDialog extends Dialog {

    private Context mContext;
    private View mDialogView;
    private ViewGroup llOperate;
    private TextView mBtnNegative, mBtnPositive, mBtnNext;
    private FrameLayout mFlContent;
    private TextView mTvTitle;
    private boolean isSetNewView = false;

    public SimpleDialog(Context context) {
        super(context, R.style.DialogFullscreen);
        init(context);
    }

    public SimpleDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected SimpleDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    private void init(Context context) {
        mContext = context;

        mDialogView = View.inflate(mContext, R.layout.dialog_simple_layout, null);
        mFlContent = (FrameLayout) mDialogView.findViewById(R.id.fl_content);
        mTvTitle = (TextView) mDialogView.findViewById(R.id.tv_title);
        llOperate = (ViewGroup) mDialogView.findViewById(R.id.ll_operate);
        mBtnNegative = (TextView) mDialogView.findViewById(R.id.btn_cancel);
        mBtnPositive = (TextView) mDialogView.findViewById(R.id.btn_enter);
        mBtnNext = (TextView) mDialogView.findViewById(R.id.btn_next);

        setContentView(mDialogView);
        setCanceledOnTouchOutside(false);

        Window window = getWindow();
        if (window != null) {
            int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
            int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
            window.setGravity(Gravity.CENTER);
            window.setLayout(matchParent, wrapContent);
        }

        mTvTitle.setVisibility(View.GONE);
        mFlContent.setVisibility(View.GONE);
        mBtnNegative.setVisibility(View.GONE);
        mBtnPositive.setVisibility(View.GONE);
        mBtnNext.setVisibility(View.GONE);

        llOperate.setVisibility(View.GONE);
    }

    /**
     * 获取 Layout
     *
     * @return Layout
     */
    public View getLayout() {
        return mDialogView;
    }

    /**
     * 设置视图
     *
     * @param view 视图
     * @return this
     */
    public SimpleDialog setView(View view) {
        if (view != null) {
            mFlContent.setVisibility(View.VISIBLE);
            mFlContent.addView(view);
        }
        return this;
    }

    /**
     * 设置视图
     *
     * @param layoutResID 视图文件资源id
     * @return this
     */
    public SimpleDialog setView(@LayoutRes int layoutResID) {
        View view = View.inflate(mContext, layoutResID, null);
        if (view != null) {
            mFlContent.setVisibility(View.VISIBLE);
            mFlContent.addView(view);
        }
        return this;
    }

    /**
     * 重新设置布局
     *
     * @param view 布局文件
     * @return this
     */
    public SimpleDialog setLayout(View view) {
        if (view != null) {
            isSetNewView = true;
            mDialogView = view;
            setContentView(view);
        }
        return this;
    }

    /**
     * 重新设置布局
     *
     * @param layoutResID 布局文件资源id
     * @return this
     */
    public SimpleDialog setLayout(@LayoutRes int layoutResID) {
        View view = View.inflate(mContext, layoutResID, null);
        if (view != null) {
            isSetNewView = true;
            mDialogView = view;
            setContentView(view);
        }
        return this;
    }

    /**
     * 设置LayoutParams
     *
     * @param width  宽
     * @param height 高
     * @return this
     */
    public SimpleDialog setLayoutParams(int width, int height) {
        Window window = getWindow();
        if (window != null) {
            window.setLayout(width, height);
        }
        return this;
    }

    /**
     * 设置Dialog标题
     */
    public SimpleDialog setTitleName(@StringRes int stringResID) {
        setTitleName(mContext.getResources().getString(stringResID));
        return this;
    }

    /**
     * 设置Dialog标题
     *
     * @param title 标题
     * @return this
     */
    public SimpleDialog setTitleName(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(title);
        }
        return this;
    }

    /**
     * 获取Dialog标题
     *
     * @return CharSequence
     */
    public TextView getTitleName() {
        return mTvTitle;
    }

    /**
     * 设置Dialog标题字体大小
     *
     * @param size 标题字体大小
     * @return this
     */
    public SimpleDialog setTitleSize(float size) {
        mTvTitle.setTextSize(size);
        return this;
    }

    /**
     * 设置Dialog标题字体Gravity
     *
     * @param gravity 标题字体Gravity
     * @return this
     */
    public SimpleDialog setTitleGravity(int gravity) {
        mTvTitle.setGravity(gravity);
        return this;
    }

    /**
     * 批量设置点击事件监听
     *
     * @param onClickListener onClickListener
     * @param ids             id数组
     * @return this
     */
    public SimpleDialog setOnClickListener(View.OnClickListener onClickListener, int... ids) {

        if (!isEmpty(ids) && onClickListener != null) {
            for (int id : ids) {
                mDialogView.findViewById(id).setOnClickListener(onClickListener);
            }
        }
        return this;
    }

    /**
     * 按钮点击事件：否定的
     *
     * @return this
     */
    public SimpleDialog setNegativeButton() {
        return setNegativeButton(null, null);
    }

    /**
     * 按钮点击事件：否定的
     *
     * @param onClickListener onClickListener
     * @return this
     */
    public SimpleDialog setNegativeButton(View.OnClickListener onClickListener) {
        return setNegativeButton(null, onClickListener);
    }

    /**
     * 按钮点击事件：否定的
     *
     * @param stringRes       button name
     * @param onClickListener onClickListener
     * @return this
     */
    public SimpleDialog setNegativeButton(@StringRes int stringRes, View.OnClickListener onClickListener) {
        return setNegativeButton(mContext.getResources().getString(stringRes), onClickListener);
    }

    /**
     * 按钮点击事件：否定的
     *
     * @param str             button name
     * @param onClickListener onClickListener
     * @return this
     */
    public SimpleDialog setNegativeButton(CharSequence str, View.OnClickListener onClickListener) {
        return setNegativeButton(str, Color.GRAY, onClickListener);
    }

    /**
     * 按钮点击事件：否定的
     *
     * @param str             button name
     * @param onClickListener onClickListener
     * @return this
     */
    public SimpleDialog setNegativeButton(CharSequence str, @ColorInt int color, View.OnClickListener onClickListener) {
        if (isSetNewView) {//已经不存在这个按钮
            throw new RuntimeException("Already there is no this button!");
        }
        if (!TextUtils.isEmpty(str)) {
            mBtnNegative.setText(str);
        }
        mBtnNegative.setTextColor(color);

        llOperate.setVisibility(View.VISIBLE);
        mBtnNegative.setVisibility(View.VISIBLE);
        mBtnNegative.setOnClickListener(new MyOnClickListener(onClickListener));
        return this;
    }

    public TextView getNegativeButton() {
        return mBtnNegative;
    }

    public TextView getPositiveButton() {
        return mBtnPositive;
    }

    /**
     * 按钮点击事件：肯定的积极的
     *
     * @return this
     */
    public SimpleDialog setPositiveButton() {
        return setPositiveButton(null, null);
    }

    /**
     * 按钮点击事件：肯定的积极的
     *
     * @param onClickListener onClickListener
     * @return this
     */
    public SimpleDialog setPositiveButton(View.OnClickListener onClickListener) {
        return setPositiveButton(null, onClickListener);
    }

    /**
     * 按钮点击事件：肯定的积极的
     *
     * @param stringRes       button name
     * @param onClickListener onClickListener
     * @return this
     */
    public SimpleDialog setPositiveButton(@StringRes int stringRes, View.OnClickListener onClickListener) {
        return setPositiveButton(stringRes, onClickListener, false);
    }

    /**
     * 按钮点击事件：肯定的积极的
     *
     * @param str             button name
     * @param onClickListener onClickListener
     * @return this
     */
    public SimpleDialog setPositiveButton(CharSequence str, View.OnClickListener onClickListener) {
        return setPositiveButton(str, onClickListener, false);
    }

    /**
     * 按钮点击事件：肯定的积极的
     *
     * @param stringRes       button name
     * @param onClickListener onClickListener
     * @param isNativeClick   原生的点击事件，不处理Dialog的关闭
     * @return this
     */
    public SimpleDialog setPositiveButton(@StringRes int stringRes, View.OnClickListener onClickListener
            , boolean isNativeClick) {
        return setPositiveButton(mContext.getResources().getString(stringRes), onClickListener, isNativeClick);
    }

    /**
     * 按钮点击事件：肯定的积极的
     *
     * @param str             button name
     * @param onClickListener onClickListener
     * @param isNativeClick   原生的点击事件，不处理Dialog的关闭
     * @return this
     */
    public SimpleDialog setPositiveButton(CharSequence str, View.OnClickListener onClickListener
            , boolean isNativeClick) {
        return setPositiveButton(str, Color.BLACK, onClickListener, isNativeClick);
    }

    /**
     * 按钮点击事件：肯定的积极的
     *
     * @param str             button name
     * @param onClickListener onClickListener
     * @param isNativeClick   原生的点击事件，不处理Dialog的关闭
     * @return this
     */
    public SimpleDialog setPositiveButton(CharSequence str, @ColorInt int color, View.OnClickListener onClickListener
            , boolean isNativeClick) {
        if (isSetNewView) {//已经不存在这个按钮
            throw new RuntimeException("Already there is no this button!");
        }
        if (!TextUtils.isEmpty(str)) {
            mBtnPositive.setText(str);
        }
        mBtnPositive.setTextColor(color);
        mBtnPositive.setVisibility(View.VISIBLE);
        llOperate.setVisibility(View.VISIBLE);
        if (isNativeClick) {//原生的点击事件，不处理Dialog的关闭
            if (onClickListener != null) {
                mBtnPositive.setOnClickListener(onClickListener);
            }
        } else {
            mBtnPositive.setOnClickListener(new MyOnClickListener(onClickListener));
        }
        return this;
    }

    /**
     * 按钮点击事件：下一步
     *
     * @return this
     */
    public SimpleDialog setNextButton() {
        return setNextButton(null, null);
    }

    /**
     * 按钮点击事件：下一步
     *
     * @param onClickListener onClickListener
     * @return this
     */
    public SimpleDialog setNextButton(View.OnClickListener onClickListener) {
        return setNextButton(null, onClickListener);
    }

    /**
     * 按钮点击事件：下一步
     *
     * @param stringRes       button name
     * @param onClickListener onClickListener
     * @return this
     */
    public SimpleDialog setNextButton(@StringRes int stringRes, View.OnClickListener onClickListener) {
        return setNextButton(stringRes, onClickListener, false);
    }

    /**
     * 按钮点击事件：下一步
     *
     * @param str             button name
     * @param onClickListener onClickListener
     * @return this
     */
    public SimpleDialog setNextButton(CharSequence str, View.OnClickListener onClickListener) {
        return setNextButton(str, onClickListener, false);
    }

    /**
     * 按钮点击事件：下一步
     *
     * @param stringRes       button name
     * @param onClickListener onClickListener
     * @return this
     */
    public SimpleDialog setNextButton(@StringRes int stringRes, View.OnClickListener onClickListener
            , boolean isNativeClick) {
        return setNextButton(getContext().getResources().getString(stringRes), onClickListener, isNativeClick);
    }

    /**
     * 按钮点击事件：下一步
     *
     * @param str             button name
     * @param onClickListener onClickListener
     * @param isNativeClick   原生的点击事件，不处理Dialog的关闭
     * @return this
     */
    public SimpleDialog setNextButton(CharSequence str, View.OnClickListener onClickListener
            , boolean isNativeClick) {
        return setNextButton(str, Color.BLACK, onClickListener, isNativeClick);
    }

    /**
     * 按钮点击事件：下一步
     *
     * @param str             button name
     * @param onClickListener onClickListener
     * @param isNativeClick   原生的点击事件，不处理Dialog的关闭
     * @return this
     */
    public SimpleDialog setNextButton(CharSequence str, @ColorInt int color, View.OnClickListener onClickListener
            , boolean isNativeClick) {
        if (isSetNewView) {//已经不存在这个按钮
            throw new RuntimeException("Already there is no this button!");
        }
        if (!TextUtils.isEmpty(str)) {
            mBtnNext.setText(str);
        }
        mBtnNext.setTextColor(color);

        llOperate.setVisibility(View.VISIBLE);
        mBtnNext.setVisibility(View.VISIBLE);

        if (isNativeClick) {//原生的点击事件，不处理Dialog的关闭
            if (onClickListener != null) {
                mBtnNext.setOnClickListener(onClickListener);
            }
        } else {
            mBtnNext.setOnClickListener(new MyOnClickListener(onClickListener));
        }
        return this;
    }


    /**
     * 设置动画，默认提示动画
     *
     * @return this
     */
    public SimpleDialog setAnimatorSet() {
        setAnimatorSet(AnimType.PROMPT);
        return this;
    }

    /**
     * 设置动画
     *
     * @param type WARNING,PROMPT
     * @return this
     */
    public SimpleDialog setAnimatorSet(AnimType type) {
        switch (type) {
            case WARNING://警告
                AnimatorSetUtil.startWarningAnimatorSet(getLayout());
                break;
            case PROMPT://提示
                AnimatorSetUtil.startPromptAnimatorSet(getLayout());
                break;
        }
        return this;
    }

    /**
     * 设置点击外部可以关闭对话框窗口
     *
     * @return this
     */
    public SimpleDialog setCanceledOnTouchOutside() {
        super.setCanceledOnTouchOutside(true);
        return this;
    }

    /**
     * 设置Gravity
     *
     * @param gravity Gravity
     * @return this
     */
    public SimpleDialog setGravity(int gravity) {
        Window window = getWindow();
        if (window != null) {
            window.setGravity(gravity);
        }
        return this;
    }

    /**
     * 设置对话框窗口动画
     *
     * @param animations 动画资源
     * @return this
     */
    public SimpleDialog setAnimations(int animations) {
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(animations);
        }
        return this;
    }

    /**
     * 加载数据的对话框关闭回调接口
     *
     * @param onClickListener 回调接口
     * @param noOverride      为了不重写父类的方法
     * @return this
     */
    public SimpleDialog setOnDismissListener(OnDismissListener onClickListener, int noOverride) {
        if (onClickListener != null) {
            setOnDismissListener(onClickListener);
        }
        return this;
    }

    @Override
    public void show() {

        boolean isTitle = mTvTitle.getVisibility() != View.VISIBLE;
        boolean isFlContent = mFlContent.getVisibility() != View.VISIBLE;
        boolean isNegative = mBtnNegative.getVisibility() != View.VISIBLE;
        boolean isPositive = mBtnPositive.getVisibility() != View.VISIBLE;
        if (isTitle && isFlContent && isNegative && isPositive && !isSetNewView) {//没有可显示的东西，不弹出对话框并抛出Exception
            throw new RuntimeException("No title and available button!");
        }
        try {
            super.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 点击事件监听器
     */
    private class MyOnClickListener implements View.OnClickListener {
        View.OnClickListener onClickListener;

        public MyOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        @Override
        public void onClick(View view) {
            SimpleDialog.this.dismiss();
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
        }
    }

    /**
     * 动画类型
     */
    public enum AnimType {
        WARNING, //警告
        PROMPT,//提示
    }

    /**
     * 动画集合工具类
     */
    private static class AnimatorSetUtil {

        /**
         * 开启一个警告抖动的动画
         *
         * @param view 需要开启动画的View
         */
        private static void startWarningAnimatorSet(View view) {
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
        private static void startPromptAnimatorSet(View view) {
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
    }

    /**
     * 判断是否为空
     *
     * @param obj 判断的数组对象
     * @return true为空，false为非空
     */
    public static boolean isEmpty(int[] obj) {
        boolean isEmpty = true;
        if (obj != null) {
            isEmpty = obj.length <= 0;
        }
        return isEmpty;
    }
}
