package com.myself.teacher.ui.base;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.myself.teacher.R;
import com.myself.teacher.base.IConstant;
import com.myself.teacher.base.MyApplication;
import com.myself.teacher.db.DaoSession;
import com.myself.teacher.db.User;
import com.myself.teacher.db.UserDao;
import com.myself.teacher.utils.SharedPreferencesUtils;
import com.myself.teacher.utils.ToastUtils;

import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "SignupActivity";
    private boolean isAdmin = false;//是否为管理员注册
    private TextView mTvSignupTitle,mTvSignupSwitch;
    private CircleImageView mIvSignupHeader;
    private EditText mEtSetUsername,mEtSetPass,mEtResetPass,mEtSetPhone,mEtSetAddress;
    private RadioButton mRbtnMale,mRbtnFamale;
    private Button mBtnSignup;
    private PopupWindow pop;
    private String photoPath = "";
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.iv_signup_back).setOnClickListener(this);
        mTvSignupTitle = findViewById(R.id.tv_signup_title);
        mTvSignupSwitch = findViewById(R.id.tv_signup_switch);
        mIvSignupHeader = findViewById(R.id.img_signup_header);

        mEtSetUsername = findViewById(R.id.et_set_username);
        mEtSetPass = findViewById(R.id.et_set_pass);
        mEtResetPass = findViewById(R.id.et_reset_pass);
        mRbtnMale = findViewById(R.id.rb_male);
        mRbtnFamale = findViewById(R.id.rb_famale);
        mEtSetPhone = findViewById(R.id.et_set_phone);
        mEtSetAddress = findViewById(R.id.et_set_address);
        mBtnSignup = findViewById(R.id.btn_signup);

        mTvSignupSwitch.setOnClickListener(this);
        mBtnSignup.setOnClickListener(this);
        mIvSignupHeader.setOnClickListener(this);
    }
    private void initData() {
        type =SharedPreferencesUtils.getInt(IConstant.THEACHER_TYPE,-1);
        if(type==1){
            mTvSignupTitle.setText("新教师注册");
        }
        if(type==2){
            mTvSignupTitle.setText("培训教师注册");
        }

        String path = SharedPreferencesUtils.getString(IConstant.PHOTO_PATH,"");
        if(!path.isEmpty()){
            Glide.with(this).load(path).into(mIvSignupHeader);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_signup_back:
                finish();
                break;
            case R.id.btn_signup:
                DaoSession daoSession = MyApplication.getMyInstance().getDaoSession();
                UserDao userDao = daoSession.getUserDao();
                if(checkAccountInput(userDao)){
                    makeSureSigupUser(userDao);
                }
                break;
            case R.id.img_signup_header:
                showPop();
                break;
        }
    }
    /**
     * 确认注册
     */
    private void makeSureSigupUser(UserDao userDao) {
        String id = UUID.randomUUID().toString();
        String nameString = mEtSetUsername.getText().toString().trim();
        String passString = mEtSetPass.getText().toString().trim();
        String gender = "男";
        if(mRbtnFamale.isChecked()){
            gender = "男";
        }
        if(mRbtnFamale.isChecked()){
            gender = "女";
        }
        String phoneString = mEtSetPhone.getText().toString().trim();
        String addressString = mEtSetAddress.getText().toString().trim();
        User user = new User(id,nameString,passString,gender,phoneString,addressString,type,photoPath);
        userDao.insert(user);
        ToastUtils.showShort("注册成功");
        finish();
    }
    /**
     * 核对输入信息
     */
    private boolean checkAccountInput( UserDao userDao) {
        String namestring = mEtSetUsername.getText().toString().trim();
        String passstring = mEtSetPass.getText().toString().trim();
        String repassstring = mEtResetPass.getText().toString().trim();
        String phoneString = mEtSetPhone.getText().toString().trim();
        String addressString = mEtSetAddress.getText().toString().trim();

        if (TextUtils.isEmpty(namestring)) {
            mEtSetUsername.requestFocus();
            mEtSetUsername.setError("用户名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(passstring)) {
            mEtSetPass.requestFocus();
            mEtSetPass.setError("密码不能为空");
            return false;
        }
        String userPasswordMatchReg = "^(\\w){3,16}$";
        if (!passstring.matches(userPasswordMatchReg)) {
            mEtSetPass.requestFocus();
            mEtSetPass.setError("必须为3到16个字符");
            return false;
        }
        if (!(passstring.equals(repassstring))) {
            mEtResetPass.requestFocus();
            mEtResetPass.setError("两次密码不一致");
            return false;
        }
        if (TextUtils.isEmpty(phoneString)) {
            mEtSetPhone.requestFocus();
            mEtSetPhone.setError("请输入电话号码");
            return false;
        }
      /*String userPasswordMatchReg1 = "^(\\w){11}$";
        if (!phoneString.matches(userPasswordMatchReg1)) {
            mEtSetPhone.requestFocus();
            mEtSetPhone.setError("必须为11个字符");
            return false;
        }*/
        if (TextUtils.isEmpty(addressString)) {
            mEtSetAddress.requestFocus();
            mEtSetAddress.setError("请输入地址！");
            return false;
        }
        List<User> users = userDao.loadAll();
        for (int i = 0;i<users.size();i++){
            if(namestring.equals(users.get(i).getUsername())){
                mEtSetUsername.requestFocus();
                mEtSetUsername.setError("用户名重复");
                return false;
            }
        }

        return true;
    }
    private void showPop() {
        View bottomView = View.inflate(SignupActivity.this, R.layout.dialog_choose_photos, null);
        TextView mAlbum = (TextView) bottomView.findViewById(R.id.tv_album);
        TextView mCamera = (TextView) bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = (TextView) bottomView.findViewById(R.id.tv_cancel);

        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        pop.setAnimationStyle(R.style.main_menu_photo_anim);
        pop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_album:
                        //相册
                        PictureSelector.create(SignupActivity.this)
                                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                                .minSelectNum(1)
                                .imageSpanCount(3)//每行显示3张图片
                                .enableCrop(true)//可裁剪
                                .circleDimmedLayer(true)//圆形裁剪
                                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                .showCropGrid(false)//// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                .selectionMode(PictureConfig.SINGLE)//设置单选或多选
                                .forResult(PictureConfig.CHOOSE_REQUEST);//返回码
                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(SignupActivity.this)
                                .openCamera(PictureMimeType.ofImage())
                                .enableCrop(true)
                                .circleDimmedLayer(true)//圆形裁剪
                                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                .showCropGrid(false)//// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_cancel:
                        //取消
                        //closePopupWindow();
                        break;
                }
                closePopupWindow();
            }
        };
        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);
    }

    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    images = PictureSelector.obtainMultipleResult(data);
                    LocalMedia media = images.get(0);
                    String path = "";
                    if (media.isCut() && !media.isCompressed()) {
                        // 裁剪过
                        path = media.getCutPath();
                    } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                        // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                        path = media.getCompressPath();
                    } else {
                        // 原图
                        path = media.getPath();
                    }
                    photoPath=path;
                    SharedPreferencesUtils.putString(IConstant.PHOTO_PATH,path);
                    Glide.with(this).load(path).into(mIvSignupHeader);
                    //Log.e(TAG,path);
                    break;
            }
        }
    }

}
