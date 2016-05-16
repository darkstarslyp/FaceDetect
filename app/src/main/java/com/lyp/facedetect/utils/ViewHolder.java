package com.lyp.facedetect.utils;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by demon.li on 2016/4/24.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private View convertView;
    private SparseArray<View> itemViews;
    public ViewHolder(@Nullable  View convertView) {
        super(convertView);
        this.convertView = convertView;
        itemViews = new SparseArray<>();
    }

    public <T extends View> T getView(@IdRes int resId){
        View view = itemViews.get(resId);
        if(view==null){
            view = convertView.findViewById(resId);
            itemViews.append(resId,view);
        }
        return (T) view;
    }
}
