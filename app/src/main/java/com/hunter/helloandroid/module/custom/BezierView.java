package com.hunter.helloandroid.module.custom;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.module.update.util.StringUtil;
import com.hunter.helloandroid.util.TextUtil;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2018
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/5/23 16:21
 * <p>
 * 描    述：贝塞尔曲线View
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class BezierView extends View {
    private Paint mPaint;
    private Paint mPointPaint;
    private Path mPath = new Path();
    private int mStartX = 0;
    private int mStartY = 0;
    private int mStopX = 0;
    private int mStopY = 0;
    private int mLineColor = Color.WHITE;
    private int mPointColor = Color.BLACK;
    private String mPointName;
    private int mNameSize = 12;

    private double mStartPercentX, mStartPercentY, mStopPercentX, mStopPercentY;

    public BezierView(Context context) {
        super(context);
        init(null);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setNameSize(DensityUtil.dip2px(getContext(), mNameSize));

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(DensityUtil.dip2px(getContext(), 1));
        mPaint.setStyle(Paint.Style.STROKE);

        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FlyLine);
            mStartX = typedArray.getDimensionPixelSize(R.styleable.FlyLine_startX, mStartX);
            mStartY = typedArray.getDimensionPixelSize(R.styleable.FlyLine_startY, mStartY);
            mStopX = typedArray.getDimensionPixelSize(R.styleable.FlyLine_stopX, mStopX);
            mStopY = typedArray.getDimensionPixelSize(R.styleable.FlyLine_stopY, mStopY);

            String startPoint = typedArray.getString(R.styleable.FlyLine_startPoint);
            String stopPoint = typedArray.getString(R.styleable.FlyLine_stopPoint);

            mLineColor = typedArray.getColor(R.styleable.FlyLine_lineColor, mLineColor);
            mPointColor = typedArray.getColor(R.styleable.FlyLine_pointColor, mPointColor);

            String pointName = typedArray.getString(R.styleable.FlyLine_pointName);
            int nameSize = typedArray.getDimensionPixelSize(R.styleable.FlyLine_nameSize, -1);

            String[] startPointArray = StringUtil.splitString(startPoint, ",");
            String[] stopPointArray = StringUtil.splitString(stopPoint, ",");

            if (startPointArray != null && startPointArray.length >= 2) {
                double startPercentX = getPercent(startPointArray[0]);
                double startPercentY = getPercent(startPointArray[1]);
                setStartPercentX(startPercentX);
                setStartPercentY(startPercentY);
            }
            if (stopPointArray != null && stopPointArray.length >= 2) {
                double stopPercentX = getPercent(stopPointArray[0]);
                double stopPercentY = getPercent(stopPointArray[1]);
                setStopPercentX(stopPercentX);
                setStopPercentY(stopPercentY);
            }
            if (!TextUtils.isEmpty(pointName)) {
                setPointName(pointName);
            }
            if (nameSize > 0) {
                setNameSize(nameSize);
            }
            typedArray.recycle();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        // 宽度模式
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        // 测量宽度
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        // 高度模式
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        // 测量高度
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//
//        int minimumWidth = getMinimumWidth();
//        int minimumHeight = getMinimumHeight();
//        // 如果宽高都是包裹内容
//        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(minimumWidth, minimumHeight);
//        } else if (widthMode == MeasureSpec.AT_MOST) {
//            // 宽度为所有子View宽度相加，高度为测量高度
//            setMeasuredDimension(minimumWidth, heightSize);
//        } else if (heightMode == MeasureSpec.AT_MOST) {
//            // 宽度为测量宽度，高度为子view最大高度
//            setMeasuredDimension(widthSize, minimumHeight);
//        } else {
//            setMeasuredDimension(widthSize, heightSize);
//        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public int getMinimumWidth() {
        PointF bezierSize = getBezierSize(getStartPoint(), getStopPoint());
        return (int) bezierSize.x;
    }

    @Override
    public int getMinimumHeight() {
        PointF bezierSize = getBezierSize(getStartPoint(), getStopPoint());
        return (int) bezierSize.y;
    }

    private double getPercent(String percent) {
        double result = 0;
        if (percent != null && percent.contains("%")) {
            try {
                result = Double.parseDouble(percent.replace("%", ""));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return (result / 100L) % 1;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        mPath = initPath(w, h);

        mPath = getPath(getStartPoint(), getStopPoint());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(getLineColor());
        mPointPaint.setColor(getPointColor());
        mPointPaint.setTextSize(getNameSize());


        //绘制贝尔曲线
        canvas.drawPath(mPath, mPaint);


        PointF startPoint = getStartPoint();
        PointF stopPoint = getStopPoint();
        String pointName = getPointName();

        if (startPoint == null || stopPoint == null) {
            return;
        }
        //绘制终点圆点
        int radius = DensityUtil.dip2px(getContext(), 4);
        canvas.drawCircle(stopPoint.x, stopPoint.y, radius, mPointPaint);
        if (!TextUtils.isEmpty(pointName)) {
            int horizontal = TextUtil.getCenterHorizontal(radius, mPointPaint);
            canvas.drawText(pointName, stopPoint.x + radius * 2, stopPoint.y + horizontal - radius / 2, mPointPaint);
        }
        //计算宽高
        PointF size = getBezierSize(startPoint, stopPoint);
        float width = size.x;
        float height = size.y;

        //起点终点矩形
        mPaint.setColor(Color.GRAY);
        float left = Math.min(startPoint.x, stopPoint.x);
        float top = Math.min(startPoint.y, stopPoint.y);
//        canvas.drawRect(left, top, left + width, top + height, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawLine(startPoint.x, startPoint.y, stopPoint.x, stopPoint.y, mPaint);

//        float width2 = left + width / 2;
//        canvas.drawLine(width2, top, width2, top + height, mPaint);


        System.out.println("............size:" + size);
        //计算斜边
        double hypotenuse = getHypotenuse(width, height);

        double hypotenuseHalf = hypotenuse / 2;
        double radiuss = hypotenuse / 2 / 3;

        double sinAwidth = width / hypotenuse;
        double tanA = radiuss / hypotenuseHalf;

        float degress = (float) Math.toDegrees(Math.asin(sinAwidth) - Math.atan(tanA));
        double hypotenuse2 = getHypotenuse(hypotenuseHalf, radiuss);

        double newXa = Math.sin(degress) * hypotenuse2;
        double newYb = Math.cos(degress) * hypotenuse2;

        PointF contorlPoint = getContorlPoint(startPoint, stopPoint);

        mPaint.setColor(Color.MAGENTA);
        canvas.drawCircle(contorlPoint.x, contorlPoint.y, DensityUtil.dip2px(getContext(), 2), mPaint);

        float x0 = left + width / 2;
        float y0 = top + height / 2;
        float r = (float) hypotenuseHalf;
//        canvas.drawCircle(x0, y0, r, mPaint);
//        canvas.drawCircle(x0, y0, (float) radiuss, mPaint);
        canvas.drawLine(contorlPoint.x, contorlPoint.y, x0, y0, mPaint);


//        double ao = Math.toDegrees(Math.asin(sinAwidth));
//        String text = "degress:" + degress + " start:" + startPoint.x + "," + startPoint.y
//                + " stop:" + stopPoint.x + "," + stopPoint.y + "  "
//                + (int) newXa + "," + (int) newYb
//                + " " + ao;
//
//
//        mPaint.setColor(Color.BLUE);
//        canvas.drawText(text, DensityUtil.dip2px(getContext(), 10), DensityUtil.dip2px(getContext(), 10), mPaint);
    }

    /**
     * 获取圆上任一点
     *
     * @param center 圆点坐标
     * @param radius 半径
     * @param degree 角度
     * @return 返回圆上任一点的坐标
     */
    public static PointF getCirclePoint(PointF center, double radius, double degree) {
        float x = (float) (center.x + radius * Math.cos(degree * Math.PI / 180));
        float y = (float) (center.y + radius * Math.sin(degree * Math.PI / 180));
        return new PointF(x, y);
    }

    /**
     * 计算斜边
     */
    public static double getHypotenuse(double x, double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public Path initPath(int width, int height) {
        float pointX1 = width / 2;
        int pointY1 = height * 2 - DensityUtil.dip2px(getContext(), 2);

        Path path = new Path();
        path.moveTo(0, 0);
        path.quadTo(pointX1, pointY1, width, 0);

        return path;
    }

    public PointF getBezierSize(PointF point1, PointF point2) {
        PointF result = new PointF();
        if (point1 != null && point2 != null) {
            float width = (float) Math.abs(Math.ceil(point1.x - point2.x));
            float height = (float) Math.abs(Math.ceil(point1.y - point2.y));
            result.set(width, height);
        }
        return result;
    }


    public Path getPath(PointF point1, PointF point2) {
        Path path = new Path();
        if (point1 != null && point2 != null) {
            PointF contorlPoint = getContorlPoint(point1, point2);

            path.moveTo(point1.x, point1.y);
            path.quadTo(contorlPoint.x, contorlPoint.y, point2.x, point2.y);
        }
        return path;
    }

    public Path getPath() {
        return mPath;
    }


    private PointF getContorlPoint(PointF startPoint, PointF stopPoint) {
        PointF result = new PointF();
        if (startPoint != null && stopPoint != null) {

            //计算宽高
            PointF size = getBezierSize(startPoint, stopPoint);
            float width = size.x;
            float height = size.y;
            //计算斜边
            double hypotenuse = getHypotenuse(width, height);
            //计算中心点坐标
            float left = Math.min(startPoint.x, stopPoint.x);
            float top = Math.min(startPoint.y, stopPoint.y);
            float centerX = left + width / 2;
            float centerY = top + height / 2;
            //半径
            double radius = hypotenuse / 2 / 3;
            //计算角度
            double sinAwidth = width / hypotenuse;
            double degree = Math.toDegrees(Math.asin(sinAwidth));
            if (startPoint.x < stopPoint.x && startPoint.y < stopPoint.y) {
                degree = 360 - degree;
            } else if (startPoint.x < stopPoint.x && startPoint.y > stopPoint.y) {
                degree = 180 + degree;
            } else if (startPoint.x > stopPoint.x && startPoint.y < stopPoint.y) {
                degree = degree;
            } else if (startPoint.x > stopPoint.x && startPoint.y > stopPoint.y) {
                degree = 180 - degree;
            }
            //获取圆上任一点
            PointF circlePoint = getCirclePoint(new PointF(centerX, centerY), radius, degree);
            result.set(circlePoint);
        }
        return result;
    }

    private PointF getContorlPoint2(PointF startPoint, PointF stopPoint) {
        //计算宽高
        PointF size = getBezierSize(startPoint, stopPoint);
        float width = size.x;
        float height = size.y;
        System.out.println("............size:" + size);
        //计算斜边
        double hypotenuse = getHypotenuse(width, height);
        //计算控制点的斜边
        double hypotenuse2 = getHypotenuse(hypotenuse / 2, hypotenuse / 4);
        //计算新的X轴
        double newX = hypotenuse2 / Math.pow(hypotenuse / 4, 2);//pow次方
        double newY = hypotenuse2 / hypotenuse / 2 * hypotenuse / 4;
        //控制点坐标
        double contorlX = width - newX;
        double contorlY = height + newY;

        return new PointF((float) contorlX, (float) contorlY);
    }


    private PointF mStartPoint, mStopPoint;

    public void setStartPoint(PointF point) {
        mStartPoint = point;
    }

    public void setStopPoint(PointF point) {
        mStopPoint = point;
    }

    public PointF getStartPoint() {
        return mStartPoint;
    }

    public PointF getStopPoint() {
        return mStopPoint;
    }

    public PointF getStartPoint(float width, float height) {
        return new PointF((float) (width * mStartPercentX), (float) (height * mStartPercentY));
    }

    public PointF getStopPoint(float width, float height) {
        return new PointF((float) (width * mStopPercentX), (float) (height * mStopPercentY));
    }

    public int getLineColor() {
        return mLineColor;
    }

    public void setLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
        invalidate();
    }

    public int getPointColor() {
        return mPointColor;
    }

    public void setPointColor(int mPointColor) {
        this.mPointColor = mPointColor;
        invalidate();
    }

    public String getPointName() {
        return mPointName;
    }

    public void setPointName(String mPointName) {
        this.mPointName = mPointName;
        invalidate();
    }

    public int getNameSize() {
        return mNameSize;
    }

    public void setNameSize(int mNameSize) {
        this.mNameSize = mNameSize;
        invalidate();
    }


    public double getStartPercentX() {
        return mStartPercentX;
    }

    public void setStartPercentX(double mStartPercentX) {
        this.mStartPercentX = mStartPercentX;
    }

    public double getStartPercentY() {
        return mStartPercentY;
    }

    public void setStartPercentY(double mStartPercentY) {
        this.mStartPercentY = mStartPercentY;
    }

    public double getStopPercentX() {
        return mStopPercentX;
    }

    public void setStopPercentX(double mStopPercentX) {
        this.mStopPercentX = mStopPercentX;
    }

    public double getStopPercentY() {
        return mStopPercentY;
    }

    public void setStopPercentY(double mStopPercentY) {
        this.mStopPercentY = mStopPercentY;
    }

    public static class DensityUtil {
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

    public boolean isTouchPoint(PointF point) {
        int dip20 = DensityUtil.dip2px(getContext(), 20);
        PointF stopPoint = getStopPoint();
        RectF rect = new RectF(stopPoint.x - dip20, stopPoint.y - dip20, stopPoint.x + dip20, stopPoint.y + dip20);
        return rect.contains(point.x, point.y);
    }

}
