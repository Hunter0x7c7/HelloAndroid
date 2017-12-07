package com.hunter.panorama;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.hunter.panorama.util.AnimatorSetUtil;
import com.hunter.panorama.util.ImageUtil;

import java.util.List;

/**
 * 一堆子View按左上角百分比坐标显示位置，并支持缩放
 *
 * @author Zihang Huang
 *         create date 2017/11/17 18:08
 */
public class CoordinateView<T extends View> extends FrameLayout {
    private Context mContext;
    private PhotoView mPhotoView;
    private PointGroup mPointGroup;
    private List<PointF> mPointList;
    private List<T> mViewList;
    private T mT;

    public CoordinateView(Context context) {
        super(context);
        init(context);
    }

    public CoordinateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CoordinateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;

        mPhotoView = new PhotoView(context);
        mPhotoView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        mPhotoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mPhotoView.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
//        mPhotoView.setImageResource(R.mipmap.bg_monitor);

        mPointGroup = new PointGroup(context);
        mPointGroup.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        mPointGroup.setPhotoView(mPhotoView);

        addView(mPhotoView);
        addView(mPointGroup);
    }

    public void clear() {
        if (mPointList != null) {
            mPointList.clear();
        }
        if (mPointGroup != null) {
            mPointGroup.removeAllViews();
        }
    }


    public void setup(List<PointF> mPointList, List<T> viewList) {
        setup(mPointList,  viewList, null, null);
    }

    public void setup(final List<PointF> mPointList, final List<T> viewList
            , final String url, final ImageUtil.CommonCallback commonCallback) {

        if (!TextUtils.isEmpty(url)) {
            CommonCallback callback = new CommonCallback(commonCallback) {
                @Override
                public void onSuccess(Drawable result) {
                    super.onSuccess(result);
                    addPoint(mPointList, viewList);
                }
            };

            ImageUtil.getInstance(mContext).display(mPhotoView, url, callback);
        } else {
            addPoint(mPointList, viewList);
        }
    }

    private void addPoint(List<PointF> mPointList, List<T> viewList) {
        clear();
        this.mPointList = mPointList;
        this.mViewList = viewList;

        //设置布局动画
        AnimatorSetUtil.setupLayoutAnimation(mPointGroup);
        mPointGroup.setPointInfo(mPointList);

        for (final T child : viewList) {
            if (child != null)
                mPointGroup.addView(child);
        }
    }

    public int getPointCount() {
        int count = 0;
        if (mPointList != null && mPointList.size() > 0) {
            count = mPointList.size();
        }
        return count;
    }

    public PointGroup getPointGroup() {
        return mPointGroup;
    }

    public T getPointAt(int index) {
        T childAt = null;
        if (index >= 0) {
            if (mViewList != null && mViewList.size() > 0 && index < mViewList.size()) {
                childAt = mViewList.get(index);
            } else if (index < getPointCount()) {
                PointGroup pointGroup = getPointGroup();
                if (pointGroup != null) {
                    View view = pointGroup.getChildAt(index);
                    try {
                        childAt = (T) view;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return childAt;
    }

    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        super.setOnTouchListener(onTouchListener);
        getPointGroup().setOnTouchListener(onTouchListener);
    }

    private class CommonCallback extends ImageUtil.CommonCallback {
        private CommonCallback commonCallback;

        private CommonCallback(CommonCallback commonCallback) {
            this.commonCallback = commonCallback;
        }

        @Override
        public void onSuccess(Drawable result) {
            super.onSuccess(result);
            if (commonCallback != null) {
                commonCallback.onSuccess(result);
            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            super.onError(ex, isOnCallback);
            if (commonCallback != null) {
                commonCallback.onError(ex, isOnCallback);
            }
        }

        @Override
        public void onCancelled(CancelledException cex) {
            super.onCancelled(cex);
            if (commonCallback != null) {
                commonCallback.onCancelled(cex);
            }
        }

        @Override
        public void onFinished() {
            super.onFinished();
            if (commonCallback != null) {
                commonCallback.onFinished();
            }
        }
    }

}
