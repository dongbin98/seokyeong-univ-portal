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

import java.util.ArrayList;

public class LecturePlanDetailWeekAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<LecturePlanDetailWeekItem> data;

    Context context;
    boolean clickable;

    // 데이터 덜 들어왔을 때 아이템 클릭 방지 메소드
    public void setAdapterClickable(boolean clickable) {
        this.clickable = clickable;
    }

    // 아이템 클릭 리스너 인터페이스
    public interface OnItemClickListener{
        void onItemClick(LecturePlanDetailWeekItem data); // 데이터 보내기
    }
    // 리스너 객체 참조 변수
    private OnItemClickListener mListener = null;
    //리스너 객체 참조를 어댑터에 전달 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public LecturePlanDetailWeekAdapter(ArrayList<LecturePlanDetailWeekItem> data) {
        this.data = data;
    }
	public void dataClear() {
		data.clear();
	}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.lecture_plan_detail_week_list, parent, false);
        LecturePlanDetailWeekHolder header = new LecturePlanDetailWeekHolder(view);
        return header;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
	    final LecturePlanDetailWeekItem item = data.get(position);
        final LecturePlanDetailWeekHolder itemController = (LecturePlanDetailWeekHolder) holder;

        itemController.lecturePlanDetailWeekNumber.setText(item.number);
        itemController.lecturePlanDetailWeekValue.setText(item.value);
        if(item.isEnd) {
            itemController.lecturePlanDetailWeekLine.setVisibility(View.INVISIBLE);
        }

        itemController.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (position != RecyclerView.NO_POSITION){
                    if (mListener!=null && clickable){
                        mListener.onItemClick (data.get(position));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class LecturePlanDetailWeekHolder extends RecyclerView.ViewHolder {
        public TextView lecturePlanDetailWeekNumber;
        public TextView lecturePlanDetailWeekValue;
        public ImageView lecturePlanDetailWeekLine;

        public LecturePlanDetailWeekHolder(View itemView) {
            super(itemView);
            lecturePlanDetailWeekNumber = (TextView) itemView.findViewById(R.id.lectureplan_detail_week_list_number);
            lecturePlanDetailWeekValue = (TextView) itemView.findViewById(R.id.lectureplan_detail_week_list_value);
            lecturePlanDetailWeekLine = (ImageView) itemView.findViewById(R.id.lectureplan_detail_week_list_line);
        }
    }

    public static class LecturePlanDetailWeekItem {
        public String number, value, plan, type, report;
        public boolean isEnd;
        public LecturePlanDetailWeekItem() {}

        public LecturePlanDetailWeekItem(String number, String value, String plan, String type, String report, boolean isEnd) {
            this.number = number;   // 주
            this.value = value.replaceAll("\\n+", "\n");     // 주별주제
            this.plan = plan;       // 강의계획 및 내용
            this.type = type;       // 강의진행방식
            this.report = report;   // 과제물,시험,독서
            this.isEnd = isEnd;     // 마지막 리스트 여부
        }
    }
}