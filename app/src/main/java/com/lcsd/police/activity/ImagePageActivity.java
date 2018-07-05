package com.lcsd.police.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lcsd.police.R;
import com.lcsd.police.photoview.PhotoView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImagePageActivity extends BaseActivity {
    @Bind(R.id.img_tv)
    TextView tv;
    @Bind(R.id.img_viewpager)
    ViewPager vp;
    private String[] img;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_page);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (getIntent() != null) {
            img = getIntent().getStringArrayExtra("imgs");
            index = getIntent().getIntExtra("index", 0);
        }
        initData();
    }

    @Override
    protected void initData() {
        tv.setText((index + 1) + "/" + img.length);
        vp.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        vp.setAdapter(pagerAdapter);
        vp.setCurrentItem(index);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tv.setText(position + 1 + "/" + img.length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return img.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            PhotoView view = new PhotoView(ImagePageActivity.this);
            view.enable();
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            String s = img[position].substring(0, 4);
            Glide.with(ImagePageActivity.this).load(img[position]).into(view);
            container.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImagePageActivity.this.finish();
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }
}
