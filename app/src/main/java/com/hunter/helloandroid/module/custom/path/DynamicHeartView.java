package com.hunter.helloandroid.module.custom.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * 动态心型动画
 */
public class DynamicHeartView extends View {

    private static final String TAG = "DynamicHeartView";
    private static final int PATH_WIDTH = 2;
    // 起始点
    private static final int[] START_POINT = new int[]{
            200, 105
    };
    // 爱心下端点
    private static final int[] BOTTOM_POINT = new int[]{
            200, 225
    };
    // 左侧控制点
    private static final int[] LEFT_CONTROL_POINT = new int[]{
            350, 60
    };
    // 右侧控制点
    private static final int[] RIGHT_CONTROL_POINT = new int[]{
            50, 60
    };

    private PathMeasure mPathMeasure;
    private Paint mPaint, mTargetPaint;
    private Path mPath;
    private float[] mTargetPosition = new float[2];

    public DynamicHeartView(Context context) {
        super(context);
        init();
    }

    public DynamicHeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DynamicHeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(PATH_WIDTH);
        mPaint.setColor(Color.RED);
        //绘制长度为4的实线后再绘制长度为4的空白区域，0位间隔
        mPaint.setPathEffect(new DashPathEffect(new float[]{2, 1}, 0));

        mTargetPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTargetPaint.setStyle(Paint.Style.FILL);
        mTargetPaint.setStrokeWidth(PATH_WIDTH);
        mTargetPaint.setColor(Color.BLUE);

        mPath = new Path();
        mPath.moveTo(START_POINT[0], START_POINT[1]);
        mPath.quadTo(RIGHT_CONTROL_POINT[0], RIGHT_CONTROL_POINT[1], BOTTOM_POINT[0],
                BOTTOM_POINT[1]);
        mPath.quadTo(LEFT_CONTROL_POINT[0], LEFT_CONTROL_POINT[1], START_POINT[0], START_POINT[1]);

        mPathMeasure = new PathMeasure(mPath, true);
        mTargetPosition = new float[2];
        mTargetPosition[0] = START_POINT[0];
        mTargetPosition[1] = START_POINT[1];

        startPathAnim(3000);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);//分别拿到宽高的大小和测量模式

        //最终宽度高度
        int finalWidth, finalHeight;
        //默认(最小)宽度高度
        int minWidth = 400, minHeight = 280;
        if (widthMode == MeasureSpec.EXACTLY) {
            //如果是指定大小或填充窗体（以后直接说成指定大小）,直接设置最终宽度
            finalWidth = widthSize;
        } else {
            //如果是包裹内容 最终宽度等于最小宽度加 左右Padding
            finalWidth = minWidth + getPaddingLeft() + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            finalHeight = heightSize;//同上
        } else {
            //如果是包裹内容 最终高度等于最小宽度加 上下Padding
            finalHeight = getPaddingTop() + getPaddingBottom() + minHeight;
        }

        setMeasuredDimension(finalWidth, finalHeight);//设置View的宽高，测量结束
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawPath(mPath, mPaint);

        canvas.drawCircle(RIGHT_CONTROL_POINT[0], RIGHT_CONTROL_POINT[1], 5, mPaint);
        canvas.drawCircle(LEFT_CONTROL_POINT[0], LEFT_CONTROL_POINT[1], 5, mPaint);

        // 绘制对应目标
        canvas.drawCircle(mTargetPosition[0], mTargetPosition[1], 12, mTargetPaint);
    }

    // 开启路径动画
    public void startPathAnim(long duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(duration);
        // 减速插值器
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mTargetPosition
                mPathMeasure.getPosTan(value, mTargetPosition, null);
                postInvalidate();

                //得到Path中的某一点或某一段
//                getPosTan(float distance, float[] pos, float[] tan)
//                getSegment(float startD, float stopD, Path dst, boolean startWithMoveTo)
            }
        });
        valueAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        startPathAnim(3000);
        return super.onTouchEvent(event);
    }

}
