package com.lyp.facedetect.net;

import android.os.AsyncTask;

import com.facepp.error.FaceppParseException;

import org.json.JSONObject;

/**
 * Created by demon.li on 2016/5/14.
 */
public class BaseRequestTask extends AsyncTask<BasePostParameters,Void,JSONObject> {

    private BaseHttpRequest mBaseHttpRequest;
    private BasePostParameters postParameters ;
    private RequestResultInterface resultInterface;

    public BaseRequestTask(RequestResultInterface resultInterface ) {
        this.mBaseHttpRequest = new BaseHttpRequest();
        this.resultInterface = resultInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        resultInterface.start();
    }

    @Override
    protected JSONObject doInBackground(BasePostParameters... params)  {
        postParameters = (BasePostParameters)params[0];
        JSONObject object = null;
        try{
            switch (postParameters.getmPostType()){
                case Detection_Detect:
                    object   = mBaseHttpRequest.detectionDetect(postParameters);
                    break;
                case Group_Create:
                    object = mBaseHttpRequest.groupCreate(postParameters);
                    break;
                case Group_List:
                    object = mBaseHttpRequest.infoGetGroupList();
                    break;
                case Group_Person_List:
                    object = mBaseHttpRequest.groupGetInfo(postParameters);
                    break;
                case Person_Create:
                    object = mBaseHttpRequest.personCreate(postParameters);
                    break;
                case Group_Add_Person:
                    object = mBaseHttpRequest.groupAddPerson(postParameters);
                    break;
                case Delete_Person:
                    object = mBaseHttpRequest.personDelete(postParameters);
                    break;
                case Delete_Person_All_Face:
                    object = mBaseHttpRequest.personRemoveFace(postParameters);
                    break;
                case Add_Person_Face:
                    object = mBaseHttpRequest.personAddFace(postParameters);
                    break;
                case Delete_Group:
                    object = mBaseHttpRequest.groupDelete(postParameters);
                    break;
                case Person_Face:
                    object = mBaseHttpRequest.personGetInfo(postParameters);
                    break;
                case Train_Person:
                    object = mBaseHttpRequest.trainVerify(postParameters);
                    break;
                case Train_Group:
                    object = mBaseHttpRequest.trainIdentify(postParameters);
                    break;
                case Face_Match_Group:
                    object = mBaseHttpRequest.recognitionIdentify(postParameters);
                    break;
                default:
                    break;
            }



        }catch (FaceppParseException e){
            e.printStackTrace();
        }
        return object;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        if(jsonObject==null){
            resultInterface.failed();
        }else{
            resultInterface.success(jsonObject);
        }
    }
}
