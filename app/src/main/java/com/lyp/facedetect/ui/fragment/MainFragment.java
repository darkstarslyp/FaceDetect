package com.lyp.facedetect.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lyp.facedetect.R;
import com.lyp.facedetect.common.AppDefine;
import com.lyp.facedetect.ui.activity.BaseActicity;
import com.lyp.facedetect.ui.activity.ClassManagerActivity;
import com.lyp.facedetect.ui.activity.FaceDetectActivity;
import com.lyp.facedetect.utils.FileUtils;
import com.lyp.facedetect.utils.StringUtils;
import com.lyp.facedetect.utils.T;
import com.soundcloud.android.crop.Crop;

import java.io.File;

/**
 * Created by demon.li on 2016/4/24.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener{

    Button mBtnZPSB;
    Button mBtnPZSB;
    Button mBtnDTSB;
    Button mBtnBJGL;
    String mTakePicPath;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.main_fragment_layout, container, false);
        mBtnPZSB = (Button) convertView.findViewById(R.id.static_detect);
        mBtnBJGL = (Button) convertView.findViewById(R.id.banji_info);
        mBtnDTSB = (Button) convertView.findViewById(R.id.dynamatic_detect);
        mBtnZPSB = (Button) convertView.findViewById(R.id.select_pic_detect);
        mBtnPZSB.setOnClickListener(this);
        mBtnBJGL.setOnClickListener(this);
        mBtnDTSB.setOnClickListener(this);
        mBtnZPSB.setOnClickListener(this);
        return convertView;
    }

    /**
     * 拍摄照片进行人脸识别
     */
    public void pzsbFunction() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            mTakePicPath = FileUtils.generatePNGFilePath(mContext);
            File imgFile = new File(mTakePicPath);
            Uri uri = Uri.fromFile(imgFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
            this.startActivityForResult(intent, AppDefine.CODE_REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 选取照片中的图片进行人脸识别
     */
    public void zpsbFunction(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,AppDefine.CODE_REQUEST_GALLERY);
    }

    public void dtsbFunction() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case AppDefine.CODE_REQUEST_CAMERA:
                    if(resultCode==BaseActicity.RESULT_OK){
                        FaceDetectActivity.openPage((BaseActicity) getActivity(),mTakePicPath);
                    }else{
                        T.toastShortWithResId(mContext,R.string.not_take_picture);
                    }
                    break;
                case AppDefine.CODE_REQUEST_GALLERY:
                    if(resultCode==BaseActicity.RESULT_OK&&data!=null){
                        Uri uri = data.getData();
                        beginCrop(uri);
                    }else{
                        T.toastShortWithResId(mContext,R.string.not_select_picture);
                    }
                    break;
                case Crop.REQUEST_CROP:
                    if(resultCode==BaseActicity.RESULT_OK&&data!=null){

                        if(StringUtils.isNotNullAndEmpty(mTakePicPath)){
                            FaceDetectActivity.openPage((BaseActicity) getActivity(),mTakePicPath);

                        }else{
                            T.toastShortWithResId(mContext,R.string.error);
                        }

                    }else if(resultCode == Crop.RESULT_ERROR){
                        T.toastLongWithString(mContext,Crop.getError(data).getMessage());                    }
                    break;
                default:
                    break;
            }
    }


    private void beginCrop(Uri source) {
        mTakePicPath = FileUtils.generatePNGFilePath(mContext);
        File imgFile = new File(mTakePicPath);
        Uri destination = Uri.fromFile(imgFile);
        Crop.of(source, destination).asSquare().start(getActivity());
    }


    @Override
    public void onClick(View v) {
        if(v==mBtnPZSB){
            pzsbFunction();
        }else if(v==mBtnDTSB){
            dtsbFunction();
        }else if(v==mBtnBJGL){
            ClassManagerActivity.openPage((BaseActicity) getActivity());
        }else if(v==mBtnZPSB){
            zpsbFunction();
        }
    }
}
