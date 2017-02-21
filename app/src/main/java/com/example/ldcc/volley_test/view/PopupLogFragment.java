package com.example.ldcc.volley_test.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.baoyz.widget.PullRefreshLayout;
import com.example.ldcc.volley_test.R;
import com.example.ldcc.volley_test.controller.ListViewCPUAdater;
import com.example.ldcc.volley_test.etc.Consts;
import com.example.ldcc.volley_test.etc.Sharedpreferences;
import com.example.ldcc.volley_test.model.ObjectCPU;

import java.util.ArrayList;

public class PopupLogFragment extends Fragment {

    private View convertView;

    private GridView mLogListView;
    private ListViewCPUAdater mLogAdapter;
    private ArrayList<ObjectCPU> mLogArrayList;

    private PullRefreshLayout mPullRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.fragment_popup_log, container, false);
        return convertView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        initAdater();
        initData();
        initButton();
    }

    private void init(){
        mLogListView = (GridView) convertView.findViewById(R.id.log_listview);
        mLogArrayList = new ArrayList<>();
        mLogAdapter = new ListViewCPUAdater(getContext(), mLogArrayList);
        mPullRefreshLayout = (PullRefreshLayout) convertView.findViewById(R.id.swipeRefreshLayout);
    }

    private void initAdater(){
        mLogListView.setAdapter(mLogAdapter);
    }

    private void initData() {
        mLogArrayList.removeAll(mLogArrayList);
        if(Sharedpreferences.getArrayListCPUPreferences(getContext(), Consts.CPU_ARRAYLIST) != null) {
            mLogArrayList.addAll(Sharedpreferences.getArrayListCPUPreferences(getContext(), Consts.CPU_ARRAYLIST));
        }
        mLogAdapter.notifyDataSetChanged();
    }

    private void initButton(){
        mLogListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog mAlertDialog = new AlertDialog.Builder(getContext())
                        .setCancelable(false)
                        .setTitle("LOG 삭제")
                        .setMessage("삭제하시겠습니까?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                removeObject(mLogArrayList, position);
                                mLogAdapter.notifyDataSetChanged();
                            }
                        })//setPositiveButton
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })//setNegativeButton
                        .create();
                mAlertDialog.show();
                return false;
            }
        });
        mPullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLogArrayList.removeAll(mLogArrayList);
                        if(Sharedpreferences.getArrayListCPUPreferences(getContext(), Consts.CPU_ARRAYLIST) != null) {
                            mLogArrayList.addAll(Sharedpreferences.getArrayListCPUPreferences(getContext(), Consts.CPU_ARRAYLIST));
                        }
                        mLogAdapter.notifyDataSetChanged();
                        mPullRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        mPullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
    }

    private void removeObject(ArrayList<ObjectCPU> arrayList, int position){
        arrayList.remove(position);
        Sharedpreferences.saveArrayListCPUPreferences(getContext(), Consts.CPU_ARRAYLIST, arrayList);
    }
}