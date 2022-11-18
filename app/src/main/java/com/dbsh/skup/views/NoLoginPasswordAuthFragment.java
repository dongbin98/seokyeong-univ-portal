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
import com.dbsh.skup.databinding.PasswordAuthFormBinding;
import com.dbsh.skup.viewmodels.PasswordAuthViewModel;

public class NoLoginPasswordAuthFragment extends Fragment implements OnBackPressedListener {

    private PasswordAuthFormBinding binding;
    private PasswordAuthViewModel viewModel;

    // this Fragment
    private Fragment NoLoginPasswordAuthFragment;

    // parent Activity
    private PasswordActivity passwordActivity;

    String type;

    TranslateAnimation anim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* DataBinding */
        binding = DataBindingUtil.inflate(inflater, R.layout.password_auth_form, container, false);
        viewModel = new PasswordAuthViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();    // 바인딩 강제 즉시실행

        NoLoginPasswordAuthFragment = this;
        Toolbar mToolbar = binding.passwordAuthToolbar;

        passwordActivity = (PasswordActivity) getActivity();
        ((PasswordActivity) getActivity()).setSupportActionBar(mToolbar);
        ((PasswordActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((PasswordActivity) getActivity()).getSupportActionBar().setTitle("");

        binding.passwordAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.passwordAuthButton.setClickable(false);
                if(binding.passwordAuthTypePhone.isChecked())
                    type = "PHONE_MOBILE";
                else if(binding.passwordAuthTypeEmail.isChecked()) {
                    type = "EMAIL";
                }
                viewModel.getPasswordAuthData(binding.passwordAuthId.getText().toString(), binding.passwordAuthName.getText().toString(), binding.passwordAuthBirth.getText().toString(), type);
            }
        });

        viewModel.authState.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("S")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", binding.passwordAuthId.getText().toString());
                    passwordActivity.pushFragment(NoLoginPasswordAuthFragment, new NoLoginPasswordCheckFragment(), bundle);
                } else if(s.equals("F")) {
                    binding.passwordAuthId.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordAuthName.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordAuthBirth.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordAuthId.startAnimation(shakeError());
                    binding.passwordAuthName.startAnimation(shakeError());
                    binding.passwordAuthBirth.startAnimation(shakeError());
                    Toast.makeText(getContext(), "정확히 입력했는지 확인해주세요", Toast.LENGTH_SHORT).show();
                } else if(s.equals("N")) {
                    Toast.makeText(getContext(), "네트워크 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                }
                binding.passwordAuthButton.setClickable(true);
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
        getActivity().finish();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((PasswordActivity) context).setOnBackPressedListener(this);
    }
}
