package com.myself.teacher.ui.newteacher.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myself.teacher.R;
import com.myself.teacher.base.IConstant;
import com.myself.teacher.base.MyApplication;
import com.myself.teacher.db.DaoSession;
import com.myself.teacher.db.User;
import com.myself.teacher.db.UserDao;
import com.myself.teacher.ui.base.BaseActivity;
import com.myself.teacher.utils.DialogUtils;
import com.myself.teacher.utils.SharedPreferencesUtils;
import com.myself.teacher.utils.ToastUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewMyinfoActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvViewMyinfoTitle,mTvViewUsername,mTvViewGender,mTvViewPhone,mTvViewAddress;
    private CircleImageView mCivViewMyHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_myinfo);
        initView();
        initData();
    }


    private void initView() {
        mTvViewMyinfoTitle = findViewById(R.id.tv_view_myinfo_title);
        mCivViewMyHeader = findViewById(R.id.civ_viewmy_header);
        mTvViewUsername = findViewById(R.id.tv_view_username);
        mTvViewGender = findViewById(R.id.tv_view_gender);
        mTvViewPhone = findViewById(R.id.tv_view_phone);
        mTvViewAddress = findViewById(R.id.tv_view_address);

        findViewById(R.id.iv_view_myinfo_back).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
    }
    private void initData() {
        String userName = SharedPreferencesUtils.getString(IConstant.LOGIN_NEW_TEACHER_USERNAME, "");
        DaoSession daoSession = MyApplication.getMyInstance().getDaoSession();
        UserDao userDao = daoSession.getUserDao();
        List<User> users = userDao.loadAll();
        for(User user:users){
            if(user.getUsername().equals(userName)&&user.getPermission()==1){
                mTvViewUsername.setText(user.getUsername());
                mTvViewGender.setText(user.getGender());
                mTvViewPhone.setText(user.getPhone());
                mTvViewAddress.setText(user.getAddress());
                if(user.getPhoto()!=null){
                    Glide.with(this).load(user.getPhoto()).into(mCivViewMyHeader);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_view_myinfo_back:
                finish();
                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
