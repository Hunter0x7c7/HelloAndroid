package com.hunter.helloandroid.module.update.bean;

import android.support.annotation.Keep;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2018
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/26 16:50
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */

@Keep
public class TupleOne<K> {
    private final K key;

    public TupleOne(K key) {
        this.key = key;
    }

    public K getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "TupleOne{" +
                "key=" + key +
                '}';
    }
}
