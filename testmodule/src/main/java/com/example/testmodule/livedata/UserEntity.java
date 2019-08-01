package com.example.testmodule.livedata;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.common.BaseBean;


//entity声明定义，并且指定了映射数据表明
@Entity(tableName = "UserEntity")
public class UserEntity extends BaseBean {
    //设置主键，并且定义自增增
    @PrimaryKey(autoGenerate = true)
    //字段映射具体的数据表字段名
    @ColumnInfo(name = "uid")
    private int uid;
    //字段映射具体的数据表字段名
    @ColumnInfo(name = "first_name")
    private String firstName;
    //字段映射具体的数据表字段名
    @ColumnInfo(name = "last_name")
    private String lastName;

    //字段映射具体的数据表字段名
    @ColumnInfo(name = "nick_name")
    private String nickname;


    //必须指定一个构造方法，room框架需要。并且只能指定一个
//，如果有其他构造方法，则其他的构造方法必须添加@Ignore注解
    public UserEntity() {
    }

    //其他构造方法要添加@Ignore注解
    @Ignore
    public UserEntity(int uid) {
        this.uid = uid;
    }

    //Setter、Getter方法是需要添加的，为了支持room工作
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "uid=" + uid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", flag=" + flag +
                ", msg='" + msg + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", httpStateCode=" + httpStateCode +
                '}';
    }
}