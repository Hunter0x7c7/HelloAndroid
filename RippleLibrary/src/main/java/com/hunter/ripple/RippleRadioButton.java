package com.hunter.ripple;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RadioButton;

/**
 * 波纹RadioButton
 *
 * @author Zihang Huang
 *         create date： 2016/9/8 10:06
 */
public class RippleRadioButton extends RadioButton {
    private int mInitX;
    private int mInitY;
    private float mCurrentX;
    private float mCurrentY;
    private float mRadius;
    private float mStepRadius;
    private float mStepOriginX;
    private float mStepOriginY;
    private float mDrawRadius;
    private boolean mDrawFinish;
    private float mCycle;
    private final Rect mRect = new Rect();
    private boolean mPressUp = false;
    private Paint mRevealPaint = new Paint(1);

    public RippleRadioButton(Context context) {
        super(context);
        this.initView();
    }

    public RippleRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView();
    }

    public RippleRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView();
    }

    @TargetApi(21)
    public RippleRadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.initView();
    }

    private void initView() {
        this.mRevealPaint.setColor(268435456);
        this.mCycle = 10.0F;
        float density = this.getResources().getDisplayMetrics().density;
        this.mCycle = density * this.mCycle;
        this.mDrawFinish = true;
    }

    protected void onDraw(Canvas canvas) {
        if (this.mDrawFinish) {
            super.onDraw(canvas);
        } else {
            canvas.drawColor(134217728);
            super.onDraw(canvas);
            if (this.mStepRadius != 0.0F) {
                this.mDrawRadius += this.mStepRadius;
                this.mCurrentX += this.mStepOriginX;
                this.mCurrentY += this.mStepOriginY;
                if (this.mDrawRadius > this.mRadius) {
                    this.mDrawRadius = 0.0F;
                    canvas.drawCircle((float) (this.mRect.width() / 2), (float) (this.mRect.height() / 2), this.mRadius, this.mRevealPaint);
                    this.mDrawFinish = true;
                    if (this.mPressUp) {
                        this.invalidate();
                    }

                } else {
                    canvas.drawCircle(this.mCurrentX, this.mCurrentY, this.mDrawRadius, this.mRevealPaint);
                    ViewCompat.postInvalidateOnAnimation(this);
                }
            }
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mRect.set(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
    }

    private void updateDrawData() {
        this.mRadius = (float) Math.sqrt((double) (this.mRect.width() / 2 * this.mRect.width() / 2 + this.mRect.height() / 2 * this.mRect.height() / 2));
        this.mStepRadius = this.mRadius / this.mCycle;
        this.mStepOriginX = (float) (this.mRect.width() / 2 - this.mInitX) / this.mCycle;
        this.mStepOriginY = (float) (this.mRect.height() / 2 - this.mInitY) / this.mCycle;
        this.mCurrentX = (float) this.mInitX;
        this.mCurrentY = (float) this.mInitY;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case 0:
                this.mPressUp = false;
                this.mDrawFinish = false;
                int eventX1 = MotionEventCompat.getActionIndex(event);
                int eventY1 = MotionEventCompat.getPointerId(event, eventX1);
                if (eventY1 != -1) {
                    this.mInitX = (int) MotionEventCompat.getX(event, eventX1);
                    this.mInitY = (int) MotionEventCompat.getY(event, eventX1);
                    this.updateDrawData();
                    this.invalidate();
                }
                break;
            case 1:
            case 3:
                this.actionUp();
                break;
            case 2:
                float eventX = event.getX();
                float eventY = event.getY();
                if (eventX < 0.0F || eventX > (float) (this.getRight() - this.getLeft()) || eventY < 0.0F || eventY > (float) (this.getBottom() - this.getTop())) {
                    this.actionUp();
                }
        }

        return super.onTouchEvent(event);
    }

    private void actionUp() {
        this.mStepRadius = (float) ((int) (5.0F * this.mStepRadius));
        this.mStepOriginX = (float) ((int) (5.0F * this.mStepOriginX));
        this.mStepOriginY = (float) ((int) (5.0F * this.mStepOriginY));
        this.mPressUp = true;
        this.invalidate();
    }

    public boolean performClick() {
        this.postDelayed(new Runnable() {
            public void run() {
                RippleRadioButton.super.performClick();
            }
        }, 150L);
        return true;
    }
}
