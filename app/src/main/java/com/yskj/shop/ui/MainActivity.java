package com.yskj.shop.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.yskj.shop.AppManager;
import com.yskj.shop.R;
import com.yskj.shop.adapter.PutAdapter;
import com.yskj.shop.adapter.TradingAdapter;
import com.yskj.shop.base.BaseActivity;
import com.yskj.shop.config.Config;
import com.yskj.shop.config.Urls;
import com.yskj.shop.dialog.UtilsDialog;
import com.yskj.shop.entity.LineEntity;
import com.yskj.shop.entity.PutEntity;
import com.yskj.shop.entity.UserInfo;
import com.yskj.shop.utils.DoubleUtils;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.StringUtils;
import com.yskj.shop.widget.NoScrollListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lecho.lib.hellocharts.formatter.LineChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.Call;
import okhttp3.Request;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private float minY = 1f;//Y轴坐标最小值
    private float maxY = 2f;//Y轴坐标最大值
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private List<AxisValue> mAxisYValues = new ArrayList<AxisValue>();
    private LineChartView lineChart;
    private boolean hasAxesY = true; //是否需要Y坐标
    private String axesYName = "";//Y坐标名称
    private String axesXName = "";
    private boolean hasLines = true;//是否要折线连接
    private boolean hasPoints = true;//数据点是否要标注
    private ValueShape shape = ValueShape.CIRCLE;//数据标注点形状,这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
    private boolean isFilled = true;//是否需要填充和X轴之间的空间
    private boolean isCubic = false;//曲线是否平滑，即是曲线还是折线
    private boolean hasLabels = true;//数据点是否显示数据值
    private boolean hasLabelForSelected = true;//点击数据坐标提示数据（设置了这个hasLabels(true);就无效）
    private boolean hasTiltedLabels = true;  //X坐标轴字体是斜的显示还是直的，true是斜的显示
    private String lineColor = "#FFffff";//折现颜色(#FF0000红色)
    private int textColor = Color.WHITE;//设置字体颜色
    private List<String> date = new ArrayList<>();
    private List<Float> score = new ArrayList<>();
    private LoadingCaches caches = LoadingCaches.getInstance();
    private TextView tvGeneral, tvecology, tvbuyback, tvavailable, txtTitle, image_right;
    private NoScrollListView trading_record, order_list_lv;
    private ImageView imageBack, stoct_return, stoct_buy, stoct_sell;
    private RadioGroup group;
    private int type=0;
    private double outPrice = 0.00,changePrice = 0.00;
    private RadioButton stoct_my_putup, stoct_record;
    public static final int SHUAXIN = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case SHUAXIN:
                    getUserInfro();
                    myPut();
                    //type 1、购买扣除通用积分   2、回购扣除回购积分  3、卖出增加生态积分
                    outPrice = (Double) msg.obj;
                    if(type == 1){
                        if(isConclude){
                            myConclude();
                        }
                        date.clear();
                        score.clear();
                        initData();
                        trading();


//                        changePrice = DoubleUtils.sub(Double.parseDouble(tvGeneral.getText().toString()),outPrice);
//                        if(changePrice>=0){
//                            tvGeneral.setText(StringUtils.getStringtodouble(changePrice));
//                        }else {
//                            tvGeneral.setText("0.00");
//                        }

                    }else if(type == 2){
                        if(isConclude){
                            myConclude();
                        }
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private UtilsDialog dialog;
    private LineChartData chartData;
    private boolean isConclude = false;
    private double sellIntegral;
    private double buyBack;//回购积分
    private double generalIntegral;//通用积分

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniview();
        getUserInfro();
        initData();
        trading();
        myPut();
        finview();
    }

    private void iniview() {
        image_right = (TextView) findViewById(R.id.image_right);
        image_right.setVisibility(View.VISIBLE);
        image_right.setText("刷新");
        image_right.setOnClickListener(this);
        lineChart = (LineChartView) findViewById(R.id.cash_chart);
        tvGeneral = (TextView) findViewById(R.id.stoct_tvgeneral);
        tvecology = (TextView) findViewById(R.id.stoct_tvecology);
        tvbuyback = (TextView) findViewById(R.id.stoct_tvbuyback);
        tvavailable = (TextView) findViewById(R.id.stoct_tvavailable);
        stoct_return = (ImageView) findViewById(R.id.stoct_return);
        stoct_buy = (ImageView) findViewById(R.id.stoct_buy);
        stoct_sell = (ImageView) findViewById(R.id.stoct_sell);
        trading_record = (NoScrollListView) findViewById(R.id.trading_record);
        order_list_lv = (NoScrollListView) findViewById(R.id.order_list_lv);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        imageBack = (ImageView) findViewById(R.id.img_back);
        group = (RadioGroup) findViewById(R.id.group);
        stoct_my_putup = (RadioButton) findViewById(R.id.stoct_my_putup);
        stoct_record = (RadioButton) findViewById(R.id.stoct_record);
        stoct_record.setOnClickListener(this);
        stoct_my_putup.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        stoct_return.setOnClickListener(this);
        stoct_buy.setOnClickListener(this);
        stoct_sell.setOnClickListener(this);
        txtTitle.setText("积分动态");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                AppManager.getInstance().killActivity(MainActivity.class);
                break;
            case R.id.stoct_return:
                //回购币
                type=2;
                dialog = new UtilsDialog(context, "2", "回购积分",buyBack, handler);
                dialog.show();
                break;
            case R.id.stoct_buy:
                //购买
                type=1;
                dialog = new UtilsDialog(context, "1", "购买积分", generalIntegral,handler);
                dialog.show();
                break;
            case R.id.stoct_sell:
                //出售
                type=3;
                dialog = new UtilsDialog(context, "3", "出售积分", sellIntegral,handler);
                dialog.show();
                break;
            case R.id.image_right:
                //刷新
                date.clear();
                score.clear();
                initData();
                break;
        }

    }

    private void finview() {

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    //我的挂单
                    case R.id.stoct_my_putup:
                        isConclude = false;
                        myPut();
                        break;
                    //我的成交记录
                    case R.id.stoct_record:
                        isConclude = true;
                        myConclude();
                        break;

                }
            }
        });
    }

    /**
     * 交易记录
     */
    private void trading() {
        OkHttpUtils.get().url(Urls.HISTORY).addHeader("Authorization", caches.get("access_token"))
                .addParams("page", "1")
                .addParams("size", "3")
                .build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                startMyDialog();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                stopMyDialog();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                isLogin(e);
            }

            @Override
            public void onResponse(String response, int id) {
                LineEntity lineEntity = new Gson().fromJson(response, LineEntity.class);
                if (lineEntity.code == 200) {
                    TradingAdapter tradingAdapter = null;
                    if (lineEntity.data.list.size() <= 3) {
                        tradingAdapter = new TradingAdapter(context, lineEntity.data.list);
                    } else {
                        tradingAdapter = new TradingAdapter(context, lineEntity.data.list.subList(0, 3));
                    }

                    trading_record.setAdapter(tradingAdapter);
                } else {
                    showToast(lineEntity.message);
                }
            }
        });
    }

    /**
     * 我的挂单
     */
    private void myPut() {
        OkHttpUtils.get().url(Urls.PUT).addHeader("Authorization", caches.get("access_token"))
                .addParams("page", "1")
                .addParams("size", "100")
                .build().execute(new StringCallback() {
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
                PutEntity putEntity = new Gson().fromJson(response, PutEntity.class);
                if (putEntity.code == 200) {
                    PutAdapter putAdapter = new PutAdapter(context, putEntity.data.list,handler);
                    order_list_lv.setAdapter(putAdapter);
                } else {
                    showToast(putEntity.message);
                }

            }
        });
    }

    /**
     * 我的成交记录
     */
    private void myConclude() {
        OkHttpUtils.get().url(Urls.DEALS).addHeader("Authorization", caches.get("access_token"))
                .addParams("page", "1")
                .addParams("size", "100")
                .build().execute(new StringCallback() {
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
                LineEntity lineEntity = new Gson().fromJson(response, LineEntity.class);
                if (lineEntity.code == 200) {
                    TradingAdapter tradingAdapter = new TradingAdapter(context, lineEntity.data.list);
                    order_list_lv.setAdapter(tradingAdapter);
                } else {
                    showToast(lineEntity.message);
                }
            }
        });
    }

    /**
     * 折线图数据
     */
    private void initData() {
        OkHttpUtils.get().url(Urls.HISTORY).addHeader("Authorization", caches.get("access_token"))
                .addParams("type", "1")
                .build().execute(new StringCallback() {
            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                startMyDialog();
                lineChart.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                stopMyDialog();
                lineChart.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                isLogin(e);
            }

            @Override
            public void onResponse(String response, int id) {
                LineEntity lineEntity = new Gson().fromJson(response, LineEntity.class);
                if (lineEntity.code == 200) {
                    Collections.reverse(lineEntity.data.list); // 倒序排列
                    for (int i = 0; i < lineEntity.data.list.size(); i++) {
                        date.add(StringUtils.timeToDate(lineEntity.data.list.get(i).createTime));
                        score.add(lineEntity.data.list.get(i).price);
                    }
                    getAxisXYLables();//获取x轴的标注
                    getAxisPoints();//获取坐标点
                    initLineChart();//初始化
                } else {
                    showToast(lineEntity.message);
                }

            }
        });
    }

    private void getAxisXYLables() {
        mAxisXValues.clear();
        for (int i = 0; i < date.size(); i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date.get(i)));
        }
        for (float i = minY; i <= maxY; i += 0.25) {
            mAxisYValues.add(new AxisValue(i).setLabel(i + ""));
        }
    }

    private void getAxisPoints() {
        mPointValues.clear();
        for (int i = 0; i < score.size(); i++) {
            mPointValues.add(new PointValue(i, score.get(i)));
        }
    }

    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor(lineColor));  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
        LineChartValueFormatter chartValueFormatter = new SimpleLineChartValueFormatter(2);
        line.setFormatter(chartValueFormatter);//显示小数点
        line.setShape(shape);//折线图上每个数据点的形状
        line.setCubic(isCubic);//曲线是否平滑，即是曲线还是折线
        line.setFilled(isFilled);//是否填充曲线的面积
        line.setHasLabels(hasLabels);//曲线的数据坐标是否加上备注
        line.setPointRadius(3);// 设置节点半径
        line.setStrokeWidth(1);// 设置折线宽度
        line.setHasLabelsOnlyForSelected(false);//点击数据坐标提示数据
        line.setHasLines(hasLines);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(hasPoints);//是否显示圆点
        lines.add(line);



        //坐标轴X
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(hasTiltedLabels);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(textColor);  //设置字体颜色
        axisX.setName(axesXName);
        axisX.setTextSize(8);//设置字体大小
        axisX.setAutoGenerated(true);//自动布局
        axisX.setValues(mAxisXValues);
        LineChartData data = new LineChartData(lines);
        data.setAxisXBottom(axisX);//x 轴在底部
        data.setValueLabelBackgroundColor(R.color.stock_bg);// 设置数据背景颜色
        data.setValueLabelBackgroundEnabled(false);// 设置是否有数据背景
        data.setValueLabelsTextColor(Color.WHITE);// 设置数据文字颜色
        data.setValueLabelTextSize(8);// 设置数据文字大小
        data.setValueLabelTypeface(Typeface.DEFAULT);// 设置数据文字样式

        //坐标轴Y
        if (hasAxesY) {
            Axis axisY = new Axis();
            axisY.setHasLines(false);
            axisY.setName(axesYName);
            axisY.setTextColor(textColor);
            axisY.setTextSize(8);
            axisY.setValues(mAxisYValues);
            data.setAxisYLeft(axisY);
        }
        data.setLines(lines);
        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setZoomEnabled(false);//设置是否支持缩放
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);

        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.bottom = minY;
        v.top = maxY;
        //固定Y轴的范围,如果没有这个,Y轴的范围会根据数据的最大值和最小值决定,这不是我想要的
        lineChart.setMaximumViewport(v);
        lineChart.setCurrentViewport(v);
    }

    /**
     * 获取个人信息
     */
    public void getUserInfro() {
        OkHttpUtils.get().url(Urls.GETUSERINFO)
                .addHeader(Config.HEADER, caches.get("access_token"))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                isLogin(e);
            }

            @Override
            public void onResponse(String response, int id) {
                UserInfo userInfo = new Gson().fromJson(response, UserInfo.class);
                if (userInfo.code == 200&&userInfo.data!=null) {
                    buyBack = userInfo.data.buybackIntegral;
                    generalIntegral = userInfo.data.generalIntegral;
                    sellIntegral = userInfo.data.sellIntegral;
                    tvGeneral.setText(StringUtils.getStringtodouble(userInfo.data.generalIntegral));
                    tvecology.setText(StringUtils.getStringtodouble(userInfo.data.ecologyIntegral));
                    tvbuyback.setText(StringUtils.getStringtodouble(userInfo.data.originalIntegral));
                    tvavailable.setText(StringUtils.getStringtodouble(userInfo.data.availableIntegral));
                } else {
                    showToast(userInfo.message);
                }
            }
        });

    }

}


