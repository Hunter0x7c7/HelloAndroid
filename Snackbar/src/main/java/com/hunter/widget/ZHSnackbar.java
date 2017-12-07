package com.hunter.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.WindowInsetsCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.hunter.widget.AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;

/**
 * 可顶部和底部显示的Snackbar
 *
 * @author Zihang Huang
 *         create date： 2017/6/20 9:40
 */
public class ZHSnackbar {
    /**
     * Callback class for {@link Snackbar} instances.
     *
     * @see Snackbar#setCallback(Snackbar.Callback)
     */
    public static abstract class Callback {
        /**
         * Indicates that the Snackbar was dismissed via a swipe.
         */
        public static final int DISMISS_EVENT_SWIPE = 0;
        /**
         * Indicates that the Snackbar was dismissed via an action click.
         */
        public static final int DISMISS_EVENT_ACTION = 1;
        /**
         * Indicates that the Snackbar was dismissed via a timeout.
         */
        public static final int DISMISS_EVENT_TIMEOUT = 2;
        /**
         * Indicates that the Snackbar was dismissed via a call to {@link #dismiss()}.
         */
        public static final int DISMISS_EVENT_MANUAL = 3;
        /**
         * Indicates that the Snackbar was dismissed from a new Snackbar being shown.
         */
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;

        /**
         * @hide
         */
        @IntDef({DISMISS_EVENT_SWIPE, DISMISS_EVENT_ACTION, DISMISS_EVENT_TIMEOUT,
                DISMISS_EVENT_MANUAL, DISMISS_EVENT_CONSECUTIVE})
        @Retention(RetentionPolicy.SOURCE)
        public @interface DismissEvent {
        }

        /**
         * Called when the given {@link Snackbar} has been dismissed, either through a time-out,
         * having been manually dismissed, or an action being clicked.
         *
         * @param snackbar The snackbar which has been dismissed.
         * @param event    The event which caused the dismissal. One of either:
         *                 {@link #DISMISS_EVENT_SWIPE}, {@link #DISMISS_EVENT_ACTION},
         *                 {@link #DISMISS_EVENT_TIMEOUT}, {@link #DISMISS_EVENT_MANUAL} or
         *                 {@link #DISMISS_EVENT_CONSECUTIVE}.
         * @see Snackbar#dismiss()
         */
        public void onDismissed(ZHSnackbar snackbar, @Snackbar.Callback.DismissEvent int event) {
            // empty  
        }

        /**
         * Called when the given {@link Snackbar} is visible.
         *
         * @param snackbar The snackbar which is now visible.
         * @see Snackbar#show()
         */
        public void onShown(ZHSnackbar snackbar) {
            // empty  
        }
    }

    /**
     * @hide
     */
    @IntDef({LENGTH_INDEFINITE, LENGTH_SHORT, LENGTH_LONG})
    @IntRange(from = 1)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    /**
     * Show the Snackbar indefinitely. This means that the Snackbar will be displayed from the time
     * that is {@link #show() shown} until either it is dismissed, or another Snackbar is shown.
     *
     * @see #setDuration
     */
    public static final int LENGTH_INDEFINITE = -2;

    /**
     * Show the Snackbar for a short period of time.
     *
     * @see #setDuration
     */
    public static final int LENGTH_SHORT = -1;

    /**
     * Show the Snackbar for a long period of time.
     *
     * @see #setDuration
     */
    public static final int LENGTH_LONG = 0;

    static final int ANIMATION_DURATION = 250;
    static final int ANIMATION_FADE_DURATION = 180;

    static final Handler sHandler;
    static final int MSG_SHOW = 0;
    static final int MSG_DISMISS = 1;

