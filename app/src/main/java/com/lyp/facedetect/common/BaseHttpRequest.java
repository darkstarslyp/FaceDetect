package com.lyp.facedetect.common;

import com.facepp.http.HttpRequests;

/**
 * Created by demon.li on 2016/5/14.
 */
public class BaseHttpRequest extends HttpRequests {

    public  BaseHttpRequest(){
        super(AppDefine.API_KEY, AppDefine.API_SECRET, true, true);
    }

    public BaseHttpRequest(boolean isCN, boolean isDebug) {
        super(AppDefine.API_KEY, AppDefine.API_SECRET, isCN, true);
    }


}
