package com.hunter.helloandroid.module.coordinator.alipay;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * ================================================================
 * <p>
 * 版    权： HunterHuang(c)2018
 * <p>
 * 作    者： Hunter_1125607007@QQ.COM
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/7/23 17:43
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class FlingBehavior extends AppBarLayout.Behavior {

    private boolean isPositive;
    private AppBarScrollListem absl;

    public FlingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        absl = new AppBarScrollListem();
    }


    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target
            , float velocityX, float velocityY) {

        System.out.println(".......onNestedPreFling......velocityX:" + velocityX + " velocityY:" + velocityY);

        if (target instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) target;
            recyclerView.removeOnScrollListener(absl);
            recyclerView.addOnScrollListener(absl);
            absl.coordinatorLayout = coordinatorLayout;
            absl.child = child;
            absl.target = target;
            absl.velocityX = velocityX;
            absl.velocityY = velocityY;
        }
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target
            , float velocityX, float velocityY, boolean consumed) {

        System.out.println(".......onNestedFling......velocityX:" + velocityX + " velocityY:" + velocityY);

        if (velocityY > 0 && !isPositive || velocityY < 0 && isPositive) {
            velocityY = velocityY * -1;
        }
        if (target instanceof RecyclerView && velocityY < 0) {
            absl.coordinatorLayout = coordinatorLayout;
            absl.child = child;
            absl.target = target;
            absl.velocityX = velocityX;
            absl.velocityY = velocityY;
            absl.totalDy = 0;
        }
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }


    public boolean onNestedFlingEx(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target
            , float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target
            , int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        System.out.println(".......onNestedPreScroll......dx:" + dx + " dy:" + dy);
        isPositive = dy > 0;
    }

    public class AppBarScrollListem extends RecyclerView.OnScrollListener {
        private AppBarLayout child;
        private View target;
        private CoordinatorLayout coordinatorLayout;
        private float velocityX;
        private float velocityY;
        private int totalDy = 0;

        public AppBarScrollListem() {
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (child != null && target != null && coordinatorLayout != null) {

                    if (target instanceof RecyclerView) {
                        RecyclerView rv = (RecyclerView) target;
                        final View firstChild = recyclerView.getChildAt(0);
                        final int childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild);
                        if (childAdapterPosition == 0 && firstChild.getY() == 0) {
                            float i = child.getMeasuredHeight() + Math.abs(totalDy);
                            int velocityFinal = (int) (velocityY / i * child.getMeasuredHeight());
                            onNestedFlingEx(coordinatorLayout, child, target, velocityX, velocityFinal, false);
                        }
                        rv.removeOnScrollListener(this);
                    }
                }
                clear();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            totalDy += dy;
        }

        public void clear() {
            totalDy = 0;
            child = null;
            target = null;
            coordinatorLayout = null;
            velocityX = 0;
            velocityY = 0;
        }


    }

}
