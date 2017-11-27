package com.yskj.shop.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 陈宙洋
 * 2017/7/22.
 */
public class UpdateApp {

    /**
     * PLATFORM : android
     * NOTE : Adkfdj测试
     * APP_NAME : wdh_mall-1.1.0.apk
     * VERSION : 1.1.0
     * DOWN_URL : http://localhost:9090/download/apk/wdh_mall-1.1.0.apk
     * CREATE_TIME : 2017-07-22 15:06:03.349
     * V_CODE : 110
     */

    @SerializedName("PLATFORM")
    public String PLATFORM;
    @SerializedName("NOTE")
    public String NOTE;
    @SerializedName("APP_NAME")
    public String APPNAME;
    @SerializedName("VERSION")
    public String VERSION;
    @SerializedName("DOWN_URL")
    public String DOWNURL;
    @SerializedName("CREATE_TIME")
    public String CREATETIME;
    @SerializedName("V_CODE")
    public String VCODE;
}
