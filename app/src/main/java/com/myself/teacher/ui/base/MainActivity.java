package com.myself.teacher.ui.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.myself.teacher.R;
import com.myself.teacher.base.IConstant;
import com.myself.teacher.utils.SharedPreferencesUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {
        findViewById(R.id.btn_new_teacher).setOnClickListener(this);
        findViewById(R.id.btn_old_teacher).setOnClickListener(this);
        findViewById(R.id.btn_admin).setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_new_teacher:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                SharedPreferencesUtils.putInt(IConstant.THEACHER_TYPE,IConstant.NEW_THEACHER);
                startActivity(intent);
                break;
            case R.id.btn_old_teacher:
                Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                SharedPreferencesUtils.putInt(IConstant.THEACHER_TYPE,IConstant.OLD_THEACHER);
                startActivity(intent2);
                break;
            case R.id.btn_admin:
                Intent intent3 = new Intent(MainActivity.this, LoginActivity.class);
                SharedPreferencesUtils.putInt(IConstant.THEACHER_TYPE,IConstant.ADMIN);
                startActivity(intent3);
                break;
        }
    }

}
