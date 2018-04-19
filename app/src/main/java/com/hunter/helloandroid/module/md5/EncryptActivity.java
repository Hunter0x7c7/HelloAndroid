package com.hunter.helloandroid.module.md5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hunter.helloandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2017
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/26 9:29
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class EncryptActivity extends AppCompatActivity {

    @BindView(R.id.et_des)
    EditText mInput;
    @BindView(R.id.tv_result)
    TextView mResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        ButterKnife.bind(this);
    }

//    private String mDesKey = "12345678";
//    private String mAesKey = "1234567890123456";

    public void onClickDESEncrypt(View v) {
        try {
            String desText = mInput.getText().toString();
            String decryptDES = DesUtil.getInstance().encrypt(desText);
//            String decryptDES = DesUtil.getInstance().encryptDES(desText, mDesKey);
            setResult(decryptDES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickDESDecode(View v) {
        try {
            String desText = mResult.getText().toString();
            String decryptDES = DesUtil.getInstance().decrypt(desText);
//            String decryptDES = DesUtil.getInstance().decryptDES(desText, mDesKey);
            setResult(decryptDES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onClickAESEncrypt(View v) {
        try {
            String desText = mInput.getText().toString();
            String decryptDES = AesUtil.getInstance().encrypt(desText);
//            String decryptDES = AesUtil.getInstance().encrypt(desText, mAesKey);
            setResult(decryptDES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickAESDecode(View v) {
        try {
            String desText = mResult.getText().toString();
            String decryptDES = AesUtil.getInstance().decrypt(desText);
//            String decryptDES = AesUtil.getInstance().decrypt(desText, mAesKey);
            setResult(decryptDES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickClear(View v) {
        setResult("");
    }

    private void setResult(String result) {
        mResult.setText(result);
        System.out.println("....setResult.16...result:" + result);
    }



}
