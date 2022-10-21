package com.dbsh.skup.views;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.dbsh.skup.R;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.TuitionFormBinding;
import com.dbsh.skup.model.ResponseTuitionMap;
import com.dbsh.skup.viewmodels.TuitionViewModel;

public class TuitionFragment extends Fragment implements OnBackPressedListener {
	private TuitionFormBinding binding;
	private TuitionViewModel viewModel;

	// parent Fragment
	private HomeLeftContainer homeLeftContainer;

	public UserData userData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    /* DataBinding */
	    binding = DataBindingUtil.inflate(inflater, R.layout.tuition_form, container, false);
	    viewModel = new TuitionViewModel();
	    binding.setViewModel(viewModel);
	    binding.executePendingBindings();    // 바인딩 강제 즉시실행

	    homeLeftContainer = ((HomeLeftContainer) this.getParentFragment());

        Toolbar mToolbar = binding.tuitionToolbar;

	    ((HomeActivity) getActivity()).setSupportActionBar(mToolbar);
	    ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    ((HomeActivity) getActivity()).getSupportActionBar().setTitle("");

	    userData = ((UserData) getActivity().getApplication());

        String token = userData.getToken();
        String id = userData.getId();
        String year = userData.getSchYear();
        String term = userData.getSchTerm();

		binding.tuitionTitle.setText(year + "년 " + term + "학기 등록금");

		viewModel.tuitionLiveData.observe(getViewLifecycleOwner(), new Observer<ResponseTuitionMap>() {
			@Override
			public void onChanged(ResponseTuitionMap responseTuitionMap) {
				binding.tuitionEntfee.setText(responseTuitionMap.getEntFee());
				binding.tuitionLesnfee.setText(responseTuitionMap.getLesnFee());
				binding.tuitionCoopdegree.setText(responseTuitionMap.getCoopDgrAmt());
				binding.tuitionUssentfee.setText(responseTuitionMap.getUssEntFee());
				binding.tuitionUsslesnfee.setText(responseTuitionMap.getUssLesnFee());
				binding.tuitionSclstot.setText(responseTuitionMap.getSclsTot());
				binding.tuitionTotamt.setText(responseTuitionMap.getTotAmt());
				binding.tuitionRegamt.setText(responseTuitionMap.getRegAmt());
				binding.tuitionTmpacct.setText(responseTuitionMap.getTempAcct());
				if (responseTuitionMap.getNonPay().equals("0")) {
					binding.tuitionBackground.setBackground(getContext().getDrawable(R.drawable.tuition_background));
					binding.tuitionCircle.setImageResource(R.drawable.tuition_paid_circle);
					binding.tuitionCircleText.setText("납부 완료");
				} else {
					binding.tuitionBackground.setBackground(getContext().getDrawable(R.drawable.tuition_none_paid_background));
					binding.tuitionCircle.setImageResource(R.drawable.tuition_none_paid_circle);
					binding.tuitionCircleText.setText("납부 미완료");
				}
			}
		});

	    viewModel.getTuition(token, id, year, term);

	    binding.tuitionPaste.setOnClickListener(new View.OnClickListener() {    // 가상계좌 복사버튼 이벤트
		    @Override
		    public void onClick(View view) {
			    ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
			    ClipData clip = ClipData.newPlainText("sex", binding.tuitionTmpacct.getText());
			    clipboard.setPrimaryClip(clip);
			    Toast.makeText(getContext(), "복사되었습니다.", Toast.LENGTH_SHORT).show();
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
		((HomeActivity) context).setOnBackPressedListner(this);
	}
}