    static {
        sHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case MSG_SHOW:
                        ((ZHSnackbar) message.obj).showView();
                        return true;
                    case MSG_DISMISS:
                        ((ZHSnackbar) message.obj).hideView(message.arg1);
                        return true;
                }
                return false;
            }
        });
    }

    private final ViewGroup mTargetParent;
    private final Context mContext;
    final ZHSnackbar.SnackbarLayout mView;
    private int mDuration;
    private ZHSnackbar.Callback mCallback;
    private int mGravity = 0;
    private final AccessibilityManager mAccessibilityManager;

    private ZHSnackbar(ViewGroup parent, int gravity) {
        mTargetParent = parent;
        mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (gravity == 0) {
            mView = (ZHSnackbar.SnackbarLayout) inflater.inflate(
                    R.layout.layout_snackbar_top, mTargetParent, false);
        } else {
            mView = (ZHSnackbar.SnackbarLayout) inflater.inflate(
                    R.layout.layout_snackbar_bottom, mTargetParent, false);
        }

        mAccessibilityManager = (AccessibilityManager)
                mContext.getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    /**
     * Make a Snackbar to display a message
     * <p>
     * <p>Snackbar will try and find a parent view to hold Snackbar's view from the value given
     * to {@code view}. Snackbar will walk up the view tree trying to find a suitable parent,
     * which is defined as a {@link CoordinatorLayout} or the window decor's content view,
     * whichever comes first.
     * <p>
     * <p>Having a {@link CoordinatorLayout} in your view hierarchy allows Snackbar to enable
     * certain features, such as swipe-to-dismiss and automatically moving of widgets like
     * {@link FloatingActionButton}.
     *
     * @param view     The view to find a parent from.
     * @param text     The text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link #LENGTH_SHORT} or {@link
     *                 #LENGTH_LONG}
     */
    public static ZHSnackbar make(@NonNull View view, @NonNull CharSequence text,
                                  @ZHSnackbar.Duration int duration, int gravity) {
        ZHSnackbar snackbar = new ZHSnackbar(findSuitableParent(view), gravity);
        snackbar.setText(text);
        snackbar.setDuration(duration);
        snackbar.setGravity(gravity);
        return snackbar;
    }

    /**
     * Make a Snackbar to display a message.
     * <p>
     * <p>Snackbar will try and find a parent view to hold Snackbar's view from the value given
     * to {@code view}. Snackbar will walk up the view tree trying to find a suitable parent,
     * which is defined as a {@link CoordinatorLayout} or the window decor's content view,
     * whichever comes first.
     * <p>
     * <p>Having a {@link CoordinatorLayout} in your view hierarchy allows Snackbar to enable
     * certain features, such as swipe-to-dismiss and automatically moving of widgets like
     * {@link FloatingActionButton}.
     *
     * @param view     The view to find a parent from.
     * @param resId    The resource id of the string resource to use. Can be formatted text.
     * @param duration How long to display the message.  Either {@link #LENGTH_SHORT} or {@link
     *                 #LENGTH_LONG}
     */
    public static ZHSnackbar make(@NonNull View view, @StringRes int resId, @ZHSnackbar.Duration int duration, int gravity) {
        return make(view, view.getResources().getText(resId), duration, gravity);
    }

    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        do {
            if (view instanceof CoordinatorLayout) {
                // We've found a CoordinatorLayout, use it
                return (ViewGroup) view;
            } else if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    // If we've hit the decor content view, then we didn't find a CoL in the
                    // hierarchy, so use it.
                    return (ViewGroup) view;
                } else {
                    // It's not the content view but we'll use it as our fallback
                    fallback = (ViewGroup) view;
                }
            }

            if (view != null) {
                // Else, we will loop and crawl up the view hierarchy and try to find a parent
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);

        // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
        return fallback;
    }

    /**
     * Set the action to be displayed in this {@link Snackbar}.
     *
     * @param resId    String resource to display
     * @param listener callback to be invoked when the action is clicked
     */
    @NonNull
    public ZHSnackbar setAction(@StringRes int resId, View.OnClickListener listener) {
        return setAction(mContext.getText(resId), listener);
    }

    /**
     * Set the action to be displayed in this {@link Snackbar}.
     *
     * @param text     Text to display
     * @param listener callback to be invoked when the action is clicked
     */
    @NonNull
    public ZHSnackbar setAction(CharSequence text, final View.OnClickListener listener) {
        final TextView tv = mView.getActionView();

        if (TextUtils.isEmpty(text) || listener == null) {
            tv.setVisibility(View.GONE);
            tv.setOnClickListener(null);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                    // Now dismiss the Snackbar
                    dispatchDismiss(Snackbar.Callback.DISMISS_EVENT_ACTION);
                }
            });
        }
        return this;
    }

    /**
     * Sets the text color of the action specified in
     * {@link #setAction(CharSequence, View.OnClickListener)}.
     */
    @NonNull
    public ZHSnackbar setActionTextColor(ColorStateList colors) {
        final TextView tv = mView.getActionView();
        tv.setTextColor(colors);
        return this;
    }

    /**
     * Sets the text color of the action specified in
     * {@link #setAction(CharSequence, View.OnClickListener)}.
     */
    @NonNull
    public ZHSnackbar setActionTextColor(@ColorInt int color) {
        final TextView tv = mView.getActionView();
        tv.setTextColor(color);
        return this;
    }

    /**
     * Update the text in this {@link ZHSnackbar}.
     *
     * @param message The new text for the Toast.
     */
    @NonNull
    public ZHSnackbar setText(@NonNull CharSequence message) {
        final TextView tv = mView.getMessageView();
        tv.setText(message);
        return this;
    }

    /**
     * Update the text in this {@link ZHSnackbar}.
     *
     * @param resId The new text for the Toast.
     */
    @NonNull
    public ZHSnackbar setText(@StringRes int resId) {
        return setText(mContext.getText(resId));
    }

    /**
     * Set how long to show the view for.
     *
     * @param duration either be one of the predefined lengths:
     *                 {@link #LENGTH_SHORT}, {@link #LENGTH_LONG}, or a custom duration
     *                 in milliseconds.
     */
    @NonNull
    public ZHSnackbar setDuration(@ZHSnackbar.Duration int duration) {
        mDuration = duration;
        return this;
    }

    @NonNull
    public ZHSnackbar setGravity(int gravity) {
        mGravity = gravity;
        return this;
    }

    @NonNull


    /**
     * Return the duration.
     *
     * @see #setDuration
     */
    @ZHSnackbar.Duration
    public int getDuration() {
        return mDuration;
    }

    /**
     * Returns the {@link ZHSnackbar}'s view.
     */
    @NonNull
    public View getView() {
        return mView;
    }

    /**
     * Show the {@link ZHSnackbar}.
     */
    public void show() {
        SnackbarManager.getInstance().show(mDuration, mManagerCallback);
    }

    /**
     * Dismiss the {@link Snackbar}.
     */
    public void dismiss() {
        dispatchDismiss(Snackbar.Callback.DISMISS_EVENT_MANUAL);
    }

    void dispatchDismiss(@Snackbar.Callback.DismissEvent int event) {
        SnackbarManager.getInstance().dismiss(mManagerCallback, event);
    }

    /**
     * Set a callback to be called when this the visibility of this {@link Snackbar} changes.
     */
    @NonNull
    public ZHSnackbar setCallback(ZHSnackbar.Callback callback) {
        mCallback = callback;
        return this;
    }

    /**
     * Return whether this {@link Snackbar} is currently being shown.
     */
    public boolean isShown() {
        return SnackbarManager.getInstance().isCurrent(mManagerCallback);
    }

    /**
     * Returns whether this {@link Snackbar} is currently being shown, or is queued to be
     * shown next.
     */
    public boolean isShownOrQueued() {
        return SnackbarManager.getInstance().isCurrentOrNext(mManagerCallback);
    }

    final SnackbarManager.Callback mManagerCallback = new SnackbarManager.Callback() {
        @Override
        public void show() {
            sHandler.sendMessage(sHandler.obtainMessage(MSG_SHOW, ZHSnackbar.this));
        }

        @Override
        public void dismiss(int event) {
            sHandler.sendMessage(sHandler.obtainMessage(MSG_DISMISS, event, 0, ZHSnackbar.this));
        }
    };

    final void showView() {
        if (mView.getParent() == null) {
            final ViewGroup.LayoutParams lp = mView.getLayoutParams();

            if (lp instanceof CoordinatorLayout.LayoutParams) {
                // If our LayoutParams are from a CoordinatorLayout, we'll setup our Behavior
                final CoordinatorLayout.LayoutParams clp = (CoordinatorLayout.LayoutParams) lp;

                final ZHSnackbar.Behavior behavior = new ZHSnackbar.Behavior();
                behavior.setStartAlphaSwipeDistance(0.1f);
                behavior.setEndAlphaSwipeDistance(0.6f);
                behavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END);
                behavior.setListener(new SwipeDismissBehavior.OnDismissListener() {
                    @Override
                    public void onDismiss(View view) {
                        view.setVisibility(View.GONE);
                        dispatchDismiss(Snackbar.Callback.DISMISS_EVENT_SWIPE);
                    }

                    @Override
                    public void onDragStateChanged(int state) {
                        switch (state) {
                            case SwipeDismissBehavior.STATE_DRAGGING:
                            case SwipeDismissBehavior.STATE_SETTLING:
                                // If the view is being dragged or settling, cancel the timeout
                                SnackbarManager.getInstance().cancelTimeout(mManagerCallback);
                                break;
                            case SwipeDismissBehavior.STATE_IDLE:
                                // If the view has been released and is idle, restore the timeout
                                SnackbarManager.getInstance().restoreTimeout(mManagerCallback);
                                break;
                        }
                    }
                });
                clp.setBehavior(behavior);
                // Also set the inset edge so that views can dodge the snackbar correctly
                clp.insetEdge = Gravity.BOTTOM;
            }

            mTargetParent.addView(mView);
        }

        mView.setOnAttachStateChangeListener(new ZHSnackbar.SnackbarLayout.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                if (isShownOrQueued()) {
                    // If we haven't already been dismissed then this event is coming from a
                    // non-user initiated action. Hence we need to make sure that we callback
                    // and keep our state up to date. We need to post the call since removeView()
                    // will call through to onDetachedFromWindow and thus overflow.
                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            onViewHidden(Snackbar.Callback.DISMISS_EVENT_MANUAL);
                        }
                    });
                }
            }
        });

        if (ViewCompat.isLaidOut(mView)) {
            if (shouldAnimate()) {
                // If animations are enabled, animate it in
                if (mGravity == 0) {
                    animateViewInTop();
                } else {
                    animateViewInBottom();
                }
            } else {
                // Else if anims are disabled just call back now
                onViewShown();
            }
        } else {
            // Otherwise, add one of our layout change listeners and show it in when laid out
            mView.setOnLayoutChangeListener(new ZHSnackbar.SnackbarLayout.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View view, int left, int top, int right, int bottom) {
                    mView.setOnLayoutChangeListener(null);

                    if (shouldAnimate()) {
                        // If animations are enabled, animate it in
                        if (mGravity == 0) {
                            animateViewInTop();
                        } else {
                            animateViewInBottom();
                        }
                    } else {
                        // Else if anims are disabled just call back now
                        onViewShown();
                    }
                }
            });
        }
    }

    void animateViewInBottom() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ViewCompat.setTranslationY(mView, mView.getHeight());
            ViewCompat.animate(mView)
                    .translationY(0f)
                    .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                    .setDuration(ANIMATION_DURATION)
                    .setListener(new ViewPropertyAnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(View view) {
                            mView.animateChildrenIn(ANIMATION_DURATION - ANIMATION_FADE_DURATION,
                                    ANIMATION_FADE_DURATION);
                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            onViewShown();
                        }
                    }).start();
        } else {
            Animation anim = AnimationUtils.loadAnimation(mView.getContext(),
                    R.anim.design_snackbar_in);
            anim.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
            anim.setDuration(ANIMATION_DURATION);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    onViewShown();
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            mView.startAnimation(anim);
        }
    }

    private void animateViewOutBottom(final int event) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ViewCompat.animate(mView)
                    .translationY(mView.getHeight())
                    .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                    .setDuration(ANIMATION_DURATION)
                    .setListener(new ViewPropertyAnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(View view) {
                            mView.animateChildrenOut(0, ANIMATION_FADE_DURATION);
                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            onViewHidden(event);
                        }
                    }).start();
        } else {
            Animation anim = AnimationUtils.loadAnimation(mView.getContext(),
                    R.anim.design_snackbar_out);
            anim.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
            anim.setDuration(ANIMATION_DURATION);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    onViewHidden(event);
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            mView.startAnimation(anim);
        }
    }

    private void animateViewInTop() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ViewCompat.setTranslationY(mView, -mView.getHeight());
            ViewCompat.animate(mView)
                    .translationY(0f)
                    .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                    .setDuration(ANIMATION_DURATION)
                    .setListener(new ViewPropertyAnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(View view) {
                            mView.animateChildrenIn(ANIMATION_DURATION - ANIMATION_FADE_DURATION,
                                    ANIMATION_FADE_DURATION);
                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            if (mCallback != null) {
                                mCallback.onShown(ZHSnackbar.this);
                            }
                            SnackbarManager.getInstance()
                                    .onShown(mManagerCallback);
                        }
                    })
                    .start();
        } else {
            Animation anim = AnimationUtils.loadAnimation(mView.getContext(),
                    R.anim.top_in);
            anim.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
            anim.setDuration(ANIMATION_DURATION);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mCallback != null) {
                        mCallback.onShown(ZHSnackbar.this);
                    }
                    SnackbarManager.getInstance()
                            .onShown(mManagerCallback);
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            mView.startAnimation(anim);
        }
    }

    private void animateViewOutTop(final int event) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ViewCompat.animate(mView)
                    .translationY(-mView.getHeight())
                    .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                    .setDuration(ANIMATION_DURATION)
                    .setListener(new ViewPropertyAnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(View view) {
                            mView.animateChildrenOut(0, ANIMATION_FADE_DURATION);
                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            onViewHidden(event);
                        }
                    })
                    .start();
        } else {
            Animation anim = AnimationUtils.loadAnimation(mView.getContext(), R.anim.top_out);
            anim.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
            anim.setDuration(ANIMATION_DURATION);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    onViewHidden(event);
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            mView.startAnimation(anim);
        }
    }

    final void hideView(@Snackbar.Callback.DismissEvent final int event) {
        if (shouldAnimate() && mView.getVisibility() == View.VISIBLE) {
            if (mGravity == 0) {
                animateViewOutTop(event);
            } else {
                animateViewOutBottom(event);
            }
        } else {
            // If anims are disabled or the view isn't visible, just call back now
            onViewHidden(event);
        }
    }

    void onViewShown() {
        SnackbarManager.getInstance().onShown(mManagerCallback);
        if (mCallback != null) {
            mCallback.onShown(this);
        }
    }

    void onViewHidden(int event) {
        // First tell the SnackbarManager that it has been dismissed
        SnackbarManager.getInstance().onDismissed(mManagerCallback);
        // Now call the dismiss listener (if available)
        if (mCallback != null) {
            mCallback.onDismissed(this, event);
        }
        if (Build.VERSION.SDK_INT < 11) {
            // We need to hide the Snackbar on pre-v11 since it uses an old style Animation.
            // ViewGroup has special handling in removeView() when getAnimation() != null in
            // that it waits. This then means that the calculated insets are wrong and the
            // any dodging views do not return. We workaround it by setting the view to gone while
            // ViewGroup actually gets around to removing it.
            mView.setVisibility(View.GONE);
        }
        // Lastly, hide and remove the view from the parent (if attached)
        final ViewParent parent = mView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(mView);
        }
    }

    /**
     * Returns true if we should animate the Snackbar view in/out.
     */
    boolean shouldAnimate() {
        return !mAccessibilityManager.isEnabled();
    }

    /**
     * @hide
     */
    public static class SnackbarLayout extends LinearLayout {
        private TextView mMessageView;
        private Button mActionView;

        private int mMaxWidth;
        private int mMaxInlineActionWidth;

        interface OnLayoutChangeListener {
            void onLayoutChange(View view, int left, int top, int right, int bottom);
        }

        interface OnAttachStateChangeListener {
            void onViewAttachedToWindow(View v);

            void onViewDetachedFromWindow(View v);
        }

        private ZHSnackbar.SnackbarLayout.OnLayoutChangeListener mOnLayoutChangeListener;
        private ZHSnackbar.SnackbarLayout.OnAttachStateChangeListener mOnAttachStateChangeListener;

        public SnackbarLayout(Context context) {
            this(context, null);
        }

        public SnackbarLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, android.support.design.R.styleable.SnackbarLayout);
            mMaxWidth = a.getDimensionPixelSize(android.support.design.R.styleable.SnackbarLayout_android_maxWidth, -1);
            mMaxInlineActionWidth = a.getDimensionPixelSize(
                    android.support.design.R.styleable.SnackbarLayout_maxActionInlineWidth, -1);
            if (a.hasValue(android.support.design.R.styleable.SnackbarLayout_elevation)) {
                ViewCompat.setElevation(this, a.getDimensionPixelSize(
                        android.support.design.R.styleable.SnackbarLayout_elevation, 0));
            }
            a.recycle();

            setClickable(true);

            // Now inflate our content. We need to do this manually rather than using an <include>
            // in the layout since older versions of the Android do not inflate includes with
            // the correct Context.
            LayoutInflater.from(context).inflate(R.layout.layout_snackbar_include, this);

            ViewCompat.setAccessibilityLiveRegion(this,
                    ViewCompat.ACCESSIBILITY_LIVE_REGION_POLITE);
            ViewCompat.setImportantForAccessibility(this,
                    ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES);

            // Make sure that we fit system windows and have a listener to apply any insets
            ViewCompat.setFitsSystemWindows(this, true);
            ViewCompat.setOnApplyWindowInsetsListener(this,
                    new android.support.v4.view.OnApplyWindowInsetsListener() {
                        @Override
                        public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                            // Copy over the bottom inset as padding so that we're displayed above the
                            // navigation bar
                            v.setPadding(v.getPaddingLeft(), v.getPaddingTop(),
                                    v.getPaddingRight(), insets.getSystemWindowInsetBottom());
                            return insets;
                        }
                    });
        }

        @Override
        protected void onFinishInflate() {
            super.onFinishInflate();
            mMessageView = (TextView) findViewById(R.id.snackbar_text);
            mActionView = (Button) findViewById(R.id.snackbar_action);
        }

        TextView getMessageView() {
            return mMessageView;
        }

        Button getActionView() {
            return mActionView;
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            if (mMaxWidth > 0 && getMeasuredWidth() > mMaxWidth) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, MeasureSpec.EXACTLY);
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }

            final int multiLineVPadding = getResources().getDimensionPixelSize(
                    R.dimen.design_snackbar_padding_vertical_2lines);
            final int singleLineVPadding = getResources().getDimensionPixelSize(
                    R.dimen.design_snackbar_padding_vertical);
            final boolean isMultiLine = mMessageView.getLayout().getLineCount() > 1;

            boolean remeasure = false;
            if (isMultiLine && mMaxInlineActionWidth > 0
                    && mActionView.getMeasuredWidth() > mMaxInlineActionWidth) {
                if (updateViewsWithinLayout(VERTICAL, multiLineVPadding,
                        multiLineVPadding - singleLineVPadding)) {
                    remeasure = true;
                }
            } else {
                final int messagePadding = isMultiLine ? multiLineVPadding : singleLineVPadding;
                if (updateViewsWithinLayout(HORIZONTAL, messagePadding, messagePadding)) {
                    remeasure = true;
                }
            }

            if (remeasure) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }

        void animateChildrenIn(int delay, int duration) {
            ViewCompat.setAlpha(mMessageView, 0f);
            ViewCompat.animate(mMessageView).alpha(1f).setDuration(duration)
                    .setStartDelay(delay).start();

            if (mActionView.getVisibility() == VISIBLE) {
                ViewCompat.setAlpha(mActionView, 0f);
                ViewCompat.animate(mActionView).alpha(1f).setDuration(duration)
                        .setStartDelay(delay).start();
            }
        }

        void animateChildrenOut(int delay, int duration) {
            ViewCompat.setAlpha(mMessageView, 1f);
            ViewCompat.animate(mMessageView).alpha(0f).setDuration(duration)
                    .setStartDelay(delay).start();

            if (mActionView.getVisibility() == VISIBLE) {
                ViewCompat.setAlpha(mActionView, 1f);
                ViewCompat.animate(mActionView).alpha(0f).setDuration(duration)
                        .setStartDelay(delay).start();
            }
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            if (mOnLayoutChangeListener != null) {
                mOnLayoutChangeListener.onLayoutChange(this, l, t, r, b);
            }
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            if (mOnAttachStateChangeListener != null) {
                mOnAttachStateChangeListener.onViewAttachedToWindow(this);
            }

            ViewCompat.requestApplyInsets(this);
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (mOnAttachStateChangeListener != null) {
                mOnAttachStateChangeListener.onViewDetachedFromWindow(this);
            }
        }

        void setOnLayoutChangeListener(ZHSnackbar.SnackbarLayout.OnLayoutChangeListener onLayoutChangeListener) {
            mOnLayoutChangeListener = onLayoutChangeListener;
        }

        void setOnAttachStateChangeListener(ZHSnackbar.SnackbarLayout.OnAttachStateChangeListener listener) {
            mOnAttachStateChangeListener = listener;
        }

        private boolean updateViewsWithinLayout(final int orientation,
                                                final int messagePadTop, final int messagePadBottom) {
            boolean changed = false;
            if (orientation != getOrientation()) {
                setOrientation(orientation);
                changed = true;
            }
            if (mMessageView.getPaddingTop() != messagePadTop
                    || mMessageView.getPaddingBottom() != messagePadBottom) {
                updateTopBottomPadding(mMessageView, messagePadTop, messagePadBottom);
                changed = true;
            }
            return changed;
        }

        private static void updateTopBottomPadding(View view, int topPadding, int bottomPadding) {
            if (ViewCompat.isPaddingRelative(view)) {
                ViewCompat.setPaddingRelative(view,
                        ViewCompat.getPaddingStart(view), topPadding,
                        ViewCompat.getPaddingEnd(view), bottomPadding);
            } else {
                view.setPadding(view.getPaddingLeft(), topPadding,
                        view.getPaddingRight(), bottomPadding);
            }
        }
    }

    final class Behavior extends SwipeDismissBehavior<Snackbar.SnackbarLayout> {
        @Override
        public boolean canSwipeDismissView(View child) {
            return child instanceof Snackbar.SnackbarLayout;
        }

        @Override
        public boolean onInterceptTouchEvent(CoordinatorLayout parent, Snackbar.SnackbarLayout child,
                                             MotionEvent event) {
            // We want to make sure that we disable any Snackbar timeouts if the user is  
            // currently touching the Snackbar. We restore the timeout when complete  
            if (parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY())) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        SnackbarManager.getInstance().cancelTimeout(mManagerCallback);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        SnackbarManager.getInstance().restoreTimeout(mManagerCallback);
                        break;
                }
            }

            return super.onInterceptTouchEvent(parent, child, event);
        }
    }
}
