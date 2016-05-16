package com.lyp.facedetect.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by demon.li on 2016/5/14.
 */
public class T {

    public static void toastLongWithString(Context context,String param){
        Toast.makeText(context,param,Toast.LENGTH_LONG).show();
    }

    public static void toastLongWithResId(Context context, @StringRes int resId){
        Toast.makeText(context,context.getResources().getString(resId),Toast.LENGTH_LONG).show();
    }

    public static void toastShortWithString(Context context,String param){
        Toast.makeText(context,param,Toast.LENGTH_SHORT).show();
    }

    public static void toastShortWithResId(Context context, @StringRes int resId){
        Toast.makeText(context,context.getResources().getString(resId),Toast.LENGTH_SHORT).show();
    }

}
