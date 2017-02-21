package com.example.mzxy.utils;

/**
 * Created by Administrator on 2017/2/11 0011.
 */

public class LaundryUtils {
    //衣服图片
    private String picture;
    //衣服的名称
    private String pictureName;
    //数量
    private String count;
    //金额
    private String amounts;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAmounts() {
        return amounts;
    }

    public void setAmounts(String amounts) {
        this.amounts = amounts;
    }

    @Override
    public String toString() {
        return "LaundryUtils{" +
                "picture=" + picture +
                ", pictureName='" + pictureName + '\'' +
                ", count='" + count + '\'' +
                ", amounts='" + amounts + '\'' +
                '}';
    }
}
