package com.lyp.facedetect.utils;

import java.text.NumberFormat;

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


    /**
     * Takes a double and turns it into a percent string.
     * Ex.  0.5 turns into 50%
     *
     * @param value
     * @return corresponding percent string
     */
    public static String getPercentValue(double value)
    {
        value = Math.floor(value * 100) / 100; //to represent 199 covered lines from 200 as 99% covered, not 100 %
        return NumberFormat.getPercentInstance().format(value);
    }


}
