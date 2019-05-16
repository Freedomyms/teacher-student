package com.myself.teacher.ui.newteacher.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myself.teacher.R;
import com.myself.teacher.base.IConstant;
import com.myself.teacher.base.MyApplication;
import com.myself.teacher.db.DaoSession;
import com.myself.teacher.db.User;
import com.myself.teacher.db.UserDao;
import com.myself.teacher.ui.newteacher.activity.EditMyinfoActivity;
import com.myself.teacher.ui.newteacher.activity.ViewMyinfoActivity;
import com.myself.teacher.utils.DialogUtils;
import com.myself.teacher.utils.SharedPreferencesUtils;
import com.myself.teacher.utils.ToastUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment  implements View.OnClickListener {

    private View mMyView;
    private CircleImageView mCivMyHeader;
    private TextView mTvWelcomeUser;
    private static final int EDIT_CODE=11;
    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMyView=inflater.inflate(R.layout.fragment_my, container, false);
        return mMyView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCivMyHeader = mMyView.findViewById(R.id.civ_my_header);
        mTvWelcomeUser = mMyView.findViewById(R.id.tv_welcome_user);

        mMyView.findViewById(R.id.btn_my_course).setOnClickListener(this);
        mMyView.findViewById(R.id.btn_view_myinfo).setOnClickListener(this);
        mMyView.findViewById(R.id.btn_edit_myinfo).setOnClickListener(this);
        mMyView.findViewById(R.id.btn_exit).setOnClickListener(this);
        initData();
    }
    private void initData() {
       String userName = SharedPreferencesUtils.getString(IConstant.LOGIN_NEW_TEACHER_USERNAME, "");
        if (!userName.isEmpty()) {
            mTvWelcomeUser.setText("Hi,欢迎:" + userName);

        }
        loadUserPhoto(userName);
    }

    private void loadUserPhoto(String userName) {
        DaoSession daoSession = MyApplication.getMyInstance().getDaoSession();
        UserDao userDao = daoSession.getUserDao();
        List<User> users = userDao.loadAll();
        for(User user:users){
            if(user.getUsername().equals(userName)&&user.getPermission()==1){
                if(user.getPhoto()!=null){
                    Glide.with(this).load(user.getPhoto()).into(mCivMyHeader);
                    return;
                }
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_my_course:
                ToastUtils.showLong("还没有课程信息");
                break;
            case R.id.btn_view_myinfo:
                Intent intentUs =new Intent(getActivity(), ViewMyinfoActivity.class);
                startActivity(intentUs);
                break;
            case R.id.btn_edit_myinfo:
                Intent intent =new Intent(getActivity(), EditMyinfoActivity.class);
                startActivityForResult(intent,EDIT_CODE);
                break;
            case R.id.btn_exit:
                    DialogUtils.createDialogForPortrait(getActivity(), new String[]{"取消", "确定"},
                            "确定退出登录?", new DialogUtils.OnOkOrCancelClickListener() {
                                @Override
                                public void clickLeftCancelButton() {
                                    //Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void clickRightOKButton() {
                                   getActivity().finish();
                                }
                            });

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EDIT_CODE:
                    initData();
                    break;
            }
        }
    }
}
