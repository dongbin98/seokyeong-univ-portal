package com.dbsh.skup.scholarship;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;

import org.w3c.dom.Text;

import java.util.List;

public class ScholarshipAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	List<ScholarshipItem> data;

	Context context;

	ScholarshipAdapter(List<ScholarshipItem> data) {this.data = data;}
	public void dataClear() {data.clear();}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		context = parent.getContext();
		LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.scholarship_list, parent, false);
		return new ScholarshipHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		final ScholarshipItem item = data.get(position);
		final ScholarshipHolder itemController = (ScholarshipHolder) holder;

		itemController.scholarshipType.setText(item.type);
		itemController.scholarshipName.setText(item.name);
		itemController.scholarshipDetail.setText(item.detail);
		itemController.scholarshipAmount.setText(item.amount);
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	public class ScholarshipHolder extends RecyclerView.ViewHolder {
		TextView scholarshipType;
		TextView scholarshipName;
		TextView scholarshipDetail;
		TextView scholarshipAmount;
		ImageView scholarshipTypeCircle;

		public ScholarshipHolder(View itemView) {
			super(itemView);
			scholarshipType = (TextView) itemView.findViewById(R.id.scholarship_type);
			scholarshipName = (TextView) itemView.findViewById(R.id.scholarship_name);
			scholarshipDetail = (TextView) itemView.findViewById(R.id.scholarship_detail);
			scholarshipAmount = (TextView) itemView.findViewById(R.id.scholarship_amount) ;
			scholarshipTypeCircle = (ImageView) itemView.findViewById(R.id.scholarship_type_circle);
		}
	}

	public static class ScholarshipItem {
		public String type, name, detail, amount;
		public ScholarshipItem() {}
		public ScholarshipItem(String type, String name, String detail, String amount) {
			this.type = type.substring(0, 2);       // 장학 타입
			this.name = name;                       // 장학명
			if(detail.equals("null"))                 // 비고
				this.detail = "";
			else
				this.detail = "ㆍ" + detail;
			this.amount = "￦" + amount;             // 금액
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDetail() {
			return detail;
		}

		public void setDetail(String detail) {
			this.detail = detail;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}
	}
}
