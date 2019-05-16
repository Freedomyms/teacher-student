package com.myself.teacher.ui.newteacher.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myself.teacher.R;
import com.myself.teacher.ui.newteacher.activity.NoticeListActivity;
import com.myself.teacher.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageFragment extends Fragment implements View.OnClickListener {

    private View mManageView;

    public ManageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mManageView = inflater.inflate(R.layout.fragment_manage, container, false);
        return mManageView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mManageView.findViewById(R.id.ll_notice).setOnClickListener(this);
        mManageView.findViewById(R.id.ll_my_task).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_notice:
                Intent intent=new Intent(getActivity(), NoticeListActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_my_task:
                ToastUtils.showLong("我的任务");
                break;
        }
    }
}
