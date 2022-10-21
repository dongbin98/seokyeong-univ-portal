package com.dbsh.skup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;

import java.util.ArrayList;

public class LecturePlanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private ArrayList<LectureplanItem> data;
    private ArrayList<LectureplanItem> filteredData;

    Context context;
    boolean clickable;

    // 데이터 덜 들어왔을 때 아이템 클릭 방지 메소드
    public void setAdapterClickable(boolean clickable) {
        this.clickable = clickable;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String search = charSequence.toString();
                if (search.isEmpty()) {     // 필터가 비어있을 시
                    filteredData = data;
                } else {                    // 뭔가 검색했을 시
                    ArrayList<LectureplanItem> filteringData = new ArrayList<>();
                    for(LectureplanItem item : data) {
                        if(item.subjectName.toLowerCase().contains(search.toLowerCase()) ||
                            item.subjectCd.toLowerCase().contains(search.toLowerCase()) ||
                            item.department.toLowerCase().contains(search.toLowerCase()) ||
                            item.professorName.toLowerCase().contains(search.toLowerCase())
                        ) {
                            filteringData.add(item);
                        }
                    }
                    filteredData = filteringData;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredData = (ArrayList<LectureplanItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    // 아이템 클릭 리스너 인터페이스
    public interface OnItemClickListener{
        void onItemClick(LectureplanItem data); // 데이터 보내기
    }
    // 리스너 객체 참조 변수
    private OnItemClickListener mListener = null;
    //리스너 객체 참조를 어댑터에 전달 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public LecturePlanAdapter(ArrayList<LectureplanItem> data) {
        this.data = data;
        this.filteredData = data;
    }
	public void dataClear() {
		data.clear();
		filteredData.clear();
	}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.lecture_plan_list, parent, false);
        LectureplanHolder header = new LectureplanHolder(view);
        return header;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        final LectureplanItem item = filteredData.get(position);
	    final LectureplanItem item = (filteredData != null) ? filteredData.get(position) : data.get(position);
        final LectureplanHolder itemController = (LectureplanHolder) holder;
        if (item.professorName.equals("")) {
            itemController.lectureplanSubjectAndProfessor.setText(item.subjectName);
        } else {
            itemController.lectureplanSubjectAndProfessor.setText(item.subjectName + " | " + item.professorName);
        }
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
                        mListener.onItemClick (filteredData.get(position));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
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

    public void setFilter(ArrayList<LectureplanItem> searchData) {
        this.data = searchData;
        notifyDataSetChanged();
    }

    public static class LectureplanItem {
        public String subjectName, professorName, department, subjectCd, college, grade, credit, time, classNumb, professorId, year, term;
        public LectureplanItem() {}

	    public LectureplanItem(String subjectName, String professorName, String department, String subjectCd, String college, String grade, String credit, String time, String classNumb, String professorId, String year, String term) {
            this.subjectName = (subjectName != null ? subjectName : "");        // 교과명
            this.professorName = (professorName != null ? professorName : "");  // 교수명
            this.department = (department != null ? department : "");           // 학과명
            this.subjectCd = (subjectCd != null ? subjectCd : "");          // 학수번호
            this.college = (college != null ? college : "");                // 단대명
            this.grade = (grade != null ? grade : "");                      // 대상 학년
            this.credit = (credit != null ? credit : "");                   // 학점
            this.time = (time != null ? time : "");                         // 수업 시간
		    this.classNumb = (classNumb != null ? classNumb : "");          // 분반
		    this.professorId = (professorId != null ? professorId : "");    // 교수 ID
		    this.year = (year != null ? year : "");                         // 년도
		    this.term = (term != null ? term : "");                         // 학기
        }
    }
}