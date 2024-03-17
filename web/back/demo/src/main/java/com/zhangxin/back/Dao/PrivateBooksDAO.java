package com.zhangxin.back.Dao;

import com.zhangxin.back.Model.PrivateBooksModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface PrivateBooksDAO {
    boolean create(String username, String origin, int id, String word);
    void delete(String username, String word);
    List<PrivateBooksModel> getAllWords(String username);
    void deleteAllOfUser(String username);
    PrivateBooksModel getEntry(String username, String word);
    int count(String username);
}
