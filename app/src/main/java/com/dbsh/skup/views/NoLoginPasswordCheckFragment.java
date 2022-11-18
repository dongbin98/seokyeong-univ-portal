package com.dbsh.skup.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.dbsh.skup.databinding.PasswordCheckFormBinding;
import com.dbsh.skup.viewmodels.PasswordCheckViewModel;

public class NoLoginPasswordCheckFragment extends Fragment implements OnBackPressedListener {

    private PasswordCheckFormBinding binding;
    private PasswordCheckViewModel viewModel;

    // this Fragment
    private Fragment NoLoginPasswordCheckFragment;

    // parent Activity
    private PasswordActivity passwordActivity;

    String id;
    TranslateAnimation anim;
    CountDownTimer countDownTimer;
    int count = 300;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* DataBinding */
        binding = DataBindingUtil.inflate(inflater, R.layout.password_check_form, container, false);
        viewModel = new PasswordCheckViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();    // 바인딩 강제 즉시실행

        if(getArguments() != null) {
            id = getArguments().getString("id");
        }

        NoLoginPasswordCheckFragment = this;
        passwordActivity = ((PasswordActivity) this.getActivity());

        countDownTimer();
        countDownTimer.start();

        Toolbar mToolbar = binding.passwordCheckToolbar;

        ((PasswordActivity) getActivity()).setSupportActionBar(mToolbar);
        ((PasswordActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((PasswordActivity) getActivity()).getSupportActionBar().setTitle("");

        binding.passwordCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.passwordCheckButton.setClickable(false);
                viewModel.getPasswordCheckData(id, binding.passwordCheckNumber.getText().toString());
            }
        });

        viewModel.checkState.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("S")) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    bundle.putString("code", binding.passwordCheckNumber.getText().toString());
                    passwordActivity.pushFragment(NoLoginPasswordCheckFragment, new NoLoginPasswordChangeFragment(), bundle);
                } else if(s.equals("F")) {
                    binding.passwordCheckNumber.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordCheckNumber.startAnimation(shakeError());
                    Toast.makeText(getContext(), "정확히 입력했는지 확인해주세요", Toast.LENGTH_SHORT).show();
                } else if(s.equals("N")) {
                    Toast.makeText(getContext(), "네트워크 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
                }
                binding.passwordCheckButton.setClickable(true);
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

    public void countDownTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                count -= 1;
                binding.passwordCheckTimer.setText("인증번호 유효시간 " + makeTimeFormat(count));
            }

            @Override
            public void onFinish() {
                Toast.makeText(getContext(), "시간 초과로 다시 인증번호를 발급받으세요", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        };
    }

    @SuppressLint("DefaultLocale")
    public String makeTimeFormat(int second) {
        String time;
        int min, sec;
        min = second / 60;
        sec = second % 60;
        time = String.format("%d:%2d", min, sec);
        return time;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            countDownTimer.cancel();
        } catch (Exception e) {
            countDownTimer = null;
        }
    }

    @Override
    public void onBackPressed() {
        passwordActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        passwordActivity.getSupportFragmentManager().popBackStackImmediate();
        passwordActivity.popFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((PasswordActivity)context).setOnBackPressedListener(this);
    }
}
