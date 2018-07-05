package com.lcsd.police.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lcsd.police.AppContext;
import com.lcsd.police.Http;
import com.lcsd.police.R;
import com.lcsd.police.adapter.SyListAdapter;
import com.lcsd.police.entity.LeaveMessage;
import com.lcsd.police.entity.Sy;
import com.lcsd.police.manager.UpdateManager;
import com.lcsd.police.util.L;
import com.lcsd.police.view.ScrollViewWithListView;
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
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    @Bind(R.id.ptr_main)
    PtrClassicFrameLayout ptr;
    @Bind(R.id.lv_main)
    ScrollViewWithListView lv;
    @Bind(R.id.rl_lyfk)
    LinearLayout rl_left;
    @Bind(R.id.rl_qly)
    LinearLayout rl_right;
    @Bind(R.id.main_ed_titlebar)
    EditText editText;
    @Bind(R.id.tv_main_num)
    TextView tv_num;
    @Bind(R.id.sc_main)
    ScrollView sc;
    @Bind(R.id.iv_mian_top)
    ImageView iv_main_top;
    @Bind(R.id.main_view)View v;
    private List<Sy.TPageProject> mList;
    private SyListAdapter adapter;
    private TextView tv_empty;
    private View emptyView;
    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    List<String> mPermissionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        initData();
        request_main(false);
        request_num();
        requestPemission();
    }
    /**
     * 权限请求
     */
    private void requestPemission() {
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            new UpdateManager(this);
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[i]);
                    if (!showRequestPermission) {
                        Toast.makeText(this, "请前往设置打开存储权限，否则会影响APP的使用！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "请允许应用读取存储权限，否则会影响APP的使用！", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
            }
            new UpdateManager(this);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void initData() {
        editText.setOnClickListener(this);
        ptr.setLastUpdateTimeRelateObject(this);
        ptr.disableWhenHorizontalMove(true);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // 下拉刷新操作
                request_main(true);
                request_num();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, sc, header);
            }
        });
        rl_left.setOnClickListener(this);
        rl_right.setOnClickListener(this);
        mList=new ArrayList<>();
        adapter=new SyListAdapter(mContext,mList);
        lv.setAdapter(adapter);
        emptyView = View.inflate(this, R.layout.item_empty, null);
        tv_empty = (TextView) emptyView.findViewById(R.id.empty_tv);
        v.setVisibility(View.VISIBLE);
        addContentView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        lv.setEmptyView(emptyView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext,SecondLevelActivity.class).putExtra("id",mList.get(position).getItems()).putExtra("url",mList.get(position).getLinkurl()).putExtra("title",mList.get(position).getTitle()));
            }
        });
    }
    private void request_num() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "words");
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
                           tv_num.setText(leavemessage.getPsizeNum());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
            }
        });
    }
    private void request_main(final boolean b) {
        Map<String,String> map=new HashMap<>();
        map.put("c","index");
        AppContext.getInstance().getmMyOkHttp().post(mContext, Http.Http_Index, map, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                if (response!=null){
                    L.d("首页数据",response);
                    try {
                        Gson gson=new Gson();
                        Sy sy=gson.fromJson(response,Sy.class);
                        if(sy!=null&&sy.getPageProject()!=null&&sy.getPageProject().size()>0){
                            v.setVisibility(View.GONE);
                            if(b){
                                mList.clear();
                            }
                            mList.addAll(sy.getPageProject());
                            adapter.notifyDataSetChanged();
                        }
                        if(sy!=null&&sy.getHeadad()!=null&&sy.getHeadad().size()>0){
                            Glide.with(mContext).load(sy.getHeadad().get(0).getThumb()).centerCrop().into(iv_main_top);
                        }
                        if(mList.size()==0){
                            v.setVisibility(View.VISIBLE);
                            tv_empty.setText("暂无数据");
                            emptyView.findViewById(R.id.empty_progress).setVisibility(View.GONE);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        if(mList.size()==0){
                            v.setVisibility(View.VISIBLE);
                        }
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
                if(mList.size()==0){
                    v.setVisibility(View.VISIBLE);
                }
                tv_empty.setText("网络异常");
                emptyView.findViewById(R.id.empty_progress).setVisibility(View.GONE);
                if (b) {
                    ptr.refreshComplete();
                }
            }
        });
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    /**
     * 返回键两次退出程序
     */
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_lyfk:
                startActivity(new Intent(mContext, LeaveMessageListActivity.class));
                break;
            case R.id.rl_qly:
                startActivity(new Intent(mContext, LeaveMessageActivity.class));
                break;
            case R.id.main_ed_titlebar:
                startActivity(new Intent(mContext, SearchActivity.class));
                break;
        }
    }
}
