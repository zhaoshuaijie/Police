package com.lcsd.police.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lcsd.police.R;
import com.lcsd.police.entity.Sy;
import com.lcsd.police.view.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/3.
 */
public class SyListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Sy.TPageProject> list;

    public SyListAdapter(Context mContext, List<Sy.TPageProject> list) {
        this.mContext = mContext;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        /*if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {*/
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_main, null);
        holder = new ViewHolder(convertView);
           /* convertView.setTag(holder);
        }*/
        if (position == list.size() - 1) {
            holder.view.setVisibility(View.GONE);
        }
        holder.title.setText(list.get(position).getTitle());
        Glide.with(mContext).load(list.get(position).getThumb()).fitCenter().into(holder.civ);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_tv_main)
        TextView title;
        @Bind(R.id.item_civ_mian)
        CircleImageView civ;
        @Bind(R.id.item_view)
        View view;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
