package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/10/14 0014.
 */
public class LoginEntity {


    /**
     * code : 200
     * data : 0dffe133a5969c2a3fc83ba3786810be
     * message : SUCCESS
     */

    @SerializedName("code")
    public int code;
    @SerializedName("data")
    public String data;
    @SerializedName("message")
    public String message;
}
