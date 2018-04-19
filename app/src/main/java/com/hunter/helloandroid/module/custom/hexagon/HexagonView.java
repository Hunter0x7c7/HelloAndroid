package com.hunter.helloandroid.module.custom.hexagon;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hunter.helloandroid.R;


/**
 * Created by zhangyuanlu on 2018/1/19.
 */

public class HexagonView extends View {
    private Context mContext;
    private Paint mPaint;
    private final float PADDING = 0f;
    private int mX, mY;
    private int mColor = Color.GREEN;
    private int mAlpha = 255;
    private Path mPath;
    private String mText;
    private int mTextColor = Color.BLACK;
    private float mTextSize = 16f;
    private onClickHVListener listener;

    public HexagonView(Context context) {
        super(context);
        init(context, null);
    }

    public HexagonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HexagonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setOnClickHVListener(onClickHVListener listener) {
        this.listener = listener;
    }

    public interface onClickHVListener {
        void onClick(int x, int y);
    }

    public int getMX() {
        return mX;
    }

    public int getMY() {
        return mY;
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HexagonView);
            mX = typedArray.getInt(R.styleable.HexagonView_x, 0);
            mY = typedArray.getInt(R.styleable.HexagonView_y, 0);
            mColor = typedArray.getColor(R.styleable.HexagonView_viewColor, mColor);
            mText = typedArray.getString(R.styleable.HexagonView_hvtext);
            mTextColor = typedArray.getColor(R.styleable.HexagonView_hvtextColor, mTextColor);
            mTextSize = typedArray.getDimension(R.styleable.HexagonView_hvtextSize, mTextSize);
            typedArray.recycle();
        }

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
        mPath = new Path();
    }

    private int measureSize(int measureSpec, int defaultSize) {
        int measureSize;
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY)
            measureSize = size;
        else if (mode == MeasureSpec.AT_MOST)
            measureSize = Math.min(size, defaultSize);
        else
            measureSize = defaultSize;
        return measureSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureSize(widthMeasureSpec, 300), measureSize(heightMeasureSpec, 300));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float cx = getWidth() / 2;
        float cy = getHeight() / 2;
        float length = cx - PADDING;
        float a = (float) Math.sqrt(3) * length / 2;  //邻边长度
        mPaint.setColor(mColor);
        mPaint.setAlpha(mAlpha);
        mPath.moveTo(PADDING, cy);
        //画一个正六边形
        mPath.lineTo(PADDING + length / 2f, cy - a);
        mPath.lineTo(3 / 2f * length + PADDING, cy - a);
        mPath.lineTo(cx + length, cy);
        mPath.lineTo(3 / 2f * length + PADDING, cy + a);
        mPath.lineTo(PADDING + length / 2f, cy + a);
        mPath.lineTo(PADDING, cy);
        canvas.drawPath(mPath, mPaint);
        if (mText != null) {
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setTextSize(dip2px(mContext, mTextSize));
            mPaint.setColor(mTextColor);
            //文字居中显示
            Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
            float baseline = cy + (fontMetricsInt.descent - fontMetricsInt.ascent) / 2 - fontMetricsInt.descent;
            canvas.drawText(mText, cx, baseline, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        Point point = new Point((int) event.getX(), (int) event.getY());
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                if (isInHexagon(mPath, point)) {
                    mAlpha = 150;
                    listener.onClick(getMX(), getMY());
                    invalidate();
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                mAlpha = 255;
                invalidate();
                break;
            }
            default:
                break;
        }
        return true;
    }

    /**
     * 判断点是否在边界内
     */
    private boolean isInHexagon(Path path, Point point) {
        RectF bounds = new RectF();
        path.computeBounds(bounds, true);
        Region region = new Region();
        region.setPath(path, new Region((int) bounds.left, (int) bounds.top,
                (int) bounds.right, (int) bounds.bottom));
        return region.contains(point.x, point.y);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


}