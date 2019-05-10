package com.bs.teachassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by limh on 2017/4/25.
 * 用户信息
 */
@Entity
public class User implements Parcelable {
    @Id
    private String userName;
    private String password;
    private String phone;
    private String sex;
    private int permission;//0为教师信息，1为学生信息




    protected User(Parcel in) {
        userName = in.readString();
        password = in.readString();
        phone = in.readString();
        sex = in.readString();
        permission = in.readInt();
    }

    @Generated(hash = 334582046)
    public User(String userName, String password, String phone, String sex,
            int permission) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.sex = sex;
        this.permission = permission;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeString(password);
        parcel.writeString(phone);
        parcel.writeString(sex);
        parcel.writeInt(permission);
    }


    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", permission=" + permission +
                '}';
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getPermission() {
        return this.permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }
}
