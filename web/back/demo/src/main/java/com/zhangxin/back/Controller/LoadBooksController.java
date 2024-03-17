package com.zhangxin.back.Controller;
import com.zhangxin.back.Dao.PrivateBooksDAO;
import com.zhangxin.back.Dao.UserDAO;
import com.zhangxin.back.Dao.WordBookDAO;
import com.zhangxin.back.Utils.CookieUtil;
import com.zhangxin.back.Model.WordBookModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/Hello")
public class LoadBooksController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private WordBookDAO wordBookDAO;

    @Autowired
    private PrivateBooksDAO privateBooksDAO;

    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/get_books/{user}", method = RequestMethod.GET)
    public List<WordBookModel> getWordBooks(HttpServletRequest request, HttpServletResponse response, @PathVariable String user) {
        if(request.getHeader("Origin").contains("localhost")) {
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        }
        response.setHeader("Access-Control-Allow-Credentials", "true");
        CookieUtil cc = new CookieUtil(request, response);
        System.out.println("getWordBooks, user: " + user);
        try {
            String studying = userDAO.getStudying(user); // could be none
            // 添加正在学习的单词书的cookie
            cc.addCookie("studying", studying, "/", "localhost");
            System.out.print("getWordBooks, ");
            cc.showCookies();
            List<WordBookModel> list = wordBookDAO.listWordBooks();
            WordBookModel w = new WordBookModel();
            w.setTitle("Private");
            w.setNum(privateBooksDAO.count(user));
            list.add(w); // 把自定义单词书加进去
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/get_book/{user}", method = RequestMethod.GET)
    public WordBookModel getBook(@PathVariable String user) { // 进入制定计划页面时调用
        System.out.println("getBook, user: " + user);
        try {
            String title = userDAO.getStudying(user);
            if (title.equals("none")) // 没有正在学习的单词书
                return null;
            return wordBookDAO.getWordBook(title);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
