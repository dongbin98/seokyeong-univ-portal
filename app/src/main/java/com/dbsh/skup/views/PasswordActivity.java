package com.dbsh.skup.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.PasswordActivityBinding;
import com.dbsh.skup.viewmodels.PasswordViewModel;

import java.util.ArrayList;
import java.util.List;

public class PasswordActivity extends AppCompatActivity {
	NoLoginPasswordAuthFragment noLoginPasswordAuthFragment;

	private long time = 0;
	private PasswordActivityBinding binding;
	private PasswordViewModel viewModel;

	private OnBackPressedListener mBackListener;

	// before Fragment List
	private ArrayList<Fragment> beforeFragments;

	public void setOnBackPressedListener(OnBackPressedListener listener) {
		mBackListener = listener;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();

		/* DataBinding */
		binding = DataBindingUtil.setContentView(this, R.layout.password_activity);
		binding.setLifecycleOwner(this);
		viewModel = new PasswordViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();	// 바인딩 강제 즉시실행

		beforeFragments = new ArrayList<>();

		noLoginPasswordAuthFragment = new NoLoginPasswordAuthFragment();
		getSupportFragmentManager().beginTransaction().add(binding.passwordContainer.getId(), noLoginPasswordAuthFragment).commit();
	}

	public void pushFragment(Fragment beforeFragment, Fragment afterFragment, Bundle bundle) {
		if (bundle != null) {
			afterFragment.setArguments(bundle);
		}
		beforeFragments.add(beforeFragment);
		System.out.println(beforeFragments.size());
		getSupportFragmentManager().beginTransaction().add(binding.passwordContainer.getId(), afterFragment).addToBackStack(null).commit();
		getSupportFragmentManager().beginTransaction().hide(beforeFragment).commit();
		getSupportFragmentManager().beginTransaction().show(afterFragment).commit();
	}
	public void popFragment() {
		getSupportFragmentManager().beginTransaction().show(beforeFragments.get(beforeFragments.size()-1)).commit();
		beforeFragments.remove(beforeFragments.size()-1);
	}

	public void popFragmentAll() {
		for (int i = 0; i < beforeFragments.size(); i++) {
			beforeFragments.remove(beforeFragments.size()-1);
		}
		getSupportFragmentManager().beginTransaction().show(beforeFragments.get(beforeFragments.size()-1)).commit();
	}

	@Override
	public void onBackPressed() {
		List<Fragment> fragments = getSupportFragmentManager().getFragments();
		for(Fragment fragment : fragments) {
			if(fragment instanceof OnBackPressedListener && fragment.isVisible()) {
				((OnBackPressedListener)fragment).onBackPressed();
				return;
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
