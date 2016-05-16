package com.lyp.facedetect.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lyp.facedetect.R;
import com.lyp.facedetect.common.AppDefine;
import com.lyp.facedetect.facemodel.ClassInfo;
import com.lyp.facedetect.facemodel.FaceInfoArray;
import com.lyp.facedetect.facemodel.JSONObjectParse;
import com.lyp.facedetect.facemodel.StudentInfo;
import com.lyp.facedetect.net.BasePostParameters;
import com.lyp.facedetect.net.BaseRequestTask;
import com.lyp.facedetect.net.RequestResultInterface;
import com.lyp.facedetect.utils.FileUtils;
import com.lyp.facedetect.utils.StringUtils;
import com.lyp.facedetect.utils.T;
import com.soundcloud.android.crop.Crop;

import org.json.JSONObject;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by demon.li on 2016/5/16.
 */
public class AddFaceToStudentActivity extends BaseActicity {

    public static  void openPage(BaseActicity acticity, StudentInfo studentInfo){
       if(acticity==null||studentInfo==null){
           return;
       }
        Intent intent = new Intent(acticity,AddFaceToStudentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StudentInfo.class.getSimpleName(),studentInfo);
        intent.putExtras(bundle);
        acticity.startActivity(intent);
    }


    @Bind(R.id.btn_select_pic)
    Button mBtnSelectPic;
    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.face_image)
    ImageView mFaceImage;

    private StudentInfo studentInfo;
    private String mTakePicPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_face_to_student_layout);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null&&bundle.containsKey(StudentInfo.class.getSimpleName())){
            studentInfo = (StudentInfo) bundle.getSerializable(StudentInfo.class.getSimpleName());
        }else{
            this.finish();
        }
        init();
    }


    private  void init(){

        mToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mToolBar.setTitle(this.getString(R.string.add_student_image));
        setSupportActionBar(mToolBar);
        mToolBar.setTitleTextColor(getResources().getColor(android.R.color.white));

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @OnClick(R.id.btn_select_pic)
    void btnToSelectPic(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,AppDefine.CODE_REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case AppDefine.CODE_REQUEST_GALLERY:
                if(resultCode==BaseActicity.RESULT_OK&&data!=null){
                    Uri uri = data.getData();
                    beginCrop(uri);
                }else{
                    T.toastShortWithResId(AddFaceToStudentActivity.this,R.string.not_select_picture);
                }
                break;
            case Crop.REQUEST_CROP:
                if(resultCode==BaseActicity.RESULT_OK&&data!=null){

                    if(StringUtils.isNotNullAndEmpty(mTakePicPath)){
                        Bitmap bitmap = BitmapFactory.decodeFile(mTakePicPath);
                        if(bitmap!=null){
                            mFaceImage.setImageBitmap(bitmap);
                        }
                        BasePostParameters params = new BasePostParameters(BasePostParameters.PostType.Detection_Detect);
                        params.setImg(new File(mTakePicPath));
                        BaseRequestTask task = new BaseRequestTask(new RequestResultInterface() {
                            @Override
                            public void start() {
                            }

                            @Override
                            public void success(Object o) {
                                JSONObject result = (JSONObject)o;
                                FaceInfoArray faceInfoArray =JSONObjectParse.getImageFaceInfoArray(result);
                                if(faceInfoArray!=null&&faceInfoArray.face!=null&&faceInfoArray.face.length==1){

                                    BasePostParameters postParameters = new BasePostParameters(BasePostParameters.PostType.Add_Person_Face);
                                    postParameters.setPersonName(studentInfo.person_name);
                                    postParameters.setFaceId(faceInfoArray.face[0].face_id);
                                    BaseRequestTask task =new BaseRequestTask(new RequestResultInterface() {
                                        @Override
                                        public void start() {

                                        }

                                        @Override
                                        public void success(Object o) {
                                            T.toastLongWithResId(AddFaceToStudentActivity.this,R.string.add_student_image_success);
                                            mTakePicPath = "";

                                        }

                                        @Override
                                        public void failed() {
                                            T.toastLongWithResId(AddFaceToStudentActivity.this,R.string.please_select_zjz);
                                        }
                                    });
                                    task.execute(postParameters);
                                }else{
                                   T.toastLongWithResId(AddFaceToStudentActivity.this,R.string.detect_error);
                                }
                            }

                            @Override
                            public void failed() {

                                T.toastShortWithResId(AddFaceToStudentActivity.this,R.string.error);

                            }

                        });
                        task.execute(params);


                    }else{
                        T.toastShortWithResId(AddFaceToStudentActivity.this,R.string.error);
                    }

                }else if(resultCode == Crop.RESULT_ERROR){
                    T.toastLongWithString(AddFaceToStudentActivity.this,Crop.getError(data).getMessage());                    }
                break;
            default:

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public String getClassName() {
        return AddFaceToStudentActivity.class.getSimpleName();
    }

    private void beginCrop(Uri source) {
        mTakePicPath = FileUtils.generatePNGFilePath(this);
        File imgFile = new File(mTakePicPath);
        Uri destination = Uri.fromFile(imgFile);
        Crop.of(source, destination).asSquare().start(this);
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
