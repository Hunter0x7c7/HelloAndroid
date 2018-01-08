package com.hunter.helloandroid.module.four;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.util.SystemUtil;

import org.xutils.common.util.DensityUtil;

import java.util.List;

/**
 * 创建日期： 2017/12/20 17:23
 */
public class FourViewActivity extends AppCompatActivity {

    private MultiViewGroup mvgMulti;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_view);


        initMultiViewGroup();


        final ViewGroup ll_test = (ViewGroup) findViewById(R.id.ll_test);
        int childCount = ll_test.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View childAt = ll_test.getChildAt(i);
            childAt.setId(i);

            childAt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    childAt.bringToFront();
                    int childCount = ll_test.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View childAt = ll_test.getChildAt(i);
                        System.out.println(".......id:" + childAt.getId());
                    }
                    System.out.println("..*****....");
                }
            });
        }
    }

    private void initMultiViewGroup() {
        int[] clolr = {0x6300f0f0, 0x630f00f0, 0x63000f0f, 0x3600ff00};
        int dip3 = DensityUtil.dip2px(3);
        int height = DensityUtil.dip2px(125);
        int width = SystemUtil.getWidth() / 2;

        mvgMulti = (MultiViewGroup) findViewById(R.id.mvg_multi);
//        mvgMulti.removeAllViews();
//        for (int i = 0; i < 4; i++) {
////            View inflate = View.inflate(this, R.layout.item_multi_view_group, null);
//            View inflate = new MultiViewChild(this);
//            inflate.setBackgroundColor(clolr[i]);
//
//            TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
//            final SurfaceView sv_surface = (SurfaceView) inflate.findViewById(R.id.sv_surface);
//
////            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
////            tv_title.setLayoutParams(params);
//            tv_title.setText("hello:" + i);
//
////            params = new RelativeLayout.LayoutParams(width - dip3 * 10, height - dip3 * 2);
////            params.setMargins(dip3 * 5, dip3, dip3 * 5, dip3);
////            sv_surface.setLayoutParams(params);
//
//            SurfaceHolder holder = sv_surface.getHolder();
//            holder.addCallback(new SurfaceCallback2());
//
//            mvgMulti.addView(inflate);
//        }

        int childCount = mvgMulti.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mvgMulti.getChildAt(i);
            TextView tv_title = (TextView) childAt.findViewById(R.id.tv_title);
            final SurfaceView sv_surface = (SurfaceView) childAt.findViewById(R.id.sv_surface);
            if (tv_title != null)
                tv_title.setText("hello:" + i);

            if (tv_title != null) {
                SurfaceHolder holder = sv_surface.getHolder();
                holder.addCallback(new SurfaceCallback2());
            }

        }


        mvgMulti.setOnDeleteRectListener(new MultiViewGroup.OnDeleteRectListener() {
            @Override
            public void onPressed(boolean isPressed) {
                System.out.println(".........isPressed:" + isPressed);
            }
        });
    }

    public void onClickChange(View view) {

        mvgMulti.change();
// View mCurrentView = mvgMulti.change();
//        final SurfaceView sv_surface = (SurfaceView)mCurrentView.findViewById(R.id.sv_surface);
//
//        System.out.println("............mCurrentView.width:" + mCurrentView.getWidth() + " height:" + mCurrentView.getHeight());
////        System.out.println("............mvgMulti.width:" + mvgMulti.getWidth() + " height:" + mvgMulti.getHeight());
////        System.out.println("............sv_surface.width:" + sv_surface.getWidth() + " height:" + sv_surface.getHeight());
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) sv_surface.getLayoutParams();
//        params.width = mCurrentView.getWidth() -100 ;
//        params.height = mCurrentView.getHeight()-100 ;
////        sv_surface.setLayoutParams(params);
////        sv_surface.setLayoutParams(new RelativeLayout.LayoutParams( mCurrentView.getWidth() - 20, mCurrentView.getHeight() - 20 ));
////        System.out.println(".............width:" + params.width + " height:" + params.height);
//        System.out.println("............sv_surface.width:" + sv_surface.getWidth() + " height:" + sv_surface.getHeight());
    }

    private class SurfaceCallback2 implements SurfaceHolder.Callback {
        private Camera mCamera;

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCamera = Camera.open(1);
                mCamera.setPreviewDisplay(holder);
                mCamera.setDisplayOrientation(90);

//                int width = SystemUtil.getWidth() / 2;
//                int height = DensityUtil.dip2px(125);
//
//                Camera.Parameters parameters = mCamera.getParameters();
//                Camera.Size preSize = getCloselyPreSize(true, width, height
//                        , parameters.getSupportedPreviewSizes());
//                parameters.setPreviewSize(preSize.width, preSize.height);
//                mCamera.setParameters(parameters);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (mCamera != null)
                mCamera.startPreview();//该方法只有相机开启后才能调用
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (mCamera != null) {
                mCamera.release();//释放相机资源
                mCamera = null;
            }
        }
    }

    /**
     * 通过对比得到与宽高比最接近的预览尺寸（如果有相同尺寸，优先选择）
     *
     * @param isPortrait    是否竖屏
     * @param surfaceWidth  需要被进行对比的原宽
     * @param surfaceHeight 需要被进行对比的原高
     * @param preSizeList   需要对比的预览尺寸列表
     * @return 得到与原宽高比例最接近的尺寸
     */
    public static Camera.Size getCloselyPreSize(boolean isPortrait, int surfaceWidth
            , int surfaceHeight, List<Camera.Size> preSizeList) {
        int reqTmpWidth;
        int reqTmpHeight;
        // 当屏幕为垂直的时候需要把宽高值进行调换，保证宽大于高
        if (isPortrait) {
            reqTmpWidth = surfaceHeight;
            reqTmpHeight = surfaceWidth;
        } else {
            reqTmpWidth = surfaceWidth;
            reqTmpHeight = surfaceHeight;
        }
        //先查找preview中是否存在与surfaceview相同宽高的尺寸
        for (Camera.Size size : preSizeList) {
            if ((size.width == reqTmpWidth) && (size.height == reqTmpHeight)) {
                return size;
            }
        }

        // 得到与传入的宽高比最接近的size
        float reqRatio = ((float) reqTmpWidth) / reqTmpHeight;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = null;
        for (Camera.Size size : preSizeList) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }

        return retSize;
    }


}
