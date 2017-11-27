package com.yskj.shop.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.dialog.MyLoading;
import com.yskj.shop.login.LoginActivity;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.SimplexToast;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public abstract class BaseActivity extends Activity {
    public SharedPreferences sp;
    private MyLoading myloading;
    public Context context;
    private LoadingCaches aCache = LoadingCaches.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁止所有activity横屏
        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public String getToken() {
        Long token = Long.valueOf(sp.getString("token", "0")) + Long.valueOf(sp.getString("server_salt", "0"));
        return String.valueOf(token);
    }
    public  void showdialog(String mess){
        new AlertDialog.Builder(context).setTitle("系统提示")//设置对话框标题
                .setMessage(mess)//设置显示的内容
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                    }
                }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
            public void onClick(DialogInterface dialog, int which) {//响应事件
                dialog.dismiss();
            }
        }).show();//在按键响应事件中显示此对话框
    }
    /**
     * 判断是否登录
     */
    public void isLogin(Exception e) {
        if(e == null||e.getMessage() == null){
            return;
        }
        if (e!=null&&e.getMessage()!=null&&e.getMessage().toString().contains("401")){
            startActivity(new Intent(context, LoginActivity.class));
        }else {
            showToast("网络链接错误 ");
        }
    }

    public void onBack(View v) {

        dismissInputMethod();
        finish();
    }

    public void rightTextview(View v) {

    }

    /**
     * dialog 启动
     */
    public void startMyDialog() {
        if (context==null){
            return;
        }
        if (myloading == null) {
            myloading = MyLoading.createLoadingDialog(context);
        }
            if (!isFinishing()) {
                try {
                    myloading.show();
                }catch (IllegalArgumentException e){
                    e.getStackTrace();
                }
        }
    }

    /**
     * dialog 销毁
     */
    public void stopMyDialog() {
        if (myloading != null) {
            if (!isFinishing()){
                try {
                    myloading.dismiss();
                }catch (IllegalArgumentException e){
                    e.getStackTrace();
                }
            }
            myloading = null;
        }
    }

    public void showToast(String msg) {
        SimplexToast.show(context,msg);
        //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int strId) {
        SimplexToast.show(context,strId);
    }



    protected void dismissInputMethod() {
        InputMethodManager imm = (InputMethodManager) BaseActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (BaseActivity.this.getCurrentFocus() != null) {
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(BaseActivity.this.getCurrentFocus().getWindowToken(),
                        0);
            }
        }

    }

    protected void showInputMethod() {
        InputMethodManager imm = (InputMethodManager) BaseActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (BaseActivity.this.getCurrentFocus() != null) {
            imm.toggleSoftInputFromWindow(BaseActivity.this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 字体大小不随系统改变而变
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }







}
