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
import com.dbsh.skup.adapter.GraduateNoneSubjectAdapter;
import com.dbsh.skup.adapter.LinearLayoutManagerWrapper;
import com.dbsh.skup.model.UserData;
import com.dbsh.skup.databinding.GraduateNoneSubjectFormBinding;
import com.dbsh.skup.dto.ResponseGraduateNoneSubjectMap;
import com.dbsh.skup.viewmodels.GraduateNoneSubjectViewModel;

import java.util.ArrayList;
import java.util.List;

public class GraduateNoneSubjectFragment extends Fragment {
    GraduateNoneSubjectFormBinding binding;
    GraduateNoneSubjectViewModel viewModel;

    RecyclerView graduateNoneSubjectRecyclerView;
    List<GraduateNoneSubjectAdapter.GraduateNoneSubjectItem> data;
    GraduateNoneSubjectAdapter adapter;

    UserData userData;

    String token, id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.graduate_none_subject_form, container, false);
        viewModel = new GraduateNoneSubjectViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        userData = ((UserData) getActivity().getApplication());

        token = userData.getToken();
        id = userData.getId();

        data = new ArrayList<>();
        graduateNoneSubjectRecyclerView = binding.graduateNoneSubjectRecyclerview;
        adapter = new GraduateNoneSubjectAdapter(data);

	    LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        graduateNoneSubjectRecyclerView.setLayoutManager(linearLayoutManagerWrapper);
        graduateNoneSubjectRecyclerView.setAdapter(adapter);

	    adapter.dataClear();
        data.clear();
	    adapter.notifyDataSetChanged();
        viewModel.getGraduateNoneSubject(token, id);

        viewModel.graduateNoneSubjectLiveData.observe(getViewLifecycleOwner(), new Observer<ResponseGraduateNoneSubjectMap>() {
            @Override
            public void onChanged(ResponseGraduateNoneSubjectMap responseGraduateNoneSubjectMap) {
                data.add(new GraduateNoneSubjectAdapter.GraduateNoneSubjectItem(
                        responseGraduateNoneSubjectMap.getRus10(),
                        "CREOS 캠프",
                        "1학년"
                ));
                data.add(new GraduateNoneSubjectAdapter.GraduateNoneSubjectItem(
                        responseGraduateNoneSubjectMap.getRus12(),
                        "SKON 캠프",
                        "2학년"
                ));
                data.add(new GraduateNoneSubjectAdapter.GraduateNoneSubjectItem(
                        responseGraduateNoneSubjectMap.getRus14(),
                        "DREAM 캠프",
                        "3학년"
                ));
                data.add(new GraduateNoneSubjectAdapter.GraduateNoneSubjectItem(
                        responseGraduateNoneSubjectMap.getRusResult(),
                        "비교과인증 이수여부",
                        "모든 캠프"
                ));
	            adapter.notifyItemInserted(data.size());
            }
        });
        return binding.getRoot();
    }
}
