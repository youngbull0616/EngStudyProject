package com.zhangxin.back.Controller;
import com.zhangxin.back.Dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Hello")
public class EmailController {

    @Autowired
    private UserDAO userDAO;


    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/resetPass/{user}", method = RequestMethod.GET)
    public Map<String, String> sendEmail(@PathVariable String user) {
        Map<String, String> result = new HashMap<String, String>();
        try {
            userDAO.resetPassword(user,"123456");
            result.put("info", "success");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("info", "error");
            return result;
        }
    }
}
