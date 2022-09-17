package com.dbsh.skup.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.dbsh.skup.attendance.AttendanceActivity;
import com.dbsh.skup.notice.Notice;
import com.dbsh.skup.R;
import com.dbsh.skup.qrcode.QrcodeActivity;
import com.dbsh.skup.webview.WebviewActivity;
import com.dbsh.skup.adapter.HomeNoticeCardAdapter;
import com.dbsh.skup.adapter.HomeTopCardAdapter;
import com.dbsh.skup.user.User;

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
    private static final String noticeUrl = "https://skuniv.ac.kr/notice";
    private static final String majorNoticeUrl = "https://ce.skuniv.ac.kr/ce_notice";

	User user;

    private ViewPager2 mPager, mPager2, mPager3;
    private CircleIndicator3 mIndicator;
    private FragmentStateAdapter pagerAdapter, pagerAdapter2, pagerAdapter3;
    ArrayList<Notice> notices, majorNotices;

    ImageButton main_home_quick_btn1, main_home_quick_btn2, main_home_quick_btn3, main_home_quick_btn4;
    TextView main_home_notice_plus, main_home_major_notice_plus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home_center_form, container, false);
        noticeData task = new noticeData();
        majorNoticeData task2 = new majorNoticeData();

        try {
            notices = task.execute().get();
            majorNotices = task2.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        user = ((User) getActivity().getApplication());

        main_home_quick_btn1 = (ImageButton) rootView.findViewById(R.id.main_home_quick_btn1);
        main_home_quick_btn2 = (ImageButton) rootView.findViewById(R.id.main_home_quick_btn2);
        main_home_quick_btn3 = (ImageButton) rootView.findViewById(R.id.main_home_quick_btn3);
        main_home_quick_btn4 = (ImageButton) rootView.findViewById(R.id.main_home_quick_btn4);

        main_home_notice_plus = (TextView) rootView.findViewById(R.id.main_home_notice_plus);
        main_home_major_notice_plus = (TextView) rootView.findViewById(R.id.main_home_major_notice_plus);

        // 공지사항 더보기
        main_home_notice_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("url", "https://skuniv.ac.kr/notice");
                startActivity(intent);
            }
        });

        // 학과 공지사항 더보기
        main_home_major_notice_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("url", "https://ce.skuniv.ac.kr/ce_notice");
                startActivity(intent);
            }
        });

        // 출결
        main_home_quick_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AttendanceActivity.class);
                startActivity(intent);
            }
        });
        // 학사일정
        main_home_quick_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        // QR
        main_home_quick_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QrcodeActivity.class);
                startActivity(intent);
            }
        });
        // Portal
        main_home_quick_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("url", "https://sportal.skuniv.ac.kr");
                startActivity(intent);
            }
        });

        mPager = rootView.findViewById(R.id.viewpager);
        pagerAdapter = new HomeTopCardAdapter(getActivity(), 3);
        mPager.setAdapter(pagerAdapter);
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager.setCurrentItem(0);
        mPager.setOffscreenPageLimit(3);
//        mPager.setPadding(10, 0, 10, 0);

        mIndicator = rootView.findViewById(R.id.indicator);
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
        for(int i = 0; i < notices.size(); i++) {
            fragments.add(HomeCenterNoticeFragment.newInstance(i, notices.get(i).getTitle(), notices.get(i).getType(), notices.get(i).getDate(), notices.get(i).getDepartment(), notices.get(i).getUrl()));
        }
        mPager2 = rootView.findViewById(R.id.viewpager2);
        pagerAdapter2 = new HomeNoticeCardAdapter(getActivity(), fragments);
        mPager2.setAdapter(pagerAdapter2);
        mPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        mPager2.setCurrentItem(0);
        mPager2.setOffscreenPageLimit(3);
        mPager2.setPadding(0, 0, 60, 0);

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
        for(int i = 0; i < notices.size(); i++) {
            fragments2.add(HomeCenterNoticeFragment.newInstance(i, majorNotices.get(i).getTitle(), majorNotices.get(i).getType(), majorNotices.get(i).getDate(), majorNotices.get(i).getDepartment(), majorNotices.get(i).getUrl()));
        }
        mPager3 = rootView.findViewById(R.id.viewpager3);
        pagerAdapter3 = new HomeNoticeCardAdapter(getActivity(), fragments2);
        mPager3.setAdapter(pagerAdapter3);
        mPager3.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager3.setCurrentItem(0);
        mPager3.setOffscreenPageLimit(3);
        mPager3.setPadding(0, 0, 60, 0);

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

        return rootView;
    }

    /* 전체 공지사항 파싱 */
    private class noticeData extends AsyncTask<Void, Void, ArrayList<Notice>> {

        @Override
        protected ArrayList<Notice> doInBackground(Void... voids) {

            ArrayList<Notice> list = new ArrayList<Notice>();
            try {
                Document document = Jsoup.connect(noticeUrl).get();
                Elements noticeList = document.select(".bg1");
                noticeList.addAll(document.select(".bg2"));

                int i = 0;
                for(Element e: noticeList) {
                    Notice notice = new Notice();
                    notice.setTitle(e.select(".title").text().substring(3));
                    notice.setDate(e.select(".date").text());
                    notice.setDepartment(e.select(".author").text());
                    notice.setType(e.select(".category").text());
                    notice.setNumber(Integer.parseInt(e.select(".num").text()));
                    notice.setUrl(e.select(".title").select("a").attr("href"));
                    list.add(notice);
                    i++;
                    if(i > 4)
                        break;
                }
                list.sort(new Comparator<Notice>() {
                    @Override
                    public int compare(Notice notice, Notice t1) {
                        int result = 1;
                        if(notice.getNumber() >= t1.getNumber())
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
    private class majorNoticeData extends AsyncTask<Void, Void, ArrayList<Notice>> {

        @Override
        protected ArrayList<Notice> doInBackground(Void... voids) {
            ArrayList<Notice> list = new ArrayList<Notice>();
            try {
                Document document = Jsoup.connect(majorNoticeUrl).get();
                Elements noticeList = document.select("tr.notice");

                int i = 0;
                for(Element e: noticeList) {
                    Notice notice = new Notice();
                    notice.setTitle(e.select(".title").text());
                    notice.setDate(e.select(".date").text());
                    notice.setDepartment(e.select(".author").text());
                    notice.setType(e.select("td.notice").text());
                    notice.setUrl(e.select(".title").select("a").attr("href"));
                    list.add(notice);
                    i++;
                    if(i > 4)
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }
    }
}