package com.zhangxin.back.Dao;
import com.zhangxin.back.Model.WordBookModel;
import org.apache.ibatis.annotations.Mapper;

import javax.sql.DataSource;
import java.util.List;

@Mapper
public interface WordBookDAO {
    public void setDataSource(DataSource ds);
    public WordBookModel getWordBook(String title);
    public List<WordBookModel> listWordBooks();
    public int getNum(String title);
}
