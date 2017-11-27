package com.yskj.shop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.yskj.shop.R;
import com.yskj.shop.config.Urls;


/**
 * Created by YSKJ-02 on 2017/1/17.
 */

public class OrderWebDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private ImageView dialog_close;
    private WebView dialog_web;
    public OrderWebDialog(Context context) {
        super(context, R.style.ShareDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_orderweb);
        initView();
    }

    private void initView() {
        dialog_close = (ImageView) findViewById(R.id.dialog_close);
        dialog_web = (WebView) findViewById(R.id.dialog_web);
        dialog_close.setOnClickListener(this);
        dialog_web.loadUrl(Urls.ORDERPCFG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_close:
                dismiss();
                break;
        }
    }
}
