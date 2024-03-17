package com.zhangxin.back.Controller;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangxin.back.Dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Hello")
public class SignupController {

    @Autowired
    private UserDAO userDAO;

    @ResponseBody // 此批注是ajax获取返回值使用
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/submit_signup", method = RequestMethod.GET)
    public Map<String, String> SignupUser(@RequestParam String obj) {
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            System.out.println("SignupUser, received: " + obj);
            JSONObject json = JSON.parseObject(obj);
            String username, password, email, phone, education;
            username = json.getString("username");
            password = json.getString("password");
            email = json.getString("email");
            phone = json.getString("phone");
            education = json.getString("education");


            if (userDAO.isUsernameExist(username)) { // 用户名已经存在
                resultMap.put("info", "user_existed");
                return resultMap;
            }
            if (userDAO.isEmailExist(email)) { // 邮箱已存在
                resultMap.put("info", "email_existed");
                return resultMap;
            }
            Calendar now = Calendar.getInstance();
            String date = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
            System.out.println(date);
            int plan = 20;
            userDAO.create(username, password, email, phone, education, plan, date);
            resultMap.put("info", "success");
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("info", "error");
            return resultMap;
        }
    }
}
