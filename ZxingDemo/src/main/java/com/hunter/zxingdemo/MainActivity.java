package com.hunter.zxingdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hunter.zxingdemo.util.ToastUtil;
import com.zxing.android.ScanActivity;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity
        implements Serializable  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClickScan1(View view) {

//        startActivity(new Intent(this, ScanActivity.class));

        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra("Method", 1);
        intent.putExtra("OnResultListener", new ScanResult2());
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickScan2(View view) {
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra("Method", 2);
        intent.putExtra("OnResultListener", new ScanResult2());
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ScanResult2 implements Serializable, ScanActivity.OnResultListener {

        @Override
        public void onResult(String result) {
            ToastUtil.showPrompt("......2............扫描结果:" + result);
        }
    }

    public static class ScanResult implements Parcelable, ScanActivity.OnResultListener {
        public ScanResult() {
        }

        public ScanResult(Parcel in) {
        }

        @Override
        public void onResult(String result) {
            ToastUtil.showPrompt("扫描结果:" + result);
        }

        public static final Creator<ScanResult> CREATOR = new Creator<ScanResult>() {
            @Override
            public ScanResult createFromParcel(Parcel in) {
                return new ScanResult(in);
            }

            @Override
            public ScanResult[] newArray(int size) {
                return new ScanResult[size];
            }
        };


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
        }
    }


}
