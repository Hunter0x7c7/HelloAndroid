package com.hunter.helloandroid.bean;


/**
 * 点信息
 *
 * @author Zihang Huang
 *         create date 2017/11/17 16:54
 */
public class PointInfo {

    private float x;
    private float y;
    private String name;
    private PointType type;

    public PointInfo() {
    }

    public PointInfo(float x, float y, String name) {
        this(x, y, name, PointType.FIELD);
    }

    public PointInfo(float x, float y, String name, PointType type) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.type = type;
    }

    public enum PointType {
        FIELD, REGION
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PointType getType() {
        return type;
    }

    public void setType(PointType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PointInfo{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
