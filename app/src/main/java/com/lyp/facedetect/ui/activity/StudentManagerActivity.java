package com.lyp.facedetect.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.lyp.facedetect.adapter.StudentInfoListAdapter;
import com.lyp.facedetect.facemodel.ClassInfo;
import com.lyp.facedetect.facemodel.JSONObjectParse;
import com.lyp.facedetect.facemodel.StudentInfo;
import com.lyp.facedetect.facemodel.StudentInfoArray;
import com.lyp.facedetect.net.BasePostParameters;
import com.lyp.facedetect.net.BaseRequestTask;
import com.lyp.facedetect.net.RequestResultInterface;
import com.lyp.facedetect.utils.T;
import com.yalantis.phoenix.PullToRefreshView;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 每一个学生相当于一个Person，每个Person包含很多的face
 *
 * Created by demon.li on 2016/5/16.
 */
public class StudentManagerActivity extends BaseActicity implements PullToRefreshView.OnRefreshListener,ListView.OnItemLongClickListener{

    public static  void openPage(BaseActicity acticity, ClassInfo classInfo){
        if(acticity==null||classInfo==null){
            return;
        }
        Intent intent = new Intent(acticity,StudentManagerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ClassInfo.class.getSimpleName(),classInfo);
        intent.putExtras(bundle);
        acticity.startActivity(intent);
    }

    @Bind(R.id.tool_bar)
    Toolbar mToolbar;
    @Bind(R.id.list_view)
    ListView mListView;
    @Bind(R.id.pull_to_refresh)
    PullToRefreshView mPullToRefreshView;


    private ClassInfo classInfo;
    private StudentInfoArray studentInfoArray;
    private StudentInfoListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_manager_layout);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null&&bundle.containsKey(ClassInfo.class.getSimpleName())){
            classInfo = (ClassInfo) bundle.getSerializable(ClassInfo.class.getSimpleName());
        }else{
            this.finish();
        }
        initToolbar();
        getData();

    }

    void initToolbar(){
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mToolbar.setTitle(R.string.student_info_manager);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mPullToRefreshView.setRefreshStyle(PullToRefreshView.STYLE_SUN);
        mPullToRefreshView.setOnRefreshListener(this);

        adapter = new StudentInfoListAdapter(this,classInfo);
        mListView.setAdapter(adapter);
        mListView.setOnItemLongClickListener(this);
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
                AddSutdentInfoActivity.openPage(StudentManagerActivity.this,classInfo);
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
    public String getClassName() {
        return StudentManagerActivity.class.getSimpleName();
    }

    @Override
    public void onRefresh() {
        getData();
    }

    public void getData(){
        BasePostParameters postParameters = new BasePostParameters(BasePostParameters.PostType.Group_Person_List);
        postParameters.setGroupName(classInfo.group_name);
        BaseRequestTask task = new BaseRequestTask(new RequestResultInterface() {
            @Override
            public void start() {

            }

            @Override
            public void success(Object o) {
                mPullToRefreshView.setRefreshing(false);
                studentInfoArray =  JSONObjectParse.getStudentInfoArray((JSONObject) o);
               if(studentInfoArray!=null){
                   adapter.setData(studentInfoArray.person);
               }
            }

            @Override
            public void failed() {
                mPullToRefreshView.setRefreshing(false);
                T.toastLongWithResId(StudentManagerActivity.this,R.string.error);
            }
        });
        task.execute(postParameters);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("onItemLongClick=","Long Click");
        showBottomMenu(position);
        return true;
    }

    private void showBottomMenu(final int position){

        final StudentInfo studentInfo = studentInfoArray.person[position];

        View convertView = LayoutInflater.from(this).inflate(R.layout.student_bottom_menu_layout,null,false);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        convertView.setLayoutParams(layoutParams);
        final PopupWindow popupWindow = new PopupWindow(convertView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(getResources().getColor(R.color.black_transparent));
        popupWindow.setBackgroundDrawable(colorDrawable);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        Button mBtnDeleteStudent = (Button) convertView.findViewById(R.id.btn_delete_student);
        Button mBtnDeleteAllFace = (Button) convertView.findViewById(R.id.btn_delete_all_face);
        Button mBtnAddFace = (Button) convertView.findViewById(R.id.btn_add_face);
        final Button mBtnCancel = (Button)convertView.findViewById(R.id.btn_cancel);

        mBtnDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasePostParameters postParameters = new BasePostParameters(BasePostParameters.PostType.Delete_Person);
                postParameters.setPersonName(studentInfo.person_name);
                BaseRequestTask task = new BaseRequestTask(new RequestResultInterface() {
                    @Override
                    public void start() {

                    }

                    @Override
                    public void success(Object o) {
                        T.toastLongWithResId(StudentManagerActivity.this,R.string.delete_success);
                        getData();
                    }

                    @Override
                    public void failed() {
                          T.toastLongWithResId(StudentManagerActivity.this,R.string.delete_student_failed);
                    }
                });
                task.execute(postParameters);
                popupWindow.dismiss();
            }
        });

        mBtnDeleteAllFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasePostParameters postParameters = new BasePostParameters(BasePostParameters.PostType.Delete_Person);
                postParameters.setPersonName(studentInfo.person_name);
                postParameters.setFaceId("all");
                BaseRequestTask task = new BaseRequestTask(new RequestResultInterface() {
                    @Override
                    public void start() {

                    }

                    @Override
                    public void success(Object o) {
                        T.toastLongWithResId(StudentManagerActivity.this,R.string.delete_success);
                    }

                    @Override
                    public void failed() {
                        T.toastLongWithResId(StudentManagerActivity.this,R.string.delete_student_all_face_failed);
                    }
                });
                task.execute(postParameters);
                popupWindow.dismiss();

            }
        });

        mBtnAddFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFaceToStudentActivity.openPage(StudentManagerActivity.this,studentInfo);
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
}
