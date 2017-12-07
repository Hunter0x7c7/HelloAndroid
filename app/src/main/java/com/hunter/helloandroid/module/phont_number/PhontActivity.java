package com.hunter.helloandroid.module.phont_number;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.util.PhoneInfo;

import java.lang.reflect.Method;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/5/8 19:39
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class PhontActivity extends AppCompatActivity {

    private TextView tvText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_phone);
        tvText = (TextView) findViewById(R.id.tv_text);

        getNumber();

        PhoneInfo siminfo = new PhoneInfo(this);
        tvText.setText(tvText.getText().toString() + "getProvidersName:" + siminfo.getProvidersName()
                + "getNativePhoneNumber:" + siminfo.getNativePhoneNumber()
                + "getPhoneInfo:" + siminfo.getPhoneInfo());

    }

    static String ISDOUBLE;
    static String SIMCARD;
    static String SIMCARD_1;
    static String SIMCARD_2;
    static boolean isDouble;

    public void getNumber() {
        TelephonyManager tm = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        String phoneNumber1 = tm.getLine1Number();

        // String phoneNumber2 = tm.getGroupIdLevel1();

        initIsDoubleTelephone(this);
        if (isDouble) {
            // tv.setText("这是双卡手机！");
            tvText.setText(tvText.getText().toString() + "\n" + "本机号码是：" + "   " + phoneNumber1 + "   " + "这是双卡手机！");
        } else {
            // tv.setText("这是单卡手机");
            tvText.setText(tvText.getText().toString() + "\n" + "本机号码是：" + "   " + phoneNumber1 + "   " + "这是单卡手机");
        }

    }

    public void initIsDoubleTelephone(Context context) {
        isDouble = true;
        Method method = null;
        Object result_0 = null;
        Object result_1 = null;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            // 只要在反射getSimStateGemini 这个函数时报了错就是单卡手机（这是我自己的经验，不一定全正确）
            method = TelephonyManager.class.getMethod("getSimStateGemini", new Class[]{int.class});
            // 获取SIM卡1
            result_0 = method.invoke(tm, new Object[]{new Integer(0)});
            // 获取SIM卡2
            result_1 = method.invoke(tm, new Object[]{new Integer(1)});
        } catch (Exception e) {
            isDouble = false;
            e.printStackTrace();
            System.out.println("3_ISSINGLETELEPHONE:" + e.toString());
        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        if (isDouble) {
            // 保存为双卡手机
            editor.putBoolean(ISDOUBLE, true);
            // 保存双卡是否可用
            // 如下判断哪个卡可用.双卡都可以用
            if (result_0.toString().equals("5") && result_1.toString().equals("5")) {
                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1")) {
                    editor.putString(SIMCARD, "0");
                }
                editor.putBoolean(SIMCARD_1, true);
                editor.putBoolean(SIMCARD_2, true);

                tvText.setText(tvText.getText().toString() + "\n" + "双卡可用");

            } else if (!result_0.toString().equals("5") && result_1.toString().equals("5")) {// 卡二可用
                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1")) {
                    editor.putString(SIMCARD, "1");
                }
                editor.putBoolean(SIMCARD_1, false);
                editor.putBoolean(SIMCARD_2, true);

                tvText.setText(tvText.getText().toString() + "\n" + "卡二可用");

            } else if (result_0.toString().equals("5") && !result_1.toString().equals("5")) {// 卡一可用
                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1")) {
                    editor.putString(SIMCARD, "0");
                }
                editor.putBoolean(SIMCARD_1, true);
                editor.putBoolean(SIMCARD_2, false);

                tvText.setText(tvText.getText().toString() + "\n" + "卡一可用");

            } else {// 两个卡都不可用(飞行模式会出现这种种情况)
                editor.putBoolean(SIMCARD_1, false);
                editor.putBoolean(SIMCARD_2, false);

                tvText.setText(tvText.getText().toString() + "\n" + "飞行模式");
            }
        } else {
            // 保存为单卡手机
            editor.putString(SIMCARD, "0");
            editor.putBoolean(ISDOUBLE, false);
        }
        editor.commit();
    }


}
