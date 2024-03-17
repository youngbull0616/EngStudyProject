package com.zhangxin.back.Dao;
import com.zhangxin.back.Model.UserModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import javax.sql.DataSource;

@Mapper
public interface UserDAO {
    // init database connection
    public void setDataSource(DataSource ds);
    // create a record
    public void create(String username, String password, String email, String phone, String education, int plan, String lastDate);
    // list one record
    public UserModel getUser(String username);
    public boolean isUsernameExist(String username);
    public boolean isEmailExist(String email);
    // list down all records
    public List<UserModel> listUsers();
    public void delete(String username);
    public void resetPassword(String username, String newPassword);
    // 获取当前正在学习的单词书
    public String getStudying(String username);
    public String getEmail(String username);
    public void setStudying(String username, String newTitle);
    // 用新计划替代旧的 但进度不变
    public void setPlan(String user, int num);
    public int getPlan(String user);
    public int getStudied(String user);
    public void updateStudied(String user, int num);
    public void updateDate(String user, String date);
    public String getLastDate(String user);
    public void clearDays(String user);
    public void leftShift(String user, int amount, int addToDay7);
    public void updateDay7(String user, int amount);
}
