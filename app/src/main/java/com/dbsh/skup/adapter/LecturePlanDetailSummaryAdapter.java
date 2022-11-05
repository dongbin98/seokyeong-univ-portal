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

public class LecturePlanDetailSummaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<BookItem> data;

    Context context;

    public LecturePlanDetailSummaryAdapter(List<BookItem> data) {this.data = data;}
    public void dataClear() {data.clear();}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.lecture_plan_detail_summary_book_list, parent, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final BookItem item = data.get(position);
        final BookHolder itemController = (BookHolder) holder;

        itemController.bookTitle.setText(item.title);
        itemController.bookAuthor.setText(item.author);
        itemController.bookYear.setText("ㆍ" + item.year);
        itemController.bookPublisher.setText(item.publisher);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class BookHolder extends RecyclerView.ViewHolder {
        TextView bookTitle;
        TextView bookAuthor;
        TextView bookYear;
        TextView bookPublisher;

        public BookHolder(View itemView) {
            super(itemView);
	        bookTitle = (TextView) itemView.findViewById(R.id.lectureplan_detail_summary_book_title);
	        bookAuthor = (TextView) itemView.findViewById(R.id.lectuerplan_detail_summary_book_author);
	        bookYear = (TextView) itemView.findViewById(R.id.lectuerplan_detail_summary_book_year);
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