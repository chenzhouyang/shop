package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：  陈宙洋
 * 描述：实名认证数据
 * 日期： 2017/10/25.
 */

public class MainEntity {
    /**
     * code : 200
     * data : null
     * message : SUCCESS
     */

    @SerializedName("code")
    public int code;
    @SerializedName("data")
    public double data;
    @SerializedName("message")
    public String message;
}
