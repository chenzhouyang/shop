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


import com.yskj.shop.R;
import com.yskj.shop.entity.GoodsCategoryListEntity;
import com.yskj.shop.home.SearchActivity;
import com.yskj.shop.utils.GlideImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YSKJ-JH on 2017/1/12.
 */

public class GoodCateAdapter extends BaseAdapter {
    // 定义Context
    private LayoutInflater mInflater;
    private List<GoodsCategoryListEntity.DataBean.TmenuBeanX.TmenuBean> list;
    private Context context;
    private GoodsCategoryListEntity.DataBean.TmenuBeanX.TmenuBean type;
    public GoodCateAdapter(Context context, List<GoodsCategoryListEntity.DataBean.TmenuBeanX.TmenuBean> list){
        mInflater= LayoutInflater.from(context);
        this.list=list;
        this.context=context;
    }

    @Override
    public int getCount() {
        if(list!=null&&list.size()>0)
            return list.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyView view;
        if(convertView==null){
            view=new MyView();
            convertView=mInflater.inflate(R.layout.item_good_cate_orchard_type, null);
            view.icon=(ImageView)convertView.findViewById(R.id.typeicon);
            view.name=(TextView)convertView.findViewById(R.id.typename);
            view.goods_cate = (LinearLayout) convertView.findViewById(R.id.goods_cate);
            convertView.setTag(view);
        }else{
            view=(MyView) convertView.getTag();
        }
        if(list!=null&&list.size()>0)
        {
            view.name.setText(list.get(position).text);
            GlideImage.loadImage(context,view.icon,list.get(position).image,R.mipmap.img_error);
        }
        view.goods_cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SearchActivity.class).putExtra("type","adapter")
                        .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        .putExtra("cat",list.get(position).id+"")
                        .putExtra("name",list.get(position).text));
            }
        });


        return convertView;
    }
    private class MyView{
        private ImageView icon;
        private TextView name;
        private LinearLayout goods_cate;
    }
}
