package com.hunter.helloandroid.module.coordinator.userinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.helloandroid.R;


/**
 * 一个简单的fragement
 */
public class UserInfoFragement extends Fragment {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    String[] s = new String[]{"dwda", "dwhnudh", "dwhnudh",
            "dwhnudh", "dwhnudh", "dwhnudh", "dwhnudh", "dwhnudh", "dwhnudh"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recycleadapterxml, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycle);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        UserInfoRecycleAdapter madapter = new UserInfoRecycleAdapter(s);
        mRecyclerView.setAdapter(madapter);
    //    mUserInfoRecycleAdapter madapter = new mUserInfoRecycleAdapter(s);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        return root;
    }


}
