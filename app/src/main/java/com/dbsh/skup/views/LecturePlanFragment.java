package com.dbsh.skup.views;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;
import com.dbsh.skup.adapter.LectureplanAdapter;
import com.dbsh.skup.adapter.LinearLayoutManagerWrapper;
import com.dbsh.skup.adapter.SpinnerAdapter;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.LecturePlanFormBinding;
import com.dbsh.skup.model.ResponseLectureplanList;
import com.dbsh.skup.model.ResponseYearList;
import com.dbsh.skup.viewmodels.LecturePlanViewModel;

import java.util.ArrayList;

public class LecturePlanFragment extends Fragment implements OnBackPressedListener {
    LecturePlanFormBinding binding;
    LecturePlanViewModel viewModel;

	// parent Fragment
	private HomeLeftContainer homeLeftContainer;

    String token;
    String id;
    String year;
    String term;
    int totalCount;
    int nowCount;

    Spinner lectureplahSpinner;
    Spinner lectureplahSpinner2;
    ImageButton lectureplanBtn;
    RecyclerView lectureplanRecyclerview;

    ArrayList<String> spinnerItem, spinnerItem2;
    ArrayList<LectureplanAdapter.LectureplanItem> data;          // 원본
    LectureplanAdapter adapter;

    UserData userData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    /* DataBinding */
	    binding = DataBindingUtil.inflate(inflater, R.layout.lecture_plan_form, container, false);
	    binding.setLifecycleOwner(this);
	    viewModel = new LecturePlanViewModel();
	    binding.setViewModel(viewModel);
	    binding.executePendingBindings();    // 바인딩 강제 즉시실행

	    homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());

        userData = ((UserData) getActivity().getApplication());

        token = userData.getToken();
        id = userData.getId();
        year = userData.getSchYear();
        term = userData.getSchTerm();

        data = new ArrayList<>();

        Toolbar mToolbar = binding.lectureplanToolbar;

	    ((HomeActivity) getActivity()).setSupportActionBar(mToolbar);
	    ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    ((HomeActivity) getActivity()).getSupportActionBar().setTitle("");

        lectureplahSpinner = binding.lectureplanSpinner;
        lectureplahSpinner2 = binding.lectureplanSpinner2;
        lectureplanBtn = binding.lectureplanBtn;
        lectureplanRecyclerview = binding.lectureplanRecyclerview;
        adapter = new LectureplanAdapter(data);

        // 강의계획서, 주차별 진도사항 보기
        adapter.setOnItemClickListener(new LectureplanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LectureplanAdapter.LectureplanItem data) {
                Toast.makeText(getContext(), data.subjectName + "클릭!\n상세 페이지 미구현", Toast.LENGTH_SHORT).show();
            }
        });

	    LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), LinearLayoutManager.VERTICAL, false);
        lectureplanRecyclerview.setLayoutManager(linearLayoutManagerWrapper);
        lectureplanRecyclerview.setAdapter(adapter);

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
        lectureplahSpinner.setAdapter(spinnerAdapter);
        lectureplahSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = spinnerItem.get(i).substring(0, 4);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        SpinnerAdapter spinnerAdapter2 = new SpinnerAdapter(getContext(), spinnerItem2);
        lectureplahSpinner2.setAdapter(spinnerAdapter2);
        lectureplahSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                term = spinnerItem2.get(i).substring(0, 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        for (int i = 0; i < spinnerItem.size(); i++) {
            if (spinnerItem.get(i).substring(0, 4).equals(year)) {
                lectureplahSpinner.setSelection(i);
            }
        }

        for (int i = 0; i < spinnerItem2.size(); i++) {
            if (spinnerItem2.get(i).substring(0, 1).equals(term)) {
                lectureplahSpinner2.setSelection(i);
            }
        }

        // 강의 조회버튼
        lectureplanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setAdapterClickable(false);
                lectureplanBtn.setClickable(false);
				binding.lectureplanSearch.setText("");
	            adapter.dataClear();
                data.clear();
	            adapter.notifyDataSetChanged();
                totalCount = 0;
                nowCount = 0;
                viewModel.getLecturePlan(token, id, year, term);
            }
        });

        adapter.setAdapterClickable(false);
        lectureplanBtn.setClickable(false);
	    adapter.dataClear();
        data.clear();
		adapter.notifyDataSetChanged();
        nowCount = 0;
        totalCount = 0;
        viewModel.getLecturePlan(token, id, year, term);

        viewModel.totalSizeLiveData.observe(binding.getLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                totalCount = integer;
            }
        });

        viewModel.responseLectureplanListLiveData.observe(binding.getLifecycleOwner(), new Observer<ResponseLectureplanList>() {
            @Override
            public void onChanged(ResponseLectureplanList responseLectureplanList) {
                String subject = "", professor = "", department = "", subjectCd = "", college = "", grade = "", credit = "", time = "";
                if(responseLectureplanList != null) {
                    /* 유효성 검사 */
                    if(responseLectureplanList.getSubjNm() != null)
                        subject = responseLectureplanList.getSubjNm();
                    if(responseLectureplanList.getProfNm() != null)
                        professor = responseLectureplanList.getProfNm();
                    if(responseLectureplanList.getDeptNm() != null)
                        department = responseLectureplanList.getDeptNm();
                    if(responseLectureplanList.getSubjCd() != null)
                        subjectCd = responseLectureplanList.getSubjCd();
                    if(responseLectureplanList.getColeNm() != null)
                        college = responseLectureplanList.getColeNm();
                    if(responseLectureplanList.getSchlShyr() != null)
                        grade = responseLectureplanList.getSchlShyr();
                    if(responseLectureplanList.getSubjPont() != null)
                        credit = responseLectureplanList.getSubjPont();
                    if(responseLectureplanList.getSubjTime() != null)
                        time = responseLectureplanList.getSubjTime();

                    /* 데이터 추가 */
                    data.add(new LectureplanAdapter.LectureplanItem(subject, professor, department, subjectCd, college, grade, credit, time));
                    nowCount++;
                }
	            adapter.notifyItemInserted(data.size());
                if (totalCount == nowCount) {
                    System.out.println("All List Done!");
                    lectureplanBtn.setClickable(true);
                    adapter.setAdapterClickable(true);
                }
            }
        });

        binding.lectureplanSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
		((HomeActivity) context).setOnBackPressedListner(this);
	}
}
