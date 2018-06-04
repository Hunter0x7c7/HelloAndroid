package com.hunter.helloandroid.util;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.IntRange;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.EditText;

import com.hunter.helloandroid.base.BaseApplication;

import java.text.DecimalFormat;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2018
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/29 14:27
 * <p>
 * 描    述：文本工具类
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class TextUtil {

    /**
     * 生成一个跨度的字符串
     */
    public static CharSequence generateSpanString(CharSequence source, int start, int end, int style) {
        return generateSpanString(source, start, end, Color.RED, style);
    }

    /**
     * 生成一个跨度的字符串
     */
    public static CharSequence generateSpanString(CharSequence source, int start, int end, int color, int style) {
        SpannableString span = new SpannableString(source);
        int flags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        //设置颜色
        span.setSpan(new ForegroundColorSpan(color != -1 ? color : Color.BLACK), start, end, flags);
        //设置字体，BOLD为粗体
        span.setSpan(new StyleSpan(style != -1 ? style : Typeface.NORMAL), start, end, flags);
        return span;
    }

    /**
     * 设置密码是否可见
     */
    public static boolean setPasswordVisibility(EditText editText, boolean isShowPassword) {
        boolean result = false;
        if (editText != null) {
            int inputType;
            if (isShowPassword) {//显示密码
                inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
            } else {
                inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
            }
            editText.setInputType(inputType);
            editText.setSelection(editText.getText().toString().length());
            result = true;
        }
        return result;
    }

    /**
     * 保留小数
     *
     * @param number 保留位数
     * @param value  要保留的小数
     * @return 保留后的小数
     */
    public static String keepDecimal(@IntRange(from = 1) int number, double value) {
        String pattern = "0.";
        while (--number >= 0) {
            pattern += "#";
        }
        //如果小数不足2位,#不会以0补足.,0会以0补足.
        //String pattern = "0.##";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(value);
    }

    /**
     * 保留小数
     *
     * @param pattern 保留规则
     * @param value   要保留的小数
     * @return 保留后的小数
     */
    public static String keepDecimal(String pattern, double value) {
        //String pattern = "0.##";  //如果小数不足2位,#不会以0补足.,0会以0补足.
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(value);
    }

    /**
     * 获取字体
     *
     * @param typeface 字体文件名称，字体放在assets目录下
     * @return 返回Typeface
     */
    public static Typeface getTypeface(String typeface) {
        return getTypeface(typeface, false);
    }

    /**
     * 获取字体
     *
     * @param typeface 字体文件名称，字体放在assets目录下
     * @param isBold   是否加粗
     * @return 返回Typeface
     */
    public static Typeface getTypeface(String typeface, boolean isBold) {
        AssetManager assets = BaseApplication.getContext().getResources().getAssets();
        Typeface fromAsset = Typeface.createFromAsset(assets, typeface);
        return Typeface.create(fromAsset, isBold ? Typeface.BOLD : Typeface.NORMAL);
    }

    /**
     * 测量字符串所占用的宽度
     *
     * @param paint 画笔
     * @param str   测量的字符串
     * @return 返回字符串所占用的宽度
     */
    public static float measureTextWidth(Paint paint, String str) {
        float width = 0;
        if (paint != null && str != null) {
            width = paint.measureText(str);
        }
        return width;
    }

    /**
     * 测量字符串所占用的高度
     *
     * @param paint 画笔
     * @param str   测量的字符串
     * @return 返回字符串所占用的高度
     */
    public static float measureTextWidth2(Paint paint, String str) {
        Rect rect = new Rect();

        if (paint != null && str != null) {
            //返回包围整个字符串的最小的一个Rect区域
            paint.getTextBounds(str, 0, 1, rect);
        }
        return rect.width();
    }

    /**
     * 获取画笔在竖直方向居中时的距离上面的距离
     *
     * @param viewHeight 总体高度
     * @param paint      画笔
     * @return 返回居中时的距离上面的距离
     */
    public static int getCenterHorizontal(int viewHeight, Paint paint) {
        int result = 0;
        if (viewHeight > 0 && paint != null) {
            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
            result = (viewHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        }
        return result;
    }
}
