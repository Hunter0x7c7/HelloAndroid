package com.hunter.helloandroid.module.update.bean;

import android.support.annotation.Keep;

import com.google.gson.annotations.SerializedName;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2018
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/31 15:34
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
@Keep
public class VersionBean {
    @SerializedName("Code")
    private String code;
    @SerializedName("Ver")
    private String versionCode;
    @SerializedName("Name")
    private String versionName;
    @SerializedName("Content")
    private String versionMessage;
    @SerializedName("Url")
    private String downloadUrl;

    public String getCode() {
        return code;
    }

    public void setCode(String returnCode) {
        this.code = returnCode;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionMessage() {
        return versionMessage;
    }

    public void setVersionMessage(String versionMessage) {
        this.versionMessage = versionMessage;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "VersionBean{" +
                "code='" + code + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionMessage='" + versionMessage + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
