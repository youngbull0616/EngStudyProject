package com.zhangxin.back.Controller;
import com.zhangxin.back.Dao.UserDAO;
import com.zhangxin.back.Model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Hello")
public class UserInfoController {

    @Autowired
    private UserDAO userDAO;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/get_userInfo/{user}", method = RequestMethod.GET)
    public @ResponseBody UserModel getUserInfo(@PathVariable String user) {
        System.out.println("getUserInfo, user: " + user);
        UserModel u = new UserModel();
        u.setUsername(user);
        try {
            UserModel res = userDAO.getUser(user);
            u.setPhone(res.getPhone());
            u.setEmail(res.getEmail());
            u.setEducation(res.getEducation());
            System.out.println("getUserInfo: done.");
            return u; // 把这个用户相关的信息都发回去
        } catch (Exception e) {
            e.printStackTrace();
            u.setUsername("null");
            return u;
        }
    }
}
