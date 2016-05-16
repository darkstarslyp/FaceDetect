package com.lyp.facedetect.net;

/**
 * Created by demon.li on 2016/5/16.
 */
public class TaskUtils {

    public static void getData(BasePostParameters postParameters, final RequestResultInterface requestResultInterface){
        if(postParameters==null||requestResultInterface==null){
            return;
        }
        BaseRequestTask task = new BaseRequestTask(new RequestResultInterface() {
            @Override
            public void start() {
                requestResultInterface.start();
            }

            @Override
            public void success(Object o) {
                  requestResultInterface.success(o);
            }

            @Override
            public void failed() {
               requestResultInterface.failed();
            }
        });
        task.execute(postParameters);

    }
}
