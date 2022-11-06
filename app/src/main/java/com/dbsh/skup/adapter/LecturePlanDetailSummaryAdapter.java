package com.dbsh.skup.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;

import java.util.List;

import kotlin.Suppress;

public class LecturePlanDetailSummaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<BookItem> data;
    Context context;

    public LecturePlanDetailSummaryAdapter(List<BookItem> data) {this.data = data;}
    public void dataClear() {data.clear();}

    public int getHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Display display = context.getDisplay();
            display.getRealMetrics(displayMetrics);
        } else {
            @Suppress(names = "DEPRECATION")
            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            display.getMetrics(displayMetrics);
        }
        // 화면 크기의 1/4 사이즈
        int pixelHeight = displayMetrics.heightPixels;
        float density = displayMetrics.density;

        if(density == 1.0)
            density *= 4.0;
        else if(density == 1.5)
            density *= (8 / 3);
        else if(density == 2.0)
            density *= 2.0;

        int dp = (int) (pixelHeight / density);
        System.out.println("화면 dp 높이" + dp);

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()) / 4 ;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.lecture_plan_detail_summary_book_list, parent, false);
        GridLayoutManager.LayoutParams params = new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getHeight());
        params.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, context.getResources().getDisplayMetrics());
        view.setLayoutParams(params);
        return new BookHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final BookItem item = data.get(position);
        final BookHolder itemController = (BookHolder) holder;

        itemController.bookTitle.setText(item.title);
        itemController.bookAuthor.setText(item.author + "ㆍ" + item.year);
        itemController.bookPublisher.setText(item.publisher);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class BookHolder extends RecyclerView.ViewHolder {
        TextView bookTitle;
        TextView bookAuthor;
        TextView bookPublisher;

        public BookHolder(View itemView) {
            super(itemView);
	        bookTitle = (TextView) itemView.findViewById(R.id.lectureplan_detail_summary_book_title);
	        bookAuthor = (TextView) itemView.findViewById(R.id.lectuerplan_detail_summary_book_author);
	        bookPublisher = (TextView) itemView.findViewById(R.id.lectuerplan_detail_summary_book_publisher);
        }
    }

    public static class BookItem {
        public String title, author, year, publisher;
        public BookItem() {}

	    public BookItem(String title, String author, String year, String publisher) {
		    this.title = title;
		    this.author = author;
		    this.year = year;
		    this.publisher = publisher;
	    }

	    public String getTitle() {
		    return title;
	    }

	    public String getAuthor() {
		    return author;
	    }

	    public String getYear() {
		    return year;
	    }

	    public String getPublisher() {
		    return publisher;
	    }
    }
}