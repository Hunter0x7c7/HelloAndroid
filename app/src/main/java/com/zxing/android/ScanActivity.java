package com.zxing.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.hunter.helloandroid.module.scan.CustomScanActivity;
import com.hunter.helloandroid.util.ToastUtil;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.Serializable;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

/**
 * Created by hunter on 17/12/16.
 */

public class ScanActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        int method = intent.getIntExtra("Method", -1);
        OnResultListener listener = (OnResultListener) intent.getSerializableExtra("OnResultListener");
        setOnResultListener(listener);

        switch (method) {
             case 1:
                onClickScan(this);
                break;
            case 2:
                onClickScan2(this);
                break;
        }


    }



    public interface OnResultListener extends Serializable {
        void onResult(String result);
    }

    private OnResultListener onResultListener;

    public void setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    public void onClickScan(Activity context) {

        //打开扫描界面扫描条形码或二维码
//        Intent openCameraIntent = new Intent(context, com.zxing.android.CaptureActivity.class);
//        startActivityForResult(openCameraIntent, REQUEST_CODE);

        Intent intent = new Intent(context, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void onClickScan2(Activity context) {

        Intent intent = new Intent(context, CustomScanActivity.class);
//        Intent intent = new Intent(context, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

    // 你也可以使用简单的扫描功能，但是一般扫描的样式和行为都是
    // 可以自定义的，这里就写关于自定义的代码了

//        new IntentIntegrator(this)
//                .setOrientationLocked(false)
//                // 设置自定义的activity是CustomActivity
//                .setCaptureActivity(CustomScanActivity.class)
//                .initiateScan(); // 初始化扫描
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.println("....data:" + data);

        String result = null;
        if (resultCode == RESULT_OK) {
            result = parseScanResult(data);//解析扫描结果
        }

        if (result == null) {
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (intentResult != null) {
                if (intentResult.getContents() != null) {

                    // ScanResult 为 获取到的字符串
                    result = intentResult.getContents();
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

        if (onResultListener != null) {
            onResultListener.onResult(result);
        }

        ToastUtil.showPrompt("扫描成功:" + result, Toast.LENGTH_LONG, -1);
        finish();
    }

    /**
     * 解析扫描结果
     *
     * @param data 返回的Intent
     */
    private String parseScanResult(Intent data) {
        Bundle bundle = data.getExtras();
        return bundle.getString("result");
    }

    /**
     * 生成一个二维码
     */
    public Bitmap encodeAsBitmap(String str) {
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 200, 200);
            // 使用 ZXing Android Embedded 要写的代码
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException iae) { // ?
            return null;
        }

        // 如果不使用 ZXing Android Embedded 的话，要写的代码

//        int w = result.getWidth();
//        int h = result.getHeight();
//        int[] pixels = new int[w * h];
//        for (int y = 0; y < h; y++) {
//            int offset = y * w;
//            for (int x = 0; x < w; x++) {
//                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
//            }
//        }
//        bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels,0,100,0,0,w,h);

        return bitmap;
    }
}
