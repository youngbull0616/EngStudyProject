package com.zhangxin.back.Controller;
import com.zhangxin.back.Dao.WordEntryDAO;
import com.zhangxin.back.Model.DailyModel;
import com.zhangxin.back.Dao.DailyDAO;
import com.zhangxin.back.Dao.UserDAO;
import com.zhangxin.back.Model.WordEntryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/Hello")
public class ReviewTableController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DailyDAO dailyDAO;

    @Autowired
    private WordEntryDAO wordEntryDAO;

    public class TableEntry {
        private String word;
        private String poses;
        private String status;

        public void setWord(String word) { this.word = word; }

        public String getWord() { return word; }

        private void setPoses(String poses) { this.poses = poses; }

        public String getPoses() { return poses; }

        private void setStatus(String status) { this.status = status; }

        public String getStatus() { return status; }
    }

    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/get_daily/{user}", method = RequestMethod.GET)
    public List<TableEntry> getDaily(@PathVariable String user) {
        System.out.println("getDaily, user: " + user);
        try {
            String studying = userDAO.getStudying(user);
            if (studying.equals("none")) { // 未选择单词书
                return null;
            }
            List<DailyModel> list = dailyDAO.getAll(user); // 取出daily中所有属于某用户的单词项
            List<TableEntry> result = new ArrayList<TableEntry>();
            for (DailyModel d : list) {
                String poses = wordEntryDAO.getPoses(d.getId(), studying); // 获取所有释义
                if (poses.length() > 40) // ===== 释义的长度超过40时 截断
                    poses = poses.substring(0, 39) + "...";
                TableEntry e = new TableEntry();
                e.setWord(d.getWord());
                e.setPoses(poses);
                e.setStatus(d.getStatus());
                result.add(e);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
