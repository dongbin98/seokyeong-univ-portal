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


public class SplashActivity extends AppCompatActivity {
    Animation animFadeIn;
    ConstraintLayout constraintLayout;

	SplashFormBinding binding;
	SplashViewModel viewModel;

	// for Json File
    final String file1164 = "1164.json";
    final String file2115 = "2115.json";
    int routeUpdate;
    boolean fileExist = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

	    /* DataBinding */
	    binding = DataBindingUtil.setContentView(this, R.layout.splash_form);
	    binding.setLifecycleOwner(this);
	    viewModel = new SplashViewModel(getApplication());
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

        routeUpdate = 0;

        File f1164 = new File(getFilesDir(), file1164);
        File f2115 = new File(getFilesDir(), file2115);

        if(!f1164.exists() && !f2115.exists()) {
            viewModel.getStaton1164();
            viewModel.getStaton2115();
        } else if(!f1164.exists() && f2115.exists()) {
            routeUpdate++;
            viewModel.getStaton1164();
        } else if(f1164.exists() && !f2115.exists()) {
            routeUpdate++;
            viewModel.getStaton2115();
        } else {
            fileExist = true;
        }
        constraintLayout.startAnimation(animFadeIn);

       /* viewModel.station1164.observe(binding.getLifecycleOwner(), new Observer<List<ResponseStationItem>>() {
            @Override
            public void onChanged(List<ResponseStationItem> responseStationItems) {
                if(responseStationItems == null) {
                    Toast.makeText(getApplicationContext(), "1164번 노선정보를 불러오지 못했습니다 마이페이지를 통해 갱신해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        viewModel.write1164File(viewModel.makeJson(responseStationItems, "1164"));
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "1164번 노선정보를 불러오지 못했습니다 마이페이지를 통해 갱신해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        viewModel.station2115.observe(binding.getLifecycleOwner(), new Observer<List<ResponseStationItem>>() {
            @Override
            public void onChanged(List<ResponseStationItem> responseStationItems) {
                if(responseStationItems == null) {
                    Toast.makeText(getApplicationContext(), "2115번 노선정보를 불러오지 못했습니다 마이페이지를 통해 갱신해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        viewModel.write2115File(viewModel.makeJson(responseStationItems, "2115"));
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "2115번 노선정보를 불러오지 못했습니다 마이페이지를 통해 갱신해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        viewModel.file1164State.observe(binding.getLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                routeUpdate++;
                if(s.equals("F")) {
                    Toast.makeText(getApplicationContext(), "1164번 노선정보를 불러오지 못했습니다 마이페이지를 통해 갱신해주세요", Toast.LENGTH_SHORT).show();
                }
                if(routeUpdate == 2) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }
        });

        viewModel.file2115State.observe(binding.getLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                routeUpdate++;
                if(s.equals("F")) {
                    Toast.makeText(getApplicationContext(), "2115번 노선정보를 불러오지 못했습니다 마이페이지를 통해 갱신해주세요", Toast.LENGTH_SHORT).show();
                }
                if(routeUpdate == 2) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }
        });*/
    }
}
