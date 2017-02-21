package com.example.ldcc.volley_test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;

import com.astuetz.PagerSlidingTabStrip;
import com.example.ldcc.volley_test.controller.PagerAdapter;
import com.example.ldcc.volley_test.etc.Dlog;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";

    private ViewPager pager;
    private PagerSlidingTabStrip tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        // 첫번째 fragment를 제일 먼저 보여 주겠다.
        pager.setCurrentItem(0);

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);


        //Firebase 추가
        FirebaseMessaging.getInstance().subscribeToTopic("alert");
//        String token = FirebaseInstanceId.getInstance().getToken();
//        Dlog.d(TAG, "Start Refreshed token: " + token);F

        //화면 항상 켜놓는 기능
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
