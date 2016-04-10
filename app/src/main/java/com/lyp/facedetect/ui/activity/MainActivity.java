package com.lyp.facedetect.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lyp.facedetect.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActicity {
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.content_container)
    LinearLayout  mContentContainer;
    @Bind(R.id.banji_info)
    Button mBtnBanJi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化布局信息
     */
    void initView(){
        setSupportActionBar(mToolBar);
        mToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mToolBar.setTitle(this.getString(R.string.app_name));
        mToolBar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolBar,R.string.close,R.string.open);
        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);

    }


}
