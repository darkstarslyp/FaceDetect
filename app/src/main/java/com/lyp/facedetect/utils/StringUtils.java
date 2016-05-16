package com.lyp.facedetect.utils;

/**
 * Created by demon.li on 2016/5/14.
 */
public class StringUtils {

    public static boolean isNotNullAndEmpty(String param){
        if(param!=null&&!param.equals("")){
            return true;
        }
        return false;
    }

    public static boolean isNullOrEmpty(String param){
        if(param==null||param.equals("")){
            return true;
        }
        return false;
    }

}
