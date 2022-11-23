package com.dbsh.skup.views;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.dbsh.skup.model.UserData;
import com.dbsh.skup.databinding.PasswordChangeFormBinding;
import com.dbsh.skup.viewmodels.PasswordChangeViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoLoginPasswordChangeFragment extends Fragment implements OnBackPressedListener {

    private PasswordChangeFormBinding binding;
    private PasswordChangeViewModel viewModel;

    // this Fragment
    private Fragment PasswordChangeFragment;

    // parent Fragment
    private PasswordActivity passwordActivity;

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

        /* 비밀번호 유효성 검사 정규식 */
        String symbolCheck = "([0-9].*[!,@,#,^,&,*,(,)])|([!,@,#,^,&,*,(,)].*[0-9])";   // 특문, 숫자 정규식
        String alphabetCheck = "([a-z])|([A-Z])";                         // 영어 대소문자 정규식
        Pattern patternSymbol = Pattern.compile(symbolCheck);
        Pattern patternAlphabet = Pattern.compile(alphabetCheck);

        if(getArguments() != null) {
            id = getArguments().getString("id");
            code = getArguments().getString("code");
        }

        PasswordChangeFragment = this;
        passwordActivity = ((PasswordActivity) this.getActivity());

        Toolbar mToolbar = binding.passwordChangeToolbar;

        ((PasswordActivity) getActivity()).setSupportActionBar(mToolbar);
        ((PasswordActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((PasswordActivity) getActivity()).getSupportActionBar().setTitle("");

        binding.passwordChangePw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Matcher matcherSymbol = patternSymbol.matcher(binding.passwordChangePw.getText().toString());
                Matcher matcherAlphabet = patternAlphabet.matcher(binding.passwordChangePw.getText().toString());

                if (binding.passwordChangePw.getText().length() < 9 || binding.passwordChangePw.getText().length() > 16) {
                    binding.passwordChangePw.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePwCorrect.setTextColor(getContext().getColor(R.color.mainRed));
                    binding.passwordChangePwCorrect.setText("비밀번호는 9~16자여야 합니다");
                } else if (!matcherAlphabet.find() || !matcherSymbol.find()) {
                    binding.passwordChangePw.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePwCorrect.setTextColor(getContext().getColor(R.color.mainRed));
                    binding.passwordChangePwCorrect.setText("비밀번호 규칙에 어긋납니다");
                } else {
                    binding.passwordChangePw.setBackgroundResource(R.drawable.edittext_white_focused_background);
                    binding.passwordChangePwCorrect.setTextColor(getContext().getColor(R.color.mainBlue));
                    binding.passwordChangePwCorrect.setText("비밀번호 규칙을 만족합니다");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.passwordChangePwCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Matcher matcherSymbol = patternSymbol.matcher(binding.passwordChangePw.getText().toString());
                Matcher matcherAlphabet = patternAlphabet.matcher(binding.passwordChangePw.getText().toString());

                if (binding.passwordChangePwCheck.getText().length() < 9 || binding.passwordChangePwCheck.getText().length() > 16) {
                    binding.passwordChangePwCheck.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePwCheckCorrect.setTextColor(getContext().getColor(R.color.mainRed));
                    binding.passwordChangePwCheckCorrect.setText("비밀번호는 9~16자여야 합니다");
                } else if (!matcherAlphabet.find() || !matcherSymbol.find()) {
                    binding.passwordChangePwCheck.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePwCheckCorrect.setTextColor(getContext().getColor(R.color.mainRed));
                    binding.passwordChangePwCorrect.setText("비밀번호 규칙에 어긋납니다");
                } else if (!binding.passwordChangePw.getText().toString().equals(binding.passwordChangePwCheck.getText().toString())){
                    binding.passwordChangePwCheck.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePwCheckCorrect.setTextColor(getContext().getColor(R.color.mainRed));
                    binding.passwordChangePwCheckCorrect.setText("두 비밀번호가 다릅니다");
                } else {
                    binding.passwordChangePwCheck.setBackgroundResource(R.drawable.edittext_white_focused_background);
                    binding.passwordChangePwCheckCorrect.setTextColor(getContext().getColor(R.color.mainBlue));
                    binding.passwordChangePwCheckCorrect.setText("비밀번호 규칙을 만족합니다");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.passwordChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matcher matcherSymbol = patternSymbol.matcher(binding.passwordChangePw.getText().toString());
                Matcher matcherAlphabet = patternAlphabet.matcher(binding.passwordChangePw.getText().toString());

                if (binding.passwordChangePw.getText().length() < 9 || binding.passwordChangePw.getText().length() > 16) {
                    binding.passwordChangePw.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePwCheck.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePw.startAnimation(shakeError());
                    binding.passwordChangePwCheck.startAnimation(shakeError());
                    Toast.makeText(getContext(), "비밀번호는 9~16자여야 합니다", Toast.LENGTH_SHORT).show();
                }
                else if (!binding.passwordChangePw.getText().toString().equals(binding.passwordChangePwCheck.getText().toString())){
                    binding.passwordChangePw.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePwCheck.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePw.startAnimation(shakeError());
                    binding.passwordChangePwCheck.startAnimation(shakeError());
                    Toast.makeText(getContext(), "두 비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
                } else if (!matcherAlphabet.find() || !matcherSymbol.find()) {
                    binding.passwordChangePw.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePwCheck.setBackgroundResource(R.drawable.edittext_white_error_background);
                    binding.passwordChangePw.startAnimation(shakeError());
                    binding.passwordChangePwCheck.startAnimation(shakeError());
                    Toast.makeText(getContext(), "비밀번호 규칙에 어긋납니다", Toast.LENGTH_SHORT).show();
                }
                else {
                    viewModel.getPasswordChangeData(id, binding.passwordChangePw.getText().toString(), binding.passwordChangePwCheck.getText().toString(), code);
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
