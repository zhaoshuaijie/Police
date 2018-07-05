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
import com.google.gson.reflect.TypeToken;
import com.lcsd.police.AppContext;
import com.lcsd.police.R;
import com.lcsd.police.adapter.SecondAdapter;
import com.lcsd.police.entity.SecondLevel;
import com.lcsd.police.util.L;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class SecondLevelActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    @Bind(R.id.ptr_second)
    PtrClassicFrameLayout ptr;
    @Bind(R.id.ed_titlebar)
    EditText editText;
    @Bind(R.id.ll_titlebar_left)
    LinearLayout ll_back;
    @Bind(R.id.tv_titlebar_title)
    TextView tv_title;
    @Bind(R.id.lv_second)
    ListView lv;
    private TextView tv_empty;
    private View emptyView;
    private String items, title, url;
    private List<SecondLevel> mList;
    private SecondAdapter adapter;
    public static List<SecondLevel.TSublist> TLIST=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_level);
        if (getIntent() != null) {
            items = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            url = getIntent().getStringExtra("url");
        }
        ButterKnife.bind(this);
        mContext = this;
        initData();
        request_second(false);
    }

    @Override
    protected void initData() {
        tv_title.setText(title);
        mList = new ArrayList<>();
        ll_back.setOnClickListener(this);
        editText.setOnClickListener(this);
        ptr.setLastUpdateTimeRelateObject(this);
        ptr.disableWhenHorizontalMove(true);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // 下拉刷新操作
                request_second(true);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv, header);
            }
        });
        emptyView = View.inflate(this, R.layout.item_empty, null);
        tv_empty = (TextView) emptyView.findViewById(R.id.empty_tv);
        adapter = new SecondAdapter(mContext, mList);
        lv.setAdapter(adapter);
        addContentView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        lv.setEmptyView(emptyView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //有子分类
                if (mList.get(position).getSublist() != null && mList.get(position).getSublist().size() > 0) {
                    if(TLIST!=null){
                        TLIST.clear();
                    }
                    TLIST.addAll(mList.get(position).getSublist());
                    startActivity(new Intent(mContext, ThereLevelActivity.class).putExtra("id", items).putExtra("title", mList.get(position).getTitle()));
                } else {
                    //无子分类
                    startActivity(new Intent(mContext, DetailListActivity.class).putExtra("id", items).putExtra("title", mList.get(position).getTitle()).putExtra("cate", mList.get(position).getIdentifier()));
                }
            }
        });
    }
    private void request_second(final boolean b) {
        AppContext.getInstance().getmMyOkHttp().post(mContext, url, null, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                if (response != null) {
                    L.d("二级页面数据", response);
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("status").equals("ok")) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<ArrayList<SecondLevel>>() {
                            }.getType();
                            List<SecondLevel> list = gson.fromJson(object.getString("content"), type);
                            if (list != null && list.size() > 0) {
                                if (b) {
                                    mList.clear();
                                }
                                mList.addAll(list);
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
                }
                if (b) {
                    ptr.refreshComplete();
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                tv_empty.setText("网络异常");
                emptyView.findViewById(R.id.empty_progress).setVisibility(View.GONE);
                if (b) {
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
