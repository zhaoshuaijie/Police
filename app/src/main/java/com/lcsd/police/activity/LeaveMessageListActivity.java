package com.lcsd.police.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lcsd.police.AppContext;
import com.lcsd.police.Http;
import com.lcsd.police.R;
import com.lcsd.police.adapter.LeaveMessageAdapter;
import com.lcsd.police.entity.LeaveMessage;
import com.lcsd.police.util.L;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class LeaveMessageListActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    @Bind(R.id.ll_titlebar_left2)
    LinearLayout ll_back;
    @Bind(R.id.tv_titlebar_title2)
    TextView tv_title;
    @Bind(R.id.ptr_leave)
    PtrClassicFrameLayout ptr;
    @Bind(R.id.lv_leave)
    ListView lv;
    private TextView tv_empty;
    private View emptyView;
    private List<LeaveMessage.TRslist> mList;
    private LeaveMessageAdapter adapter;
    private Integer pageid = 1, total = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_message_list);
        ButterKnife.bind(this);
        mContext = this;
        initData();
        request_lm(0);
    }

    @Override
    protected void initData() {
        tv_title.setText("留言反馈");
        ll_back.setOnClickListener(this);
        mList = new ArrayList<>();
        adapter = new LeaveMessageAdapter(mContext, mList);
        lv.setAdapter(adapter);
        emptyView = View.inflate(this, R.layout.item_empty, null);
        tv_empty = (TextView) emptyView.findViewById(R.id.empty_tv);
        addContentView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        lv.setEmptyView(emptyView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, DetailsActivity.class).putExtra("url", mList.get(position).getUrl()).putExtra("title", "留言反馈"));
            }
        });
        ptr.setLastUpdateTimeRelateObject(this);
        ptr.disableWhenHorizontalMove(true);
        ptr.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                request_lm(2);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                request_lm(1);
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                if (pageid < total) {
                    return super.checkCanDoLoadMore(frame, lv, footer);
                } else {
                    return false;
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv, header);
            }
        });

    }

    private void request_lm(final int i) {
        if (i == 2) {
            if (pageid < total) {
                pageid++;
            } else {
                ptr.refreshComplete();
                return;
            }
        }
        if (i == 1) {
            pageid = 1;
        }
        Map<String, String> map = new HashMap<>();
        map.put("id", "words");
        map.put("pageid",pageid+"");
        AppContext.getInstance().getmMyOkHttp().post(mContext, Http.Http_Index, map, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                if(response!=null){
                    L.d("留言反馈列表：",response);
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equals("ok")) {
                            Gson gson = new Gson();
                            LeaveMessage leavemessage = gson.fromJson(object.getString("content"), LeaveMessage.class);
                            total = leavemessage.getTotal();
                            if (leavemessage != null && leavemessage.getRslist() != null && leavemessage.getRslist().size() > 0) {
                                if (i == 1) {
                                    mList.clear();
                                }
                                mList.addAll(leavemessage.getRslist());
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(mContext, object.getString("content"), Toast.LENGTH_SHORT).show();
                        }
                        if (mList.size() == 0) {
                            tv_empty.setText("暂无数据");
                            emptyView.findViewById(R.id.empty_progress).setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        tv_empty.setText("网络异常");
                        emptyView.findViewById(R.id.empty_progress).setVisibility(View.GONE);
                    }
                    if (i == 1 || i == 2) {
                        ptr.refreshComplete();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                tv_empty.setText("网络异常");
                emptyView.findViewById(R.id.empty_progress).setVisibility(View.GONE);
                if (i == 1 || i == 2) {
                    ptr.refreshComplete();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_titlebar_left2:
                finish();
                break;
        }
    }
}
