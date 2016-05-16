package com.lyp.facedetect.facemodel;

import java.io.Serializable;

/**
 * Created by demon.li on 2016/5/16.
 */
public class FaceInfo implements Serializable {

    public String face_id;


    class FaceAttribute implements Serializable {
        public Age age;
        public Gender gender;
        public Glass glass;
        public Race race;
    }

    /**
     * 年龄相关
     */
    class Age implements Serializable {
        public int range;
        public int value;
    }

    /**
     * 性别相关
     */
    class Gender implements Serializable {
        public double confidence;
        public String value;
    }

    /**
     * 眼镜相关
     */
    class Glass implements Serializable {
        public double confidence;
        public String value;
    }

    /**
     * 种族相关
     */
    class Race implements Serializable {
        public double confidence;
        public String value;
    }


}
