package com.bs.teachassistant.activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bs.teachassistant.R;
import com.bs.teachassistant.entity.User;
import com.bs.teachassistant.utils.GsonUtils;
import com.bs.teachassistant.utils.ToastUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by limh on 2017/4/25.
 * 用户注册模块
 */
@ContentView(R.layout.activity_regiter)
public class RegisterActivity extends BaseActivity {

    @ViewInject(R.id.tv_register_title)
    TextView mTvRegisterTitle;
    @ViewInject(R.id.edit_regiter_name)
    EditText editName;
    @ViewInject(R.id.edit_regiter_pass1)
    EditText editPass1;
    @ViewInject(R.id.edit_regiter_pass2)
    EditText editPass2;
    @ViewInject(R.id.edit_regiter_phone)
    EditText editPhone;
    @ViewInject(R.id.radio_sex)
    RadioGroup radioSex;


    private ProgressDialog dialog;
    private String sex = "boy";
    private boolean isTeacher = true;
    private int permission=0;
    @Override
    public void initViews() {
        radioSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.radio_boy:
                        sex = "boy";
                        break;
                    case R.id.radio_girl:
                        sex = "girl";
                        break;
                }
            }
        });


    }

    @Override
    public void initDatas() {
        isTeacher= getIntent().getBooleanExtra("is_teacher",true);
        if(isTeacher){
            permission=0;
            mTvRegisterTitle.setText("教师注册");
        }else {
            permission=1;
            mTvRegisterTitle.setText("学生注册");
        }
    }

    @Event(value = {R.id.image_regiter_close, R.id.btn_regiter}, type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_regiter_close:
                finish();
                break;
            case R.id.btn_regiter:
                regiterInfo();
                break;
        }
    }

    private void regiterInfo() {
        if (TextUtils.isEmpty(editName.getText().toString())) {
            Toast.makeText(this, "用户昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(editPhone.getText().toString())) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(editPass1.getText().toString())) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!editPass1.getText().toString().equals(editPass2.getText().toString())) {
            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        List<User>userList =userDao.loadAll();
        for(User user:userList){
            if(editName.getText().toString().equals(user.getUserName())){
                ToastUtils.showShort("用户名重复,请修改用户名");
                return;
            }
        }

        User user = new User(editName.getText().toString(), editPass1.getText().toString(), editPhone.getText().toString(), sex,permission);
        userPreference.edit().putString("useInfo", GsonUtils.GsonString(user)).apply();
        userDao.insert(user);
        if (null == dialog)
            dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setMessage("正在注册，请稍后...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0x123);
            }
        }).start();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (dialog.isShowing())
                dialog.dismiss();
            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }
    });
}
