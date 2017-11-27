package com.yskj.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yskj.shop.AppManager;
import com.yskj.shop.home.HomeActivity;
import com.yskj.shop.ui.MainActivity;
import com.yskj.shop.R;
import com.yskj.shop.deposit.DepositToBankActivity;
import com.yskj.shop.entity.ShoppingHeadViewEntity;
import com.yskj.shop.login.LoginActivity;
import com.yskj.shop.ui.OrderListActivity;
import com.yskj.shop.utils.LoadingCaches;

import java.util.ArrayList;


public class ShoppingHeadGridViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ShoppingHeadViewEntity> arrayList;
    private LoadingCaches caches = LoadingCaches.getInstance();
    public ShoppingHeadGridViewAdapter(Context context, ArrayList<ShoppingHeadViewEntity> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        if (arrayList == null || arrayList.size() == 0)
            return 0;
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shopping_head_orchard_gridview,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(arrayList.get(position).getName());
        holder.imageView.setImageResource(arrayList.get(position).getIconId());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //按钮跳转事件
                switch (position){
                    case 0:
                        HomeActivity.classification();
                        break;
                    case 3:
                        if(caches.get("access_token").equals("null")){
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }else {
                            HomeActivity.personalcenter();
                        }
                        break;
                    case 2:
                        if(caches.get("access_token").equals("null")){
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }else {
                            AppManager.getInstance().killActivity(LoginActivity.class);
                            context.startActivity(new Intent(context, OrderListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).putExtra("orderstaut","1"));
                        }
                        break;
                    case 1:
                        if(caches.get("access_token").equals("null")){
                            context.startActivity(new Intent(context,LoginActivity.class));
                        }else {
                            AppManager.getInstance().killActivity(LoginActivity.class);
                           context.startActivity(new Intent(context,MainActivity.class));
                        }
                        break;
                }
            }
        });
        return convertView;
    }
    class ViewHolder {
        ImageView imageView;
        TextView title;
        LinearLayout linearLayout;
        public ViewHolder(View itemview) {
            title = (TextView) itemview.findViewById(R.id.tv_title);
            imageView = (ImageView) itemview.findViewById(R.id.img);
            linearLayout = (LinearLayout) itemview.findViewById(R.id.item_all_fragment_head_ll);
        }
    }
}
