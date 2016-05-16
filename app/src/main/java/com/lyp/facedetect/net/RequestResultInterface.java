package com.lyp.facedetect.net;

/**
 * Created by demon.li on 2016/5/14.
 */
public interface RequestResultInterface {
    public void start();
    public void success(Object o);
    public void failed();
}
