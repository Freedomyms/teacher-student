package com.myself.teacher.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myself.teacher.R;
import com.myself.teacher.base.IConstant;
import com.myself.teacher.base.MyApplication;
import com.myself.teacher.db.DaoSession;
import com.myself.teacher.db.User;
import com.myself.teacher.db.UserDao;
import com.myself.teacher.ui.newteacher.activity.NewTeacherActivity;
import com.myself.teacher.utils.SharedPreferencesUtils;
import com.myself.teacher.utils.ToastUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvLoginTitle, mTvSignup;
    private boolean isAdmin = false;//管理员登录
    private EditText mEtUsername, mEtPass;
    private CircleImageView mCivLoginHeader;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    private void initView() {
        mTvLoginTitle = findViewById(R.id.tv_login_title);

        mEtUsername = findViewById(R.id.et_username);
        mEtPass = findViewById(R.id.et_pass);
        mCivLoginHeader = findViewById(R.id.civ_login_header);
        mTvSignup = findViewById(R.id.tv_signup);

        mTvSignup.setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    private void initData() {
        type = SharedPreferencesUtils.getInt(IConstant.THEACHER_TYPE, -1);
        if (type == 1) {
            mTvLoginTitle.setText("新教师登录");
            String name = SharedPreferencesUtils.getString(IConstant.LOGIN_NEW_TEACHER_USERNAME, "");
            String pass = SharedPreferencesUtils.getString(IConstant.LOGIN_NEW_TEACHER_PASSWORD, "");
            mEtUsername.setText(name);
            mEtPass.setText(pass);
        }
        if (type == 2) {
            mTvLoginTitle.setText("培训教师登录");
            String name = SharedPreferencesUtils.getString(IConstant.LOGIN_OLD_TEACHER_USERNAME, "");
            String pass = SharedPreferencesUtils.getString(IConstant.LOGIN_OLD_TEACHER_PASSWORD, "");
            mEtUsername.setText(name);
            mEtPass.setText(pass);
        }
        if (type == 3) {
            mTvLoginTitle.setText("管理员登录");
            mTvSignup.setVisibility(View.GONE);
            String name = SharedPreferencesUtils.getString(IConstant.LOGIN_ADMIN_USERNAME, "");
            String pass = SharedPreferencesUtils.getString(IConstant.LOGIN_ADMIN_PASSWORD, "");
            mEtUsername.setText(name);
            mEtPass.setText(pass);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_signup:
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        String namestring = mEtUsername.getText().toString().trim();
        String passstring = mEtPass.getText().toString().trim();
        DaoSession daoSession = MyApplication.getMyInstance().getDaoSession();
        UserDao userDao = daoSession.getUserDao();
        List<User> users = userDao.loadAll();
       /* for(int i=0;i<users.size();i++){
            Log.e("登录",users.get(i).toString());
        }*/
        if (checkAccountInput(namestring, passstring)) {
            if (type == 1) {
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getUsername().trim().equals(namestring) && users.get(i).getPassword().trim().equals(passstring)
                            && users.get(i).getPermission() == type) {
                        SharedPreferencesUtils.putString(IConstant.LOGIN_NEW_TEACHER_USERNAME, namestring);
                        SharedPreferencesUtils.putString(IConstant.LOGIN_NEW_TEACHER_PASSWORD, passstring);
                        ToastUtils.showShort("登录成功！");
                        Intent intent =new Intent(LoginActivity.this,NewTeacherActivity.class);
                        startActivity(intent);
                        return;
                    }
                }
                ToastUtils.showShort("用户名和密码不匹配！");
            }
            if (type == 2) {
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getUsername().trim().equals(namestring) && users.get(i).getPassword().trim().equals(passstring)
                            && users.get(i).getPermission() == type) {
                        SharedPreferencesUtils.putString(IConstant.LOGIN_OLD_TEACHER_USERNAME, namestring);
                        SharedPreferencesUtils.putString(IConstant.LOGIN_OLD_TEACHER_PASSWORD, passstring);
                        ToastUtils.showShort("登录成功！");
                        return;
                    }
                    //Log.e("LoginActivity",users.get(i).toString());
                }
                ToastUtils.showShort("用户名和密码不匹配！");
            }
            if (type == 3) {
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getUsername().trim().equals(namestring) && users.get(i).getPassword().trim().equals(passstring)
                            && users.get(i).getPermission() == type) {
                        SharedPreferencesUtils.putString(IConstant.LOGIN_ADMIN_USERNAME, namestring);
                        SharedPreferencesUtils.putString(IConstant.LOGIN_ADMIN_PASSWORD, passstring);
                        ToastUtils.showShort("登录成功！");
                        return;
                    }
                    //Log.e("LoginActivity",users.get(i).toString());
                }
                ToastUtils.showShort("管理员名称和密码不匹配！");
            }
        }
    }

    /**
     * 核对输入信息
     */
    private boolean checkAccountInput(String namestring, String passstring) {
        if (TextUtils.isEmpty(namestring)) {
            mEtUsername.requestFocus();
            mEtUsername.setError("用户名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(passstring)) {
            mEtPass.requestFocus();
            mEtPass.setError("密码不能为空");
            return false;
        }
        String userPasswordMatchReg = "^(\\w){3,16}$";
        if (!passstring.matches(userPasswordMatchReg)) {
            mEtPass.requestFocus();
            mEtPass.setError("必须为3到16个字符");
            return false;
        }
        return true;
    }

    private void loadUserPhoto() {
        String path = SharedPreferencesUtils.getString(IConstant.PHOTO_PATH, "");
        if (!path.isEmpty()) {
            Glide.with(this).load(path).into(mCivLoginHeader);
        }
    }

    @Override
    protected void onRestart() {
        loadUserPhoto();
        super.onRestart();
    }
}
