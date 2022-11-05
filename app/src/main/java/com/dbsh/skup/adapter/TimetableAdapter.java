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

public class TimetableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<TimetableItem> data;

    Context context;

    public TimetableAdapter(List<TimetableItem> data) {this.data = data;}
    public void dataClear() {data.clear();}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.timetable_etc_form, parent, false);
        return new TimetableHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TimetableItem item = data.get(position);
        final TimetableHolder itemController = (TimetableHolder) holder;

        itemController.lectureTitle.setText(item.title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TimetableHolder extends RecyclerView.ViewHolder {
        TextView lectureTitle;

        public TimetableHolder(View itemView) {
            super(itemView);
	        lectureTitle = (TextView) itemView.findViewById(R.id.timetable_etc_title);

        }
    }

    public static class TimetableItem {
        public String title;
        public TimetableItem() {}

        public TimetableItem(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}