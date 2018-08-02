package com.hunter.text.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.widget.EditText;

/**
 * 设置Hint大小
 *
 * @author Zihang Huang
 *         create date： 2018/3/30 11:05
 */
public class InputUtil {

    /**
     * 设置Hint大小
     */
    public static void setHintSize(EditText editText, String hint, int size, boolean dip) {
        if (editText == null || TextUtils.isEmpty(hint) || size <= 0) {
            return;
        }
        SpannableString ss = new SpannableString(hint);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, dip);//设置字体大小 true表示单位是dp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }
}
