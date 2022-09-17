package com.dbsh.skup.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dbsh.skup.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
	BottomNavigationView bottomNavigationView;
	Menu menu;
	HomeCenterFragment centerFragment;
	HomeLeftFragment leftFragment;
	HomeRightFragment rightFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_form);
		Intent intent = getIntent();

		bottomNavigationView = findViewById(R.id.bottomNavi);
		menu = bottomNavigationView.getMenu();
		centerFragment = new HomeCenterFragment();
//		leftFragment = new HomeLeftFragment();
//		rightFragment = new HomeRightFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.main_container, centerFragment).commit();
		menu.findItem(R.id.home_fragment).setChecked(true);

		bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				switch (item.getItemId()) {
					case R.id.menu_framgent:
						if(leftFragment == null) {
							leftFragment = new HomeLeftFragment();
							getSupportFragmentManager().beginTransaction().add(R.id.main_container, leftFragment).commit();
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
							getSupportFragmentManager().beginTransaction().add(R.id.main_container, centerFragment).commit();
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
							getSupportFragmentManager().beginTransaction().add(R.id.main_container, rightFragment).commit();
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
}
