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

public class GradeTermAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GradeTermItem> data;

    Context context;

    public void dataClear() {data.clear();}
    public GradeTermAdapter(List<GradeTermItem> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.grade_term_list, parent, false);
	    GradeTermHolder header = new GradeTermHolder(view);
        return header;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GradeTermItem item = data.get(position);
        final GradeTermHolder itemController = (GradeTermHolder) holder;

	    if(item.grade.equals("F")) {
		    itemController.gradeType.setTextColor(context.getColor(R.color.mainRed));
		    itemController.grade.setTextColor(context.getColor(R.color.mainRed));
		    itemController.gradeCircle.setImageDrawable(context.getDrawable(R.drawable.imageview_attendance_red_dot_circle));
	    } else {
		    itemController.gradeType.setTextColor(context.getColor(R.color.mainBlue));
		    itemController.grade.setTextColor(context.getColor(R.color.mainBlue));
		    itemController.gradeCircle.setImageDrawable(context.getDrawable(R.drawable.imageview_attendance_blue_dot_circle));
	    }
		itemController.grade.setText(item.grade);
		itemController.gradeType.setText(item.type);
	    itemController.subjectAndProfessor.setText(item.subject + " | " + item.professor);
	    itemController.gradeCd.setText("ㆍ" + item.subjectCd);
	    itemController.gradeCredit.setText("ㆍ" + item.credit + "학점");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class GradeTermHolder extends RecyclerView.ViewHolder {
        public ImageView gradeCircle;       // 성적 동그라미
        public TextView grade;              // 성적
        public TextView gradeType;          // 전공 교양 구분
	    public TextView gradeCredit;        // 학점
	    public TextView gradeCd;            // 학수번호
	    public TextView subjectAndProfessor;// 수업 및 교수 명

        public GradeTermHolder(View itemView) {
            super(itemView);
			gradeCircle = (ImageView) itemView.findViewById(R.id.grade_term_type_circle);
			grade = (TextView) itemView.findViewById(R.id.grade_term_grade);
	        gradeType = (TextView) itemView.findViewById(R.id.grade_term_type);
	        gradeCredit = (TextView) itemView.findViewById(R.id.grade_term_credit);
			gradeCd = (TextView) itemView.findViewById(R.id.grade_term_cd);
	        subjectAndProfessor = (TextView) itemView.findViewById(R.id.grade_term_name);
        }
    }

    public static class GradeTermItem {
        public String grade, type, subject, subjectCd, credit, professor;
        public int percent;
        public double subjTime;
        public GradeTermItem() {}
	    public GradeTermItem(String grade, String type, String subject, String subjectCd, String credit, String professor) {
		    this.grade = grade;
		    this.type = type;
		    this.subject = subject;
		    this.subjectCd = subjectCd;
		    this.credit = credit;
		    this.professor = professor;
	    }
    }
}