package com.dbsh.skup.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.HomeFormBinding;
import com.dbsh.skup.viewmodels.HomeViewModel;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
	Menu menu;
	HomeCenterFragment centerFragment;
	HomeLeftFragment leftFragment;
	HomeRightFragment rightFragment;

	private long time = 0;
	private HomeFormBinding binding;
	private HomeViewModel viewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();

		/* DataBinding */
		binding = DataBindingUtil.setContentView(this, R.layout.home_form);
		binding.setLifecycleOwner(this);
		viewModel = new HomeViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();	// 바인딩 강제 즉시실행

		menu = binding.bottomNavi.getMenu();
		centerFragment = new HomeCenterFragment();
		getSupportFragmentManager().beginTransaction().add(binding.mainContainer.getId(), centerFragment).commit();
		menu.findItem(R.id.home_fragment).setChecked(true);

		binding.bottomNavi.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				switch (item.getItemId()) {
					case R.id.menu_framgent:
						if(leftFragment == null) {
							leftFragment = new HomeLeftFragment();
							getSupportFragmentManager().beginTransaction().add(binding.mainContainer.getId(), leftFragment).commit();
						}
						if(leftFragment != null)
							getSupportFragmentManager().beginTransaction().show(leftFragment).commit();
						if(centerFragment != null)
							getSupportFragmentManager().beginTransaction().hide(centerFragment).commit();
						if(rightFragment != null)
							getSupportFragmentManager().beginTransaction().hide(rightFragment).commit();
						break;
					case R.id.home_fragment:
						if(centerFragment == null) {
							centerFragment = new HomeCenterFragment();
							getSupportFragmentManager().beginTransaction().add(binding.mainContainer.getId(), centerFragment).commit();
						}
						if(leftFragment != null)
							getSupportFragmentManager().beginTransaction().hide(leftFragment).commit();
						if(centerFragment != null)
							getSupportFragmentManager().beginTransaction().show(centerFragment).commit();
						if(rightFragment != null)
							getSupportFragmentManager().beginTransaction().hide(rightFragment).commit();
						break;
					case R.id.setting_fragment:
						if(rightFragment == null) {
							rightFragment = new HomeRightFragment();
							getSupportFragmentManager().beginTransaction().add(binding.mainContainer.getId(), rightFragment).commit();
						}
						if(leftFragment != null)
							getSupportFragmentManager().beginTransaction().hide(leftFragment).commit();
						if(centerFragment != null)
							getSupportFragmentManager().beginTransaction().hide(centerFragment).commit();
						if(rightFragment != null)
							getSupportFragmentManager().beginTransaction().show(rightFragment).commit();
						break;
				}
				return true;
			}
		});
	}

	@Override
	public void onBackPressed() {
		if(System.currentTimeMillis()-time >= 2000) {
			time=System.currentTimeMillis();
			Toast.makeText(getApplicationContext(),"한번 더 누르면 로그인창으로 이동합니다.", Toast.LENGTH_SHORT).show();
		}
		else if(System.currentTimeMillis()-time < 2000){
			finish();
		}
	}
}
