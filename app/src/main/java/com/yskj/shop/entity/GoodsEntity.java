package com.yskj.shop.entity;

/**
 * 作者：陈宙洋
 * 日期：2017/8/11.
 * 描述：商家实体类
 */

public class GoodsEntity {
    private String title;
    private double price;
    private String imageurl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
