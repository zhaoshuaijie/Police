package com.lcsd.police.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lcsd.police.AppContext;
import com.lcsd.police.Http;
import com.lcsd.police.R;
import com.lcsd.police.util.StringUtil;
import com.lcsd.police.adapter.SearchAdapter;
import com.lcsd.police.entity.Search;
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

public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private TextView tv_empty;
    private View emptyView;
    private List<Search.TRslist> list;
    private SearchAdapter adapter;
    private Integer pageid = 1, total = 1;
    private boolean isOne = true;
    private String key;
    @Bind(R.id.tv_search_back)
    TextView tv_back;
    @Bind(R.id.et_search)
    EditText et;
    @Bind(R.id.ptr_search)
    PtrClassicFrameLayout ptr;
    @Bind(R.id.lv_search)
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);
        mContext = this;
        initData();
    }

    @Override
    protected void initData() {
        tv_back.setOnClickListener(this);
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //实现自己的搜索逻辑
                    if (StringUtil.isEmpty(et.getText().toString())) {
                        Toast.makeText(mContext, "请输入要查询的内容 ", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    key = et.getText().toString();
                    request_search(0);
                    return true;
                }
                return false;
            }
        });
        emptyView = View.inflate(this, R.layout.item_empty, null);
        tv_empty = (TextView) emptyView.findViewById(R.id.empty_tv);
        ptr.setLastUpdateTimeRelateObject(this);
        ptr.disableWhenHorizontalMove(true);
        ptr.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                request_search(2);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                request_search(1);
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
                if (key != null && key.length() > 0) {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv, header);
                } else {
                    return false;
                }
            }
        });
        list = new ArrayList<>();
        adapter = new SearchAdapter(mContext, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).getDfiles() != null && list.get(position).getDfiles().length() > 0) {
                    startActivity(new Intent(mContext, PdfActivity.class).putExtra("url", list.get(position).getDfiles()).putExtra("title", list.get(position).getCate_name()));
                } else {
                    startActivity(new Intent(mContext, DetailsActivity.class).putExtra("url", list.get(position).getUrl()).putExtra("title", list.get(position).getCate_name()));
                }
            }
        });
    }

    private void request_search(final int i) {
        if (isOne) {
            addContentView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            lv.setEmptyView(emptyView);
        }
        isOne = false;
        if (i == 1) {
            pageid = 1;
        }
        if (i == 2 && pageid < total) {
            pageid++;
        }
        Map<String, String> map = new HashMap<>();
        map.put("c", "search");
        map.put("keywords", key);
        map.put("pageid", pageid + "");
        AppContext.getInstance().getmMyOkHttp().post(mContext, Http.Http_Index, map, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                if (response != null) {
                    L.d("搜素：", response);
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equals("ok")) {
                            Gson gson = new Gson();
                            Search search = gson.fromJson(object.getString("content"), Search.class);
                            total = search.getTotal_page();
                            if (search != null && search.getRslist() != null) {
                                if (i == 0 || i == 1) {
                                    list.clear();
                                }
                                list.addAll(search.getRslist());
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(mContext, object.getString("content"), Toast.LENGTH_SHORT).show();
                        }
                        if (list.size() == 0) {
                            tv_empty.setText("无该关键字相关内容，换个试试？");
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
            case R.id.tv_search_back:
                finish();
                break;
        }
    }
}
