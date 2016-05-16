package com.lyp.facedetect.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lyp.facedetect.R;
import com.lyp.facedetect.ui.fragment.MainFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActicity {



//    @Bind(R.id.drawer_layout)
//    DrawerLayout mDrawerLayout;
    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
//    @Bind(R.id.fragment_container)
//    LinearLayout  mContentContainer;
//    @Bind(R.id.banji_info)
//    Button mBtnBanJi;

    private MainFragment mMainFragment;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

        if(savedInstanceState!=null){
            mFragmentManager = getSupportFragmentManager();
            mMainFragment = (MainFragment) mFragmentManager.findFragmentByTag(MainFragment.class.getSimpleName());
            if(mMainFragment!=null){
                mFragmentManager.beginTransaction().show(mMainFragment).commit();
            }
        }else{
            initConent();
        }

    }

    /**
     * 初始侧边Menu
     */
    void initView(){
        setSupportActionBar(mToolBar);
        mToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mToolBar.setTitle(this.getString(R.string.app_name));
        mToolBar.setTitleTextColor(getResources().getColor(android.R.color.white));

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolBar,R.string.close,R.string.open);
//        toggle.syncState();
//        mDrawerLayout.addDrawerListener(toggle);


    }

    /**
     * 初始化首页布局信息
     */
    void initConent(){
        mMainFragment = new MainFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.fragment_container,mMainFragment,MainFragment.class.getSimpleName()).commit();
    }






    @Override
    public String getClassName() {
        return MainActivity.class.getSimpleName();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mMainFragment!=null){
            mMainFragment.onActivityResult(requestCode,resultCode,data);
        }
    }
}
