package com.hunter.helloandroid.module.four;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.Collections;
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
 * 创建日期： 2017/12/20 17:28
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class MultiViewGroup extends ViewGroup {

    private Context mContext;

    public MultiViewGroup(Context context) {
        super(context);
        init(context);
    }


    public MultiViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MultiViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        /*
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildView(i);
            if (child != null && child.getVisibility() != GONE) {
                child.measure(
                        MeasureSpec.makeMeasureSpec(getMeasuredWidth() / 2, MeasureSpec.AT_MOST),
                        MeasureSpec.makeMeasureSpec(getMeasuredHeight() / 2, MeasureSpec.AT_MOST));
            }
        }
//        measureChildren(widthMeasureSpec, heightMeasureSpec);
//        change();
    }

    /**
     * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
     * 1.精确模式（MeasureSpec.EXACTLY）
     * 在这种模式下，尺寸的值是多少，那么这个组件的长或宽就是多少。
     * 2.最大模式（MeasureSpec.AT_MOST）
     * 这个也就是父组件，能够给出的最大的空间，当前组件的长或宽最大只能为这么大，当然也可以比这个小。
     * 3.未指定模式（MeasureSpec.UNSPECIFIED）
     * 这个就是说，当前组件，可以随便用空间，不受限制。
     */
    private void measureDimension(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        /**
         * 记录如果是wrap_content是设置的宽和高
         */
        int width = 0;
        int height = 0;

        int cCount = getChildCount();

        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        // 用于计算左边两个childView的高度
        int lHeight = 0;
        // 用于计算右边两个childView的高度，最终高度取二者之间大值
        int rHeight = 0;

        // 用于计算上边两个childView的宽度
        int tWidth = 0;
        // 用于计算下面两个childiew的宽度，最终宽度取二者之间大值
        int bWidth = 0;

        /**
         * 根据childView计算的出的宽和高，以及设置的margin计算容器的宽和高，主要用于容器是warp_content时
         */
        for (int i = 0; i < cCount; i++) {
            View childView = getChildView(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            // 上面两个childView
            if (i == 0 || i == 1) {
                tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 2 || i == 3) {
                bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 0 || i == 2) {
                lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

            if (i == 1 || i == 3) {
                rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

        }

        width = Math.max(tWidth, bWidth);
        height = Math.max(lHeight, rHeight);

        /**
         * 如果是wrap_content设置为我们计算的值
         * 否则：直接设置为父容器计算的值
         */
        int measuredWidth = (widthMode == MeasureSpec.EXACTLY) ? sizeWidth : width;
        int measuredHeight = (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : height;
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layout();
    }


    private void layout() {
        int childCount = getChildCount();
        int width = getWidth();
        int height = getHeight();
        switch (childCount) {
            case 1:

                getChildView(0).layout(0, 0, width, height);
                break;
            case 2:
                for (int i = 0; i < childCount; i++) {
                    View childAt = getChildView(i);
                    int childAtWidth = width / 2;
                    int left = i % 2 * childAtWidth;
                    childAt.layout(left, 0, childAtWidth + left, height);
                }
                break;
            case 4:
                for (int i = 0; i < childCount; i++) {
                    View childView = getChildView(i);
                    int childAtWidth = width / 2;
                    int childAtHeight = height / 2;
                    int left = i % 2 * childAtWidth;
                    int top = i / 2 % 2 * childAtHeight;

                    int cWidth = childView.getMeasuredWidth();
                    int cHeight = childView.getMeasuredHeight();
                    childView.layout(left, top, cWidth + left, cHeight + top);

//                    childView.layout(left, top, childAtWidth + left, childAtHeight + top);
                }
                break;
            case 6:
                for (int i = 0; i < childCount; i++) {
                    View childView = getChildView(i);
                    int childAtWidth = width / 3;
                    int childAtHeight = height / 2;
                    int left = i % 3 * childAtWidth;
                    int top = i / 3 % 3 * childAtHeight;

//                    int cWidth = childView.getMeasuredWidth();
//                    int cHeight = childView.getMeasuredHeight();

                    childView.layout(left, top, childAtWidth + left, childAtHeight + top);
                }
                break;
        }
    }

    private int mStartTouchX, mStartTouchY, mStartX, mStartY;
    private View mCurrentView;
    private int mDeleteRectHeight;
    private boolean isPressed, isChange = false;
    private int mCurrentViewLeft = -1, mCurrentViewTop = -1, mCurrentViewWidth = -1, mCurrentViewHeight = -1;

    public View change() {
        if (mCurrentView == null) {
            mCurrentView = getChildView(0);
        }

        int childCount = getChildCount();
        if (!isChange) {
            mCurrentViewLeft = mCurrentView.getLeft();
            mCurrentViewTop = mCurrentView.getTop();
            mCurrentViewWidth = mCurrentView.getWidth();
            mCurrentViewHeight = mCurrentView.getHeight();
//            System.out.println(".c..mCurrentViewLeft:" + mCurrentViewLeft
//                    + " mCurrentViewTop:" + mCurrentViewTop
//                    + " mCurrentViewWidth:" + mCurrentViewWidth
//                    + " mCurrentViewHeight:" + mCurrentViewHeight);
//            mCurrentView.layout(0, 0, getWidth(), getHeight());
            layoutAllView(mCurrentView);

            for (int i = 0; i < childCount; i++) {
                View childAt = getChildView(i);
//                isVisibleChildren(childAt, childAt == mCurrentView);
                childAt.setVisibility(childAt == mCurrentView ? View.VISIBLE : View.INVISIBLE);
            }

        } else {
            mCurrentView.layout(mCurrentViewLeft, mCurrentViewTop
                    , mCurrentViewWidth + mCurrentViewLeft, mCurrentViewHeight + mCurrentViewTop);
            for (int i = 0; i < childCount; i++) {
                visibleAllView(getChildView(i));
            }
        }
        isChange = !isChange;

//        String str = "left:" + mCurrentView.getLeft() + " top:" + mCurrentView.getTop() + " width:" + mCurrentView.getWidth() + " height:" + mCurrentView.getHeight();
//        ToastUtil.showPrompt(str);
        return mCurrentView;
    }

    private void layoutAllView(View mCurrentView) {
        layoutAllView(mCurrentView, 0, 0, 0, 0);
    }

    private void layoutAllView(View mCurrentView, int marginLeft, int marginTop, int marginRight, int marginBottom) {
        mCurrentView.layout(marginLeft, marginTop, getWidth() - marginRight, getHeight() - marginBottom);
        if (mCurrentView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) mCurrentView;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                MarginLayoutParams params = (MarginLayoutParams) childAt.getLayoutParams();
                layoutAllView(childAt, params.leftMargin, params.topMargin, params.rightMargin, params.bottomMargin);
            }
        }
    }

    private void visibleAllView(View mCurrentView) {
        if (mCurrentView.getVisibility() != View.VISIBLE)
            mCurrentView.setVisibility(View.VISIBLE);

        if (mCurrentView instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) mCurrentView;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                visibleAllView(viewGroup.getChildAt(i));
            }
        }
    }

    public interface OnDeleteRectListener {
        void onPressed(boolean isPressed);
    }

    private OnDeleteRectListener onDeleteRectListener;

    public void setOnDeleteRectListener(OnDeleteRectListener onDeleteRectListener) {
        this.onDeleteRectListener = onDeleteRectListener;
    }

    public interface OnDiscardListener {
        void onDiscard();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (!isChange) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    isPressed = false;
                    int index = getHitIndex((int) event.getX(), (int) event.getY());
                    View childView = getChildView(index);
                    mStartX = childView.getLeft();
                    mStartY = childView.getTop();
                    mStartTouchX = (int) event.getX() - mStartX;
                    mStartTouchY = (int) event.getY() - mStartY;

                    mCurrentView = childView;

                    int j = indexOfChild(mCurrentView);
                    int i1 = getChildCount() - 1;
                    if (j != i1) {
                        Collections.swap(mViewList, j, i1);
                        childView.bringToFront();
//                                invalidate();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:

                    boolean pressed = event.getY() <= mDeleteRectHeight;
                    if (pressed != isPressed) {
                        if (onDeleteRectListener != null) {
                            onDeleteRectListener.onPressed(event.getY() <= mDeleteRectHeight);
                        }
                        isPressed = pressed;
                    }


                    int startX = (int) (event.getX() - mStartTouchX);
                    int startY = (int) (event.getY() - mStartTouchY);

                    mCurrentView.layout(startX, startY, startX + mCurrentView.getWidth(), startY + mCurrentView.getHeight());
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (isPressed) {
                        isPressed = false;
                        if (onDeleteRectListener != null) {
                            onDeleteRectListener.onPressed(false);
                        }
                    }

                    if (event.getY() <= mDeleteRectHeight) {
                        System.out.println("...ACTION_UP...delete.......");

                        if (mCurrentView instanceof OnDiscardListener) {
                            ((OnDiscardListener) mCurrentView).onDiscard();
                        }
                    }
                    View exchangeView = getHitView(mCurrentView, (int) event.getX(), (int) event.getY());

//                mCurrentView.layout(mStartX, mStartY, mStartX + mCurrentView.getWidth(), mStartY + mCurrentView.getHeight());
                    if (exchangeView == mCurrentView) {
                        mCurrentView.layout(mStartX, mStartY, mStartX + mCurrentView.getWidth(), mStartY + mCurrentView.getHeight());
                    } else {

                        startX = exchangeView.getLeft();
                        startY = exchangeView.getTop();
                        int width = exchangeView.getWidth();
                        int height = exchangeView.getHeight();

                        exchangeView.layout(mStartX, mStartY, mStartX + mCurrentView.getWidth(), mStartY + mCurrentView.getHeight());
                        mCurrentView.layout(startX, startY, startX + width, startY + height);
                    }
                    break;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private int getHitIndex(int x, int y) {
        int index = -1;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            Rect rect = new Rect();
            View childAt = getChildView(i);
            childAt.getHitRect(rect);
            if (rect.contains(x, y)) {
                index = i;
            }
        }
        return index;
    }

    private View getHitView(View mCurrentView, int x, int y) {
        int count = getChildCount();
        List<View> viewList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Rect rect = new Rect();
            View childAt = getChildView(i);
            childAt.getHitRect(rect);
            if (rect.contains(x, y)) {
                viewList.add(childAt);
            }
//                    childAt.getHitRect(rect);   childAt.getGlobalVisibleRect(rect);
        }

        View exchangeView = mCurrentView;
        if (viewList.size() == 2) {
            viewList.remove(mCurrentView);
            exchangeView = viewList.get(0);
        }
        return exchangeView;
    }

    private View getChildView(int i) {
        View view = null;
        if (mViewList != null && mViewList.size() > i && i >= 0) {
            view = mViewList.get(i);
        }
        return view;
//        return getChildAt(i);
    }

    private void init(Context context) {
        mContext = context;
        mDeleteRectHeight = DensityUtil.dip2px(50);
    }

    private List<View> mViewList = new ArrayList<>();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mViewList.size() == 0) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                mViewList.add(getChildAt(i));
            }
        }
    }


}
