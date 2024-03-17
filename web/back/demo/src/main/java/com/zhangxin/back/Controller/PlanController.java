package com.zhangxin.back.Controller;
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
public class PlanController {

    @Autowired
    private UserDAO userDAO;

    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/submit_plan/{numWords}", method = RequestMethod.GET)
    public Map<String, String> submitPlan(HttpServletRequest request, HttpServletResponse response, @PathVariable int numWords) { // 还需要user参数
        if(request.getHeader("Origin").contains("localhost")) {
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        }
        response.setHeader("Access-Control-Allow-Credentials", "true");
        CookieUtil cc = new CookieUtil(request, response);
        Map<String, String> resultMap = new HashMap<String, String>();
        String user = cc.getValue("username");
        try {
            userDAO.setPlan(user, numWords);
            resultMap.put("info", "success");
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("info", "error");
            return resultMap;
        }
    }
}
