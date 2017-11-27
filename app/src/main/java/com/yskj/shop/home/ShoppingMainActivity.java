package com.yskj.shop.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yskj.shop.R;
import com.yskj.shop.adapter.GoodsAdapter;
import com.yskj.shop.adapter.HeadGridViewAdapter;
import com.yskj.shop.adapter.PutAwayAdapter;
import com.yskj.shop.adapter.ShoppingHeadGridViewAdapter;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Urls;
import com.yskj.shop.entity.GoodsEntity;
import com.yskj.shop.entity.PutAwayEntity;
import com.yskj.shop.entity.ShoppingHeadViewEntity;
import com.yskj.shop.ui.CommodityDetailsActivity;
import com.yskj.shop.utils.GlideCatchUtil;
import com.yskj.shop.widget.DividerGridItemDecoration;
import com.yskj.shop.widget.NoScrollGridView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;


/**
 * 作者：陈宙洋
 * 日期：2017/8/11.
 * 描述：商城首页
 */
public class ShoppingMainActivity extends BaseActivity implements View.OnClickListener {
    private XRecyclerView lvMallGoods;
    private ImageView ivReturn;
    private View headview;
    private TextView ed_keyword;
    private ConvenientBanner convenientBanner;
    private NoScrollGridView recycler_putaway, recycler_recommend;
    private NoScrollGridView recycler_view;
    private ArrayList image = new ArrayList();//头部轮播数组
    private final int SETHEAD = 100;
    private GoodsAdapter adapter;//商品列表
    private PutAwayAdapter putAwayAdapter;
    private List<PutAwayEntity.DataBean> putdatalistpage = new ArrayList<>();
    private List<PutAwayEntity.DataBean> putdatalistAll = new ArrayList<>();
    private GridLayoutManager layoutManager;
    private List<GoodsEntity> list;
    private int page = 0;
    int mDistance = 0;
    int maxDistance;//当距离在[0,最大值]变化时，透明度在[0,255之间变化]
    private boolean isaddheade = false;
    private boolean isRequesting = false;
    private PutAwayEntity putAwayEntity;
    private HeadGridViewAdapter headGridViewAdapter;
    private LinearLayout search_ll;
    private ImageView img_back,image_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orchard_shopmain);
        iniview();
        initListen();
    }

    //加载recyclerview 头部
    private void iniHeadView() {
        search_ll = (LinearLayout) findViewById(R.id.search_ll);
        img_back = (ImageView) findViewById(R.id.img_back);
        headview = LayoutInflater.from(context).inflate(R.layout.head_fragement_orchard_all, null);
        ed_keyword = (TextView) findViewById(R.id.ed_keyword);
        image_banner =(ImageView)  headview.findViewById(R.id.image_banner);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ed_keyword.getWindowToken(), 0);
        convenientBanner = (ConvenientBanner) headview.findViewById(R.id.convenientBanner);
        recycler_view = (NoScrollGridView) headview.findViewById(R.id.recycler_view);
        ed_keyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SearchActivity.class).putExtra("type", "shoppingmain"));
            }
        });
        //最新上架
        recycler_putaway = (NoScrollGridView) headview.findViewById(R.id.recycler_putaway);
        recycler_recommend = (NoScrollGridView) headview.findViewById(R.id.recycler_recommend);
        if (!isaddheade) {
            isaddheade = true;
            lvMallGoods.addHeaderView(headview);
            Glide.with(context).resumeRequests();
        }
        img_back.setOnClickListener(this);
        /**
         * 请求数据
         */

        //最新上架数据
