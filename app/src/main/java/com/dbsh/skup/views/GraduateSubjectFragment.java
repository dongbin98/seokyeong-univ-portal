package com.dbsh.skup.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbsh.skup.R;
import com.dbsh.skup.adapter.GraduateSubjectAdapter;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.GraduateSubjectFormBinding;
import com.dbsh.skup.model.ResponseGraduateBasicMap;
import com.dbsh.skup.model.ResponseGraduateSubjectList;
import com.dbsh.skup.viewmodels.GraduateSubjectViewModel;

import java.util.ArrayList;
import java.util.List;

public class GraduateSubjectFragment extends Fragment {
    GraduateSubjectFormBinding binding;
    GraduateSubjectViewModel viewModel;

    RecyclerView graduateSubjectRecyclerView;
    List<GraduateSubjectAdapter.GraduateSubjectItem> data;
    GraduateSubjectAdapter adapter;

    UserData userData;

    String token, id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.graduate_subject_form, container, false);
        viewModel = new GraduateSubjectViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        userData = ((UserData) getActivity().getApplication());

        token = userData.getToken();
        id = userData.getId();

        data = new ArrayList<>();
        graduateSubjectRecyclerView = binding.graduateSubjectRecyclerview;
        adapter = new GraduateSubjectAdapter(data);
        graduateSubjectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        graduateSubjectRecyclerView.setAdapter(adapter);

        data.clear();
        viewModel.getGraduateBasic(token, id);
        viewModel.getGraduateSubject(token, id);

        viewModel.graduateBasicLiveData.observe(getViewLifecycleOwner(), new Observer<ResponseGraduateBasicMap>() {
            @Override
            public void onChanged(ResponseGraduateBasicMap responseGraduateBasicMap) {
                System.out.println(responseGraduateBasicMap.getAcquPnt());
                binding.graduateTotalCredit.setText(responseGraduateBasicMap.getAcquPnt());
                binding.graduateAverageGrade.setText(responseGraduateBasicMap.getGrdMarkAvg());
                binding.graduatePassCheck.setText(responseGraduateBasicMap.getGrdScrnRslt());
            }
        });

        viewModel.graduateSubjectLiveData.observe(getViewLifecycleOwner(), new Observer<ResponseGraduateSubjectList>() {
            @Override
            public void onChanged(ResponseGraduateSubjectList responseGraduateSubjectList) {
                if (responseGraduateSubjectList.getSubjNm() != null) {
                    data.add(new GraduateSubjectAdapter.GraduateSubjectItem(
                            responseGraduateSubjectList.getIsuName(),
                            responseGraduateSubjectList.getSubjNm(),
                            responseGraduateSubjectList.getAreaNm(),
                            responseGraduateSubjectList.getDisData()
                    ));
                    adapter.notifyDataSetChanged();
                }
            }
        });

        return binding.getRoot();
    }
}
