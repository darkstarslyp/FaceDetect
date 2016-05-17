package com.lyp.facedetect.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.lyp.facedetect.R;
import com.lyp.facedetect.adapter.MatchFaceResultAdapter;
import com.lyp.facedetect.facemodel.FaceInfo;
import com.lyp.facedetect.facemodel.FaceInfoArray;
import com.lyp.facedetect.facemodel.JSONObjectParse;
import com.lyp.facedetect.facemodel.MatchFaceInfo;
import com.lyp.facedetect.net.BasePostParameters;
import com.lyp.facedetect.net.RequestResultInterface;
import com.lyp.facedetect.net.TaskUtils;
import com.lyp.facedetect.utils.StringUtils;
import com.lyp.facedetect.utils.T;

import org.json.JSONObject;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by demon.li on 2016/5/17.
 */
public class FaceMatchActivity extends BaseActicity {


    public static void openPage(BaseActicity acticity, FaceInfoArray faceInfoArray) {
        if (acticity == null || faceInfoArray == null) {
            return;
        }
        Intent intent = new Intent(acticity, FaceMatchActivity.class);
        sFaceInfoArray = faceInfoArray;
        acticity.startActivity(intent);
    }

    public static FaceInfoArray sFaceInfoArray;

    TextView mTxtFaceNum;
    EditText mEidtClassId;
    Button mBtnMatch;
    Toolbar mToolbar;
    ListView mListView;

    private FaceInfo[] mFaceInfoArray;
    private StringBuilder faecIds = new StringBuilder();
    private MatchFaceResultAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face_match_class_student_layout);

        mTxtFaceNum = (TextView)findViewById(R.id.face_num);
        mEidtClassId = (EditText)findViewById(R.id.class_id);
        mBtnMatch = (Button)findViewById(R.id.start_to_match);
        mToolbar = (Toolbar)findViewById(R.id.tool_bar);
        mListView = (ListView)findViewById(R.id.list_view);
        mAdapter = new MatchFaceResultAdapter(this);
        mListView.setAdapter(mAdapter);
        mBtnMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceToMatch();
            }
        });
        try{
            mFaceInfoArray = sFaceInfoArray.face;
            initToolbar();
        }catch (Exception e){
           e.printStackTrace();
        }

    }

    private void initToolbar() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mToolbar.setTitle(R.string.person_face_match);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if(mFaceInfoArray!=null){
            int length = mFaceInfoArray.length;
            for(int i=0;i<length;i++){
                if(i==length-1){
                    faecIds.append(mFaceInfoArray[i].face_id);
                }else{
                    faecIds.append(mFaceInfoArray[i].face_id+",");
                }
            }
            mTxtFaceNum.setText(mFaceInfoArray.length+"");
        }
    }


    void faceToMatch(){
        String strClassId = mEidtClassId.getText().toString();
        if(StringUtils.isNullOrEmpty(strClassId)){
            T.toastLongWithResId(this,R.string.please_edit_full_info);
            return;
        }

        BasePostParameters params = new BasePostParameters(BasePostParameters.PostType.Face_Match_Group);
        params.setGroupName(strClassId);
        params.setKeyFaceId(faecIds.toString());
        TaskUtils.getData(params, new RequestResultInterface() {
            @Override
            public void start() {

            }

            @Override
            public void success(Object o) {
                JSONObject result = (JSONObject)(o);
                Log.d("result=",result.toString());
                try{

                    MatchFaceInfo matchFaceInfo = JSONObjectParse.getMatchFaceInfo(result);
                    mAdapter.setData(matchFaceInfo);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void failed() {

            }
        });
    }

    @Override
    public String getClassName() {
        return FaceMatchActivity.class.getSimpleName();
    }
}
