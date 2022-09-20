package com.dbsh.skup.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dbsh.skup.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

	private final List<String> list;
	private final LayoutInflater inflater;
	private String text;

	public SpinnerAdapter(Context context, ArrayList<String> list) {
		this.list = list;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (list != null)
			return list.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// 화면에 들어왔을 때 보여지는 텍스트뷰 설정
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.attendance_spinner_form, parent, false);
		if (list != null) {
			text = list.get(position);
			((TextView) convertView.findViewById(R.id.attendance_spinner_text)).setText(text);
		}
		return convertView;
	}

	// 클릭 후 나타나는 텍스트뷰 설정
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.attendance_spinner_inner_form, parent, false);
		if (list != null) {
			text = list.get(position);
			((TextView) convertView.findViewById(R.id.attendance_spinner_inner_text)).setText(text);
		}

		return convertView;
	}

	// 스피너에서 선택된 아이템을 액티비티에서 꺼내오는 메서드
	public String getItem() {
		return text;
	}
}