package com.hunter.helloandroid.module.update.util;

import android.text.TextUtils;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串分割处理
 *
 * @author Zihang Huang
 */
public class StringUtil {
    /**
     * 按照splitor分割src
     *
     * @param src
     * @param splitor
     * @return
     */
    public static String[] splitString(String src, String splitor) {
        String[] strs = null;
        if (!TextUtils.isEmpty(src) && !TextUtils.isEmpty(splitor)) {
            StringTokenizer s = new StringTokenizer(src, splitor);
            strs = new String[s.countTokens()];
            int i = 0;
            while (s.hasMoreTokens()) {
                strs[i++] = s.nextToken();
            }
        }
        return strs;
    }

    /**
     * 去除回车换行
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll(" ");
            dest = Pattern.compile("\"|\\s*").matcher(dest).replaceAll("");
        }
        return dest;
    }

    /**
     * 去除重复部分
     */
    public static String replaceRepeat(String a, String b) {
        if (TextUtils.isEmpty(a) || TextUtils.isEmpty(b)) {
            return null;
        }
        int length = Math.min(a.length(), b.length());
        int pos = 0;
        while (pos < length) {
            if (0 != (a.charAt(pos) ^ b.charAt(pos))) {
                break;
            }
            pos++;
        }
        return b.substring(pos);
    }

    /**
     * 如果为空则替换为-
     */
    public static String isEmptyReplace_(String str) {
        String result = str;
        if (!TextUtils.isEmpty(str)) {
            str = str.replace("null", "--");
            if (str.length() > 1 && str.endsWith("-")) {
                result = "--";
            }
        }
        return result;
    }


}
