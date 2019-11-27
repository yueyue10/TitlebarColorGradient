package com.example.titlebarcolorgradient.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.titlebarcolorgradient.R;

import butterknife.ButterKnife;

public class FourActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        ButterKnife.bind(this);
        setAllClick();
    }

    private void setAllClick() {

    }


}
