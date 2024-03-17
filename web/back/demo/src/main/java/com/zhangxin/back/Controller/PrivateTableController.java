package com.zhangxin.back.Controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangxin.back.Dao.PrivateBooksDAO;
import com.zhangxin.back.Dao.WordEntryDAO;
import com.zhangxin.back.Model.PrivateBooksModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping("/Hello")
public class PrivateTableController {

    @Autowired
    private PrivateBooksDAO privateBooksDAO;

    @Autowired
    private WordEntryDAO wordEntryDAO;

    public class MyEntry {
        private String word;
        private String poses;

        public void setWord(String word) { this.word = word; }

        public String getWord() { return word; }

        private void setPoses(String poses) { this.poses = poses; }

        public String getPoses() { return poses; }
    }

    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/get_private/{user}", method = RequestMethod.GET)
    public List<MyEntry> getPrivate(@PathVariable String user) {
        System.out.println("getPrivate, user: " + user);
        try {
            List<PrivateBooksModel> list = privateBooksDAO.getAllWords(user); // 取出所有自定义单词
            List<MyEntry> result = new ArrayList<MyEntry>();
            for (PrivateBooksModel w: list) {
                String poses = wordEntryDAO.getPoses(w.getId(), w.getOrigin());
                if (poses.length() > 50) // 释义的长度超过50时 截断
                    poses = poses.substring(0, 49) + "...";
                MyEntry e = new MyEntry();
                e.setWord(w.getWord());
                e.setPoses(poses);
                result.add(e);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/delete_private", method = RequestMethod.GET)
    public Map<String, String> deletePrivate(@RequestParam String obj) {
        Map<String, String> result = new HashMap<String, String>();
        System.out.println("deletePrivate, obj: " + obj);
        try {
            JSONObject json = JSON.parseObject(obj);
            String word = json.getString("word");
            String user = json.getString("username");
            privateBooksDAO.delete(user, word);
            result.put("info", "success");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("info", "error");
            return result;
        }
    }
}
