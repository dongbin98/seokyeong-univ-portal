package com.dbsh.skup.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.dbsh.skup.R;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.PasswordChangeFormBinding;
import com.dbsh.skup.viewmodels.PasswordChangeViewModel;

public class PasswordChangeFragment extends Fragment implements OnBackPressedListener {

    private PasswordChangeFormBinding binding;
    private PasswordChangeViewModel viewModel;

    // this Fragment
    private Fragment PasswordChangeFragment;

    // parent Fragment
    private HomeRightContainer homeRightContainer;

    String id, code;
    UserData userData;
    TranslateAnimation anim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* DataBinding */
        binding = DataBindingUtil.inflate(inflater, R.layout.password_change_form, container, false);
        viewModel = new PasswordChangeViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();    // 바인딩 강제 즉시실행

        userData = ((UserData) getActivity().getApplication());
        id = userData.getId();
        if(getArguments() != null) {
            code = getArguments().getString("code");
        }

        PasswordChangeFragment = this;
        homeRightContainer = ((HomeRightContainer) this.getParentFragment());

        Toolbar mToolbar = binding.passwordChangeToolbar;

        ((HomeActivity) getActivity()).setSupportActionBar(mToolbar);
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("");

        binding.passwordChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.passwordChangePw.getText().toString().equals(binding.passwordChangePwCheck.getText().toString()))
                    viewModel.getPasswordChangeData(id, binding.passwordChangePw.getText().toString(), binding.passwordChangePwCheck.getText().toString(), code);
                else if (binding.passwordChangePw.getText().length() < 9 || binding.passwordChangePwCheck.getText().length() < 9){
                    binding.passwordChangePw.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePwCheck.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePw.startAnimation(shakeError());
                    binding.passwordChangePwCheck.startAnimation(shakeError());
                    Toast.makeText(getContext(), "비밀번호는 최소 9자여야합니다", Toast.LENGTH_SHORT).show();
                }
                else {
                    binding.passwordChangePw.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePwCheck.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePw.startAnimation(shakeError());
                    binding.passwordChangePwCheck.startAnimation(shakeError());
                    Toast.makeText(getContext(), "두 비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.changeState.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("S")) {
                    // 다 꺼버리기
                    Toast.makeText(getContext(), "비밀번호가 변경되었습니다. 새로 로그인해주세요", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                } else if(s.equals("F")) {
                    binding.passwordChangePw.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePwCheck.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePw.startAnimation(shakeError());
                    binding.passwordChangePwCheck.startAnimation(shakeError());
                    Toast.makeText(getContext(), "비밀번호는 최소 9자여야합니다", Toast.LENGTH_SHORT).show();
                } else if(s.equals("N")) {
                    Toast.makeText(getContext(), "네트워크 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }

    public TranslateAnimation shakeError() {
        anim = new TranslateAnimation(0, 10, 0, 0);
        anim.setDuration(500);
        anim.setInterpolator(new CycleInterpolator(7));
        return anim;
    }

    @Override
    public void onBackPressed() {
        homeRightContainer.getChildFragmentManager().beginTransaction().remove(this).commit();
        homeRightContainer.getChildFragmentManager().popBackStackImmediate();
        homeRightContainer.popFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((HomeActivity)context).setOnBackPressedListner(this);
    }
}
