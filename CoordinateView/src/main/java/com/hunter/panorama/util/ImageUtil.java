package com.hunter.panorama.util;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.hunter.panorama.BuildConfig;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;


/**
 * xUtils图片工具类
 *
 * @author Zihang Huang
 *         create date 2017/11/17 16:54
 */
public class ImageUtil {
    private static ImageUtil mImageUtil;
    private Context mContext;

    private ImageUtil(Context context) {
        mContext = context;

        Application application = (Application) context.getApplicationContext();
        x.Ext.init(application);
        x.Ext.setDebug(BuildConfig.LOG_DEBUG); // 开启debug会影响性能
    }

    public static ImageUtil getInstance(Context context) {
        if (mImageUtil == null) {
            mImageUtil = new ImageUtil(context);
        }
        return mImageUtil;
    }

    /**
     * 显示图片（默认情况）
     *
     * @param imageView 图像控件
     * @param iconUrl   图片地址
     */
    public void display(ImageView imageView, String iconUrl) {
        display(imageView, iconUrl, ImageView.ScaleType.CENTER_CROP);
    }

    /**
     * 显示图片（默认情况）
     *
     * @param imageView 图像控件
     * @param iconUrl   图片地址
     */
    public void display(ImageView imageView, String iconUrl, ImageView.ScaleType scaleType) {
        display(imageView, iconUrl, scaleType, true, null);
    }

    /**
     * 显示图片（默认情况）
     *
     * @param imageView 图像控件
     * @param iconUrl   图片地址
     */
    public void display(ImageView imageView, String iconUrl, CommonCallback callback) {
        display(imageView, iconUrl, ImageView.ScaleType.CENTER_CROP, true, callback);
    }

    /**
     * 显示图片（默认情况）
     *
     * @param imageView 图像控件
     * @param iconUrl   图片地址
     */
    public void display(ImageView imageView, String iconUrl, ImageView.ScaleType scaleType
            , boolean isShowLoading, CommonCallback callback) {
        ImageOptions.Builder builder = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(scaleType)
                .setAutoRotate(true);
        ImageOptions imageOptions = builder.build();
        if (isShowLoading) {
//            builder.setLoadingDrawableId(R.mipmap.icon_ntt);
        }
        x.image().bind(imageView, iconUrl, imageOptions, callback);
    }


    /**
     * 显示圆角图片
     *
     * @param imageView 图像控件
     * @param iconUrl   图片地址
     * @param radius    圆角半径，
     */
    public void display(ImageView imageView, String iconUrl, int width, int height, int radius) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRadius(DensityUtil.dip2px(mContext, radius))
                .setIgnoreGif(false)
                .setCrop(true)//是否对图片进行裁剪
                .setSize(width, height)
                .build();
        x.image().bind(imageView, iconUrl, imageOptions);
    }

    /**
     * 显示圆形头像，第三个参数为true
     *
     * @param imageView  图像控件
     * @param iconUrl    图片地址
     * @param isCircluar 是否显示圆形
     */
    public void display(ImageView imageView, String iconUrl, boolean isCircluar) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setCircular(isCircluar)
                .setCrop(true)
//                .setFailureDrawableId(R.mipmap.load_failed)
//                .setLoadingDrawableId(R.mipmap.loading)
                .build();
        x.image().bind(imageView, iconUrl, imageOptions);
    }

    public static class CommonCallback implements Callback.CommonCallback<Drawable> {

        @Override
        public void onSuccess(Drawable result) {
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
        }

        @Override
        public void onCancelled(CancelledException cex) {
        }

        @Override
        public void onFinished() {
        }
    }

    /**
     * 清空缓存文件
     */
    public void clearCache() {
        x.image().clearMemCache();      //清空内存缓存
        x.image().clearCacheFiles();    //清空缓存文件
    }

}
