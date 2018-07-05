package com.lcsd.police.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lcsd.police.AppContext;
import com.lcsd.police.Http;
import com.lcsd.police.R;
import com.lcsd.police.adapter.DetailsAdapter;
import com.lcsd.police.entity.DetailsList;
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

public class DetailListActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    @Bind(R.id.ptr_details)
    PtrClassicFrameLayout ptr;
    @Bind(R.id.ed_titlebar)
    EditText editText;
    @Bind(R.id.ll_titlebar_left)
    LinearLayout ll_back;
    @Bind(R.id.tv_titlebar_title)
    TextView tv_title;
    @Bind(R.id.lv_details)
    ListView lv;
    private TextView tv_empty;
    private View emptyView;
    private String id, title, cate;
    private List<DetailsList.TRslist> mList;
    private DetailsAdapter adapter;
    private Integer pageid = 1, total = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            cate = getIntent().getStringExtra("cate");
        }
        ButterKnife.bind(this);
        mContext = this;
        initData();
        request_details(0);
    }

    @Override
    protected void initData() {
        tv_title.setText(title);
        mList = new ArrayList<>();
        ll_back.setOnClickListener(this);
        editText.setOnClickListener(this);
        ptr.setLastUpdateTimeRelateObject(this);
        ptr.disableWhenHorizontalMove(true);
        ptr.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                request_details(2);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                request_details(1);
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
        emptyView = View.inflate(this, R.layout.item_empty, null);
        tv_empty = (TextView) emptyView.findViewById(R.id.empty_tv);
        adapter = new DetailsAdapter(mContext, mList);
        lv.setAdapter(adapter);
        addContentView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        lv.setEmptyView(emptyView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mList.get(position).getDfiles()!=null&&mList.get(position).getDfiles().length()>0){
                    startActivity(new Intent(mContext,PdfActivity.class).putExtra("url",mList.get(position).getDfiles()).putExtra("title",title));
                }else {
                    startActivity(new Intent(mContext, DetailsActivity.class).putExtra("url", mList.get(position).getUrl()).putExtra("title", title));
                }
            }
        });
    }


    private void request_details(final int i) {
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
        map.put("id", id);
        map.put("cate", cate);
        map.put("pageid", pageid + "");
        L.d("上传：", "id=" + id + "  cate=" + cate);
        AppContext.getInstance().getmMyOkHttp().post(mContext, Http.Http_Index, map, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                if (response != null) {
                    L.d("文章列表：", response);
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equals("ok")) {
                            Gson gson = new Gson();
                            DetailsList detailsList = gson.fromJson(object.getString("content"), DetailsList.class);
                            total = detailsList.getTotal();
                            if (detailsList != null && detailsList.getRslist() != null && detailsList.getRslist().size() > 0) {
                                if (i == 1) {
                                    mList.clear();
                                }
                                mList.addAll(detailsList.getRslist());
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
            case R.id.ll_titlebar_left:
                finish();
                break;
            case R.id.ed_titlebar:
                startActivity(new Intent(mContext, SearchActivity.class));
                break;
        }
    }
}
