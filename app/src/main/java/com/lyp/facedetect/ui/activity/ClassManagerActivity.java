package com.lyp.facedetect.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.lyp.facedetect.R;
import com.lyp.facedetect.adapter.ClassInfoListAdapter;
import com.lyp.facedetect.facemodel.ClassInfo;
import com.lyp.facedetect.facemodel.ClassInfoArray;
import com.lyp.facedetect.facemodel.JSONObjectParse;
import com.lyp.facedetect.facemodel.StudentInfo;
import com.lyp.facedetect.net.BasePostParameters;
import com.lyp.facedetect.net.BaseRequestTask;
import com.lyp.facedetect.net.RequestResultInterface;
import com.lyp.facedetect.utils.T;
import com.yalantis.phoenix.PullToRefreshView;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by demon.li on 2016/4/24.
 */
public class ClassManagerActivity extends BaseActicity implements PullToRefreshView.OnRefreshListener ,ListView.OnItemLongClickListener{

    public static void openPage(BaseActicity activity) {
        Intent intent = new Intent(activity, ClassManagerActivity.class);
        activity.startActivity(intent);
    }



    @Bind(R.id.tool_bar)
    Toolbar mToolbar;
    @Bind(R.id.list_view)
    ListView mListView;
    @Bind(R.id.pull_to_refresh)
    PullToRefreshView mPullToRefreshView;

    private ClassInfoListAdapter adapter;
    private ClassInfoArray classInfoArry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_manager_activity);
        ButterKnife.bind(this);
        initToolbar();
        getData();
    }

    void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mToolbar.setTitle(R.string.class_info_manager);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mPullToRefreshView.setRefreshStyle(PullToRefreshView.STYLE_SUN);
        mPullToRefreshView.setOnRefreshListener(this);

        adapter = new ClassInfoListAdapter(this);
        mListView.setAdapter(adapter);
        mListView.setOnItemLongClickListener(this);

    }


    @Override
    public String getClassName() {
        return ClassManagerActivity.class.getSimpleName();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.class_manager_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add_class:
                AddClassInfoActivity.openPage(ClassManagerActivity.this);
                break;
            case android.R.id.home:
                this.finish();
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public void onRefresh() {
        getData();
    }


    public void getData() {
        BasePostParameters postParameters = new BasePostParameters(BasePostParameters.PostType.Group_List);
        BaseRequestTask task = new BaseRequestTask(new RequestResultInterface() {
            @Override
            public void start() {

            }

            @Override
            public void success(final Object o) {
                JSONObject jsonObject = (JSONObject) o;
                classInfoArry = JSONObjectParse.getClassInfoList(jsonObject);
                if (classInfoArry != null) {
                    adapter.setData(classInfoArry.group);
                }
                mPullToRefreshView.setRefreshing(false);
            }

            @Override
            public void failed() {
                mPullToRefreshView.setRefreshing(false);
                T.toastLongWithResId(ClassManagerActivity.this, R.string.error);
            }
        });
        task.execute(postParameters);
    }


    private void showBottomMenu(final int position){

        final ClassInfo classInfo = classInfoArry.group[position];

        View convertView = LayoutInflater.from(this).inflate(R.layout.student_bottom_menu_layout,null,false);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        convertView.setLayoutParams(layoutParams);
        final PopupWindow popupWindow = new PopupWindow(convertView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(getResources().getColor(R.color.black_transparent));
        popupWindow.setBackgroundDrawable(colorDrawable);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        Button mBtnDeleteClass = (Button) convertView.findViewById(R.id.btn_delete_student);
//        Button mBtnDeleteAllFace = (Button) convertView.findViewById(R.id.btn_delete_all_face);
        Button mBtnAddFace = (Button) convertView.findViewById(R.id.btn_add_face);
        mBtnAddFace.setVisibility(View.GONE);
        final Button mBtnCancel = (Button)convertView.findViewById(R.id.btn_cancel);

        mBtnDeleteClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasePostParameters postParameters = new BasePostParameters(BasePostParameters.PostType.Delete_Group);
                postParameters.setGroupName(classInfo.group_name);
                BaseRequestTask task = new BaseRequestTask(new RequestResultInterface() {
                    @Override
                    public void start() {

                    }

                    @Override
                    public void success(Object o) {
                        T.toastLongWithResId(ClassManagerActivity.this,R.string.delete_success);
                        getData();
                    }

                    @Override
                    public void failed() {
                        T.toastLongWithResId(ClassManagerActivity.this,R.string.delete_class_failed);
                    }
                });
                task.execute(postParameters);
                popupWindow.dismiss();
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.TOP,0,0);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //showBottomMenu(position);
        return true;
    }
}
