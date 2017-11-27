package com.yskj.shop.config;

/**
 * 创建日期 2017/6/12on 10:17.
 * 描述：
 * 作者：
 */

public class Ips {
    //测试
    //  ###########################################################################################
//    public final static String API_URL = "http://192.168.0.120:9000";
//    public final static String API_SHOPURL = "http://192.168.0.120:8200";
//    public final static String SOILGOODSIS= "288";

    //  ###########################################################################################


    //正式
    // ###########################################################################################
    //弃用
    public final static String API_SHOPURL = "http://47.52.94.255:8180/b2c";
    public final static String API_URL = "http://47.52.94.255:9000";
    public final static String SOILGOODSIS= "286";



    //  ###########################################################################################



    public final static String CONTENT_TYPE = "application/x-www-form-urlencoded";
    public final static String AUTHORIZATION = "Basic bW9iaWxlOjEyMzQ1Ng==";
    //用户头像
    public final static String API_URL_PHOTO =  API_SHOPURL + "/api/v1/mgs/file/download?fid=";
}
