package com.lyp.facedetect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lyp.facedetect.R;
import com.lyp.facedetect.facemodel.ClassInfo;
import com.lyp.facedetect.ui.activity.BaseActicity;
import com.lyp.facedetect.ui.activity.StudentManagerActivity;
import com.lyp.facedetect.utils.StringUtils;
import com.lyp.facedetect.utils.T;
import com.lyp.facedetect.utils.ViewHolder;

/**
 * Created by demon.li on 2016/5/16.
 */
public class ClassInfoListAdapter extends BaseAdapter {

    private ClassInfo[] classInfoArray;
    private Context mContext;
    private LayoutInflater layoutInflater;

    public ClassInfoListAdapter(Context context) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return classInfoArray == null ? 0 : classInfoArray.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;


        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.class_info_item_layout,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentManagerActivity.openPage((BaseActicity)mContext,classInfoArray[position]);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        TextView mTxtClassId = viewHolder.getView(R.id.class_id);
        TextView mTxtClassTag = viewHolder.getView(R.id.class_tag);
        String strClassId = classInfoArray[position].group_name;
        String strClassTag = classInfoArray[position].tag;
        if(StringUtils.isNotNullAndEmpty(strClassId)){
            mTxtClassId.setText(strClassId);
        }
        if(StringUtils.isNotNullAndEmpty(strClassTag)){
            mTxtClassTag.setText(strClassTag);
        }
        return convertView;
    }

    public void setData(ClassInfo[] classInfoArray) {
        if (classInfoArray == null || classInfoArray.length == 0) {
            return;
        }
        this.classInfoArray = classInfoArray;
        notifyDataSetChanged();
    }
}
