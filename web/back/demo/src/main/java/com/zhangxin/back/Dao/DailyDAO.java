package com.zhangxin.back.Dao;
import com.zhangxin.back.Model.DailyModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
@Mapper
public interface DailyDAO {

    void create(@Param("username") String username, @Param("status") String status,
                @Param("word") String word, @Param("id") int id);

    List<DailyModel> getAll(@Param("username") String username);

    List<DailyModel> getYes(@Param("username") String username);

    List<DailyModel> getNo(@Param("username") String username);

    void batchCreate(@Param("batch") List<DailyModel> batch);

    void batchDelete(@Param("username") String username);
}
