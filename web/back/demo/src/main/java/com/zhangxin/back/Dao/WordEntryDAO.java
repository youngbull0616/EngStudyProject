package com.zhangxin.back.Dao;
import com.zhangxin.back.Model.WordEntryModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WordEntryDAO {
    WordEntryModel getWordEntry(int id, String title);
    WordEntryModel getWordEntryById(int id, String title);
    List<WordEntryModel> getWordEntryList(int start, int end, String title);
    String getPoses(int id, String title);
    String getWord(int id, String title);
    String getPhonetic(int id, String title);
}
