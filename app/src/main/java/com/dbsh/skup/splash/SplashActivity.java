package com.dbsh.skup.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dbsh.skup.R;
// import com.dbsh.skup.login.MainActivity;
import com.dbsh.skup.tmpstructure.views.MainActivity;

public class SplashActivity extends AppCompatActivity {
    Animation animFadeIn;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_form);

        constraintLayout = (ConstraintLayout) findViewById(R.id.splash_layout);
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
        constraintLayout.startAnimation(animFadeIn);
    }
}
