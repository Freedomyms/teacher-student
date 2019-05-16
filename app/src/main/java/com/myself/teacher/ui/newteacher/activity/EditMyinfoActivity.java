package com.myself.teacher.ui.newteacher.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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
import com.myself.teacher.ui.base.BaseActivity;
import com.myself.teacher.utils.SharedPreferencesUtils;
import com.myself.teacher.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditMyinfoActivity extends BaseActivity implements View.OnClickListener {

    private CircleImageView mCivEditHeader;
    private EditText mEtEditUsername, mEtOldPass, mEtNewPass, mEtRenewPass, mEtEditPhone, mEtEditAddress;
    private RadioButton mRbtnMale, mRbtnFamale;
    private PopupWindow pop;
    private String photoPath = "";
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_myinfo);
        initView();
        initData();
    }

    private void initView() {
        mCivEditHeader = findViewById(R.id.civ_edit_header);
        mEtEditUsername = findViewById(R.id.et_edit_username);
        mEtOldPass = findViewById(R.id.et_old_pass);
        mEtNewPass = findViewById(R.id.et_new_pass);
        mEtRenewPass = findViewById(R.id.et_renew_pass);
        mRbtnMale = findViewById(R.id.rb_male);
        mRbtnFamale = findViewById(R.id.rb_famale);
        mEtEditPhone = findViewById(R.id.et_edit_phone);
        mEtEditAddress = findViewById(R.id.et_edit_address);

        findViewById(R.id.iv_edit_myinfo_back).setOnClickListener(this);
        findViewById(R.id.btn_edit_sure).setOnClickListener(this);
        mCivEditHeader.setOnClickListener(this);
//        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    private UserDao userDao;
    private List<User> userList = new ArrayList<>();

    private void initData() {
        String userName = SharedPreferencesUtils.getString(IConstant.LOGIN_NEW_TEACHER_USERNAME, "");
        DaoSession daoSession = MyApplication.getMyInstance().getDaoSession();
        userDao = daoSession.getUserDao();
        userList = userDao.loadAll();
        for (User user : userList) {
            if (user.getUsername().equals(userName) && user.getPermission() == 1) {
                mUser = user;
                mEtEditUsername.setText(user.getUsername());
                if (user.getGender().equals("男")) {
                    mRbtnMale.setChecked(true);
                }
                if (user.getGender().equals("女")) {
                    mRbtnFamale.setChecked(true);
                }
                mEtEditPhone.setText(user.getPhone());
                mEtEditAddress.setText(user.getAddress());
                if (user.getPhoto() != null) {
                    Glide.with(this).load(user.getPhoto()).into(mCivEditHeader);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_edit_myinfo_back:
                finish();
                break;
            case R.id.civ_edit_header:
                showPop();
                break;
            case R.id.btn_edit_sure:
                if (checkAccountInput()) {
                    makeSureEditUser();
                }
                break;
        }
    }

    /**
     * 确认修改
     */
    private void makeSureEditUser() {
        String nameString = mEtEditUsername.getText().toString().trim();
        String newPass = mEtNewPass.getText().toString().trim();
        String phoneString = mEtEditPhone.getText().toString().trim();
        String addressString = mEtEditAddress.getText().toString().trim();
        SharedPreferencesUtils.putString(IConstant.LOGIN_NEW_TEACHER_USERNAME, nameString);
        SharedPreferencesUtils.putString(IConstant.LOGIN_NEW_TEACHER_PASSWORD, newPass);
        mUser.setUsername(nameString);
        mUser.setPassword(newPass);
        mUser.setPhone(phoneString);
        mUser.setAddress(addressString);
        if (!photoPath.isEmpty()) {
            mUser.setPhoto(photoPath);
        }

        if (mRbtnFamale.isChecked()) {
            mUser.setGender("男");
        }
        if (mRbtnFamale.isChecked()) {
            mUser.setGender("女");
        }
        userDao.update(mUser);
        ToastUtils.showShort("修改成功");
        Intent intentTemp = new Intent();
        setResult(RESULT_OK, intentTemp);
        finish();
    }

    /**
     * 核对输入信息
     */
    private boolean checkAccountInput() {
        String namestring = mEtEditUsername.getText().toString().trim();
        String oldPass = mEtOldPass.getText().toString().trim();
        String passstring = mEtNewPass.getText().toString().trim();
        String repassstring = mEtRenewPass.getText().toString().trim();
        String phoneString = mEtEditPhone.getText().toString().trim();
        String addressString = mEtEditAddress.getText().toString().trim();

        if (TextUtils.isEmpty(oldPass)) {
            mEtOldPass.requestFocus();
            mEtOldPass.setError("旧密码不能为空");
            return false;
        }
        if (!oldPass.equals(mUser.getPassword())) {
            mEtOldPass.requestFocus();
            mEtOldPass.setError("旧密码不正确！");
            return false;
        }
        if (TextUtils.isEmpty(namestring)) {
            mEtEditUsername.requestFocus();
            mEtEditUsername.setError("用户名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(passstring)) {
            mEtNewPass.requestFocus();
            mEtNewPass.setError("新密码不能为空");
            return false;
        }
        String userPasswordMatchReg = "^(\\w){3,16}$";
        if (!passstring.matches(userPasswordMatchReg)) {
            mEtNewPass.requestFocus();
            mEtNewPass.setError("必须为3到16个字符");
            return false;
        }
        if (!(passstring.equals(repassstring))) {
            mEtRenewPass.requestFocus();
            mEtRenewPass.setError("两次密码不一致");
            return false;
        }
        if (TextUtils.isEmpty(phoneString)) {
            mEtEditPhone.requestFocus();
            mEtEditPhone.setError("请输入电话号码");
            return false;
        }
        /*String userPasswordMatchReg1 = "^(\\w){11}$";
        if (!phoneString.matches(userPasswordMatchReg1)) {
            mEtEditPhone.requestFocus();
            mEtEditPhone.setError("必须为11个字符");
            return false;
        }*/
        if (TextUtils.isEmpty(addressString)) {
            mEtEditAddress.requestFocus();
            mEtEditAddress.setError("请输入地址！");
            return false;
        }
        List<User> users = userDao.loadAll();
        for (int i = 0; i < users.size(); i++) {
            if (namestring.equals(users.get(i).getUsername())) {
                mEtEditUsername.requestFocus();
                mEtEditUsername.setError("用户名重复");
                return false;
            }
        }
        return true;
    }

    /**
     * 选择头像
     */
    private void showPop() {
        View bottomView = View.inflate(EditMyinfoActivity.this, R.layout.dialog_choose_photos, null);
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
                        PictureSelector.create(EditMyinfoActivity.this)
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
                        PictureSelector.create(EditMyinfoActivity.this)
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
                    photoPath = path;
                    //SharedPreferencesUtils.putString(IConstant.PHOTO_PATH,path);
                    Glide.with(this).load(path).into(mCivEditHeader);
                    //Log.e(TAG,path);
                    break;
            }
        }
    }


}
