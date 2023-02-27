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
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;
import com.dbsh.skup.adapter.GradeTermAdapter;
import com.dbsh.skup.adapter.LinearLayoutManagerWrapper;
import com.dbsh.skup.adapter.SpinnerAdapter;
import com.dbsh.skup.model.UserData;
import com.dbsh.skup.databinding.GradeTermFormBinding;
import com.dbsh.skup.dto.ResponseGradeTermList;
import com.dbsh.skup.dto.ResponseGradeTermSubjectList;
import com.dbsh.skup.dto.ResponseYearList;
import com.dbsh.skup.viewmodels.GradeTermViewModel;

import java.util.ArrayList;
import java.util.List;

public class GradeTermFragment extends Fragment implements OnBackPressedListener {
    GradeTermFormBinding binding;
    GradeTermViewModel viewModel;

	// parent Fragment
	private HomeLeftContainer homeLeftContainer;

	String token;
	String id;
	String year;
	String term;

	Spinner gradeTermSpinner;
	Spinner gradeTermSpinner2;
	ImageButton gradeTermBtn;
	RecyclerView gradeTermRecyclerview;

	ArrayList<String> spinnerItem, spinnerItem2;
	List<GradeTermAdapter.GradeTermItem> data;
	GradeTermAdapter adapter;

	UserData userData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.grade_term_form, container, false);
        viewModel = new GradeTermViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

	    homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());

	    userData = ((UserData) getActivity().getApplication());

	    token = userData.getToken();
	    id = userData.getId();
		year = userData.getSchYear();
		term = userData.getSchTerm();

	    data = new ArrayList<>();

	    gradeTermSpinner = binding.gradeTermSpinner;
	    gradeTermSpinner2 = binding.gradeTermSpinner2;
	    gradeTermBtn = binding.gradeTermBtn;
	    gradeTermRecyclerview = binding.gradeTermRecyclerview;
	    adapter = new GradeTermAdapter(data);

	    LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false);
	    gradeTermRecyclerview.setLayoutManager(linearLayoutManagerWrapper);
	    gradeTermRecyclerview.setAdapter(adapter);

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
	    gradeTermSpinner.setAdapter(spinnerAdapter);
	    gradeTermSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
			    year = spinnerItem.get(i).substring(0, 4);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> adapterView) {
		    }
	    });

	    SpinnerAdapter spinnerAdapter2 = new SpinnerAdapter(getContext(), spinnerItem2);
	    gradeTermSpinner2.setAdapter(spinnerAdapter2);
	    gradeTermSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
			    gradeTermSpinner.setSelection(i);
		    }
	    }

	    for(int i = 0; i < spinnerItem2.size(); i++) {
		    if(spinnerItem2.get(i).substring(0, 1).equals(term)) {
			    gradeTermSpinner2.setSelection(i);
		    }
	    }

		gradeTermBtn.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			    gradeTermBtn.setClickable(false);
			    adapter.dataClear();
			    data.clear();
			    adapter.notifyDataSetChanged();
				binding.gradeTermCredit.setText("");
				binding.gradeTermAverage.setText("");
				binding.gradeTermRank.setText("");
			    viewModel.getGradeTerm(token, id);
			    viewModel.getGradeTermSubject(token, id, year, term);
		    }
	    });

	    gradeTermBtn.setClickable(false);
	    adapter.dataClear();
	    data.clear();
	    adapter.notifyDataSetChanged();
		viewModel.getGradeTerm(token, id);
		viewModel.getGradeTermSubject(token, id, year, term);

		viewModel.responseGradeTermListLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ResponseGradeTermList>>() {
			@Override
			public void onChanged(ArrayList<ResponseGradeTermList> responseGradeTermLists) {
				for(ResponseGradeTermList responseGradeTermList : responseGradeTermLists) {
					if(year.equals(responseGradeTermList.getSchYear()) && term.equals(responseGradeTermList.getSchTerm())) {
						binding.gradeTermCredit.setText(responseGradeTermList.getApplyPnt() + "/" + responseGradeTermList.getAcquPnt().replace(" ", ""));
						binding.gradeTermAverage.setText(responseGradeTermList.getGrdMarkAvg());
						binding.gradeTermRank.setText(responseGradeTermList.getSchRank().replace(" ", ""));
					}
				}
			}
		});

		viewModel.responseGradeTermSubjectLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<ResponseGradeTermSubjectList>>() {
			@Override
			public void onChanged(ArrayList<ResponseGradeTermSubjectList> responseGradeTermSubjectLists) {
				if (responseGradeTermSubjectLists != null) {
					for (ResponseGradeTermSubjectList responseGradeTermSubjectList : responseGradeTermSubjectLists) {
						// 어댑터에서 null 처리
						data.add(new GradeTermAdapter.GradeTermItem(
								responseGradeTermSubjectList.getGrd(),
								responseGradeTermSubjectList.getCmptDivNm(),
								responseGradeTermSubjectList.getSubjNm(),
								responseGradeTermSubjectList.getSubjNo(),
								responseGradeTermSubjectList.getPnt(),
								responseGradeTermSubjectList.getProf1Nm()
						));
						adapter.notifyItemInserted(data.size());
					}
				}
				gradeTermBtn.setClickable(true);
			}
		});

        return binding.getRoot();
    }

	@Override
	public void onBackPressed() {
		homeLeftContainer.getChildFragmentManager().beginTransaction().remove(this).commit();
		homeLeftContainer.getChildFragmentManager().popBackStackImmediate();
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		((HomeActivity) context).setOnBackPressedListener(this);
	}
}
