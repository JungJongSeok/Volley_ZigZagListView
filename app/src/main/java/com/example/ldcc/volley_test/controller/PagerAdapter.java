package com.example.ldcc.volley_test.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ldcc.volley_test.view.APIFragment;
import com.example.ldcc.volley_test.view.ListViewFragment;
import com.example.ldcc.volley_test.view.PopupLogFragment;

public class PagerAdapter extends FragmentPagerAdapter{

    private final int MAX_FRAGMENT = 3;
    private final int FRAGMENT_ITEM_1 = 0;
    private final int FRAGMENT_ITEM_2 = 1;
    private final int FRAGMENT_ITEM_3 = 2;

    private String[] pageTitle = {"API","Log","ListView"};

    // 생성자 필수
    public PagerAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return pageTitle[position];
    }

    @Override
    public Fragment getItem(int i) {

        // 필요한 fragment 가져오기
        if(i == FRAGMENT_ITEM_1){
            return new APIFragment();
        }
        else if(i == FRAGMENT_ITEM_2){
            return new PopupLogFragment();
        }
        else if(i == FRAGMENT_ITEM_3){
            return new ListViewFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // pager가 보여줘야 하는 fragment 갯수
        return MAX_FRAGMENT;
    }
}

