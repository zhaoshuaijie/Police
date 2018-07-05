package com.lcsd.police.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lcsd.police.R;
import com.lcsd.police.util.StringUtil;
import com.lcsd.police.entity.Search;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/5.
 */
public class SearchAdapter extends BaseAdapter {
    private Context mContext;
    private List<Search.TRslist> list;

    public SearchAdapter(Context mContext, List<Search.TRslist> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_leave_message, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_time.setText(StringUtil.timeStamp2Date(list.get(position).getDateline()));
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_lm_title)
        TextView tv_title;
        @Bind(R.id.tv_lm_time)
        TextView tv_time;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
