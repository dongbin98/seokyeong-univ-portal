package com.dbsh.skup.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.dbsh.skup.R;
import com.dbsh.skup.adapter.GraduatePagerAdapter;
import com.dbsh.skup.databinding.GraduateFormBinding;
import com.dbsh.skup.viewmodels.GraduateViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class GraduateActivity extends AppCompatActivity {
    private long time = 0;
    private GraduateFormBinding binding;
    private GraduateViewModel viewModel;

    private TabLayout tabs;
    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;

    private int numPage = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* DataBinding */
        binding = DataBindingUtil.setContentView(this, R.layout.graduate_form);
        binding.setLifecycleOwner(this);
        viewModel = new GraduateViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();    // 바인딩 강제 즉시실행

        Intent intent = getIntent();
        Bundle bundle = new Bundle();

        Toolbar mToolbar = binding.graduateToolbar;
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        tabs = binding.graduateTab;
        String[] titles = new String[] {"교과", "비교과"};

        mPager = binding.graduateViewpager;
        pagerAdapter = new GraduatePagerAdapter(this, numPage, bundle);
        mPager.setAdapter(pagerAdapter);
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager.setCurrentItem(0);
        mPager.setOffscreenPageLimit(2);
        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        new TabLayoutMediator(tabs, mPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
            }
        }).attach();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
