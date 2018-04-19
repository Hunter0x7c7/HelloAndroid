package com.hunter.helloandroid.module.win_manager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class StarrySkyView extends View {

    public StarrySkyView(Context context) {
        super(context);
        init();
    }

    public StarrySkyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StarrySkyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
//        initStarrySky(getWidth(), getHeight(), 10);
    }

    private boolean isDraw = false;
    private int count = 0;

    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint mPaint = new Paint();  //定义一个Paint
        int[] colors = {Color.RED, Color.GREEN, Color.BLUE};
        float[] stops = {0.0025f, 0.3f, 1.0f};

//新建一个线性渐变，前两个参数是渐变开始的点坐标，第三四个参数是渐变结束的点的坐标。连接这2个点就拉出一条渐变线了，玩过PS的都懂。然后那个数组是渐变的颜色。下一个参数是渐变颜色的分布，如果为空，每个颜色就是均匀分布的。最后是模式，这里设置的是循环渐变
//        Shader mShader = new LinearGradient(0, 0, 40, 60, ints, null, Shader.TileMode.REPEAT);

        float centerX = (float) getWidth() / 2;
        float centerY = (float) getHeight() / 2;
        float min = Math.min(centerX, centerY);
        Shader radialGradient = new RadialGradient(centerX, centerY, min, colors, stops, Shader.TileMode.CLAMP);
        radialGradient = new RadialGradient(centerX, centerY, min, 0x00FFFFFF, 0xFF58FAAC, Shader.TileMode.CLAMP);
        mPaint.setShader(radialGradient);
        canvas.drawPaint(mPaint);

        if (true) {
            return;
        }

        count++;
        if (count > 20) {
//        if (isDraw) {
            return;
        }
        if (ssbList != null && ssbList.size() > 0) {
            for (StarrySkyBean ssb : ssbList) {
                double x = Math.sin(ssb.timePassed) * ssb.orbitRadius + ssb.orbitX;
                double y = Math.cos(ssb.timePassed) * ssb.orbitRadius + ssb.orbitY;
                double twinkle = random(10);

                if (twinkle == 1 && ssb.alpha > 0) {
                    ssb.alpha -= 0.05;
                } else if (twinkle == 2 && ssb.alpha < 1) {
                    ssb.alpha += 0.05;
                }

//            ctx.globalAlpha = this.alpha;
                double radius = ssb.radius;
                double xVuale = x - radius / 2;
                double yVuale = y - radius / 2;
                double width = radius, height = radius;
//            ctx.drawImage(canvas2, xVuale, yVuale, width, height);
                ssb.timePassed += ssb.speed;

                Paint paint = new Paint();  //定义一个Paint

//新建一个线性渐变，前两个参数是渐变开始的点坐标，第三四个参数是渐变结束的点的坐标。连接这2个点就拉出一条渐变线了，玩过PS的都懂。然后那个数组是渐变的颜色。下一个参数是渐变颜色的分布，如果为空，每个颜色就是均匀分布的。最后是模式，这里设置的是循环渐变
                Shader mShader = new LinearGradient(0, 0, 40, 60, colors, null, Shader.TileMode.REPEAT);

                Shader mRadialGradient = new RadialGradient((float) xVuale, (float) yVuale, (float) radius, colors, stops, Shader.TileMode.REPEAT);
                paint.setShader(mRadialGradient);
                canvas.drawPaint(paint);
            }
        }
        isDraw = true;
    }


    /**
     * 取一个随机数
     */
    public double random(long max) {
        return random(0, max);
    }

    /**
     * 取一个随机数
     */
    public double random(long min, long max) {
        if (min > max) {
            long hold = max;
            max = min;
            min = hold;
        }
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }

    /**
     * 最大的轨道
     */
    public long maxOrbit(int x, int y) {
        int max = Math.max(x, y);
        long diameter = Math.round(Math.sqrt(max * max + max * max));
        return diameter / 2;
    }

    private List<StarrySkyBean> ssbList = new ArrayList<>();

    /**
     * 初始化
     */
    void initStarrySky(int width, int height, int maxStars) {

        for (int i = 0; i < maxStars; i++) {
            double orbitRadius = random(maxOrbit(width + 20, height + 10));
            double radius = random(60, (int) orbitRadius) / 12;
            int orbitX = width / 2;
            int orbitY = height / 2;
            double timePassed = random(0, maxStars);
            double speed = random((int) orbitRadius) / 900000;
            double alpha = random(2, 10) / 10;
            ssbList.add(new StarrySkyBean(orbitRadius, radius, orbitX, orbitY, timePassed, speed, alpha));
        }

        System.out.println("........ssb.size:" + ssbList.size());
    }

    public class StarrySkyBean {
        private double orbitRadius;
        private double radius;
        private int orbitX;
        private int orbitY;
        private double timePassed;
        private double speed;
        private double alpha;

        public StarrySkyBean(double orbitRadius, double radius, int orbitX, int orbitY, double timePassed, double speed, double alpha) {
            this.orbitRadius = orbitRadius;
            this.radius = radius;
            this.orbitX = orbitX;
            this.orbitY = orbitY;
            this.timePassed = timePassed;
            this.speed = speed;
            this.alpha = alpha;
        }
    }
}
