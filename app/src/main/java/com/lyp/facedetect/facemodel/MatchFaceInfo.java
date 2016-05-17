package com.lyp.facedetect.facemodel;

/**
 * Created by demon.li on 2016/5/17.
 */
public class MatchFaceInfo {

    public String session_id;
    public Face face[];

    public static  class Face{

        public Candidate candidate[];
        public String face_id;

    }

    public static  class Candidate{
        public double confidence;
        public String person_id;
        public String person_name;
        public String tag;
    }

}
