package com.example.titlebarcolorgradient.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.titlebarcolorgradient.adapter.MyAdapter;
import com.example.titlebarcolorgradient.R;
import com.example.titlebarcolorgradient.utils.ColorUtil;
import com.example.titlebarcolorgradient.utils.DensityUtil;
import com.example.titlebarcolorgradient.utils.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_title_bg)
    View viewTitleBg;
    @BindView(R.id.rl_bar)
    RelativeLayout rlBar;
    @BindView(R.id.mlv)
    ListView mlv;
    @BindView(R.id.view_action_more_bg)
    View viewActionMoreBg;

    View headview;
    Context mcontext;
    MyAdapter madapter;
    ArrayList<String> datas = new ArrayList<>();
    //
    private boolean isScrollIdle = true; // ListView是否在滑动
    private View itemHeaderBannerView; // 从ListView获取的广告子View
    private int bannerViewTopMargin; // 广告视图距离顶部的距离
    private int bannerViewHeight = 240; // 广告视图的高度
    private int titleViewHeight = 65; // 标题栏的高度
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        mcontext=MainActivity.this;
        initData();
        process();
        setAllClick();
    }

    private void setAllClick() {
        mlv.setOnScrollListener(new ListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScrollIdle && bannerViewTopMargin < 0) return;
                // 获取广告头部View、自身的高度、距离顶部的高度
                if (itemHeaderBannerView == null) {
                    itemHeaderBannerView = mlv.getChildAt(firstVisibleItem);
                }
                if (itemHeaderBannerView != null) {
                    bannerViewTopMargin = DensityUtil.px2dip(mcontext, itemHeaderBannerView.getTop());
                    bannerViewHeight = DensityUtil.px2dip(mcontext, itemHeaderBannerView.getHeight());
                }
                // 处理标题栏颜色渐变
                handleTitleBarColorEvaluate();
            }
        });
        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(mcontext,SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    private void process() {
        madapter=new MyAdapter(mcontext,datas);
        mlv.setAdapter(madapter);
        headview= LayoutInflater.from(this).inflate(R.layout.header_mlv,null);
        mlv.addHeaderView(headview);
    }

    private void initData() {
        datas.add("张三");
        datas.add("张思");
        datas.add("张三");
        datas.add("张思");
        datas.add("张三");
        datas.add("张思");
        datas.add("张三");
        datas.add("张思");
        datas.add("张三");
        datas.add("张思");
        datas.add("张三");
        datas.add("张思");
        datas.add("张三");
        datas.add("张思");
    }
    // 处理标题栏颜色渐变
    private void handleTitleBarColorEvaluate() {
        float fraction;
        if (bannerViewTopMargin > 0) {
            fraction = 1f - bannerViewTopMargin * 1f / 60;
            if (fraction < 0f) fraction = 0f;
            rlBar.setAlpha(fraction);
            return ;
        }

        float space = Math.abs(bannerViewTopMargin) * 1f;
        fraction = space / (bannerViewHeight - titleViewHeight);
        if (fraction < 0f) fraction = 0f;
        if (fraction > 1f) fraction = 1f;
        rlBar.setAlpha(1f);

        if (fraction >= 1f ) {
            viewTitleBg.setAlpha(0f);
            viewActionMoreBg.setAlpha(0f);
            rlBar.setBackgroundColor(mcontext.getResources().getColor(R.color.colorPrimary));
        } else {
            viewTitleBg.setAlpha(1f - fraction);
            viewActionMoreBg.setAlpha(1f - fraction);
            rlBar.setBackgroundColor(ColorUtil.getNewColorByStartEndColor(mcontext, fraction, R.color.transparent, R.color.colorPrimary));
        }
    }
}
