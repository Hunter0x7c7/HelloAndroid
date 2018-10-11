package com.hunter.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.hunter.widget.gif.R;

import java.io.InputStream;

/**
 * 显示GIF动画
 *
 * @author Zihang Huang
 *         create date： 2015/9/8 10:06
 */
public class GifView extends View {
    /**
     * 默认为1秒
     */
    private static final int DEFAULT_MOVIE_DURATION = 1000;
    //最小宽度
    private int mMinWidth = 18, mMinHeight = 18;
    private int mMovieResourceId;
    private Movie mMovie;
    private long mMovieStart;
    private int mCurrentAnimationTime = 0;
    private float mLeft;
    private float mTop;
    private float mScale = -1;
    private int mMeasuredMovieWidth;
    private int mMeasuredMovieHeight;
    private boolean mVisible = true;
    private volatile boolean mPaused = false;
    private Paint mBitPaint;
    private Bitmap mBitmap;
    private Rect mSrcRect, mDstRect;

    public GifView(Context context) {
        super(context, null);
        init(context, null, 0);
    }

    public GifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public GifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GifView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mMinWidth = DensityUtil.dip2px(context, mMinWidth);
        mMinHeight = DensityUtil.dip2px(context, mMinHeight);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        // 从描述文件中读出gif的值，创建出Movie实例
        if (attrs != null) {
            final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GifView, defStyle, defStyle);
            mMovieResourceId = array.getResourceId(R.styleable.GifView_src_gif, -1);
            mPaused = array.getBoolean(R.styleable.GifView_gif_paused, false);
            array.recycle();
        }
        if (mMovieResourceId != -1) {
            InputStream is = getResources().openRawResource(mMovieResourceId);
            mMovie = Movie.decodeStream(is);
        }
        initPaint();
        initBitmap(mMovieResourceId);//初始化Bitmap
    }

    /**
     * 设置gif图资源
     */
    public void setMovieResource(int movieResId) {
        this.mMovieResourceId = movieResId;
        mMovie = Movie.decodeStream(getResources().openRawResource(mMovieResourceId));
        initBitmap(mMovieResourceId);//初始化Bitmap
        requestLayout();
        invalidate();
    }

    public void setMovie(Movie movie) {
        this.mMovie = movie;
        requestLayout();
    }

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovieTime(int time) {
        mCurrentAnimationTime = time;
        invalidate();
    }

    /**
     * 设置暂停
     */
    public void setPaused(boolean paused) {
        this.mPaused = paused;
        if (!paused) {
            long millis = SystemClock.uptimeMillis();
            mMovieStart = millis - mCurrentAnimationTime;
        }
        invalidate();
    }

    /**
     * 判断gif图是否停止了
     */
    public boolean isPaused() {
        return this.mPaused;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureSize(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mLeft = (getWidth() - mMeasuredMovieWidth) / 2f;
        mTop = (getHeight() - mMeasuredMovieHeight) / 2f;
        mVisible = getVisibility() == View.VISIBLE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mMovie != null) {
            if (!mPaused) {
                updateAnimationTime();
                drawMovieFrame(canvas);
                invalidateView();
            } else {
                drawMovieFrame(canvas);
            }
        } else if (mBitmap != null) {
            mDstRect.set(0, 0, getWidth(), getHeight());
            canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, mBitPaint);
        }
    }

    private void measureSize(int widthMeasureSpec, int heightMeasureSpec) {

        //分别拿到宽高的大小和测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int mWidth, mHeight;//最终宽度高度
        if (widthMode == MeasureSpec.EXACTLY) {
//            System.out.println("...onMeasure...EXACTLY...widthMode:" + widthMode + " widthSize:" + widthSize);
            mWidth = widthSize;//如果是指定大小或填充窗体（指定大小）,直接设置最终宽度
            if (mMovie != null) {
                int movieWidth = mMovie.width();
                mScale = calculateScale(movieWidth, widthSize);
            }
//            System.out.println(".............EXACTLY.....widthSize:" + widthSize);
        } else {
            //如果是包裹内容
            if (mMovie != null) {
//                int movieWidth = mMovie.width();
                mWidth = widthSize;//maxWidth
//                mScale = calculateScale(movieWidth, widthSize);
            } else if (mBitmap != null) {
                mWidth = mBitmap.getWidth();
            } else {
                mWidth = mMinWidth;
            }
            //System.out.println("...onMeasure..包裹内容.......mWidth:" + mWidth);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;//同上
            //System.out.println("...onMeasure...EXACTLY...heightMode:" + heightMode + " heightSize:" + heightSize);
//            System.out.println(".............EXACTLY.....heightSize:" + heightSize);
        } else {
            //如果是包裹内容
            if (mMovie != null) {
                int movieWidth = mMovie.width();
                int movieHeight = mMovie.height();
                mScale = calculateScale(movieWidth, MeasureSpec.getSize(widthMeasureSpec));
                mHeight = (int) (movieHeight * mScale);
            } else if (mBitmap != null) {
                mHeight = mBitmap.getHeight();
            } else {
                mHeight = mMinHeight;//同上
            }
            //System.out.println("...onMeasure..包裹内容.......mHeight:" + mHeight);
        }
        mMeasuredMovieWidth = mWidth;
        mMeasuredMovieHeight = mHeight;
        setMeasuredDimension(mWidth, mHeight);//设置View的宽高，测量结束
        //System.out.println("...onMeasure......mWidth:" + mWidth + " mHeight:" + mHeight);
        if (mScale == -1) {
            mScale = 1f;
        }

//        switch (widthMode) {
//            case MeasureSpec.EXACTLY:
//                System.out.println(".............EXACTLY..... widthSize:" + widthSize);
//                break;
//            case MeasureSpec.AT_MOST:
//                System.out.println(".............AT_MOST..... widthSize:" + widthSize);
//                break;
//            case MeasureSpec.UNSPECIFIED:
//                System.out.println(".............UNSPECIFIED..... widthSize:" + widthSize);
//                break;
//        }
//        switch (heightMode) {
//            case MeasureSpec.EXACTLY:
//                System.out.println(".............EXACTLY..... heightSize:" + heightSize);
//                break;
//            case MeasureSpec.AT_MOST:
//                System.out.println(".............AT_MOST..... heightSize:" + heightSize);
//                break;
//            case MeasureSpec.UNSPECIFIED:
//                System.out.println(".............UNSPECIFIED..... heightSize:" + heightSize);
//                break;
//        }
//   System.out.println("...onMeasure......mWidth:" + mWidth + " mHeight:" + mHeight);
    }

    private float calculateScale(float width, float maxWidth) {
        float scaleW = width / maxWidth;
        return 1f / scaleW;
    }

    public int getMinWidth() {
        return mMinWidth;
    }

    public void setMinWidth(int mMinWidth) {
        this.mMinWidth = mMinWidth;
        setMeasuredDimension(mMinWidth, getMinHeight());//设置View的宽高
    }

    public int getMinHeight() {
        return mMinHeight;
    }

    public void setMinHeight(int mMinHeight) {
        this.mMinHeight = mMinHeight;
        setMeasuredDimension(getMinHeight(), mMinHeight);//设置View的宽高
    }

    private void invalidateView() {
        if (mVisible) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                postInvalidateOnAnimation();
            } else {
                invalidate();
            }
        }
    }

    private void updateAnimationTime() {
        long now = SystemClock.uptimeMillis();
        // 如果第一帧，记录起始时间
        if (mMovieStart == 0) {
            mMovieStart = now;
        }
        // 取出动画的时长
        int dur = mMovie.duration();
        if (dur == 0) {
            dur = DEFAULT_MOVIE_DURATION;
        }
        // 算出需要显示第几帧
        mCurrentAnimationTime = (int) ((now - mMovieStart) % dur);
    }

    private void drawMovieFrame(Canvas canvas) {
        // 设置要显示的帧，绘制即可
        mMovie.setTime(mCurrentAnimationTime);
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.scale(mScale, mScale);
        mMovie.draw(canvas, mLeft / mScale, mTop / mScale);
        canvas.restore();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        mVisible = screenState == SCREEN_STATE_ON;
        invalidateView();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        mVisible = visibility == View.VISIBLE;
        invalidateView();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        mVisible = visibility == View.VISIBLE;
        invalidateView();
    }

    private void initPaint() {
        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
    }

    private void initBitmap(int resId) {
        if (mMovie == null) {
            mBitmap = BitmapFactory.decodeResource(getResources(), resId);
            if (mBitmap != null) {
                mSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
            }
            mDstRect = new Rect();
        }
    }

    static class DensityUtil {
        /**
         * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
         */
        public static int dip2px(Context context, float dpValue) {
            return (int) (dpValue * getScale(context) + 0.5f);
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        public static int px2dip(Context context, float pxValue) {
            return (int) (pxValue / getScale(context) + 0.5f);
        }

        private static float getScale(Context context) {
            float density = 0;
            if (context != null) {
                Resources res = context.getResources();
                if (res != null) {
                    DisplayMetrics metrics = res.getDisplayMetrics();
                    if (metrics != null) {
                        density = metrics.density;
                    }
                }
            }
            return density;
        }
    }
}