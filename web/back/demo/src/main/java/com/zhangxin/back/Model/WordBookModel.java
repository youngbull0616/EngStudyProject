package com.zhangxin.back.Model;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WordBookModel {
    private String title;
    private int num;

    public String getTitle() {
        return title;
    }

    public int getNum() {
        return num;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
