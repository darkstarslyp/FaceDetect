package com.lyp.facedetect.facemodel;



import com.google.gson.Gson;

import org.json.JSONObject;


/**
 * Created by demon.li on 2016/5/16.
 */
public class JSONObjectParse {

    public static ClassInfoArray getClassInfoList(JSONObject jsonObject) {
        ClassInfoArray classInfoArry = null;
        String dataObject;
        try {
            dataObject  = jsonObject.toString();

            Gson gson = new Gson();
            classInfoArry = gson.fromJson(dataObject,ClassInfoArray.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

       return classInfoArry;
    }

    public static StudentInfoArray getStudentInfoArray(JSONObject jsonObject) {
        StudentInfoArray studentInfoArray = null;
        String dataObject;
        try {
            dataObject  = jsonObject.toString();

            Gson gson = new Gson();
            studentInfoArray = gson.fromJson(dataObject,StudentInfoArray.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentInfoArray;
    }

    public static FaceInfoArray getImageFaceInfoArray(JSONObject jsonObject){
        FaceInfoArray faceInfoArray = null;
        String dataObject = jsonObject.toString();
        try {
            dataObject  = jsonObject.toString();
            Gson gson = new Gson();
            faceInfoArray = gson.fromJson(dataObject,FaceInfoArray.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return faceInfoArray;
    }
}
