package com.yskj.shop.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yskj.shop.R;
import com.yskj.shop.config.Urls;
import com.yskj.shop.dialog.MyLoading;
import com.yskj.shop.entity.ApproveEntity;
import com.yskj.shop.entity.LineEntity;
import com.yskj.shop.entity.PutEntity;
import com.yskj.shop.login.LoginActivity;
import com.yskj.shop.utils.LoadingCaches;
import com.yskj.shop.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

import static com.yskj.shop.ui.MainActivity.SHUAXIN;

/**
 * Created by YSKJ-02 on 2017/1/20.
 */

public class PutAdapter extends BaseAdapter {
private Context context;
    private List<PutEntity.DataBean.ListBean> listBeen;
    private LoadingCaches caches = LoadingCaches.getInstance();
    private MyLoading myloading;
    private Activity activity;
    private Handler handler;
    public PutAdapter(Context context, List<PutEntity.DataBean.ListBean> listBeen,Handler handler){
        super();
        this.context = context;
        this.listBeen = listBeen;
        this.handler = handler;
        activity = (Activity) context;
    }
    @Override
    public int getCount() {
        if(listBeen!=null&&listBeen.size()!=0){
            return listBeen.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return listBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_put,null);
            holder.item_stause = (TextView) convertView.findViewById(R.id.item_stause);
            holder.item_time = (TextView) convertView.findViewById(R.id.item_time);
            holder.item_number = (TextView) convertView.findViewById(R.id.item_number);
            holder.item_money = (TextView) convertView.findViewById(R.id.item_money);
            holder.item_state = (TextView) convertView.findViewById(R.id.item_state);
            holder.revocation = (LinearLayout) convertView.findViewById(R.id.revocation);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.item_time.setText(StringUtils.timeRecordToDate(listBeen.get(position).createTime+""));
        if(listBeen.get(position).tradeNum>0){
            holder.item_number.setText(listBeen.get(position).num+"个\n(已成交"+listBeen.get(position).tradeNum+"个)");
        }else {
            holder.item_number.setText(listBeen.get(position).num+"个");
        }
        if(listBeen.get(position).type == 2){
            holder.item_stause.setText("卖出");
        }else {
            holder.item_stause.setText("买入");
        }
        holder.item_state.setText(StringUtils.getStringtodouble(listBeen.get(position).price));
        holder.revocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpUtils.post().url(Urls.CANCEL).addHeader("Authorization",caches.get("access_token"))
                        .addParams("orderId",listBeen.get(position).id+"")
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
                        if(e == null||e.getMessage() == null){
                            return;
                        }
                        if (e!=null&&e.getMessage()!=null&&e.getMessage().toString().contains("401")){
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }else {
                            Toast.makeText(context,"网络链接错误 ",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ApproveEntity approveEntity = new Gson().fromJson(response,ApproveEntity.class);
                        if(approveEntity.code == 200){
                            listBeen.remove(listBeen.get(position));
                            Toast.makeText(context,"撤销成功 ",Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                            Message message =new Message();
                            message.what=SHUAXIN;
                            message.obj =0.00;
                            handler.handleMessage(message);
                        }else {
                            Toast.makeText(context,approveEntity.message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return convertView;
    }
    public class ViewHolder{
        TextView item_stause,item_time,item_number,item_money,item_state;
        LinearLayout revocation;
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
        if (!activity.isFinishing()) {
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
            if (!activity.isFinishing()){
                try {
                    myloading.dismiss();
                }catch (IllegalArgumentException e){
                    e.getStackTrace();
                }
            }
            myloading = null;
        }
    }
}
