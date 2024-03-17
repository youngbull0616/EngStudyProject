package com.zhangxin.back.Controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangxin.back.Dao.WordEntryDAO;
import com.zhangxin.back.Model.WordEntryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/Hello")
public class WordInfoController {

    @Autowired
    private WordEntryDAO wordEntryDAO;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/get_wordInfo", method = RequestMethod.GET)
    public @ResponseBody WordEntryModel getWordInfo(@RequestParam String obj) {
        System.out.println("getWordInfo, received: " + obj);
        JSONObject json = JSON.parseObject(obj);
        String title = json.getString("title");
        int id = json.getIntValue("id");
        try {
            return wordEntryDAO.getWordEntry(id, title);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
