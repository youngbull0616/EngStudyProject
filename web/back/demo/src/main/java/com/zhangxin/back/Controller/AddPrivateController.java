package com.zhangxin.back.Controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangxin.back.Dao.PrivateBooksDAO;
import com.zhangxin.back.Model.PrivateBooksModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Hello")
public class AddPrivateController {
    @Autowired
    private PrivateBooksDAO privateBooksDAO;

    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/add_private", method = RequestMethod.GET)
    public Map<String, String> addPrivate(@RequestParam String obj) {
        Map<String, String> resultMap = new HashMap<String, String>();
        System.out.println("addPrivate, received: " + obj);
        try {
            JSONObject json = JSON.parseObject(obj);
            String studying, word, username;
            studying = json.getString("studying");
            word = json.getString("word");
            username = json.getString("username");
            int id = json.getIntValue("id");
            if (privateBooksDAO.getEntry(username, word) == null) {
                // === 没有重复的单词
                privateBooksDAO.create(username, studying, id, word);
                resultMap.put("info", "success");
                return resultMap;
            } else {
                resultMap.put("info", "duplicate");
                return resultMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("info", "error");
            return resultMap;
        }
    }
}
