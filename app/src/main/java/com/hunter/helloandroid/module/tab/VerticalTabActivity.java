package com.hunter.helloandroid.module.tab;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.hunter.helloandroid.R;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;

import q.rorbin.verticaltablayout.VerticalTabLayout;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2019
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2019/10/29 17:45
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class VerticalTabActivity extends AppCompatActivity {

    private ViewPager viewpager;
    private VerticalTabLayout tablayout;

    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_tab);


        mAdapter = new MyPagerAdapter(getSupportFragmentManager());

        viewpager = (ViewPager) findViewById(R.id.vp_view_pager);
        viewpager.setAdapter(mAdapter);

        tablayout = (VerticalTabLayout) findViewById(R.id.vertical_tab);
        tablayout.setupWithViewPager(viewpager);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private String[] mTitles = {"远程对讲", "报警记录", "开门记录", "留言记录"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public BaseFragment getItem(int position) {
            return SmartFragmentFactory.createFragment(position);
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    public @interface ActivityFragmentInject {

        /**
         * 顶部局的id
         *
         * @return
         */
        int contentViewId() default R.layout.activity_main;

        /**
         * 是否存在NavigationView
         *
         * @return
         */
        boolean hasNavigationView() default false;

        /**
         * 是否存在Toolbar
         *
         * @return
         */
        boolean hasToolbar() default false;

        /**
         * toolbar的标题id
         *
         * @return
         */
        int toolbarTitle() default -1;

        /**
         * 左边文本资源id
         *
         * @return
         */
        int toolbarLeftText() default -1;

        /**
         * 右边文本资源id
         *
         * @return
         */
        int toolbarRightText() default -1;

        /**
         * 左边图片资源id
         *
         * @return
         */
        int toolbarLeftIcon() default -1;

        /**
         * 右边图片资源id
         *
         * @return
         */
        int toolbarRightIcon() default -1;

    }

    public static abstract class BaseFragment extends Fragment implements MyToolBarClickListener {

        protected static final int DEFAULT_LOADING_TIME = 2000;
        protected static final int WHAT_LOAD = 1;
        protected static final int WHAT_LOAD_FINISH = 2;
        protected static final int WHAT_REFRESH = 3;

        protected Activity mActivity;
        protected Context mContext;
        protected View mRootView;

        protected ActivityFragmentInject annotation;
        protected int contentViewId;

        protected Handler mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                toHandleMessage(msg);
                return true;
            }
        });


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mActivity = getActivity();
            mContext = getContext();
            if (!getClass().isAnnotationPresent(ActivityFragmentInject.class)) {
                throw new RuntimeException("must use ActivityFragmentInitParams.class");
            }
            annotation = getClass().getAnnotation(ActivityFragmentInject.class);
            contentViewId = annotation.contentViewId();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            if (mRootView == null) {
                mRootView = View.inflate(mContext, contentViewId, null);
            }
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            initViewNData();
            return mRootView;
        }

        protected void initViewNData() {
            if (annotation.hasToolbar()) {
                //initToolbar();
            }
            findViewAfterViewCreate();
            initDataAfterFindView();
        }

        protected void initToolbar() {

        }

        protected abstract void toHandleMessage(Message msg);

        protected abstract void findViewAfterViewCreate();

        protected abstract void initDataAfterFindView();


        @Override
        public void leftTextClick(View view) {
        }

        @Override
        public void leftIconClick(View view) {
        }

        @Override
        public void rightTextClick(View view) {
        }

        @Override
        public void rightIconClick(View view) {
        }

        public boolean onKeyDown(int keyCode, KeyEvent event) {
            return true;
        }
    }


    public static class SmartFragmentFactory {
        private static HashMap<Integer, BaseFragment> mBaseFragments = new HashMap<>();

        public static BaseFragment createFragment(int position) {
            BaseFragment baseFragment = mBaseFragments.get(position);

            if (baseFragment == null) {
                switch (position) {
                    case 0:
//                        baseFragment = new RemoteVideoFragment();
                        baseFragment = new LeaveMsgFragment();
                        break;
                    case 1:
//                        baseFragment = new AlarmRecordFragment();
                        baseFragment = new LeaveMsgFragment();
                        break;
                    case 2:
//                        baseFragment = new DoorOpenRecordFragment();
                        baseFragment = new LeaveMsgFragment();
                        break;
                    case 3:
                        baseFragment = new LeaveMsgFragment();
                        break;
                }

            }

            mBaseFragments.put(position, baseFragment);
            return baseFragment;
        }
    }

    public interface MyToolBarClickListener {
        void leftTextClick(View view);

        void leftIconClick(View view);

        void rightTextClick(View view);

        void rightIconClick(View view);
    }

    @ActivityFragmentInject(
            contentViewId = R.layout.fragment_tab,
            hasNavigationView = false)
    public static class LeaveMsgFragment extends BaseFragment {
        @Override
        protected void toHandleMessage(Message msg) {

        }

        @Override
        protected void findViewAfterViewCreate() {

        }

        @Override
        protected void initDataAfterFindView() {

        }
    }


}
