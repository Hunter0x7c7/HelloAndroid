package com.hunter.helloandroid.module.add_view_anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.bean.PointInfo;
import com.hunter.helloandroid.interfaces.OnNestItemClickListener;
import com.hunter.helloandroid.util.ListUtil;
import com.hunter.helloandroid.view.PointButton;
import com.hunter.panorama.CoordinateView;
import com.hunter.panorama.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2017
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/11/16 15:25
 * <p>
 * 描    述：Add View Anim
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class AddViewAnimActivity extends AppCompatActivity implements View.OnTouchListener {

    private LinearLayout llViewgroup;
    private CoordinateView mCoordinateView;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addview_anim);

        mContext = this;

        llViewgroup = (LinearLayout) findViewById(R.id.ll_viewgroup);
        mCoordinateView = (CoordinateView) findViewById(R.id.p_panorama);
//        setLinearContainerAnimation(llViewgroup);

//        //一行代码直接搞定：
//        ILayoutAnimationController.IndexAlgorithm indexsimplependulum = ILayoutAnimationController.IndexAlgorithm.MIDDLETOEDGE;
//        ILayoutAnimationController.setLayoutAnimation(llViewgroup,R.anim.activity_open_enter,0.8f, indexsimplependulum);

        cCoordinate(null);
    }

    public void onClickAdd(View view) {
//        setLinearContainerAnimation(llViewgroup);
        initAinm(llViewgroup);
        final ScrollView parent = (ScrollView) llViewgroup.getParent();
//        parent.fullScroll(View.FOCUS_DOWN);

        llViewgroup.removeAllViews();
        for (int i = 0; i < 5; i++) {
            final View inflate = View.inflate(AddViewAnimActivity.this, R.layout.scroll_button, null);
            inflate.setLayoutParams(new LinearLayout.LayoutParams((int) (Math.random() + 80), -2));
            llViewgroup.addView(inflate);
            inflate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llViewgroup.removeView(inflate);
                }
            });
        }

        llViewgroup.postDelayed(new Runnable() {
            @Override
            public void run() {
                parent.fullScroll(View.FOCUS_DOWN);
            }
        }, 500);
    }

    public void removeAllViews(View view) {
        llViewgroup.removeAllViews();
    }

    /**
     * 设置ViewGroup添加子View时的动画
     */
    private void setLinearContainerAnimation(ViewGroup view) {
        //ViewGroup中添加View时的动画操作对象
        LayoutTransition mTransitioner = new LayoutTransition();
        mTransitioner.setStagger(LayoutTransition.APPEARING, 13000);//添加View
        mTransitioner.setStagger(LayoutTransition.DISAPPEARING, 15000);//移除View

        mTransitioner.setStagger(LayoutTransition.CHANGE_APPEARING, 13000);//添加View
        mTransitioner.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 15000);//移除View
        //定制动画
        setupCustomAnimations(mTransitioner);
        //设置mLinearContainer布局改变时动画
        view.setLayoutTransition(mTransitioner);
    }

    /**
     * 定制ViewGroup添加View时显示的动画
     */
    private void setupCustomAnimations(LayoutTransition mTransitioner) {
//        // 添加改变时执行 LayoutTransition.CHANGE_APPEARING - view出现时，其他view的动画（如果对其他view的布局有影响的话）
//        PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left", 0, 1);
//        PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt("top", 0, 1);
//        PropertyValuesHolder pvhRight = PropertyValuesHolder.ofInt("right", 0, 1);
//        PropertyValuesHolder pvhBottom = PropertyValuesHolder.ofInt("bottom", 0, 1);
//        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f, 1f);
//        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f, 1f);
//        long duration = mTransitioner.getDuration(LayoutTransition.CHANGE_APPEARING);
//        final ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(this, pvhLeft, pvhTop
//                , pvhRight, pvhBottom, pvhScaleX, pvhScaleY).setDuration(duration);
//
//        mTransitioner.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);
//        changeIn.addListener(new AnimatorListenerAdapter() {
//            public void onAnimationEnd(Animator anim) {
//                View view = (View) ((ObjectAnimator) anim).getTarget();
//                view.setScaleX(1f);
//                view.setScaleY(1f);
//            }
//        });

//        // 移除改变时执行 LayoutTransition.CHANGE_DISAPPEARING - view消失时，其他view的动画（如果对其他view的布局有影响的话）
//        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
//        Keyframe kf1 = Keyframe.ofFloat(.9999f, 360f);
//        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
//        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
//        long duration1 = mTransitioner.getDuration(LayoutTransition.CHANGE_DISAPPEARING);
//        final ObjectAnimator changeOut = ObjectAnimator.ofPropertyValuesHolder(this, pvhLeft
//                , pvhTop, pvhRight, pvhBottom, pvhRotation).setDuration(duration1);
//
//        mTransitioner.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeOut);
//        changeOut.addListener(new AnimatorListenerAdapter() {
//            public void onAnimationEnd(Animator anim) {
//                View view = (View) ((ObjectAnimator) anim).getTarget();
//                view.setRotation(0f);
//            }
//        });

        // 添加时执行 LayoutTransition.APPEARING - view出现时，view本身的动画
        long duration2 = mTransitioner.getDuration(LayoutTransition.APPEARING);
        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "rotationY", 90f, 0f).setDuration(duration2);
        mTransitioner.setAnimator(LayoutTransition.APPEARING, animIn);
        animIn.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setRotationY(0f);
            }
        });

        // 移除View时执行的动画  LayoutTransition.DISAPPEARING - view消失时，view本身的动画
        long duration3 = mTransitioner.getDuration(LayoutTransition.DISAPPEARING);
        ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "rotationX", 0f, 90f).setDuration(duration3);
        mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);
        animOut.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator anim) {
                View view = (View) ((ObjectAnimator) anim).getTarget();
                view.setRotationX(0f);
            }
        });
        mTransitioner.setStartDelay(LayoutTransition.CHANGE_APPEARING, 1000);
