package com.dbsh.skup.views;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.dbsh.skup.R;
import com.dbsh.skup.adapter.HomeNoticeCardAdapter;
import com.dbsh.skup.adapter.HomeTopCardAdapter;
import com.dbsh.skup.data.NoticeData;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.HomeCenterFormBinding;
import com.dbsh.skup.viewmodels.HomeCenterViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

import me.relex.circleindicator.CircleIndicator3;

public class HomeCenterFragment extends Fragment {

    private HomeCenterFormBinding binding;
    private HomeCenterViewModel viewModel;

    private static final String noticeUrl = "https://skuniv.ac.kr/notice";
    private static final String majorNoticeUrl = "https://ce.skuniv.ac.kr/ce_notice";

	// will be replaced Fragment
	private Fragment QrFragment;
	private Fragment AttendanceFragment;

	// this Fragment
	private Fragment HomeCenterFragment;

	// parent Pragment
	private HomeCenterContainer homeCenterContainer;

    UserData userData;

    private ViewPager2 mPager, mPager2, mPager3;
    private CircleIndicator3 mIndicator;
    private FragmentStateAdapter pagerAdapter, pagerAdapter2, pagerAdapter3;
    ArrayList<NoticeData> noticeData, majorNoticeData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* Data Binding */
        binding = DataBindingUtil.inflate(inflater, R.layout.home_center_form, container, false);
        viewModel = new HomeCenterViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

		HomeCenterFragment = this;
		homeCenterContainer = ((HomeCenterContainer) this.getParentFragment());
        userData = ((UserData) getActivity().getApplication());

        noticeData task = new noticeData();
        majorNoticeData task2 = new majorNoticeData();

        try {
            noticeData = task.execute().get();
            majorNoticeData = task2.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // 공지사항 더보기
        binding.mainHomeNoticePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("url", "https://skuniv.ac.kr/notice");
                startActivity(intent);
            }
        });

        // 학과 공지사항 더보기
        binding.mainHomeMajorNoticePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("url", "https://ce.skuniv.ac.kr/ce_notice");
                startActivity(intent);
            }
        });

        // 출결
        binding.mainHomeQuickBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AttendanceFragment == null) {
					AttendanceFragment = new AttendanceFragment();
                }
				homeCenterContainer.replaceFragment(HomeCenterFragment, AttendanceFragment, null);
//	            getParentFragment().replaceFragment(HomeCenterFragment, AttendanceFragment, null);
            }
        });
        // 학사일정
        binding.mainHomeQuickBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        // QR
        binding.mainHomeQuickBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				if (QrFragment == null) {
					QrFragment = new QrcodeFragment();
				}
	            homeCenterContainer.replaceFragment(HomeCenterFragment, QrFragment, null);
			}
        });
        // Portal
        binding.mainHomeQuickBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("url", "https://sportal.skuniv.ac.kr");
                startActivity(intent);
            }
        });

        mPager = binding.viewpager;
        pagerAdapter = new HomeTopCardAdapter(getActivity(), 3);
        mPager.setAdapter(pagerAdapter);
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager.setCurrentItem(0);
        mPager.setOffscreenPageLimit(3);
//        mPager.setPadding(10, 0, 10, 0);

        mIndicator = binding.indicator;
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(3,0);

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
                mIndicator.animatePageSelected(position % 3);
            }
        });

        ArrayList<Fragment> fragments = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
//        for(int i = 0; i < noticeData.size(); i++) {  // 전체 다 가져오기
            fragments.add(HomeCenterNoticeFragment.newInstance(i, noticeData.get(i).getTitle(), noticeData.get(i).getType(), noticeData.get(i).getDate(), noticeData.get(i).getDepartment(), noticeData.get(i).getUrl()));
        }
        mPager2 = binding.viewpager2;
        pagerAdapter2 = new HomeNoticeCardAdapter(getActivity(), fragments);
        mPager2.setAdapter(pagerAdapter2);
        mPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        mPager2.setCurrentItem(0);
        mPager2.setOffscreenPageLimit(3);
        mPager2.setPadding(0, 0, 90, 0);

        mPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager2.setCurrentItem(position);
                }
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        ArrayList<Fragment> fragments2 = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
//        for(int i = 0; i < majorNoticeData.size(); i++) { // 전체 다 가져오기
            fragments2.add(HomeCenterNoticeFragment.newInstance(i, majorNoticeData.get(i).getTitle(), majorNoticeData.get(i).getType(), majorNoticeData.get(i).getDate(), majorNoticeData.get(i).getDepartment(), majorNoticeData.get(i).getUrl()));
        }
        mPager3 = binding.viewpager3;
        pagerAdapter3 = new HomeNoticeCardAdapter(getActivity(), fragments2);
        mPager3.setAdapter(pagerAdapter3);
        mPager3.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager3.setCurrentItem(0);
        mPager3.setOffscreenPageLimit(3);
        mPager3.setPadding(0, 0, 90, 0);

        mPager3.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager3.setCurrentItem(position);
                }
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        return binding.getRoot();
    }

	/* 전체 공지사항 파싱 */
    private class noticeData extends AsyncTask<Void, Void, ArrayList<NoticeData>> {

        @Override
        protected ArrayList<NoticeData> doInBackground(Void... voids) {

            ArrayList<NoticeData> list = new ArrayList<NoticeData>();
            try {
                Document document = Jsoup.connect(noticeUrl).get();
                Elements noticeList = document.select(".bg1");
                noticeList.addAll(document.select(".bg2"));

                for(Element e: noticeList) {
                    NoticeData noticeData = new NoticeData();
                    noticeData.setTitle(e.select(".title").text().substring(3));
                    noticeData.setDate(e.select(".date").text());
                    noticeData.setDepartment(e.select(".author").text());
                    noticeData.setType(e.select(".category").text());
                    noticeData.setNumber(Integer.parseInt(e.select(".num").text()));
                    noticeData.setUrl(e.select(".title").select("a").attr("href"));
                    list.add(noticeData);
                }
                list.sort(new Comparator<NoticeData>() {
                    @Override
                    public int compare(NoticeData noticeData, NoticeData t1) {
                        int result = 1;
                        if(noticeData.getNumber() >= t1.getNumber())
                            result = -1;
                        return result;
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }
    }

    /* 학과 공지사항 파싱 */
    private class majorNoticeData extends AsyncTask<Void, Void, ArrayList<NoticeData>> {

        @Override
        protected ArrayList<NoticeData> doInBackground(Void... voids) {
            ArrayList<NoticeData> list = new ArrayList<NoticeData>();
            try {
                Document document = Jsoup.connect(majorNoticeUrl).get();
                Elements noticeList = document.select("tr.notice");

                for(Element e: noticeList) {
                    NoticeData noticeData = new NoticeData();
                    noticeData.setTitle(e.select(".title").text());
                    noticeData.setDate(e.select(".date").text());
                    noticeData.setDepartment(e.select(".author").text());
                    noticeData.setType(e.select("td.notice").text());
                    noticeData.setUrl(e.select(".title").select("a").attr("href"));
                    list.add(noticeData);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }
    }
}