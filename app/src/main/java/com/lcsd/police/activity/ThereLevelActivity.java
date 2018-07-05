package com.lcsd.police.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lcsd.police.R;
import com.lcsd.police.adapter.ThereAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class ThereLevelActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private String items, title;
    @Bind(R.id.ptr_there)
    PtrClassicFrameLayout ptr;
    @Bind(R.id.ed_titlebar)
    EditText editText;
    @Bind(R.id.ll_titlebar_left)
    LinearLayout ll_back;
    @Bind(R.id.tv_titlebar_title)
    TextView tv_title;
    @Bind(R.id.lv_there)
    ListView lv;
    private ThereAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_there_level);
        if (getIntent() != null) {
            items = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
        }
        ButterKnife.bind(this);
        mContext = this;
        initData();
    }

    @Override
    protected void initData() {
        tv_title.setText(title);
        ll_back.setOnClickListener(this);
        editText.setOnClickListener(this);
        ptr.setLastUpdateTimeRelateObject(this);
        ptr.disableWhenHorizontalMove(true);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // 下拉刷新操作
                ptr.refreshComplete();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv, header);
            }
        });
        adapter = new ThereAdapter(mContext, SecondLevelActivity.TLIST);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext,DetailListActivity.class).putExtra("id",items).putExtra("title",SecondLevelActivity.TLIST.get(position).getTitle()).putExtra("cate", SecondLevelActivity.TLIST.get(position).getIdentifier()));
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
