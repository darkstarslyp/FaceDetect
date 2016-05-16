package com.lyp.facedetect.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.lyp.facedetect.R;
import com.lyp.facedetect.net.BasePostParameters;
import com.lyp.facedetect.net.BaseRequestTask;
import com.lyp.facedetect.net.RequestResultInterface;
import com.lyp.facedetect.utils.StringUtils;
import com.lyp.facedetect.utils.T;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by demon.li on 2016/4/25.
 */
public class AddClassInfoActivity extends BaseActicity {


    public static void openPage(BaseActicity acticity){
        Intent intent = new Intent(acticity,AddClassInfoActivity.class);
        acticity.startActivity(intent);
    }

    @Bind(R.id.class_id)
    EditText mTxtClassId;
    @Bind(R.id.class_des)
    EditText mTxtClassDes;
    @Bind(R.id.save)
    Button mBtnSave;
    @Bind(R.id.tool_bar)
    Toolbar mToolBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class_info_activity);
        ButterKnife.bind(this);
        init();
    }


    private  void init(){

        mToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mToolBar.setTitle(this.getString(R.string.add_class_info));
        setSupportActionBar(mToolBar);
        mToolBar.setTitleTextColor(getResources().getColor(android.R.color.white));

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }



    @OnClick(R.id.save)
    void saveFunction(){

        final String classIdStr = mTxtClassId.getText().toString();
        final String classDesStr = mTxtClassDes.getText().toString();
        if(StringUtils.isNullOrEmpty(classIdStr)||StringUtils.isNullOrEmpty(classDesStr)){
            T.toastLongWithResId(this,R.string.please_edit_full_info);
        }else{
            BasePostParameters postParameters = new BasePostParameters(BasePostParameters.PostType.Group_Create);
            postParameters.setGroupName(classIdStr);
            postParameters.setTag(classDesStr);
            BaseRequestTask task = new BaseRequestTask(new RequestResultInterface() {
                @Override
                public void start() {

                }

                @Override
                public void success(Object o) {

                    JSONObject jsonObject =  (JSONObject) o;
                    T.toastShortWithResId(AddClassInfoActivity.this,R.string.create_class_info_success);
                    finish();
//                    ClassModel classModel = new ClassModel();
//                    classModel.setGroupId(classIdStr);
//                    classModel.setClassDes(classDesStr);
//                    classModel.setClassId(classIdStr);
//                    classModel.saveInBackground(new SaveCallback() {
//                        @Override
//                        public void done(AVException e) {
//                            if(e==null){
//
//                            }else {
//
//                            }
//                        }
//                    });
                }

                @Override
                public void failed() {
                      T.toastLongWithResId(AddClassInfoActivity.this,R.string.error);
                }
            });
            task.execute(postParameters);
        }
    }

    @Override
    public String getClassName() {
        return AddClassInfoActivity.class.getSimpleName();
    }
}
