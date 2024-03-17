package com.zhangxin.back.Controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangxin.back.Dao.UserDAO;
import com.zhangxin.back.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Hello")
public class ResetController {

    @Autowired
    private UserDAO userDAO;

    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/submit_reset", method = RequestMethod.GET)
    public Map<String, String> ResetUser(@RequestParam String obj) {
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            System.out.println("ResetUser, received: " + obj);
            JSONObject json = JSON.parseObject(obj);
            String username, old_password, new_password, email, phone, education;
            username = json.getString("username");
            old_password = json.getString("old_pwd");
            new_password = json.getString("new_pwd");
            email = json.getString("email");
            phone = json.getString("phone");
            education = json.getString("education");
            UserModel old_userModel = userDAO.getUser(username);
            if (!old_userModel.getPassword().equals(old_password)) { // 密码错误 不能更改
                resultMap.put("info", "wrong_pwd");
                return resultMap;
            }
            userDAO.delete(username); // 删除旧用户所有信息
            if (userDAO.isEmailExist(email)) { // 邮箱已经存在
                resultMap.put("info", "email_existed");
                return resultMap;
            }
            Calendar now = Calendar.getInstance();
            String date = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
            int plan = 20;
            userDAO.create(username, new_password, email, phone, education, plan, date);
            resultMap.put("info", "success");
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("info", "error");
            return resultMap;
        }
    }
}
