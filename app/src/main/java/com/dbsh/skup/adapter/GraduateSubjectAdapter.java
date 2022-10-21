package com.dbsh.skup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;

import java.util.List;

public class GraduateSubjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<GraduateSubjectItem> data;
    Context context;

    public GraduateSubjectAdapter(List<GraduateSubjectItem> data) {this.data = data;}
    public void dataClear() {data.clear();}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.graduate_subject_list, parent, false);
        return new GraduateSubjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final GraduateSubjectItem item = data.get(position);
        final GraduateSubjectHolder itemController = (GraduateSubjectHolder) holder;

        if(item.grade.equals("F")) {
            itemController.graduateSubjectType.setTextColor(context.getColor(R.color.mainRed));
            itemController.graduateSubjectGrade.setTextColor(context.getColor(R.color.mainRed));
            itemController.graduateSubjectCircle.setImageDrawable(context.getDrawable(R.drawable.imageview_attendance_red_dot_circle));
        } else {
            itemController.graduateSubjectType.setTextColor(context.getColor(R.color.mainBlue));
            itemController.graduateSubjectGrade.setTextColor(context.getColor(R.color.mainBlue));
            itemController.graduateSubjectCircle.setImageDrawable(context.getDrawable(R.drawable.imageview_attendance_blue_dot_circle));
        }
        itemController.graduateSubjectType.setText(item.type);
        itemController.graduateSubjectName.setText(item.name);
        itemController.graduateSubjectTypeDetail.setText("ㆍ" + item.typeDetail);
        itemController.graduateSubjectGrade.setText(item.grade);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class GraduateSubjectHolder extends RecyclerView.ViewHolder {
        TextView graduateSubjectType;
        TextView graduateSubjectName;
        TextView graduateSubjectTypeDetail;
        TextView graduateSubjectGrade;
        ImageView graduateSubjectCircle;

        public GraduateSubjectHolder(View itemView) {
            super(itemView);
            graduateSubjectType = (TextView) itemView.findViewById(R.id.graduate_subject_type);
            graduateSubjectName = (TextView) itemView.findViewById(R.id.graduate_subject_name);
            graduateSubjectTypeDetail = (TextView) itemView.findViewById(R.id.graduate_subject_type_detail);
            graduateSubjectGrade = (TextView) itemView.findViewById(R.id.graduate_subject_grade);
            graduateSubjectCircle = (ImageView) itemView.findViewById(R.id.graduate_subject_type_circle);
        }
    }

    public static class GraduateSubjectItem {
        public String type, name, typeDetail, grade;
        public GraduateSubjectItem() {}
        public GraduateSubjectItem(String type, String name, String typeDetail, String grade) {
            this.type = (type != null ? type : "");               // 교과영역 타입
            this.name = (name != null ? name : "");               // 교과영역 강의명
            this.typeDetail = (typeDetail != null ? typeDetail : "");   // 세부 역량
            this.grade = (grade != null ? grade : "");             // 성적
        }
    }
}