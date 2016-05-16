package com.lyp.facedetect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facepp.http.PostParameters;
import com.lyp.facedetect.R;
import com.lyp.facedetect.facemodel.ClassInfo;
import com.lyp.facedetect.facemodel.StudentInfo;
import com.lyp.facedetect.net.BasePostParameters;
import com.lyp.facedetect.net.RequestResultInterface;
import com.lyp.facedetect.net.TaskUtils;
import com.lyp.facedetect.ui.activity.AddFaceToStudentActivity;
import com.lyp.facedetect.ui.activity.AddSutdentInfoActivity;
import com.lyp.facedetect.ui.activity.BaseActicity;
import com.lyp.facedetect.utils.StringUtils;
import com.lyp.facedetect.utils.ViewHolder;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by demon.li on 2016/5/16.
 */
public class StudentInfoListAdapter extends BaseAdapter {

    private StudentInfo[] studentInfoArray;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private ClassInfo mClassInfo;
    public StudentInfoListAdapter(Context context, ClassInfo classInfo) {
        this.mContext = context;
        this.mClassInfo = classInfo;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return studentInfoArray==null?0:studentInfoArray.length;
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
            convertView = layoutInflater.inflate(R.layout.student_info_item_layout,parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFaceToStudentActivity.openPage((BaseActicity)mContext,studentInfoArray[position]);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        TextView mTxtStudentId = viewHolder.getView(R.id.student_id);
        TextView mTxtStudentTag = viewHolder.getView(R.id.student_tag);
        final TextView mTxtFaceNum = viewHolder.getView(R.id.face_num_tip);
        String strStudentId = studentInfoArray[position].person_name;
        String strStudentTag = studentInfoArray[position].tag;
        if(StringUtils.isNotNullAndEmpty(strStudentId)){
            mTxtStudentId.setText(strStudentId);
        }
        if(StringUtils.isNotNullAndEmpty(strStudentTag)){
            mTxtStudentTag.setText(strStudentTag);
        }
        final StringBuilder builder = new StringBuilder(mContext.getResources().getString(R.string.face_num));


        BasePostParameters postParameters = new BasePostParameters(BasePostParameters.PostType.Person_Face);
        postParameters.setPersonName(studentInfoArray[position].person_name);
        TaskUtils.getData(postParameters, new RequestResultInterface() {
            @Override
            public void start() {

            }

            @Override
            public void success(Object o) {
                try{
                    JSONObject jsonObject = (JSONObject)o;
                    JSONArray faceArray = jsonObject.getJSONArray("face");
                    if(faceArray!=null){
                        int faceNum = faceArray.length();
                        builder.append(faceNum);
                    }else{
                        builder.append(0);
                    }
                    mTxtFaceNum.setText(builder.toString());
                }catch (Exception e){
                    builder.append(0);
                    mTxtFaceNum.setText(builder.toString());
                }

            }

            @Override
            public void failed() {
                builder.append(0);
                mTxtFaceNum.setText(builder.toString());
            }
        });
        return convertView;
    }


    public void setData(StudentInfo[] studentInfoArray) {
        if (studentInfoArray == null || studentInfoArray.length == 0) {
            return;
        }
        this.studentInfoArray = studentInfoArray;
        notifyDataSetChanged();
    }
}
