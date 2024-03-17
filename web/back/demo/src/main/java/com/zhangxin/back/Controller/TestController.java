package com.zhangxin.back.Controller;
import com.zhangxin.back.Dao.UserDAO;
import com.zhangxin.back.Dao.WordEntryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequestMapping("/Hello")
public class TestController {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private WordEntryDAO wordEntryDAO;

    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/get_test/{user}", method = RequestMethod.GET)
    public List<String> getTestWords(@PathVariable String user) {
        System.out.println("getTestWords, user: " + user);
        Map<String, String> result = new HashMap<String, String>();
        try {
            String studying = userDAO.getStudying(user); // 正在学习的单词书
            int studied = userDAO.getStudied(user); // 当前单词书中已经学习的单词数
            if (studied < 30 || studying.equals("none")) { // 单词量过少 不能测试
                return null;
            }
            HashSet<Integer> set = new HashSet<Integer>();
            randomSet(0, studied - 1, 30, set);
            List<String> list = new ArrayList<String>();
            for (Integer i : set) { // 代价较大
                list.add(wordEntryDAO.getWord(i, studying));
                list.add(wordEntryDAO.getPhonetic(i, studying));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void randomSet(int min, int max, int n, HashSet<Integer> set) {
        if (n > (max - min + 1) || max < min)
            return;
        for (int i = 0; i < n; i++) {
            int num = (int) (Math.random() * (max - min)) + min;
            set.add(num); // 将不同的数存入HashSet
        }
        int setSize = set.size();
        System.out.println("set size: " + setSize + ", n: " + n);
        // 如果存入的数小于指定生成的个数 调用递归再生成剩余个数的随机数
        if (setSize < 30)
            randomSet(min, max, 30 - setSize, set);
    }
}
