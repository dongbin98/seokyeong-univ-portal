package com.dbsh.skup.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;
import com.dbsh.skup.adapter.LinearLayoutManagerWrapper;
import com.dbsh.skup.adapter.ScholarshipAdapter;
import com.dbsh.skup.adapter.SpinnerAdapter;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.ScholarshipFormBinding;
import com.dbsh.skup.model.ResponseScholarList;
import com.dbsh.skup.model.ResponseYearList;
import com.dbsh.skup.viewmodels.ScholarshipViewModel;

import java.util.ArrayList;
import java.util.List;

public class ScholarshipActivity extends AppCompatActivity {

    private ScholarshipFormBinding binding;
    private ScholarshipViewModel viewModel;

    String token;
    String id;
    String year;
    String term;
    int totalCount;
    int nowCount;

    Spinner scholarshipSpinner;
    Spinner scholarshipSpinner2;
    ImageButton scholarshipBtn;
    RecyclerView scholarshipRecyclerview;

	ArrayList<String> spinnerItem, spinnerItem2;
	List<ScholarshipAdapter.ScholarshipItem> data;
	ScholarshipAdapter adapter;

	UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* DataBinding */
        binding = DataBindingUtil.setContentView(this, R.layout.scholarship_form);
        binding.setLifecycleOwner(this);
        viewModel = new ScholarshipViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();	// 바인딩 강제 즉시실행

        userData = ((UserData) getApplication());

        Intent intent = getIntent();

        token = userData.getToken();
        id = userData.getId();
        year = userData.getSchYear();
        term = userData.getSchTerm();

	    data = new ArrayList<>();

	    scholarshipSpinner = binding.scholarshipSpinner;
	    scholarshipSpinner2 = binding.scholarshipSpinner2;
	    scholarshipBtn = binding.scholarshipBtn;
	    scholarshipRecyclerview = binding.scholarshipRecyclerview;
        adapter = new ScholarshipAdapter(data);

	    Toolbar mToolbar = binding.scholarshipToolbar;
	    setSupportActionBar(mToolbar);

	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    getSupportActionBar().setTitle("");

	    LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
	    scholarshipRecyclerview.setLayoutManager(linearLayoutManagerWrapper);
	    scholarshipRecyclerview.setAdapter(adapter);

        spinnerItem = new ArrayList<>();
        for (ResponseYearList yearList : userData.getYearList()) {
            String value = yearList.getValue() + "년";

            if (!spinnerItem.contains(value)) {
                spinnerItem.add(value);
            }
        }

        spinnerItem2 = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            String value = i + "학기";

            if (!spinnerItem2.contains(value)) {
                spinnerItem2.add(value);
            }
        }

	    SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, spinnerItem);
	    scholarshipSpinner.setAdapter(spinnerAdapter);
	    scholarshipSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
			    year = spinnerItem.get(i).substring(0, 4);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> adapterView) {
		    }
	    });

	    SpinnerAdapter spinnerAdapter2 = new SpinnerAdapter(this, spinnerItem2);
	    scholarshipSpinner2.setAdapter(spinnerAdapter2);
	    scholarshipSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
			    term = spinnerItem2.get(i).substring(0, 1);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> adapterView) {
		    }
	    });

        for(int i = 0; i < spinnerItem.size(); i++) {
            if(spinnerItem.get(i).substring(0, 4).equals(year)) {
                scholarshipSpinner.setSelection(i);
            }
        }

        for(int i = 0; i < spinnerItem2.size(); i++) {
            if(spinnerItem2.get(i).substring(0, 1).equals(term)) {
                scholarshipSpinner2.setSelection(i);
            }
        }

        // 장학 조회버튼
        scholarshipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scholarshipBtn.setClickable(false);
				adapter.dataClear();
                data.clear();
	            adapter.notifyDataSetChanged();
                totalCount = 0;
                nowCount = 0;
                viewModel.getScholar(token, id, year, term);
            }
        });

        scholarshipBtn.setClickable(false);
	    adapter.dataClear();
        data.clear();
	    adapter.notifyDataSetChanged();
        nowCount = 0;
        totalCount = 0;
        viewModel.getScholar(token, id, year, term);

        viewModel.totalSizeLiveData.observe(binding.getLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                totalCount = integer;
            }
        });

        viewModel.scholarLiveData.observe(binding.getLifecycleOwner(), new Observer<ResponseScholarList>() {
            @Override
            public void onChanged(ResponseScholarList responseScholarList) {
                if(responseScholarList != null) {
                    data.add(new ScholarshipAdapter.ScholarshipItem(
                            responseScholarList.getSclsNm(),
                            responseScholarList.getSclsNm(),
                            responseScholarList.getRemkText(),
                            responseScholarList.getSclsAmt().replace(" ", "")));
                    nowCount++;
                }
	            adapter.notifyItemInserted(data.size());
                if (totalCount == nowCount) {
                    scholarshipBtn.setClickable(true);
                }
            }
        });
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
				finish();
				return true;
			}
		}
		return super.onOptionsItemSelected(item);
	}
}
