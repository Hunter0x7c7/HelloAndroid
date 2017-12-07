package com.hunter.helloandroid.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.jude.beam.Beam;
import com.jude.beam.expansion.BeamBaseActivity;
import com.jude.beam.expansion.overlay.ViewExpansionDelegate;
import com.jude.beam.expansion.overlay.ViewExpansionDelegateProvider;

import org.xutils.BuildConfig;
import org.xutils.x;

import java.util.LinkedList;
import java.util.List;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/2/28 17:19
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class BaseApplication extends Application {
    /**
     * 全局上下文对象
     */
    private static Context mContext;
    private List<Activity> mList = new LinkedList<>();   //用于存放每个Activity的List
    private static BaseApplication mInstance;    //BaseApplication实例
    //开发者需要填入自己申请的appkey  699903205
    public static String AppKey = "dd5992e08c0c496c9a0a981a80254e83";

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能

        Beam.init(this);//Beam——便捷的MVP开发框架
        Beam.setViewExpansionDelegateProvider(new ViewExpansionDelegateProvider() {
            @Override
            public ViewExpansionDelegate createViewExpansionDelegate(BeamBaseActivity activity) {
                return null;
            }
        });
//        ListConfig config = new ListConfig().setRefreshAble(true).setContainerLayoutRes(R.layout.activity_recyclerview).setLoadmoreAble(true);
//        ListConfig.setDefaultListConfig(config);

    }

    /**
     * 获取上下文对象
     *
     * @return 返回上下文对象
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 通过一个方法给外面提供实例
     *
     * @return
     */
    public synchronized static BaseApplication getInstance() {
        if (null == mInstance) {
            mInstance = new BaseApplication();
        }
        return mInstance;
    }

    /**
     * add Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    /**
     * remove Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        mList.remove(activity);
    }

    /**
     * 退出登录。遍历List，退出其它Activity
     */
    public void logout(Activity activity) {

        if (mList != null) {
            try {
                for (Activity a : mList) {
                    if (a != null && a != activity)
                        a.finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 遍历List，退出每一个Activity
     */
    public void exit() {

        if (mList != null) {
            try {
                for (Activity activity : mList) {
                    if (activity != null)
                        activity.finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();   //告诉系统回收
    }

}
