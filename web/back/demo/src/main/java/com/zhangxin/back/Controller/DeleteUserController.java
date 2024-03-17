package com.zhangxin.back.Controller;
import com.zhangxin.back.Dao.DailyDAO;
import com.zhangxin.back.Dao.PrivateBooksDAO;
import com.zhangxin.back.Dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Hello")
public class DeleteUserController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PrivateBooksDAO privateBooksDAO;

    @Autowired
    private DailyDAO dailyDAO;

    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/delete_user/{user}", method = RequestMethod.GET)
    public Map<String, String> DeleteUser(@PathVariable String user) {
        Map<String, String> resultMap = new HashMap<String, String>();
        System.out.println("DeleteUser, user: " + user);
        try {
            userDAO.delete(user);
            privateBooksDAO.deleteAllOfUser(user);
            dailyDAO.batchDelete(user);
            resultMap.put("info", "success");
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("info", "error");
            return resultMap;
        }
    }
}
