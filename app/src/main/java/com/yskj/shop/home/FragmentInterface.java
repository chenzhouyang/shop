package com.yskj.shop.home;

/**
 * Created by YSKJ-02 on 2017/3/15.
 */

public class FragmentInterface {
    //定义了activity必须实现的接口方法
    public interface OnGetUrlListener {
        //跳转到一级分类
        void getfirstPosition(int firstPosition);
        //跳转到二级分类
        void getTwoPosition(int firstPosition, int twoPosition);
    }
}
