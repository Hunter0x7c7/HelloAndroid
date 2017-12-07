package com.hunter.helloandroid.bean;

import android.support.annotation.Keep;

import java.io.Serializable;

@Keep
public class ImgInfoBean implements Serializable {
    private String Img_Url;
    private String Img_MiniUrl;
    private int Img_FID;

    public String getImg_Url() {
        return Img_Url;
    }

    public void setImg_Url(String img_Url) {
        Img_Url = img_Url;
    }

    public String getImg_MiniUrl() {
        return Img_MiniUrl;
    }

    public void setImg_MiniUrl(String img_MiniUrl) {
        Img_MiniUrl = img_MiniUrl;
    }

    public int getImg_FID() {
        return Img_FID;
    }

    public void setImg_FID(int img_FID) {
        Img_FID = img_FID;
    }

    @Override
    public String toString() {
        return "ImgInfoBean{" +
                "Img_Url='" + Img_Url + '\'' +
                ", Img_MiniUrl='" + Img_MiniUrl + '\'' +
                ", Img_FID=" + Img_FID +
                '}';
    }
}

