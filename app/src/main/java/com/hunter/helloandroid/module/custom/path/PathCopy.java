package com.hunter.helloandroid.module.custom.path;

import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2018
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/6/1 15:54
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class PathCopy extends ViewGroup {

    public PathCopy(Context context) {
        super(context);
        init();
    }

    public PathCopy(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathCopy(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (getChildCount() <= 0) {
            return;
        }
        View child = getChildAt(0);
//        removeAllViews();
        float length = mPathMeasure.getLength();
        int width = child.getWidth();
        float count = length / (width + mInterval);

        if (child instanceof Serializable) {

            Serializable serializable = deepCopy((Serializable) child);
            System.out.println(".............serializable:" + serializable);
        }

    }

    private void init() {
        mPath = new Path();
        mPathMeasure = new PathMeasure(mPath, false);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        if (count <= 0) {
            return;
        }
        View child = getChildAt(0);
        child.layout(l, t, r, b);

    }

    private Path mPath;
    private PathMeasure mPathMeasure;
    private int mInterval;

    /**
     * 深度复制集合
     */
    public static <T extends Serializable> T deepCopy(T obj) {
        T result = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(obj);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            result = (T) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
