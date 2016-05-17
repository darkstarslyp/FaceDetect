package com.lyp.facedetect.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.facepp.error.FaceppParseException;
import com.facepp.http.PostParameters;
import com.lyp.facedetect.R;
import com.lyp.facedetect.facemodel.FaceInfoArray;
import com.lyp.facedetect.facemodel.JSONObjectParse;
import com.lyp.facedetect.net.BasePostParameters;
import com.lyp.facedetect.net.BaseRequestTask;
import com.lyp.facedetect.net.RequestResultInterface;
import com.lyp.facedetect.utils.StringUtils;
import com.lyp.facedetect.utils.T;

import org.json.JSONObject;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  静态人脸识别，根据照片中的人脸信息是识别出人脸，并且进行匹配给出相应的匹配结果
 *
 *   请注意：调用API要求图片小于 1M
 *
 * Created by demon.li on 2016/5/14.
 */
public class FaceDetectActivity extends BaseActicity {

    public static final String IMAGE_PATH = "IMAGE_PATH";
    public static void openPage(BaseActicity acticity, String imgPath){
        if(acticity==null|| StringUtils.isNullOrEmpty(imgPath)){
            T.toastShortWithResId(acticity, R.string.param_error);
        }
        Intent intent = new Intent(acticity,FaceDetectActivity.class);
        intent.putExtra(IMAGE_PATH,imgPath);
        acticity.startActivity(intent);
    }

    @Bind(R.id.image_view)
    ImageView mPersonsImg;
    @Bind(R.id.btn_start_to_detect)
    Button mBtnDetect;
    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;


    private String mImagePath;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detect_layout);
        ButterKnife.bind(this);
        init();
    }

    private void init(){

        mToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mToolBar.setTitle(this.getString(R.string.student_info_detect));
        mToolBar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        mImagePath = intent.getStringExtra(IMAGE_PATH);
        Bitmap bitmap = BitmapFactory.decodeFile(mImagePath);
        if(bitmap!=null){
            mPersonsImg.setImageBitmap(bitmap);
        }
    }

    @Override
    public String getClassName() {
        return FaceDetectActivity.class.getSimpleName();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                this.finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =  item.getItemId();
        switch (id){
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_start_to_detect)
    void btnStartDetect(){

        BasePostParameters params = new BasePostParameters(BasePostParameters.PostType.Detection_Detect);
        params.setImg(new File(mImagePath));
        BaseRequestTask task = new BaseRequestTask(new RequestResultInterface() {
            @Override
            public void start() {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void success(Object o) {
                mProgressBar.setVisibility(View.INVISIBLE);
                JSONObject result = (JSONObject)o;
                Log.d("result=",result.toString());
                FaceInfoArray faceInfoArray = JSONObjectParse.getImageFaceInfoArray(result);
                if(faceInfoArray!=null&&faceInfoArray.face!=null&&faceInfoArray.face.length>0){
                    Log.d("face num =",faceInfoArray.face.length+"");

                }else{
                    T.toastShortWithResId(FaceDetectActivity.this,R.string.not_detect_face);
                }
            }

            @Override
            public void failed() {

                mProgressBar.setVisibility(View.INVISIBLE);
                T.toastShortWithResId(FaceDetectActivity.this,R.string.image_size_to_big);

            }

        });
        task.execute(params);

    }
}
