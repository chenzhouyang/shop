package com.yskj.shop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yskj.shop.R;
import com.yskj.shop.config.Urls;
import com.yskj.shop.dialog.MyLoading;
import com.yskj.shop.entity.LocationListEntity;
import com.yskj.shop.entity.ResultEntity;
import com.yskj.shop.ui.PerfectActivity;
import com.yskj.shop.utils.LoadingCaches;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

import static com.yskj.shop.ui.LocationListActivity.CONFIRM;
import static com.yskj.shop.ui.LocationListActivity.SHUAXIN;


/**
 * Created by YSKJ-02 on 2017/1/14.
 */

public class AddressAdapter extends BaseAdapter {
    private ArrayList<LocationListEntity.DataBean.AddressListBean> addressList;
    // 用来控制CheckBox的选中状况
    private HashMap<Integer, Boolean> isSelected;
    private MyLoading myloading;
    private Handler handler;
    private Context context;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private int id;
    public AddressAdapter(Context context, ArrayList<LocationListEntity.DataBean.AddressListBean> addressList, Handler handler){
        super();
        this.addressList = addressList;
        this.context = context;
        this.handler = handler;
        init();
    }
    // 初始化 设置所有checkbox都为未选择
    public void init() {
        if(addressList!=null&&addressList.size()!=0){
            isSelected = new HashMap<>();
            for (int i = 0; i < addressList.size(); i++) {
                isSelected.put(i, false);
            }
        }

    }
    @Override
    public int getCount() {
        if(addressList!=null&&addressList.size()!=0){
            return addressList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return addressList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_orchard_address, null);
            mHolder = new ViewHolder();
            mHolder.adderss_phone = (TextView) convertView.findViewById(R.id.adderss_phone);
            mHolder.address_name = (TextView) convertView.findViewById(R.id.address_name);
            mHolder.xiangxi_dizhi = (TextView) convertView.findViewById(R.id.xiangxi_dizhi);
            mHolder.danxunkuang = (RadioButton) convertView.findViewById(R.id.danxunkuang);
            mHolder.item_addtess_del = (TextView) convertView.findViewById(R.id.item_addtess_del);
            mHolder.item_addtess_amend = (TextView) convertView.findViewById(R.id.item_addtess_amend);
            mHolder.bianji = convertView.findViewById(R.id.bianji);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.address_name.setText(addressList.get(position).name);
        mHolder.xiangxi_dizhi.setText(addressList.get(position).province+addressList.get(position).city+addressList.get(position).region+addressList.get(position).addr);
        mHolder.adderss_phone.setText(addressList.get(position).mobile);
        if ((addressList.get(position).defAddr==1)) {
            mHolder.danxunkuang.setChecked(true);
            mHolder.danxunkuang.setText("默认地址");
            mHolder.item_addtess_amend.setVisibility(View.INVISIBLE);
        } else {
            mHolder.danxunkuang.setChecked(false);
            mHolder.danxunkuang.setText("设为默认地址");
        }
        mHolder.item_addtess_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("系统提示")//设置对话框标题
                        .setMessage("确定删除？")//设置显示的内容
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                startMyDialog();
                                OkHttpUtils.get().url(Urls.DELETEADDRESS)
                                        .addParams("token",caches.get("php_token"))
                                        .addParams("addr_id",addressList.get(position).addrId+"")
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

                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        ResultEntity resultEntity = new Gson().fromJson(response,ResultEntity.class);
                                        if(resultEntity.result == 1){
                                            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                            Message message =new Message();
                                            message.what=SHUAXIN;
                                            handler.handleMessage(message);
                                        }else {
                                            Toast.makeText(context,resultEntity.message, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                    public void onClick(DialogInterface dialog, int which) {//响应事件

                        dialog.dismiss();
                    }
                }).show();//在按键响应事件中显示此对话框

            }
        });

        //更改默认地址
        mHolder.danxunkuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.get().url(Urls.MORENADDRES)
                        .addParams("token",caches.get("php_token"))
                        .addParams("addr_id",addressList.get(position).addrId+"")
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
                        stopMyDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ResultEntity resultEntity = new Gson().fromJson(response,ResultEntity.class);
                        if(resultEntity.result == 1){
                            stopMyDialog();
                            Toast.makeText(context, "设置成功", Toast.LENGTH_SHORT).show();
                            if(caches.get("addresscode").equals("0")){
                                Message message =new Message();
                                message.what=CONFIRM;
                                handler.handleMessage(message);
                            }else {
                                Message message =new Message();
                                message.what=SHUAXIN;
                                handler.handleMessage(message);
                            }
                        }else{
                            Toast.makeText(context, resultEntity.message, Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }
        });
        mHolder.item_addtess_amend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationListEntity.DataBean.AddressListBean addressEntity = addressList.get(position);
                Intent intent = new Intent(context, PerfectActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("address",  addressEntity);
                mBundle.putString("addresstype","1");
                intent.putExtras(mBundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }





    /**
     * 使用ViewHolder来优化listview
     *
     * @author yanbin
     */
    private class ViewHolder {
        TextView address_name, adderss_phone, xiangxi_dizhi,item_addtess_amend,item_addtess_del;
        RadioButton danxunkuang;
        View bianji;
    }

    public HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }
    /**
     * dialog 启动
     */
    public void startMyDialog() {
        if (myloading == null) {
            myloading = MyLoading.createLoadingDialog(context);
        }
        myloading.show();
    }

    /**
     * dialog 销毁
     */
    public void stopMyDialog() {
        if (myloading != null) {
            myloading.dismiss();
            myloading = null;
        }
    }
}
