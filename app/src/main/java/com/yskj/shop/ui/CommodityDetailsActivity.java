package com.yskj.shop.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.adapter.ProperGridViewAdapter;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.CommodityEntity;
import com.yskj.shop.entity.ResultEntity;
import com.yskj.shop.home.ShoppingCartActivity;
import com.yskj.shop.login.LoginActivity;
import com.yskj.shop.utils.GlideImage;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.StringUtils;
import com.yskj.shop.widget.NoScrollGridView;
import com.yskj.shop.widget.NoScrollListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：陈宙洋
 * 日期：2017/8/12.
 * 描述：商品详情
 */

public class CommodityDetailsActivity extends BaseActivity implements View.OnClickListener{
    private TextView tvBuy,goods_integral;//购买按钮
    private ConvenientBanner convenientBanner;//轮播组件
    private CommodityEntity commodityEntity;//商品详情实体
    private ArrayList image = new ArrayList();//轮播数组
    private WebView commodity_web;//图文详情
    private LinearLayout commodity_property;//查看商品属性
    private PopupWindow mPropertyWindow;//弹出框
    private TextView nameTv,priceTv,tv_good_art,propertyTv;
    private EditText amountTv;
    private List<CommodityEntity.DataBean.SpecListBean> standardlist = new ArrayList<>();
    private List<CommodityEntity.DataBean.ProductListBean> productList = new ArrayList<>();
    private int mCount = 1;
    private Map<Integer, String> tagList = new HashMap<>();
    private String allitemid =null;
    private ArrayList<String> midList ;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private ArrayList<String> midLists ;
    private CommodityEntity.DataBean.ProductListBean productAdapterList;
    private ImageView imgIcon,imageBack;
    private ResultEntity resultEntity;//通用返回实体
    private String goodsId;
    private TextView goods_name,goods_price,tv_put_cart,txt_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_orchard_details);
        iniview();
        getCommodity();
    }

    private void iniview() {
        AppManager.getInstance().killActivity(LoginActivity.class);
        mCount = 1;
        goodsId = getIntent().getStringExtra("id");
        tvBuy = (TextView) findViewById(R.id.tv_buy);
        goods_integral = (TextView) findViewById(R.id.goods_integral);
        imageBack = (ImageView) findViewById(R.id.img_back);
        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        commodity_web = (WebView) findViewById(R.id.commodity_web);
        goods_name = (TextView) findViewById(R.id.goods_name);
        goods_price = (TextView) findViewById(R.id.goods_price);
        tv_put_cart = (TextView) findViewById(R.id.tv_put_cart);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText("商品详情");
        commodity_property = (LinearLayout) findViewById(R.id.commodity_property);
        commodity_web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        tvBuy.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        tv_put_cart.setOnClickListener(this);
        commodity_property.setOnClickListener(this);
    }
