package com.zhangxin.back.Controller;
import com.zhangxin.back.Dao.DailyDAO;
import com.zhangxin.back.Dao.UserDAO;
import com.zhangxin.back.Utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Hello")
public class ChangeController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DailyDAO dailyDAO;

    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/change_book/{newTitle}", method = RequestMethod.GET)
    public Map<String, String> changeStudying(HttpServletRequest request, HttpServletResponse response, @PathVariable String newTitle) {
        if(request.getHeader("Origin").contains("localhost")) {
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        }
        response.setHeader("Access-Control-Allow-Credentials", "true");
        CookieUtil cc = new CookieUtil(request, response);
        System.out.println("changeStudying, newTitle: " + newTitle);
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            String oldTitle = cc.getValue("studying");
            if (oldTitle.equals(newTitle)) {
                resultMap.put("info", "error");
                return resultMap;
            }
            userDAO.setStudying(cc.getValue("username"), newTitle); // studied清0
            dailyDAO.batchDelete(cc.getValue("username"));
            cc.addCookie("studying", newTitle, "/", "localhost"); // 更改cookie
            resultMap.put("info", "success");
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("info", "error");
            return resultMap;
        }
    }
}
