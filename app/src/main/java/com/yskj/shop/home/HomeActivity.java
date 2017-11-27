package com.yskj.shop.home;

import android.Manifest;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.config.Config;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.LoginEntity;
import com.yskj.shop.login.LoginActivity;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.SystemBarTintManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class HomeActivity extends TabActivity {

   private FrameLayout tabcontent;
    TabWidget tabs;
    //主页商城
    static RadioButton homeTabMain;
    static RadioButton homeTabAd;
    //购物车
    RadioButton homeTabShoppingCart;
    //我的
    static RadioButton homeTabUser;
    RadioGroup homeRadioButtonGroup;
    public SharedPreferences sp;
    static TabHost tabhost;
    private static SystemBarTintManager mBarTintManager;
    public static final String TAB_MAIN = "MAIN_ACTIVITY";
    public static final String TAB_LOCAL = "LOCAL_ACTIVITY";
    public static final String TAB_AD = "AD_ACTIVITY";
    public static final String TAB_CART = "CART_ACTIVITY";
    public static final String TAB_PERSONAL = "USER_ACTIVITY";
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS = 1;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 2;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private String mobile,password;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        AppManager.getInstance().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁止所有activity横屏
        setContentView(R.layout.activity_orchard_home);
        mBarTintManager = new SystemBarTintManager(this);
        mBarTintManager.setStatusBarTintEnabled(true);
        sp = getSharedPreferences("control", 0);
        initView();
    }
    private void initView() {
        PgyCrashManager.register(this);
        //动态请求权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                        REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS);
                requestPermissions(
                        new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
            }
        }
        PgyUpdateManager.setIsForced(true); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyUpdateManager.register(HomeActivity.this,
                getString(R.string.provider_file),
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        new AlertDialog.Builder(HomeActivity.this)
                                .setTitle("更新")
                                .setMessage(appBean.getReleaseNote())
                                .setNegativeButton(
                                        "确定",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                startDownloadTask(
                                                        HomeActivity.this,
                                                        appBean.getDownloadURL());
                                            }
                                        }).show();
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                    }
                });

        SharedPreferences share2    = getSharedPreferences("mobile", 0);
         mobile  = share2.getString("mobile", "null");
        SharedPreferences share    = getSharedPreferences("password", 0);
         password  = share.getString("password", "null");
         if(!mobile.equals("null")&&!password.equals("null")){
            login();
         }
        tabhost = (TabHost) findViewById(android.R.id.tabhost);
        tabcontent = (FrameLayout) findViewById(android.R.id.tabcontent);
        tabs = (TabWidget) findViewById(android.R.id.tabs);
        homeRadioButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);
        homeTabMain = (RadioButton) findViewById(R.id.home_tab_main);
        homeTabAd = (RadioButton) findViewById(R.id.home_tab_ad);
        homeTabShoppingCart = (RadioButton) findViewById(R.id.home_tab_shopping_cart);
        homeTabUser = (RadioButton) findViewById(R.id.home_tab_user);
        homeRadioButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);
        tabhost = getTabHost();
        Intent i_main = new Intent(this, ShoppingMainActivity.class);// 商城
        Intent i_advertising = new Intent(this, GoodsCategoryActivity.class);// 分类
        Intent shopping_cart = new Intent(this, ShoppingCartActivity.class);// 购物车
        shopping_cart.putExtra("type", "0");
        Intent i_mine = new Intent(this, MineActivity.class);// 我的
        tabhost.addTab(tabhost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN).setContent(i_main));
        tabhost.addTab(tabhost.newTabSpec(TAB_AD).setIndicator(TAB_AD).setContent(i_advertising));
        tabhost.addTab(tabhost.newTabSpec(TAB_CART).setIndicator(TAB_CART).setContent(shopping_cart));
        tabhost.addTab(tabhost.newTabSpec(TAB_PERSONAL).setIndicator(TAB_PERSONAL).setContent(i_mine));
        mBarTintManager.setStatusBarAlpha(0);
        homeRadioButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.home_tab_main:
                        tabhost.setCurrentTabByTag(TAB_MAIN);
                        mBarTintManager.setStatusBarAlpha(0);
                        break;
                    case R.id.home_tab_ad:
                        tabhost.setCurrentTabByTag(TAB_AD);
                        mBarTintManager.setStatusBarAlpha(0);
                        break;
                    case R.id.home_tab_shopping_cart:
                        if (caches.get("access_token").equals("null")) {
                            homeTabMain.setChecked(true);
                            homeTabShoppingCart.setChecked(false);
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    } else {
                        tabhost.setCurrentTabByTag(TAB_CART);
                        mBarTintManager.setStatusBarAlpha(0);
                    }

                        break;
                    case R.id.home_tab_user:
                        if (caches.get("access_token").equals("null")) {
                            homeTabMain.setChecked(true);
                            homeTabUser.setChecked(false);
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                        } else {
                            tabhost.setCurrentTabByTag(TAB_PERSONAL);
                            mBarTintManager.setStatusBarAlpha(0);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }
    /**
     * 跳转到个人中心
     */
    public static void personalcenter(){
        homeTabMain.setChecked(false);
        homeTabUser.setChecked(true);
        tabhost.setCurrentTabByTag(TAB_PERSONAL);
        mBarTintManager.setStatusBarAlpha(0);
    }
    /**
     * 跳转到分类
     */
    public static void classification(){
        homeTabMain.setChecked(false);
        homeTabAd.setChecked(true);
        tabhost.setCurrentTabByTag(TAB_AD);
        mBarTintManager.setStatusBarAlpha(0);
    }

    private void login() {
        OkHttpUtils.post().url(Urls.LOGIN)
                .addParams("mobile", mobile)
                .addParams("password", password)
                .build()
                .execute(new LoginCallBack());

    }

    private class LoginCallBack extends Callback<LoginEntity> {
        @Override
        public LoginEntity parseNetworkResponse(Response response, int id) throws Exception {
            String s = response.body().string();
            LoginEntity loginEntity = new Gson().fromJson(s, new TypeToken<LoginEntity>() {
            }.getType());

            return loginEntity;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            if(e == null||e.getMessage() == null){
                return;
            }
            if (e!=null&&e.getMessage()!=null&&e.getMessage().toString().contains("401")){
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            }else {
                Toast.makeText(HomeActivity.this,"网络链接错误 ",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onResponse(LoginEntity response, int id) {
            if(response.code == 200){
                caches.put("access_token", Config.TOKENHEADER + response.data);
                caches.put("php_token",response.data);
            }else {
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }

        }
    }
}
