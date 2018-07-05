package com.lcsd.police.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lcsd.police.AppContext;
import com.lcsd.police.Http;
import com.lcsd.police.R;
import com.lcsd.police.util.StringUtil;
import com.lcsd.police.util.L;
import com.lcsd.police.view.CleanableEditText;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import butterknife.Bind;
import butterknife.ButterKnife;

public class LeaveMessageActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    @Bind(R.id.ll_titlebar_left2)
    LinearLayout ll_back;
    @Bind(R.id.tv_titlebar_title2)
    TextView tv_title;
    @Bind(R.id.et_leave_message1)
    CleanableEditText et_title;
    @Bind(R.id.et_leave_message2)
    CleanableEditText et_content;
    @Bind(R.id.tv_leave_message)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_message);
        ButterKnife.bind(this);
        mContext = this;
        initData();
    }

    @Override
    protected void initData() {
        tv_title.setText("留言");
        ll_back.setOnClickListener(this);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_titlebar_left2:
                finish();
                break;
            case R.id.tv_leave_message:
                if(StringUtil.isEmpty(et_title.getText().toString())){
                    Toast.makeText(mContext,"请输入标题",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(StringUtil.isEmpty(et_content.getText().toString())){
                    Toast.makeText(mContext,"请输入内容描述",Toast.LENGTH_SHORT).show();
                    return;
                }
                request_ly();
                break;
        }
    }

    private void request_ly() {
        Map<String,String> map=new HashMap<>();
        map.put("f","message");
        map.put("title",et_title.getText().toString());
        map.put("content",et_content.getText().toString());
        AppContext.getInstance().getmMyOkHttp().post(mContext, Http.Http_Index, map, new RawResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                if(response!=null){
                    L.d("留言：",response);
                    try {
                        JSONObject object=new JSONObject(response);
                        Toast.makeText(mContext,object.getString("content"),Toast.LENGTH_SHORT).show();
                        if(object.getString("status").equals("ok")){
                            LeaveMessageActivity.this.finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {

            }
        });
    }
}
