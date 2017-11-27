package com.yskj.shop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.yskj.shop.R;
import com.yskj.shop.entity.GoodsCategoryListEntity;
import com.yskj.shop.widget.NoScrollGridView;

import java.util.List;

/**
 * Created by chenzhouyang on 2016/5/24 0024.
 */

public class CatograyReclasspyAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsCategoryListEntity.DataBean.TmenuBeanX> list;
    public CatograyReclasspyAdapter(Context context, List<GoodsCategoryListEntity.DataBean.TmenuBeanX> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        View view = convertView;
        final Viewholder viewholder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_orchard_category, null);
            viewholder = new Viewholder();
            viewholder.category_reclassify = (TextView) view.findViewById(R.id.category_reclassify);
            viewholder.listView = (NoScrollGridView) view.findViewById(R.id.listView);
            view.setTag(viewholder);
        } else {
            viewholder = (Viewholder) view.getTag();
        }
        viewholder.category_reclassify.setText(list.get(position).text);
        GoodCateAdapter goodCateAdapter = new GoodCateAdapter(context,list.get(position).tmenu);
        viewholder.listView.setAdapter(goodCateAdapter);
        viewholder.listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }
    class Viewholder {
        TextView category_reclassify;
        NoScrollGridView listView;
    }

}
