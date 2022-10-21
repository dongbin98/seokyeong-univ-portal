package com.dbsh.skup.adapter;

import android.animation.ValueAnimator;
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
        itemController.attendance_title.setText(item.subjName);
        itemController.attendance_subj.setText(item.subjCd);
        itemController.attendance_percent.setText(item.strPercent + "%");

	    // 애니메이션 적용
	    setAnimation(itemController.attendance_progressbar, itemController.attendance_percent, item.percent);

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

	    public TextView getAttendance_title() {
		    return attendance_title;
	    }

	    public TextView getAttendance_subj() {
		    return attendance_subj;
	    }

	    public TextView getAttendance_percent() {
		    return attendance_percent;
	    }

	    public ProgressBar getAttendance_progressbar() {
		    return attendance_progressbar;
	    }
    }

    public static class AttendanceItem {
        public String subjName, subjCd, strPercent, subjNumber;
        public int percent;
        public double subjTime;
        public AttendanceItem() {}
        public AttendanceItem(String subjName, String subjCd, String strPercent, String subjNumber, int percent, double subjTime) {
            this.subjName = (subjName != null ? subjName : "");           // 과목명
            this.subjCd = (subjCd != null ? subjCd : "");               // 학수번호
            this.strPercent = (strPercent != null ? strPercent : "");       // 퍼센트
	        this.subjNumber = (subjNumber != null ? subjNumber : "");       // 분반
            this.percent = percent;             // 프로그레스바에 넣을 int형 퍼센트
            this.subjTime = subjTime;           // 해당 수업 시간
        }
    }

	private void setAnimation(final ProgressBar progressBar, final TextView textView, final int percent) {
		ValueAnimator animator = ValueAnimator.ofInt(0, percent).setDuration(1000);

		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				if((int) valueAnimator.getAnimatedValue() == 100) {
					// 파란색 막대기
					progressBar.setProgressDrawable(context.getDrawable(R.drawable.attendance_list_blue_progressbar));
				}
				else if((int) valueAnimator.getAnimatedValue() >= 75 && (int) valueAnimator.getAnimatedValue() < 100) {
					// 주황색 막대기
					progressBar.setProgressDrawable(context.getDrawable(R.drawable.attendance_list_yellow_progressbar));
				}
				else {
					// 빨간색 막대기
					progressBar.setProgressDrawable(context.getDrawable(R.drawable.attendance_list_red_progressbar));
				}
				textView.setText((int) valueAnimator.getAnimatedValue() + "%");
				progressBar.setProgress((int) valueAnimator.getAnimatedValue());
			}
		});
		animator.start();
	}
}