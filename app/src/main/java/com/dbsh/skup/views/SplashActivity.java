package com.dbsh.skup.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.SplashFormBinding;
import com.dbsh.skup.viewmodels.SplashViewModel;

import java.io.File;
import java.io.IOException;


public class SplashActivity extends AppCompatActivity {
    Animation animFadeIn;
    ConstraintLayout constraintLayout;

	SplashFormBinding binding;
	SplashViewModel viewModel;

	// for Json File
	final String fileName = "station.json";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

	    /* DataBinding */
	    binding = DataBindingUtil.setContentView(this, R.layout.splash_form);
	    binding.setLifecycleOwner(this);
	    viewModel = new SplashViewModel(getApplicationContext());
	    binding.setViewModel(viewModel);
	    binding.executePendingBindings();	// 바인딩 강제 즉시실행

        constraintLayout = binding.splashLayout;
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_splash_fadein);
        animFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

	    File file = new File(getFilesDir(), fileName);
	    if (!file.exists()) {
		    System.out.println("파일이 존재하지 않음");
		    try {
			    viewModel.getFile();
			    constraintLayout.startAnimation(animFadeIn);
		    } catch (IOException e) {
			    e.printStackTrace();
		    }
	    } else {
		    constraintLayout.startAnimation(animFadeIn);
	    }
    }
}
