package com.lcsd.police.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lcsd.police.R;
import com.lcsd.police.entity.DetailsList;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/4.
 */
public class DetailsAdapter extends BaseAdapter {
    private Context mContext;
    private List<DetailsList.TRslist> list;

    public DetailsAdapter(Context mContext, List<DetailsList.TRslist> list) {
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
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_details, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.title.setText(list.get(position).getTitle());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_tv_details)
        TextView title;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
