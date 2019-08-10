package com.hunter.helloandroid.bean;

import android.support.annotation.Keep;

import java.util.List;

@Keep
public class CommentsBean {
    /*
     * uid的值
     */
    private int COM_ID;
    /*
     * 批注内容
     */
    private String COM_Message;
    /*
     * 批注等级 总监
     */
    private String COM_Type;
    /*
     * 时间
     */
    private String COM_Date;
    /*
     * 批注用户名
     */
    private String COM_User;
    /*
     * 角色
     */
    private String COM_Role;
    /*
     * 图片数组
     */
    private List<ImgInfoBean> PathModel;

    public CommentsBean( ) {
    }
    public CommentsBean(int COM_ID, String COM_Message, String COM_User) {
        this.COM_ID = COM_ID;
        this.COM_Message = COM_Message;
        this.COM_User = COM_User;
    }

    public int getCOM_ID() {
        return COM_ID;
    }

    public void setCOM_ID(int COM_ID) {
        this.COM_ID = COM_ID;
    }

    public String getCOM_Message() {
        return COM_Message;
    }

    public void setCOM_Message(String COM_Message) {
        this.COM_Message = COM_Message;
    }

    public String getCOM_Type() {
        return COM_Type;
    }

    public void setCOM_Type(String COM_Type) {
        this.COM_Type = COM_Type;
    }

    public String getCOM_Date() {
        return COM_Date;
    }

    public void setCOM_Date(String COM_Date) {
        this.COM_Date = COM_Date;
    }

    public String getCOM_User() {
        return COM_User;
    }

    public void setCOM_User(String COM_User) {
        this.COM_User = COM_User;
    }

    public String getCOM_Role() {
        return COM_Role;
    }

    public void setCOM_Role(String COM_Role) {
        this.COM_Role = COM_Role;
    }

    public List<ImgInfoBean> getPathModel() {
        return PathModel;
    }

    public void setPathModel(List<ImgInfoBean> pathModel) {
        PathModel = pathModel;
    }
}

