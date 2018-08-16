package com.sky.wang.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sky.wang.R;
import com.sky.wang.model.bean.DataInfo;

import java.util.List;

/**
 * Created by bluesky on 2018/7/30.
 */

public class PullToRefreshAdapter extends BaseQuickAdapter<DataInfo.NormalBean.ListBeanX> {


    public PullToRefreshAdapter(int layoutResId, List<DataInfo.NormalBean.ListBeanX> data) {
        super(layoutResId, data);
    }

    public PullToRefreshAdapter(List<DataInfo.NormalBean.ListBeanX> data) {
        super(data);
    }

    public PullToRefreshAdapter(View contentView, List<DataInfo.NormalBean.ListBeanX> data) {
        super(contentView, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DataInfo.NormalBean.ListBeanX listBeanX) {
            baseViewHolder.setText(R.id.tv_title,listBeanX.getUser().getUsername());
        Glide.with(mContext).load(listBeanX.getUser().getProfile_image()).into((ImageView)baseViewHolder.getView(R.id.imageView));
    }
}
