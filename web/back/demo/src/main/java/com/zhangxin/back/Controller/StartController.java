package com.zhangxin.back.Controller;
import com.zhangxin.back.Dao.UserDAO;
import com.zhangxin.back.Dao.WordBookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Hello")
public class StartController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private WordBookDAO wordBookDAO;

    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/start_info/{user}", method = RequestMethod.GET)
    public Map<String, String> startInfo(@PathVariable String user) {
        System.out.println("startInfo, user: " + user);
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            String studying = userDAO.getStudying(user);
            if (studying.equals("none")) {
                resultMap.put("info", "no_book"); // 没有选择学习的单词书
                return resultMap;
            }
            int num = wordBookDAO.getNum(studying);
            int plan = userDAO.getPlan(user);
            int studied = userDAO.getStudied(user);
            resultMap.put("num", num + "");
            resultMap.put("plan", plan + "");
            resultMap.put("studying", studying);
            resultMap.put("studied", studied + "");
            resultMap.put("info", "success");
            return resultMap; // 有4个属性 都是字符串的形式
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("info", "error");
            return resultMap;
        }
    }
}
