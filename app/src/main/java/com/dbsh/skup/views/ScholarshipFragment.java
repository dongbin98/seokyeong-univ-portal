package com.dbsh.skup.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;
import com.dbsh.skup.adapter.LinearLayoutManagerWrapper;
import com.dbsh.skup.adapter.ScholarshipAdapter;
import com.dbsh.skup.adapter.SpinnerAdapter;
import com.dbsh.skup.model.UserData;
import com.dbsh.skup.databinding.ScholarshipFormBinding;
import com.dbsh.skup.dto.ResponseScholarList;
import com.dbsh.skup.dto.ResponseYearList;
import com.dbsh.skup.viewmodels.ScholarshipViewModel;

import java.util.ArrayList;
import java.util.List;

public class ScholarshipFragment extends Fragment implements OnBackPressedListener {

    private ScholarshipFormBinding binding;
    private ScholarshipViewModel viewModel;

	// parent Fragment
	private HomeLeftContainer homeLeftContainer;

    String token;
    String id;
    String year;
    String term;

    Spinner scholarshipSpinner;
    Spinner scholarshipSpinner2;
    ImageButton scholarshipBtn;
    RecyclerView scholarshipRecyclerview;

	ArrayList<String> spinnerItem, spinnerItem2;
	List<ScholarshipAdapter.ScholarshipItem> data;
	ScholarshipAdapter adapter;

	UserData userData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* DataBinding */
		binding = DataBindingUtil.inflate(inflater, R.layout.scholarship_form, container, false);
		viewModel = new ScholarshipViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();    // 바인딩 강제 즉시실행

		homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());

        userData = ((UserData) getActivity().getApplication());

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

		((HomeActivity) getActivity()).setSupportActionBar(mToolbar);
		((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		((HomeActivity) getActivity()).getSupportActionBar().setTitle("");

	    LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false);
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

	    SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getContext(), spinnerItem);
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

	    SpinnerAdapter spinnerAdapter2 = new SpinnerAdapter(getContext(), spinnerItem2);
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
                viewModel.getScholar(token, id, year, term);
            }
        });

        scholarshipBtn.setClickable(false);
	    adapter.dataClear();
        data.clear();
	    adapter.notifyDataSetChanged();
        viewModel.getScholar(token, id, year, term);

        viewModel.scholarLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ResponseScholarList>>() {
	        @Override
	        public void onChanged(ArrayList<ResponseScholarList> responseScholarLists) {
				// 데이터가 없을 시에도 버튼 활성화를 처리해주기 위함
				if(responseScholarLists != null) {
					for (ResponseScholarList responseScholarList : responseScholarLists) {
						// 어댑터에서 null 처리
						data.add(new ScholarshipAdapter.ScholarshipItem(
								responseScholarList.getSclsNm(),
								responseScholarList.getSclsNm(),
								responseScholarList.getRemkText(),
								responseScholarList.getSclsAmt().replace(" ", "")));
						adapter.notifyItemInserted(data.size());
					}
				}
				scholarshipBtn.setClickable(true);
	        }
        });

		return binding.getRoot();
    }

	@Override
	public void onBackPressed() {
		homeLeftContainer.getChildFragmentManager().beginTransaction().remove(this).commit();
		homeLeftContainer.getChildFragmentManager().popBackStackImmediate();
		homeLeftContainer.popFragment();
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		((HomeActivity) context).setOnBackPressedListener(this);
	}
}