//获取商品详情数据
    private void getCommodity(){
        OkHttpUtils.get().url(Urls.COMMODITY).addParams("id",goodsId).build().execute(new StringCallback() {
            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                stopMyDialog();
            }

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                startMyDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                isLogin(e);
            }

            @Override
            public void onResponse(String response, int id) {
                commodityEntity = new Gson().fromJson(response,CommodityEntity.class);
                if(commodityEntity.result == 1){
                    image.clear();
                    List<CommodityEntity.DataBean.GalleryListBean> adList = commodityEntity.data.galleryList;
                    if (adList != null && adList.size() != 0) {
                        int size = adList.size();
                        for (int i = 0; i < size; i++) {
                            image.add(adList.get(i).original.replace("localhost","192.168.0.220"));
                        }
                    } else {
                        return;
                    }
                    //初始化首页轮播图
                    initCB();
                    //显示图文详情
                    commodity_web.loadDataWithBaseURL("about:blank", "<style>img {width: 100%;}</style>" +commodityEntity.data.intro, "text/html", "utf-8", null);
                    //规格
                    if(commodityEntity.data.haveSpec != 0){
                        standardlist.addAll(commodityEntity.data.specList);
                    }
                    //包含的规格库存
                    productList.addAll(commodityEntity.data.productList);
                    goods_name.setText(commodityEntity.data.name);
                    goods_price.setText("¥"+ StringUtils.getStringtodouble(commodityEntity.data.price));
                    if((int)(commodityEntity.data.point/100)<=0){
                        goods_integral.setVisibility(View.GONE);
                    }else {
                        goods_integral.setVisibility(View.VISIBLE);
                        goods_integral.setText("赠送"+(int)(commodityEntity.data.point/100)+"积分");
                    }
                }
            }
        });
    }

    //初始化首页轮播图
    private void initCB() {
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        convenientBanner.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, image)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.dot_dark, R.drawable.dot_red})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }
    //轮播图适配图片
    public class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            GlideImage.loadImage(context,imageView,data,R.mipmap.img_error);
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_buy:
                if(caches.get("access_token").equals("null")||caches.get("access_token")== null){
                    startActivity(new Intent(context,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                }else {
                    if(commodityEntity.data.haveSpec == 1){
                        showPropertyWindow(caches.get("php_token"));
                    }else {
                        setShoppingCart(commodityEntity.data.haveSpec,caches.get("php_token"));
                    }
                }
                break;
            case R.id.tv_put_cart:
                if(caches.get("access_token").equals("null")||caches.get("access_token")== null){
                    startActivity(new Intent(context,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                }else {
                    if(commodityEntity.data.haveSpec == 1){
                        showPropertyWindow(caches.get("php_token"));
                    }else {
                        setShoppingCart(commodityEntity.data.haveSpec,caches.get("php_token"));
                    }
                }
                break;
            case R.id.commodity_property:
                showPropertyWindow(caches.get("php_token"));
                break;
            case R.id.img_back:
                AppManager.getInstance().killActivity(CommodityDetailsActivity.class);
                break;
        }
    }

    /**
     * 添加购物车
     */
    private void setShoppingCart(int type,String token){
        if(type == 1){
            OkHttpUtils.post().url(Urls.ADDSHOPPINGCART)
                    .addParams("token",token)
                    .addParams("productid",productAdapterList.productId+"")
                    .addParams("num",mCount+"")
                    .build().execute(new AddShoppingCartCallBack());
        }else {
            OkHttpUtils.post().url(Urls.ADDSHOPPGOODS)
                    .addParams("token",token)
                    .addParams("goodsid",goodsId+"")
                    .addParams("num",mCount+"")
                    .build().execute(new AddShoppingCartCallBack());
        }

    }

    /**
     * 获取数据实例化
     */
    private class AddShoppingCartCallBack extends Callback<ResultEntity> {
        @Override
        public void onAfter(int id) {
            super.onAfter(id);
            stopMyDialog();
        }

        @Override
        public void onBefore(Request request, int id) {
            super.onBefore(request, id);
            startMyDialog();
        }

        @Override
        public ResultEntity parseNetworkResponse(Response response, int id) throws Exception {
            String s = response.body().string();
            resultEntity = new Gson().fromJson(s,ResultEntity.class);
            return resultEntity;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            isLogin(e);
        }

        @Override
        public void onResponse(ResultEntity response, int id) {
            if(response.result == 1){
                startActivity(new Intent(context,ShoppingCartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra("type", "1"));
            }
            showToast(response.message);
        }
    }
    //弹出商品规格
    private void showPropertyWindow(final String token) {
        if (mPropertyWindow == null) {
            View contentView = getLayoutInflater().inflate(R.layout.good_property_select_orchard_layout, null);
            mPropertyWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mPropertyWindow.setContentView(contentView);
//在PopupWindow里面就加上下面代码，让键盘弹出时，不会挡住pop窗口。
            mPropertyWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            mPropertyWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//点击空白处时，隐藏掉pop窗口
            mPropertyWindow.setFocusable(true);
            mPropertyWindow.setBackgroundDrawable(new BitmapDrawable());
            backgroundAlpha(1f);

            //添加pop窗口关闭事件
            mPropertyWindow.setOnDismissListener(new poponDismissListener());
            mPropertyWindow.setAnimationStyle(R.style.AnimationSEX);
            nameTv = (TextView) contentView.findViewById(R.id.tv_good_description);
            priceTv = (TextView) contentView.findViewById(R.id.tv_price);
            tv_good_art = (TextView) contentView.findViewById(R.id.tv_good_art);
            propertyTv = (TextView) contentView.findViewById(R.id.tv_select_property);
            ImageView imgClose = (ImageView) contentView.findViewById(R.id.imgView_close);
             imgIcon = (ImageView) contentView.findViewById(R.id.imgView_good_icon);
            final Button decreaseBtn = (Button) contentView.findViewById(R.id.btn_decrease);
            Button increaseBtn = (Button) contentView.findViewById(R.id.btn_increase);
            amountTv = (EditText) contentView.findViewById(R.id.tv_amount);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(amountTv.getWindowToken(), 0);
            amountTv.setCursorVisible(false);
            amountTv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String text = s.toString();
                    int len = s.toString().length();
                    if (len == 1 && text.equals("0")) {
                        amountTv.setText("1");
                        amountTv.setSelection(amountTv.getText().length());
                    }
                }
            });
            NoScrollListView propertyLv = (NoScrollListView) contentView.findViewById(R.id.lv_commodity_property);
            TextView cartTv = (TextView) contentView.findViewById(R.id.tv_put_true);
            final TextView confirmTv = (TextView) contentView.findViewById(R.id.tv_confirm);
            View placeholderView = contentView.findViewById(R.id.view_placeholder);
            if(commodityEntity.data.galleryList.size()>0){
                Glide.with(context).load(commodityEntity.data.galleryList.get(0).original.replace("localhost","192.168.0.220")).into(imgIcon);
//                GlideImage.loadImage(context, imgIcon, commodityEntity.data.galleryList.get(0).original.replace("localhost","192.168.0.220"), R.mipmap.add_bank_card);
            }

            priceTv.setText("价格：¥" + commodityEntity.data.price + "");
            if(commodityEntity.data.productList.size()>0){
                nameTv.setText("库存：" + commodityEntity.data.productList.get(0).store);
            }
            tv_good_art.setVisibility(View.GONE);
           /* if (commodityEntity.goodsBean.giveIntegral.length()!=0) {
                tv_good_art.setText("赠送" + Math.floor(Double.parseDouble(commodityEntity.goodsBean.giveIntegral)/3)+"积分");
            } else {
                tv_good_art.setVisibility(View.GONE);
            }*/
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.imgView_close:
                            mPropertyWindow.dismiss();
                            mPropertyWindow.setOnDismissListener(new poponDismissListener());
                            break;
                        case R.id.btn_decrease:
                            if (mCount == 1) {
                                decreaseBtn.setEnabled(false);
                            } else {
                                --mCount;
                                amountTv.setText(mCount + "");
                            }
                            break;
                        case R.id.btn_increase:
                            decreaseBtn.setEnabled(true);
                            ++mCount;
                            amountTv.setText(mCount + "");
                            break;
                        case R.id.tv_put_true:
                            if(caches.get("access_token").equals("null")||caches.get("access_token") == null){
                                AppManager.getInstance().killActivity(CommodityDetailsActivity.class);
                                startActivity(new Intent(context,LoginActivity.class));
                            }else {
                                    setShoppingCart(commodityEntity.data.haveSpec,caches.get("php_token"));

                            }
                            break;
                    }
                }
            };
            imgClose.setOnClickListener(clickListener);
            placeholderView.setOnClickListener(clickListener);
            decreaseBtn.setOnClickListener(clickListener);
            increaseBtn.setOnClickListener(clickListener);
            cartTv.setOnClickListener(clickListener);
            confirmTv.setOnClickListener(clickListener);
            propertyLv.setAdapter(new PropertyAdapter(context,standardlist));
        }
        mPropertyWindow.getContentView().findViewById(R.id.layout_bottom).setVisibility(View.VISIBLE);
        mPropertyWindow.getContentView().findViewById(R.id.tv_confirm).setVisibility(View.GONE);
        if (!mPropertyWindow.isShowing()) {
            mPropertyWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }
    //
    public class PropertyAdapter extends BaseAdapter {
        private  List<CommodityEntity.DataBean.SpecListBean> standardlist;
        private Context context;
        public PropertyAdapter(Context context, List<CommodityEntity.DataBean.SpecListBean> gridviewlist) {
            this.context = context;
            this.standardlist = gridviewlist;
            for (int i = 0; i < standardlist.size(); i++) {
                tagList.put(i, "");
            }
        }

        private int setposition(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return standardlist.size();
        }

        @Override
        public Object getItem(int position) {
            return standardlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHodler holder = null;
            if (convertView == null) {
                holder = new ViewHodler();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_orchard_property, null);
                holder.item_property_tv = (TextView) convertView.findViewById(R.id.item_property_tv);
                holder.item_property_gv = (NoScrollGridView) convertView.findViewById(R.id.item_property_gv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHodler) convertView.getTag();
            }
            holder.item_property_tv.setText(standardlist.get(position).specName);
            final ProperGridViewAdapter adapter = new ProperGridViewAdapter(context, standardlist.get(position).valueList);
            holder.item_property_gv.setAdapter(adapter);
            holder.item_property_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    adapter.setSeclection(pos);
                    adapter.notifyDataSetChanged();
                    String data = standardlist.get(position).valueList.get(pos).specValueId + "";
                    tagList.put(position,data);
                    allitemid = "";
                    midList = new ArrayList<String>();
                    midLists = new ArrayList<String>();
                    for (int i = 0; i < tagList.size(); i++) {
                        midList.add(tagList.get(i));
                        midLists.add(tagList.get(i));
                    }
                    Collections.sort(midList);
                    for (int i = 0; i < productList.size(); i++) {
                        if(productList.get(i).specsvIdJson.toString().equals(midList.toString().replace(" ",""))
                                ||productList.get(i).specsvIdJson.toString().equals(midLists.toString().replace(" ",""))){
                            productAdapterList = productList.get(i);
                        }
                    }
                    if(productAdapterList!=null){
                        priceTv.setText("价格：¥" + productAdapterList.price + "");
                        nameTv.setText("库存：" + productAdapterList.store+"");
                    }

                }
            });
            return convertView;
        }

        public class ViewHodler {
            TextView item_property_tv;
            NoScrollGridView item_property_gv;
        }
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }
}