//        putawaynewAcquisitions(100010, recycler_putaway);
//        //精品推荐
//        putawaynewAcquisitions(100011, recycler_recommend);
        //热销商品
        putaway();

        //开始自动翻页
        convenientBanner.startTurning(3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (convenientBanner != null) {
            convenientBanner.startTurning(5000);
        }

    }

    /**
     * 最新上架
     */
    private void putawaynewAcquisitions(int tigid, final NoScrollGridView noScrollGridView) {
        OkHttpUtils.get().url(Urls.MAINPUTAWAY).addParams("tagid", tigid + "")
                .addParams("start", page * 10 + "")
                .addParams("length", "10")
                .build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                isLogin(e);
            }

            @Override
            public void onResponse(String response, int id) {

                putAwayEntity = new Gson().fromJson(response, PutAwayEntity.class);
                if (putAwayEntity.result == 1) {
                    if (putAwayEntity.data.size() <= 4) {
                        headGridViewAdapter = new HeadGridViewAdapter(context, putAwayEntity.data);
                    } else {
                        headGridViewAdapter = new HeadGridViewAdapter(context, putAwayEntity.data.subList(0, 4));
                    }
                    noScrollGridView.setAdapter(headGridViewAdapter);
                    noScrollGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
                }
            }

        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
        GlideCatchUtil.getInstance().clearCacheMemory();
        GlideCatchUtil.getInstance().clearCacheDiskSelf();
    }

    private void iniview() {
        lvMallGoods = (XRecyclerView) findViewById(R.id.lv_mall_goods);
        ivReturn = (ImageView) findViewById(R.id.iv_return);

        iniHeadView();
        //为头部轮播添加图片数据
        image.clear();
        //初始化首页轮播图
        initCB();
        initHeadGridView();
    }

    //初始化首页轮播图
    private void initCB() {
        convenientBanner.setManualPageable(false);//设置不能手动影响
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        convenientBanner.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, image);
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.drawable.dot_dark, R.drawable.dot_red})
                //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 返回顶部
             */
            case R.id.iv_return:
                lvMallGoods.scrollToPosition(0);
                break;

        }
    }

    //轮播图适配图片
    public class NetworkImageHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    /**
     * 商品数据
     */
    private void putaway() {
        OkHttpUtils.get().url(Urls.MAINPUTAWAY).addParams("tagid", 100012 + "")
                .addParams("start", page * 10 + "")
                .addParams("length", "10")
                .build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                if(ShoppingMainActivity.this!=null){
                    startMyDialog();
                }

            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                if(ShoppingMainActivity.this!=null) {
                    stopMyDialog();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                isLogin(e);
            }

            @Override
            public void onResponse(String response, int id) {
                if(response==null){
                    return;
                }
                putAwayEntity = new Gson().fromJson(response, PutAwayEntity.class);
                if (putAwayEntity.result == 1) {
                    putdatalistpage.clear();
                    if (putAwayEntity.data.size() > 0) {
                        putdatalistpage.addAll(putAwayEntity.data);
                    } else {
                        if (page > 0) {
                            showToast("没有数据了");
                            lvMallGoods.setNoMore(true);
                        }

                    }
                    if (page == 0) {
                        putdatalistAll.clear();
                    }
                    putdatalistAll.addAll(putdatalistpage);
                    putAwayAdapter.notifyDataSetChanged();
                }
            }

        });

    }

    //轮播下gridview
    private void initHeadGridView() {
        ArrayList<ShoppingHeadViewEntity> allFragmentHeadViewEntityArrayList = new ArrayList<>();

        ShoppingHeadViewEntity allFragmentHeadViewEntity = new ShoppingHeadViewEntity();
        allFragmentHeadViewEntity.setName("分类");
        allFragmentHeadViewEntity.setIconId(R.mipmap.classify);
        allFragmentHeadViewEntityArrayList.add(allFragmentHeadViewEntity);
        ShoppingHeadViewEntity allFragmentHeadViewEntity2 = new ShoppingHeadViewEntity();
        allFragmentHeadViewEntity2.setName("生态农庄");
        allFragmentHeadViewEntity2.setIconId(R.mipmap.blended);
        allFragmentHeadViewEntityArrayList.add(allFragmentHeadViewEntity2);
        ShoppingHeadViewEntity allFragmentHeadViewEntity4 = new ShoppingHeadViewEntity();
        allFragmentHeadViewEntity4.setName("我的订单");
        allFragmentHeadViewEntity4.setIconId(R.mipmap.my_order);
        allFragmentHeadViewEntityArrayList.add(allFragmentHeadViewEntity4);
        ShoppingHeadViewEntity allFragmentHeadViewEntity1 = new ShoppingHeadViewEntity();
        allFragmentHeadViewEntity1.setName("个人中心");
        allFragmentHeadViewEntity1.setIconId(R.mipmap.android_my_jd_service_manager);
        allFragmentHeadViewEntityArrayList.add(allFragmentHeadViewEntity1);


        ShoppingHeadGridViewAdapter allFragmentHeadGridViewAdapter = new ShoppingHeadGridViewAdapter(context, allFragmentHeadViewEntityArrayList);
        recycler_view.setAdapter(allFragmentHeadGridViewAdapter);
    }

    private void initListen() {
        lvMallGoods.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        lvMallGoods.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 0;
                putaway();
                lvMallGoods.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                page++;
                if (page > 0) {
                    putaway();
                    lvMallGoods.refreshComplete();
                }
            }
        });
        lvMallGoods.setLoadingMoreEnabled(false);
        lvMallGoods.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                maxDistance = image_banner.getBottom() - search_ll.getHeight();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                mDistance+=dy;
                float percent = mDistance * 1f / maxDistance;//百分比
                int alpha = (int) (percent * 255);
                Drawable searchDrawable = null;
                if (alpha>=125){
                    searchDrawable = getResources().getDrawable(R.mipmap.img_new_local_search_gray);
                    searchDrawable.setBounds(0, 0, 39, 39);
                    ed_keyword.setCompoundDrawables(searchDrawable,null,null,null);
                    ed_keyword.setBackgroundResource(R.drawable.round_corner_999999);
                }else {
                    searchDrawable = getResources().getDrawable(R.mipmap.img_new_local_search);
                    searchDrawable.setBounds(0, 0, 39, 39);
                    ed_keyword.setCompoundDrawables(searchDrawable,null,null,null);
                    ed_keyword.setBackgroundResource(R.drawable.round_corner_f27a6b);
                }
                setSystemBarAlpha(alpha);
                if (lastVisibleItem >= 8) {
                    ivReturn.setVisibility(View.VISIBLE);
                } else {
                    ivReturn.setVisibility(View.GONE);
                }
            }
        });

        putAwayAdapter = new PutAwayAdapter(context, putdatalistAll);
        layoutManager = new GridLayoutManager(context, 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lvMallGoods.setLayoutManager(layoutManager);
        lvMallGoods.setHasFixedSize(true);
        lvMallGoods.addItemDecoration(new DividerGridItemDecoration(context, 21, R.drawable.divider));
        lvMallGoods.setAdapter(putAwayAdapter);
        putAwayAdapter.setOnItemClickListener(new PutAwayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(context, CommodityDetailsActivity.class)
                        .putExtra("id", putdatalistAll.get(position).goodsId + ""));
            }
        });
        ivReturn.setOnClickListener(this);
    }
    /**
     * 设置标题栏背景透明度
     * @param alpha 透明度
     */
    private void setSystemBarAlpha(int alpha) {
        if (alpha >= 255) {
            search_ll.setBackgroundColor(Color.WHITE);
        } else {
            //标题栏渐变。a:alpha透明度 r:红 g：绿 b蓝
            search_ll.setBackgroundColor(Color.argb(alpha, 255, 255, 255));//透明效果是由参数1决定的，透明范围[0,255]
        }

    }
}
