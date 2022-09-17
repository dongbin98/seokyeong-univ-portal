package com.dbsh.skup.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AttendanceItem> data;

    Context context;

    //아이템 클릭 리스너 인터페이스
    interface OnItemClickListener{
        void onItemClick(View v, int position); //뷰와 포지션값
    }
    //리스너 객체 참조 변수
    private OnItemClickListener mListener = null;
    //리스너 객체 참조를 어댑터에 전달 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void dataClear() {data.clear();}
    public AttendanceAdapter(List<AttendanceItem> data) {
        this.data = data;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.attendance_list, parent, false);
        AttendanceHolder header = new AttendanceHolder(view);
        return header;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final AttendanceItem item = data.get(position);
        final AttendanceHolder itemController = (AttendanceHolder) holder;
        itemController.attendance_title.setText(item.text);
        itemController.attendance_subj.setText(item.text2);
        itemController.attendance_percent.setText(item.text3 + "%");
        
        if(Integer.parseInt(item.text3) == 100) {
            // 파란색 막대기
        }
        else if(Integer.parseInt(item.text3) >= 75 && Integer.parseInt(item.text3) < 100) {
            // 주황색 막대기
            itemController.attendance_progressbar.setProgressDrawable(context.getDrawable(R.drawable.attendance_list_progressbar2));
        }
        else {
            // 빨간색 막대기
            itemController.attendance_progressbar.setProgressDrawable(context.getDrawable(R.drawable.attendance_list_progressbar3));
        }
        
        itemController.itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (position!=RecyclerView.NO_POSITION){
                    if (mListener!=null){
                        mListener.onItemClick (view, position);
                    }
                }
            }
        });
        itemController.attendance_progressbar.setProgress(item.percent);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class AttendanceHolder extends RecyclerView.ViewHolder {
        public TextView attendance_title;   // 교과목명
        public TextView attendance_subj;    // 학수번호
        public TextView attendance_percent; // 출석률
        public ProgressBar attendance_progressbar; // 프로그레스바

        public AttendanceHolder(View itemView) {
            super(itemView);
            attendance_title = (TextView) itemView.findViewById(R.id.attendance_title);
            attendance_subj = (TextView) itemView.findViewById(R.id.attendance_subj);
            attendance_percent = (TextView) itemView.findViewById(R.id.attendance_percent);
            attendance_progressbar = (ProgressBar) itemView.findViewById(R.id.attendance_progressbar);
        }
    }

    public static class AttendanceItem {
        public String text, text2, text3;
        public int percent;
        public AttendanceItem() {}
        public AttendanceItem(String text, String text2, String text3, int percent) {
            this.text = text;       // 과목명
            this.text2 = text2;     // 학수번호
            this.text3 = text3;     // 퍼센트
            this.percent = percent; // 프로그레스바에 넣을 int형 퍼센트
        }
    }
}
