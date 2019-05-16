package com.myself.teacher.ui.newteacher.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.myself.teacher.R;
import com.myself.teacher.adapter.FragmentAdapter;
import com.myself.teacher.ui.base.BaseActivity;
import com.myself.teacher.ui.newteacher.fragment.CourseFragment;
import com.myself.teacher.ui.newteacher.fragment.ManageFragment;
import com.myself.teacher.ui.newteacher.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class NewTeacherActivity extends BaseActivity  implements View.OnClickListener {
    private static final String TAG = "NewTeacherActivity";
    private List<Fragment> mListFragment;
    private FragmentAdapter mFragmentAdapter;
    public int sChooseTrackPointMode = 1;

    private ViewPager mVpFragment;
    private TextView mTvManage,mTvChoose,mTvMy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_teacher);
        initView();
        initData();
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mListFragment);
        mVpFragment.setAdapter(mFragmentAdapter);
        mVpFragment.setCurrentItem(0);  //初始化显示第一个页面
    }
    private void initView() {
        mVpFragment = findViewById(R.id.vp_fragment);
        mTvManage = findViewById(R.id.tv_manage);
        mTvChoose = findViewById(R.id.tv_choose);
        mTvMy = findViewById(R.id.tv_my);

        mTvManage.setOnClickListener(this);
        mTvChoose.setOnClickListener(this);
        mTvMy.setOnClickListener(this);
        mVpFragment.setOnPageChangeListener(new MyPagerChangeListener());

    }

    private void initData() {
        mListFragment = new ArrayList<>();
        mListFragment.add(new ManageFragment());
        mListFragment.add(new CourseFragment());
        mListFragment.add(new MyFragment());
    }
    /**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     */
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    selectedManage();
                    break;
                case 1:
                    selectedChooseCourse();
                    break;
                case 2:
                    selectedMy();
                    break;

            }
        }
    }
    private void selectedManage() {
        mTvManage.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
        mTvManage.setTextColor(this.getResources().getColor(R.color.white));

        mTvChoose.setBackgroundColor(this.getResources().getColor(R.color.white));
        mTvChoose.setTextColor(this.getResources().getColor(R.color.black));
        mTvMy.setBackgroundColor(this.getResources().getColor(R.color.white));
        mTvMy.setTextColor(this.getResources().getColor(R.color.black));

        sChooseTrackPointMode = 1;
    }

    private void selectedChooseCourse() {
        mTvManage.setBackgroundColor(this.getResources().getColor(R.color.white));
        mTvManage.setTextColor(this.getResources().getColor(R.color.black));

        mTvChoose.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
        mTvChoose.setTextColor(this.getResources().getColor(R.color.white));

        mTvMy.setBackgroundColor(this.getResources().getColor(R.color.white));
        mTvMy.setTextColor(this.getResources().getColor(R.color.black));
        sChooseTrackPointMode = 2;
    }

    private void selectedMy() {
        mTvManage.setBackgroundColor(this.getResources().getColor(R.color.white));
        mTvManage.setTextColor(this.getResources().getColor(R.color.black));
        mTvChoose.setBackgroundColor(this.getResources().getColor(R.color.white));
        mTvChoose.setTextColor(this.getResources().getColor(R.color.black));

        mTvMy.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
        mTvMy.setTextColor(this.getResources().getColor(R.color.white));
        sChooseTrackPointMode = 4;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_manage:
                mVpFragment.setCurrentItem(0);
                selectedManage();
                break;
            case R.id.tv_choose:
                mVpFragment.setCurrentItem(1);
                selectedChooseCourse();
                break;
            case R.id.tv_my:
                mVpFragment.setCurrentItem(3);
                selectedMy();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
