package com.lyp.facedetect.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by demon.li on 2016/4/24.
 */
public abstract  class BaseFragment extends Fragment{
    public Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }
}
