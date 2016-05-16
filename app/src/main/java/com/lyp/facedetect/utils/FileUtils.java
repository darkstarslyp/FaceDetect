package com.lyp.facedetect.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by demon.li on 2016/5/14.
 */
public class FileUtils {

    /**
     * 生成以时间戳作为文件名，非线程安全
     *
     * @param context
     * @return
     */
    public static String generateFileName(Context context) {
        String result = null;
        SimpleDateFormat date = new SimpleDateFormat("yyMMDDHHmmss");
        result = date.format(new Date());
        return result;
    }

    /**
     * 生成以.png结尾的文件名
     *
     * @param context
     * @return
     */
    public static String generatePNGFileName(Context context) {
        String result = generateFileName(context) + ".png";
        return result;
    }

    /**
     * 生成以 .jpg结尾的文件名
     *
     * @param context
     * @return
     */
    public static String generateJPGFileName(Context context) {
        String result = generateFileName(context) + ".jpg";
        return result;
    }

    public static String getPackageName(Context context) {
        String packageName = context.getPackageName();
        return packageName;
    }

    /**
     * 检查外置内存卡是否存在
     *
     * @param context
     * @return
     */
    public static boolean checkExternSDCard(Context context) {
        boolean result = false;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            result = true;
        }
        return result;
    }

    /**
     * 生成文件默认的根目录
     *
     * @param context
     * @return
     */
    public static String generateRootFilePath(Context context) {
        String result = null;
        if (checkExternSDCard(context)) {
            result = Environment.getExternalStorageDirectory().getAbsolutePath() + "/facedetect";

        } else {//存储至内置存储卡
            result = context.getFilesDir().getAbsolutePath() + "facedetect";
        }

        return result;
    }

    public static String generateImgRootFilePath(Context context) {
        String result = generateRootFilePath(context);
        if (StringUtils.isNotNullAndEmpty(result)) {
            result = result + "/img";
            File file = new File(result);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return result;
    }

    public static String generatePNGFilePath(Context context) {
        StringBuilder builder = new StringBuilder("");
        String prefix = generateImgRootFilePath(context);
        String suffix = generatePNGFileName(context);
        if (StringUtils.isNotNullAndEmpty(prefix) && StringUtils.isNotNullAndEmpty(suffix)) {
            builder.append(prefix).append("/").append(suffix);
        }
        try {
            File file = new File(builder.toString());
            if (!file.exists()) {
                file.createNewFile();
            }
        }catch(IOException e){
           e.printStackTrace();
        }
        return builder.toString();
    }

    public static String generateJPGFilePath(Context context) {
        StringBuilder builder = new StringBuilder("");
        String prefix = generateImgRootFilePath(context);
        String suffix = generateJPGFileName(context);
        if (StringUtils.isNotNullAndEmpty(prefix) && StringUtils.isNotNullAndEmpty(suffix)) {
            builder.append(prefix).append("/").append(suffix);
        }
        try {
            File file = new File(builder.toString());
            if (!file.exists()) {
                file.createNewFile();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return builder.toString();
    }
}
