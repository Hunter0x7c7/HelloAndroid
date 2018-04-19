package com.hunter.helloandroid.module.custom.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2018
 * <p>
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/4/10 17:49
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class PathView extends View {

    private Paint paint;
    private Path path;

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        paint = new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        //防抖动
        paint.setDither(true);
        //设置画笔未实心
        paint.setStyle(Paint.Style.STROKE);
        //设置颜色
        paint.setColor(Color.parseColor("#EBBD21"));
        //设置画笔宽度
        paint.setStrokeWidth(5);
        //绘制长度为4的实线后再绘制长度为4的空白区域，0位间隔
        paint.setPathEffect(new DashPathEffect(new float[]{5, 10}, 0));
//        paint.setPathEffect(new CornerPathEffect(2.5f));

        //设置画笔着色器
//        Bitmap fieldBitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
//        Shader mShader = new BitmapShader(fieldBitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
//        paint.setShader(mShader);

        path = new Path();
        path.moveTo(60, 60);
        path.lineTo(460, 460);
        path.quadTo(660, 260, 860, 460);
        path.cubicTo(160, 660, 960, 1060, 260, 1260);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);

    }

    public Path getPath() {
        return path;
    }

    /**
     * 判断点是否在边界内 HexagonView
     */
    public boolean isInHexagon(Path path, Point point) {
        RectF bounds = new RectF();
        path.computeBounds(bounds, true);
        Region region = new Region();
        region.setPath(path, new Region((int) bounds.left, (int) bounds.top,
                (int) bounds.right, (int) bounds.bottom));
        return region.contains(point.x, point.y);
    }

}
