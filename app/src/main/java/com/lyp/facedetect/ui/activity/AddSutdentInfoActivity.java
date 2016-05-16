package com.lyp.facedetect.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.lyp.facedetect.R;
import com.lyp.facedetect.facemodel.ClassInfo;
import com.lyp.facedetect.net.BasePostParameters;
import com.lyp.facedetect.net.BaseRequestTask;
import com.lyp.facedetect.net.RequestResultInterface;
import com.lyp.facedetect.utils.StringUtils;
import com.lyp.facedetect.utils.T;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * 此处涉及到\Face \Person \Group等概念
 * Created by demon.li on 2016/5/16.
 */
public class AddSutdentInfoActivity extends BaseActicity {

   public static void openPage(BaseActicity acticity, ClassInfo classInfo){
       if(acticity==null){
          return;
       }
       Intent intent = new Intent(acticity,AddSutdentInfoActivity.class);
       Bundle bundle = new Bundle();
       bundle.putSerializable(ClassInfo.class.getSimpleName(),classInfo);
       intent.putExtras(bundle);
       acticity.startActivity(intent);
   }


    @Bind(R.id.student_id)
    EditText mTxtStudentId;
    @Bind(R.id.student_tag)
    EditText mTxtStudentName;
    @Bind(R.id.upload_image)
    Button mBtnUploadImage;
    @Bind(R.id.save)
    Button mBtnSave;
    @Bind(R.id.tool_bar)
    Toolbar mToolbar;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressbar;

    private ClassInfo mClassInfo;
    private String mStrFaceId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student_info_layout);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null&&bundle.containsKey(ClassInfo.class.getSimpleName())){
            mClassInfo = (ClassInfo) bundle.getSerializable(ClassInfo.class.getSimpleName());
        }else{
            this.finish();
        }
        initToolbar();
        initToolbar();
    }

    void initToolbar(){
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mToolbar.setTitle(R.string.add_student_info);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 保存信息
     */
    @OnClick(R.id.save)
    void saveSutdentInfoFuction(){

        final String strStudentId = mTxtStudentId.getText().toString();
        String strStudentName = mTxtStudentName.getText().toString();
        if(StringUtils.isNullOrEmpty(strStudentId)||StringUtils.isNullOrEmpty(strStudentName)){
            T.toastLongWithResId(this,R.string.please_edit_full_info);
            return;
        }
        BasePostParameters postParameters = new BasePostParameters(BasePostParameters.PostType.Person_Create);
        postParameters.setPersonName(strStudentId);
        postParameters.setTag(strStudentName);
        postParameters.setGroupName(mClassInfo.group_name);
        BaseRequestTask task = new BaseRequestTask(new RequestResultInterface() {
            @Override
            public void start() {
                mProgressbar.setVisibility(View.VISIBLE);
                mBtnSave.setEnabled(false);
            }

            @Override
            public void success(Object o) {
                T.toastLongWithResId(AddSutdentInfoActivity.this,R.string.create_student_info_success);
                AddSutdentInfoActivity.this.finish();
            }

            @Override
            public void failed() {
                mProgressbar.setVisibility(View.INVISIBLE);
                T.toastLongWithResId(AddSutdentInfoActivity.this,R.string.error);
            }
        });
        task.execute(postParameters);

    }

    @Override
    public String getClassName() {
        return AddSutdentInfoActivity.class.getSimpleName();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                this.finish();
                break;
            default:
                break;
        }
        return true;
    }

}
