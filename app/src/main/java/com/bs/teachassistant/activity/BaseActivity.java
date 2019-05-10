package com.bs.teachassistant.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bs.teachassistant.R;
import com.bs.teachassistant.common.AppConfig;
import com.bs.teachassistant.database.CourseDao;
import com.bs.teachassistant.database.LessDao;
import com.bs.teachassistant.database.NoteDao;
import com.bs.teachassistant.database.ScoreDao;
import com.bs.teachassistant.database.UserDao;

import org.xutils.x;

/**
 * Created by limh on 2017/4/23.
 * aicitivy基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    public AppConfig appConfig;
    public SharedPreferences userPreference;
    public LessDao lessDao;
    public NoteDao noteDao;
    public ScoreDao scoreDao;
    public CourseDao courseDao;
    public UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        appConfig = AppConfig.getAppConfig();
        lessDao=appConfig.getDaoSession().getLessDao();
        noteDao=appConfig.getDaoSession().getNoteDao();
        scoreDao=appConfig.getDaoSession().getScoreDao();
        courseDao = appConfig.getDaoSession().getCourseDao();
        userDao = appConfig.getDaoSession().getUserDao();
        userPreference=getSharedPreferences("user",MODE_PRIVATE);
        initViews();
        initDatas();
        back();
        transparentStatusBar();
    }

    public abstract void initViews();

    public abstract void initDatas();

    public void back() {
        ImageView imageView = (ImageView) findViewById(R.id.image_info_close);
        if(imageView == null) {

        }
        if(imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }
    /**
     * 透明状态栏
     */
    protected void transparentStatusBar() {
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
