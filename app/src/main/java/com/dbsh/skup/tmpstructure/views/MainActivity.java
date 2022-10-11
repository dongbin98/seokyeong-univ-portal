package com.dbsh.skup.tmpstructure.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.LoginTmpFormBinding;
import com.dbsh.skup.tmpstructure.viewmodels.LoginViewModel;

public class MainActivity extends AppCompatActivity {
	private long time = 0;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LoginTmpFormBinding binding = DataBindingUtil.setContentView(this, R.layout.login_tmp_form);
		binding.setViewModel(new LoginViewModel());
		binding.executePendingBindings();
	}

	@BindingAdapter({"toastMessage"})
	public static void runMe(View view , String message) {
		if (message != null)
			Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onBackPressed() {
		if(System.currentTimeMillis()-time >= 2000) {
			time=System.currentTimeMillis();
			Toast.makeText(getApplicationContext(),"한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
		}
		else if(System.currentTimeMillis()-time < 2000){
			finishAffinity();
			System.runFinalization();
			System.exit(0);
		}
	}
}
