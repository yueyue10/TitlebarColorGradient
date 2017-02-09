package com.example.titlebarcolorgradient.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.titlebarcolorgradient.R;
import com.example.titlebarcolorgradient.adapter.MyAdapter;
import com.example.titlebarcolorgradient.model.FilterData;
import com.example.titlebarcolorgradient.model.FilterEntity;
import com.example.titlebarcolorgradient.model.FilterTwoEntity;
import com.example.titlebarcolorgradient.utils.ColorUtil;
import com.example.titlebarcolorgradient.utils.DensityUtil;
import com.example.titlebarcolorgradient.utils.ModelUtil;
import com.example.titlebarcolorgradient.utils.StatusBarUtil;
import com.example.titlebarcolorgradient.utils.ToastUtil;
import com.example.titlebarcolorgradient.view.FilterView;
import com.example.titlebarcolorgradient.view.HeaderFilterView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.view_title_bg)
    View viewTitleBg;
    @BindView(R.id.rl_bar)
    RelativeLayout rlBar;
    @BindView(R.id.mlv)
    ListView mlv;
    @BindView(R.id.view_action_more_bg)
    View viewActionMoreBg;
    @BindView(R.id.filterView)
    FilterView filterView;

    View headview;
    Context mcontext;
    MyAdapter madapter;
    ArrayList<String> datas = new ArrayList<>();
    //
    private boolean isScrollIdle = true; // ListView是否在滑动
    private View itemHeaderBannerView; // 从ListView获取的广告子View
    private View itemHeaderFilterView; // 从ListView获取的筛选子View
    private int bannerViewTopMargin; // 广告视图距离顶部的距离
    private int bannerViewHeight = 240; // 广告视图的高度
    private int titleViewHeight = 65; // 标题栏的高度
    //
    private Activity mActivity;
    private FilterData filterData; // 筛选数据
    private boolean isSmooth = false; // 没有吸附的前提下，是否在滑动
    private boolean isStickyTop = false; // 是否吸附在顶部
    private int filterViewTopMargin; // 筛选视图距离顶部的距离
    private int filterViewPosition = 1; // 筛选视图的位置-如果是5的话实际上已经指向了第6项
    private int filterPosition = -1; // 点击FilterView的位置：分类(0)、排序(1)、筛选(2)
    private HeaderFilterView headerFilterView; // 分类筛选视图
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        mcontext=SecondActivity.this;
        mActivity=this;
        initData();
        process();
        setAllClick();
    }


    private void initData() {
        datas.add("张三1");
        datas.add("张思2");
        datas.add("张三3");
        datas.add("张思4");
        datas.add("张三5");
        datas.add("张思6");
        datas.add("张三7");
        datas.add("张思8");
        datas.add("张三9");
        datas.add("张思");
        datas.add("张三");
        datas.add("张思");
        datas.add("张三");
        datas.add("张思");
        datas.add("张思");
        datas.add("张思");
        datas.add("张思");
        datas.add("张思");
        datas.add("张思");
        datas.add("张思");
        datas.add("张思");
        datas.add("张思");
        datas.add("张思");
        // 筛选数据
        filterData = new FilterData();
        filterData.setCategory(ModelUtil.getCategoryData());
        filterData.setSorts(ModelUtil.getSortData());
        filterData.setFilters(ModelUtil.getFilterData());
    }
    private void process() {
        headview= LayoutInflater.from(this).inflate(R.layout.header_mlv,null);
        mlv.addHeaderView(headview);
        // 设置假FilterView数据
        headerFilterView = new HeaderFilterView(this);
        headerFilterView.fillView(new Object(), mlv);
        // 设置真FilterView数据
        filterView.setFilterData(mActivity, filterData);
        filterView.setVisibility(View.GONE);
        //
        madapter=new MyAdapter(mcontext,datas);
        mlv.setAdapter(madapter);
    }
    private void setAllClick() {
        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(intent);
            }
        });
        mlv.setOnScrollListener(new ListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScrollIdle && bannerViewTopMargin < 0) return;
                //下面是通过对广告高度的检测来设置渐变标题栏
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
                //下面是处理筛选view的逻辑
                // 获取筛选View、距离顶部的高度
                if (itemHeaderFilterView == null) {
                    itemHeaderFilterView = mlv.getChildAt(filterViewPosition - firstVisibleItem);
                }
                if (itemHeaderFilterView != null) {
                    filterViewTopMargin = DensityUtil.px2dip(mcontext, itemHeaderFilterView.getTop());
                }

                // 处理筛选是否吸附在顶部
                if (filterViewTopMargin <= titleViewHeight || firstVisibleItem > filterViewPosition) {
                    isStickyTop = true; // 吸附在顶部
                    filterView.setVisibility(View.VISIBLE);
                } else {
                    isStickyTop = false; // 没有吸附在顶部
                    filterView.setVisibility(View.GONE);
                }

                if (isSmooth && isStickyTop) {
                    isSmooth = false;
                    filterView.show(filterPosition);
                }
            }
        });
        // (假的ListView头部展示的)筛选视图点击
        headerFilterView.setOnFilterClickListener(new HeaderFilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterPosition = position;
                isSmooth = true;
                mlv.smoothScrollToPositionFromTop(filterViewPosition, DensityUtil.dip2px(mcontext, titleViewHeight));
            }
        });
        // (真正的)筛选视图点击
        filterView.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                if (isStickyTop) {
                    filterPosition = position;
                    filterView.show(position);
                    if (titleViewHeight - 3 > filterViewTopMargin || filterViewTopMargin > titleViewHeight + 3) {
                        mlv.smoothScrollToPositionFromTop(filterViewPosition, DensityUtil.dip2px(mcontext, titleViewHeight));
                    }
                }
            }
        });
        // 分类Item点击
        filterView.setOnItemCategoryClickListener(new FilterView.OnItemCategoryClickListener() {
            @Override
            public void onItemCategoryClick(FilterTwoEntity leftEntity, FilterEntity rightEntity) {
//                fillAdapter(ModelUtil.getCategoryTravelingData(leftEntity, rightEntity));
                ToastUtil.show(mActivity,"left="+leftEntity.getType()+";right="+rightEntity.getKey() );
            }
        });

        // 排序Item点击
        filterView.setOnItemSortClickListener(new FilterView.OnItemSortClickListener() {
            @Override
            public void onItemSortClick(FilterEntity entity) {
//                fillAdapter(ModelUtil.getSortTravelingData(entity));
            }
        });

        // 筛选Item点击
        filterView.setOnItemFilterClickListener(new FilterView.OnItemFilterClickListener() {
            @Override
            public void onItemFilterClick(FilterEntity entity) {
//                fillAdapter(ModelUtil.getFilterTravelingData(entity));
            }
        });
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
