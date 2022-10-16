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

public class GraduateNoneSubjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<GraduateNoneSubjectItem> data;
    Context context;

    public GraduateNoneSubjectAdapter(List<GraduateNoneSubjectItem> data) {this.data = data;}
    public void dataClear() {data.clear();}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.graduate_none_subject_list, parent, false);
        return new GraduateNoneSubjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final GraduateNoneSubjectItem item = data.get(position);
        final GraduateNoneSubjectHolder itemController = (GraduateNoneSubjectHolder) holder;

        if(item.type.equals("이수")) {
            itemController.graduateNoneSubjectType.setTextColor(context.getColor(R.color.mainBlue));
            itemController.graduateNoneSubjectType2.setTextColor(context.getColor(R.color.mainBlue));
            itemController.graduateNoneSubjectTypeCircle.setImageDrawable(context.getDrawable(R.drawable.imageview_attendance_blue_dot_circle));
        } else {
            itemController.graduateNoneSubjectType.setTextColor(context.getColor(R.color.mainRed));
            itemController.graduateNoneSubjectType2.setTextColor(context.getColor(R.color.mainRed));
            itemController.graduateNoneSubjectTypeCircle.setImageDrawable(context.getDrawable(R.drawable.imageview_attendance_red_dot_circle));
        }

        itemController.graduateNoneSubjectType.setText(item.type);
        itemController.graduateNoneSubjectType2.setText(item.type);
        itemController.graduateNoneSubjectName.setText(item.name);
        itemController.graduateNoneSubjectGrade.setText("ㆍ" + item.grade);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class GraduateNoneSubjectHolder extends RecyclerView.ViewHolder {
        TextView graduateNoneSubjectType;
        TextView graduateNoneSubjectType2;
        TextView graduateNoneSubjectName;
        TextView graduateNoneSubjectGrade;
        ImageView graduateNoneSubjectTypeCircle;

        public GraduateNoneSubjectHolder(View itemView) {
            super(itemView);
            graduateNoneSubjectType = (TextView) itemView.findViewById(R.id.graduate_none_subject_type);
            graduateNoneSubjectType2 = (TextView) itemView.findViewById(R.id.graduate_none_subject_type2);
            graduateNoneSubjectName = (TextView) itemView.findViewById(R.id.graduate_none_subject_name);
            graduateNoneSubjectGrade = (TextView) itemView.findViewById(R.id.graduate_none_subject_grade) ;
            graduateNoneSubjectTypeCircle = (ImageView) itemView.findViewById(R.id.graduate_none_subject_type_circle);
        }
    }

    public static class GraduateNoneSubjectItem {
        public String type, name, grade;
        public GraduateNoneSubjectItem() {}
        public GraduateNoneSubjectItem(String type, String name, String grade) {
            if(type.equals("Y") || type.equals("P"))
                this.type = "이수";               // 이수여부
            else
                this.type = "미이수";
            this.name = name;               // 비교과인증 명칭
            this.grade = grade;             // 학년
        }
    }
}