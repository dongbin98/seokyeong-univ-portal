package com.dbsh.skup.tmpstructure.adapter;

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

public class AttendanceDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<AttendanceDetailItem> data;

    Context context;

    public AttendanceDetailAdapter(List<AttendanceDetailItem> data) {this.data = data;}
    public void dataClear() {data.clear();}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.attendance_detail_list, parent, false);
        return new AttendanceDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final AttendanceDetailItem item = data.get(position);
        final AttendanceDetailHolder itemController = (AttendanceDetailHolder) holder;

        if(item.type.equals("출석")) {
            itemController.attendanceType.setTextColor(context.getColor(R.color.mainBlue));
            itemController.attendanceState.setTextColor(context.getColor(R.color.mainBlue));
            itemController.attendanceTypeCircle.setImageDrawable(context.getDrawable(R.drawable.imageview_attendance_blue_dot_circle));
        } else if(item.type.equals("지각")) {
            itemController.attendanceType.setTextColor(context.getColor(R.color.mainYellow));
            itemController.attendanceState.setTextColor(context.getColor(R.color.mainYellow));
            itemController.attendanceTypeCircle.setImageDrawable(context.getDrawable(R.drawable.imageview_attendance_yellow_dot_circle));
        } else {
            itemController.attendanceType.setTextColor(context.getColor(R.color.mainRed));
            itemController.attendanceState.setTextColor(context.getColor(R.color.mainRed));
            itemController.attendanceTypeCircle.setImageDrawable(context.getDrawable(R.drawable.imageview_attendance_red_dot_circle));
        }

        itemController.attendanceType.setText(item.type);
        itemController.attendanceState.setText(item.state);
        itemController.attendanceDate.setText("ㆍ" + item.date);
        itemController.attendanceString.setText(item.text);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class AttendanceDetailHolder extends RecyclerView.ViewHolder {
        TextView attendanceType;
        TextView attendanceState;
        TextView attendanceDate;
        TextView attendanceString;
        ImageView attendanceTypeCircle;

        public AttendanceDetailHolder(View itemView) {
            super(itemView);
            attendanceType = (TextView) itemView.findViewById(R.id.attendance_type);
            attendanceState = (TextView) itemView.findViewById(R.id.attendance_state);
            attendanceDate = (TextView) itemView.findViewById(R.id.attendance_date);
            attendanceString = (TextView) itemView.findViewById(R.id.attendance_string);
            attendanceTypeCircle = (ImageView) itemView.findViewById(R.id.attendance_type_circle);
        }
    }

    public static class AttendanceDetailItem {
        public String type, state, date, text;
        public AttendanceDetailItem() {}

        public AttendanceDetailItem(String type, String state, String date, String text) {
            this.type = type;       // 동그라미 안에 출석 타입
            this.state = state;     // 출석 상태
            this.date = date.replace("/", "-");     // 날짜
            this.text = text;     // 텍스트로 안내
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}