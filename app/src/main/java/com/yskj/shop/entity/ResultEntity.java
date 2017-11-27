package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：陈宙洋
 * 日期：2017/8/17.
 * 描述：通用返回结果
 */

public class ResultEntity {
    /**
     * result : 1
     * message : 添加成功
     * data : null
     */

    @SerializedName("result")
    public int result;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public Object data;
}
