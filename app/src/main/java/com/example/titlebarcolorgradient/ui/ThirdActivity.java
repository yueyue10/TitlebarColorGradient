package com.example.titlebarcolorgradient.ui;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import static android.view.View.VISIBLE;

public class ThirdActivity extends AppCompatActivity {

    @BindView(R.id.ll_content_list_view)
    LinearLayout llContentListView;
    @BindView(R.id.change_tv)
    TextView change_tv;
    @BindView(R.id.image_tv)
    TextView image_tv;
    @BindView(R.id.iv_category_arrow)
    ImageView iv_category_arrow;
    @BindView(R.id.turn_tv)
    TextView turn_tv;
    int arrow = 1;//1代表向上，2代表向下
    int show = 2;//1代表显示，2代表隐藏
    private int panelHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        ButterKnife.bind(this);
        setAllClick();
    }

    private void setAllClick() {
        change_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (arrow) {
                    case 1:
                        rotateArrowDownAnimation(iv_category_arrow);
                        arrow = 2;
                        break;
                    case 2:
                        rotateArrowUpAnimation(iv_category_arrow);
                        arrow = 1;
                        break;
                }
            }
        });
        image_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (show) {
                    case 1:
                        hideView();
                        show = 2;
                        break;
                    case 2:
                        showView();
                        show = 1;
                        break;
                }
            }
        });
        turn_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ThirdActivity.this,FourActivity.class);
                startActivity(intent);
            }
        });
    }

    // 旋转箭头向上
    public static void rotateArrowUpAnimation(final ImageView iv) {
        if (iv == null) return;
        RotateAnimation animation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        iv.startAnimation(animation);
    }

    // 旋转箭头向下
    public static void rotateArrowDownAnimation(final ImageView iv) {
        if (iv == null) return;
        RotateAnimation animation = new RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        iv.startAnimation(animation);
    }

    public void showView() {
        llContentListView.setVisibility(VISIBLE);
        llContentListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llContentListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                panelHeight = llContentListView.getHeight();
                ObjectAnimator.ofFloat(llContentListView, "translationY", -panelHeight, 0).setDuration(200).start();
            }
        });
//        ObjectAnimator.ofFloat(llContentListView, "translationY", -panelHeight, 0).setDuration(200).start();
    }

    public void hideView() {
        ObjectAnimator.ofFloat(llContentListView, "translationY", 0, -panelHeight).setDuration(200).start();
    }
}
