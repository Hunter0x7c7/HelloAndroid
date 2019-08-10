package com.hunter.helloandroid.module.update.bean;

import android.support.annotation.Keep;

/**
 * ================================================================
 * <p>
 * 版    权： HunterHuang(c)2018
 * <p>
 * 作    者： Hunter_1125607007@QQ.COM
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
public class TupleThree<K, V, E> extends TupleTwo<K, V> {
    private final E threeValue;

    public TupleThree(K key, V value, E threeValue) {
        super(key, value);
        this.threeValue = threeValue;
    }

    public E getThreeValue() {
        return threeValue;
    }

    @Override
    public String toString() {
        return "TupleThree{" +
                "threeValue=" + threeValue +
                '}';
    }
}
