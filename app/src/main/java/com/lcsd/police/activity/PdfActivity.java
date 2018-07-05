package com.lcsd.police.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.lcsd.police.R;
import com.lcsd.police.util.L;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PdfActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private String pdfurl, title;
    private int maxnum;
    @Bind(R.id.ll_titlebar_left2)
    LinearLayout ll_back;
    @Bind(R.id.tv_titlebar_title2)
    TextView tv_title;
    @Bind(R.id.pdfview)
    PDFView pdfView;
    private InputStream is;
    @Bind(R.id.pdf_progress)
    ProgressBar progressBar;
    @Bind(R.id.pdf_tv_index)
    TextView tv_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        if (getIntent() != null) {
            pdfurl = getIntent().getStringExtra("url");
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
        thread.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_titlebar_left2:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.interrupt();
        if(is!=null){
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (is != null) {
                    pdfView.fromStream(is)
                            .enableSwipe(true)
                            .enableDoubletap(true)
                            .swipeVertical(true)
                            .onLoad(new OnLoadCompleteListener() {
                                @Override
                                public void loadComplete(int nbPages) {
                                    progressBar.setVisibility(View.GONE);
                                    maxnum = nbPages;
                                    tv_index.setText("1/" + maxnum);
                                }
                            })
                            .onPageChange(new OnPageChangeListener() {
                                @Override
                                public void onPageChanged(int page, int pageCount) {
                                    L.d("pagechange：", page + "");
                                    tv_index.setText(page + "/" + maxnum);
                                }
                            })
                            .onError(new OnErrorListener() {
                                @Override
                                public void onError(Throwable t) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(mContext, "加载出错,请稍后重试！", Toast.LENGTH_SHORT).show();
                                    L.d("加载出错：", t.toString());
                                }
                            })
                            .enableAnnotationRendering(true)
                            .password(null)
                            .load();
                }
            } else if (msg.what == 2) {
                Toast.makeText(mContext, "网络出错", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }
    };
    Thread thread = new Thread() {
        @Override
        public void run() {
            super.run();
            try {
                URL url = new URL(pdfurl);
                HttpURLConnection connection = (HttpURLConnection)
                        url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                //实现连接
                connection.connect();

                if (connection.getResponseCode() == 200) {
                    is = connection.getInputStream();
                    //这里给过去就行了
                    handler.sendEmptyMessage(1);
                } else {
                    handler.sendEmptyMessage(2);
                }
            } catch (IOException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(2);
            }
        }
    };

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }
}