//        mTransitioner.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 1300);
//        mTransitioner.setStartDelay(LayoutTransition.CHANGING, 1300);
        mTransitioner.setStartDelay(LayoutTransition.APPEARING, 1000);
//        mTransitioner.setStartDelay(LayoutTransition.DISAPPEARING, 1300);
    }

    private void initAinm(ViewGroup viewGroup) {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new TranslateAnimation(0, 0, -100, 0));
        animationSet.addAnimation(new AlphaAnimation(0, 1));
        animationSet.setDuration(500);
        // 设置弹跳效果插值器
        animationSet.setInterpolator(new BounceInterpolator());

        //得到一个LayoutAnimationController对象；
        LayoutAnimationController lac = new LayoutAnimationController(animationSet);
        //设置控件显示的顺序；
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        //设置控件显示间隔时间；
        lac.setDelay(0.5f);
        //为ListView设置LayoutAnimationController属性；
        viewGroup.setLayoutAnimation(lac);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        System.out.println("......onTouch....Action:" + event.getAction());
        return false;
    }

    public class Util {

        private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:

                        if (!ListUtil.isEmpty(list)) {
                            View view = list.get(0);
                            LayoutTransition setup = setup(new AnimatorListenerAdapter() {
                                public void onAnimationEnd(Animator anim) {
                                    View view = (View) ((ObjectAnimator) anim).getTarget();
                                    view.setRotationX(0f);
                                }
                            });
                            viewGroup.setLayoutTransition(setup);
                        }
                        break;
                    case 2:

                        break;
                }
            }
        };
        private List<View> list;
        private ViewGroup viewGroup;

        void start(ViewGroup viewGroup, List<View> list) {
            this.viewGroup = viewGroup;
            this.list = list;
            mHandler.sendEmptyMessage(1);
        }

        public LayoutTransition setup(AnimatorListenerAdapter listenerAdapter) {
            //ViewGroup中添加View时的动画操作对象
            LayoutTransition mTransitioner = new LayoutTransition();
//            mTransitioner.setStagger(LayoutTransition.APPEARING, 13000);//添加View
//            mTransitioner.setStagger(LayoutTransition.DISAPPEARING, 15000);//移除View
//            mTransitioner.setStagger(LayoutTransition.CHANGE_APPEARING, 13000);//添加View
//            mTransitioner.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 15000);//移除View

            long duration = mTransitioner.getDuration(LayoutTransition.APPEARING);
            ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "rotationY", 90f, 0f).setDuration(duration);
            mTransitioner.setAnimator(LayoutTransition.APPEARING, animIn);
            if (listenerAdapter != null)
                animIn.addListener(listenerAdapter);
            return mTransitioner;
        }
    }

    public void cCoordinate(View view) {

        List<PointF> pointFs = new ArrayList<>();
        List<PointInfo> mPointList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int x = 10 + i * 6;
            int y = 25 + i * 5;
            pointFs.add(new PointF(x, y));
            mPointList.add(new PointInfo(x, y, "ffff" + i, PointInfo.PointType.FIELD));
        }
        List<PointButton> buttonList = getPointButtons(mPointList);

        String url = "http://www.nongtt.com/ntt_proj_img/zj-cxmm-view.jpg";
//        mCoordinateView.setup(pointFs, buttonList );
        ImageUtil.CommonCallback commonCallback = new ImageUtil.CommonCallback() {
            @Override
            public void onSuccess(Drawable result) {
                super.onSuccess(result);
                System.out.println(".............result:" + result);
//   ImageUtil.display(ivBackground, url);
            }
        };
        mCoordinateView.setup(pointFs,  buttonList, url, commonCallback);
        mCoordinateView.setOnTouchListener(this);
    }

    /**
     * 由点信息列表生成点按钮列表
     */
    private List<PointButton> getPointButtons(List<PointInfo> mPointList) {
        List<PointButton> viewList = null;
        if (mPointList != null && mPointList.size() > 0) {
            viewList = new ArrayList<>();
            String name;
            PointButton child;
            int size = mPointList.size();
            for (int i = 0; i < size; i++) {
                name = mPointList.get(i).getName();

                child = new PointButton(mContext);
                child.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
                child.setNameRight(name);
                child.setOnItemClickListener(i, new OnPointMenuClickListener());
                viewList.add(child);
            }
        }
        return viewList;
    }

    private class OnPointMenuClickListener implements OnNestItemClickListener {

        @Override
        public void onNestItemClick(View view, int itemPosition, int subPosition) {
            System.out.println(".......OnPointMenuClickListener.......itemPosition:" + itemPosition + " subPosition:" + subPosition);

        }
    }


}
