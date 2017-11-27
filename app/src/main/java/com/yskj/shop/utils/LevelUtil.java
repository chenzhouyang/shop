package com.yskj.shop.utils;

/**
 * 作者：  陈宙洋
 * 描述：等级描述
 * 日期： 2017/11/10.
 */

public class LevelUtil {
    public static String geterr_code(int level){
        String err_messge = null;
        switch (level){
            case 0:
                err_messge = "贫农";
                break;
            case 3:
                err_messge = "贫农";
                break;
         case 10:
             err_messge = "中农";
             break;
         case 20:
             err_messge = "富农";
             break;
         case 30:
             err_messge = "地主";
             break;
        }
        return err_messge;
    }
}
