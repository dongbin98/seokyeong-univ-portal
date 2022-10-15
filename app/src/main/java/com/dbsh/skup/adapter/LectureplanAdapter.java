package com.dbsh.skup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;

import java.util.List;

public class LectureplanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<LectureplanItem> data;

    Context context;
    boolean clickable;

    // 데이터 덜 들어왔을 때 아이템 클릭 방지 메소드
    public void setAdapterClickable(boolean clickable) {
        this.clickable = clickable;
    }

    // 아이템 클릭 리스너 인터페이스
    public interface OnItemClickListener{
        void onItemClick(View v, int position); //뷰와 포지션값
    }
    // 리스너 객체 참조 변수
    private OnItemClickListener mListener = null;
    //리스너 객체 참조를 어댑터에 전달 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void dataClear() {data.clear();}
    public LectureplanAdapter(List<LectureplanItem> data) {
        this.data = data;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.lectureplan_list, parent, false);
        LectureplanHolder header = new LectureplanHolder(view);
        return header;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final LectureplanItem item = data.get(position);
        final LectureplanHolder itemController = (LectureplanHolder) holder;
        itemController.lectureplanSubjectAndProfessor.setText(item.subjectName + " | " + item.professorName);
        itemController.lectureplanDepartment.setText(item.department);
        itemController.lectureplanSubjectCd.setText("ㆍ" + item.subjectCd);
        itemController.lectureplanCollege.setText(item.college);
        itemController.lectureplanGrade.setText(item.grade);
        itemController.lectureplanCredit.setText(item.credit + "학점");
        itemController.lectureplanTime.setText(item.time + "시간");

        itemController.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (position != RecyclerView.NO_POSITION){
                    if (mListener!=null && clickable){
                        mListener.onItemClick (view, position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class LectureplanHolder extends RecyclerView.ViewHolder {
        public TextView lectureplanSubjectAndProfessor;
        public TextView lectureplanDepartment;
        public TextView lectureplanSubjectCd;
        public TextView lectureplanCollege;
        public TextView lectureplanGrade;
        public TextView lectureplanCredit;
        public TextView lectureplanTime;

        public LectureplanHolder(View itemView) {
            super(itemView);
            lectureplanSubjectAndProfessor = (TextView) itemView.findViewById(R.id.lectureplan_subject_and_professor);
            lectureplanDepartment = (TextView) itemView.findViewById(R.id.lectureplan_department);
            lectureplanSubjectCd = (TextView) itemView.findViewById(R.id.lectureplan_subject_cd);
            lectureplanCollege = (TextView) itemView.findViewById(R.id.lectureplan_college);
            lectureplanGrade = (TextView) itemView.findViewById(R.id.lectureplan_grade);
            lectureplanCredit = (TextView) itemView.findViewById(R.id.lectureplan_credit);
            lectureplanTime = (TextView) itemView.findViewById(R.id.lectureplan_time);
        }
    }

    public static class LectureplanItem {
        public String subjectName, professorName, department, subjectCd, college, grade, credit, time;
        public LectureplanItem() {}

        public LectureplanItem(String subjectName, String professorName, String department, String subjectCd, String college, String grade, String credit, String time) {
            this.subjectName = subjectName;         // 교과명
            this.professorName = professorName;     // 교수명
            this.department = department;           // 학과명
            this.subjectCd = subjectCd;             // 학수번호
            this.college = college;                 // 단대명
            this.grade = grade;                     // 학년
            this.credit = credit;                   // 학점
            this.time = time;                       // 시간
        }
    }
}