package com.yskj.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yskj.shop.R;
import com.yskj.shop.config.Urls;
import com.yskj.shop.deposit.AddBankActivity;
import com.yskj.shop.entity.ApproveEntity;
import com.yskj.shop.entity.BankListEntity;
import com.yskj.shop.login.LoginActivity;
import com.yskj.shop.utils.JSONFormat;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.MessgeUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by YSKJ-02 on 2017/1/20.
 */

public class BankListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BankListEntity.DataBean.ListBean> list;
    private  BankListEntity.DataBean  databean;
    private int mRightWidth = 0;
    private LoadingCaches caches = LoadingCaches.getInstance();
    public BankListAdapter(Context context, ArrayList<BankListEntity.DataBean.ListBean> list, int rightWidth){
        super();
        this.context = context;
        this.list = list;
        this.mRightWidth = rightWidth;
    }
    @Override
    public int getCount() {
        if(list!= null&&list.size()!=0){
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewBankHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bank, parent, false);
            holder = new ViewBankHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewBankHolder) convertView.getTag();
        }

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(mRightWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        holder.item_right.setLayoutParams(lp2);
        holder.item_bank_name.setText(list.get(position).name);
        holder.item_bank_number.setText(list.get(position).cardNo);
        switch (list.get(position).name){
            case "建设银行":
                holder.item_bank_image.setImageResource(R.mipmap.jian_pay_ico);
                break;
            case "中国银行":
                holder.item_bank_image.setImageResource(R.mipmap.china_pay_ico);
                break;
            case "农业银行":
                holder.item_bank_image.setImageResource(R.mipmap.long_pay_ico);
                break;
            case "工商银行":
                holder.item_bank_image.setImageResource(R.mipmap.buess_pay_ico);
                break;
            case "招商银行":
                holder.item_bank_image.setImageResource(R.mipmap.zhao_pay_ico);
                break;
            case "交通银行":
                holder.item_bank_image.setImageResource(R.mipmap.jiao_pay_ico);
                break;
            case "支付宝":
                holder.item_bank_image.setImageResource(R.mipmap.zhifu_pay_ico);
                break;
        }
        holder.item_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils.post().url(Urls.DELBANK)
                        .addHeader("Authorization",caches.get("access_token"))
                        .addParams("id",list.get(position).id+"")
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if(e == null||e.getMessage() == null){
                            return;
                        }
                        if (e!=null&&e.getMessage()!=null&&e.getMessage().toString().contains("401")){
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }else {
                            Toast.makeText(context, "网络链接错误 ", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ApproveEntity approveEntity = new Gson().fromJson(response,ApproveEntity.class);
                        if(approveEntity.code == 200){
                            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                            list.remove(list.get(position));
                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(context, approveEntity.message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        holder.ll_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddBankActivity.class);
                intent.putExtra("cardid",list.get(position).id+"");
                intent.putExtra("type","1");
                context.startActivity(intent);
            }
        });
        return convertView;
    }
     class ViewBankHolder{
        ImageView item_bank_image;
        TextView item_bank_name,item_bank_number,tv_del;
        LinearLayout ll_layout;
         RelativeLayout item_right;

         public ViewBankHolder(View view) {
             item_bank_image = (ImageView)view.findViewById(R.id.item_bank_image);
             item_bank_name = (TextView)view.findViewById(R.id.item_bank_name);
             item_bank_number = (TextView)view.findViewById(R.id.item_bank_number);
             tv_del=(TextView)view.findViewById(R.id.item_right_txt);
             ll_layout = (LinearLayout) view.findViewById(R.id.ll_layout);
             item_right = (RelativeLayout) view.findViewById(R.id.item_right);

         }
    }
    /**
     * 单击事件监听器
     */
    private onRightItemClickListener mListener = null;

    public void setOnRightItemClickListener(onRightItemClickListener listener){
        mListener = listener;
    }

    public interface onRightItemClickListener {
        void onRightItemClick(View v, int position);
    }
}
