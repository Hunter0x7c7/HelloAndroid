package com.hunter.panorama;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.OnCoordinateChangeListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.hunter.panorama.util.DensityUtil;

import java.util.List;

/**
 * 一堆子View按左上角百分比坐标点显示位置，PhotoView缩放时实时改变子View位置
 * @author Zihang Huang
 *         create date 2017/11/17 16:54
 */
public class PointGroup extends ViewGroup implements OnCoordinateChangeListener {
    private List<PointF> mPointFList;
    private float oldLeft, oldTop, oldRight, oldBottom;
    private int yoff;

    public PointGroup(Context context) {
        super(context);
        init(context);
    }

    public PointGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PointGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        yoff = DensityUtil.dip2px(context, -2);
    }

    public void setPointInfo(List<PointF> mPointList) {
        this.mPointFList = mPointList;
    }

    public void setPhotoView(PhotoView photoView) {
        if (photoView != null)
            photoView.setOnCoordinateChangeListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!(mPointFList != null && mPointFList.size() > 0)) {
            return;
        }
        if (!changed) {
            return;
        }
        int width = getWidth();
        int height = getHeight();

        View child;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            PointF p = mPointFList.get(i);
            if (p == null) {
                continue;
            }
            int x = (int) (width * p.x / 100);
            int y = (int) (height * p.y / 100);
            change(child, x, y);
        }
    }

    @Override
    public void onCoordinateChange(float width, float height, float left, float top, float right, float bottom) {
        if (mPointFList == null) {
            return;
        }
        if (oldLeft == left && oldTop == top && oldRight == right && oldBottom == bottom) {
            return;
        }
        int size = getChildCount();
        if (size != mPointFList.size()) {
            return;
        }

        View child;
        for (int i = 0; i < size; i++) {
            child = getChildAt(i);
            PointF p = mPointFList.get(i);
            if (p == null) {
                continue;
            }
            int x = (int) (width * p.x / 100 + left);
            int y = (int) (height * p.y / 100 + top);
            change(child, x, y);
        }
        oldLeft = left;
        oldTop = top;
        oldRight = right;
        oldBottom = bottom;
    }

    private void change(View child, int x, int y) {
        int measuredWidth = child.getMeasuredWidth();
        int measuredHeight = child.getMeasuredHeight();
        int x1 = x - measuredWidth / 2;
        int y1 = y - measuredHeight + yoff;

        changeCoordinate(child, x1, y1);
    }

    /**
     * 指定孩子的位置
     */
    public void changeCoordinate(View child, int x, int y) {
        child.layout(x, y, x + child.getMeasuredWidth(), y + child.getMeasuredHeight());
    }

}
