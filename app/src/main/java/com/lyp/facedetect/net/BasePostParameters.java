package com.lyp.facedetect.net;

import com.facepp.http.PostParameters;

/**
 * Created by demon.li on 2016/5/16.
 */
public class BasePostParameters extends PostParameters {
    public static enum PostType {
        Detection_Detect(0),
        Group_Create(1),
        Group_List(2),
        Group_Person_List(3),
        Person_Create(4),
        Group_Add_Person(5),
        Delete_Person(6),
        Delete_Person_All_Face(7),
        Add_Person_Face(8),
        Delete_Group(9),
        Person_Face(10)
        ;

        private int type;
        private PostType(int param){
            type = param;
        }

        public int getType() {
            return type;
        }
        @Override
        public String toString() {
            return super.toString();
        }
    }

    private PostType mPostType;

    public BasePostParameters(PostType mPostType) {
        this.mPostType = mPostType;
    }


    public PostType getmPostType() {
        return mPostType;
    }
}
