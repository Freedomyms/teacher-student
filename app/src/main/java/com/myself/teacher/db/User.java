package com.myself.teacher.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by admin on 2019/2/16.
 */
@Entity(
        nameInDb = "user",
        createInDb = false
)
public class User {
    @Id
    @Property(nameInDb = "id")
    private String id;

    @Property(nameInDb = "username")
    private String username;

    @Property(nameInDb = "password")
    private String password;
    @Property(nameInDb = "gender")
    private String gender;

    @Property(nameInDb = "phone")
    private String phone;

    @Property(nameInDb = "address")
    private String address;
    /**
     * 1新教师，2培训教师，3管理员
     */
    @Property(nameInDb = "permission")
    private int permission;
    @Property(nameInDb = "photo")
    private String photo;
@Generated(hash = 962098464)
public User(String id, String username, String password, String gender,
        String phone, String address, int permission, String photo) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.gender = gender;
    this.phone = phone;
    this.address = address;
    this.permission = permission;
    this.photo = photo;
}
@Generated(hash = 586692638)
public User() {
}
public String getId() {
    return this.id;
}
public void setId(String id) {
    this.id = id;
}
public String getUsername() {
    return this.username;
}
public void setUsername(String username) {
    this.username = username;
}
public String getPassword() {
    return this.password;
}
public void setPassword(String password) {
    this.password = password;
}
public String getGender() {
    return this.gender;
}
public void setGender(String gender) {
    this.gender = gender;
}
public String getPhone() {
    return this.phone;
}
public void setPhone(String phone) {
    this.phone = phone;
}
public String getAddress() {
    return this.address;
}
public void setAddress(String address) {
    this.address = address;
}
public int getPermission() {
    return this.permission;
}
public void setPermission(int permission) {
    this.permission = permission;
}
public String getPhoto() {
    return this.photo;
}
public void setPhoto(String photo) {
    this.photo = photo;
}

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", permission=" + permission +
                ", photo='" + photo + '\'' +
                '}';
    }
}

