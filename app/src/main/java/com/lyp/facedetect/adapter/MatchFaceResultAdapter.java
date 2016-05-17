package com.lyp.facedetect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lyp.facedetect.R;
import com.lyp.facedetect.facemodel.MatchFaceInfo;
import com.lyp.facedetect.utils.StringUtils;
import com.lyp.facedetect.utils.ViewHolder;

/**
 * Created by demon.li on 2016/5/17.
 */
public class MatchFaceResultAdapter extends BaseAdapter {


    private final LayoutInflater layoutInflater;
    private MatchFaceInfo matchFaceInfo;
    private Context mContext;

    public MatchFaceResultAdapter(Context mContext) {
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return matchFaceInfo == null ? 0 : matchFaceInfo.face.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.match_face_item_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TextView mTxtStudentId = (TextView) convertView.findViewById(R.id.student_id);
        TextView mTxtStudentName = (TextView) convertView.findViewById(R.id.student_tag);
        TextView mTxtConfidence = (TextView) convertView.findViewById(R.id.confidence);

        if (matchFaceInfo != null && matchFaceInfo.face != null) {
            MatchFaceInfo.Candidate candidate = matchFaceInfo.face[position].candidate[0];

            String strStudentId = candidate.person_name;
            String strStudentName = candidate.tag;
            double dConfidence = candidate.confidence;

            if (StringUtils.isNotNullAndEmpty(strStudentId)) {
                mTxtStudentId.setText(strStudentId);
            }

            if (StringUtils.isNotNullAndEmpty(strStudentName)) {
                mTxtStudentName.setText(strStudentName);
            }
            mTxtConfidence.setText(String.format("%.2f",dConfidence)+"%");
        }

        return convertView;
    }

    public void setData(MatchFaceInfo matchFaceInfo) {
        if (matchFaceInfo == null) {
            return;
        }
        this.matchFaceInfo = matchFaceInfo;
        notifyDataSetChanged();
    }
}
