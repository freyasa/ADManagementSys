package com.exmaple.Vo;

import com.exmaple.Entity.User;
import com.exmaple.Service.UserServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

/**
 * todo: 1. 首先建立购物车功能，通过PriceTable进行购买，并且每次自动跳转到结算（invoice）界面，期间用session存储list（或字典）对象，
 *       session时长为30分钟，点击结算之后进入本Servlet中，通过servlet读session（用户名+"Cart"）并向数据库存入购买数据，建立购
 *       买记录。并生成一个随机链接，点击流量+1。
 *       2. Todo功能需要补充
 *       3. 改进：广告总访问量改为每日广告访问量
 *       购物车在session里
 */

@WebServlet(urlPatterns = "/buyService")
public class BuyService extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = new User();

        Cookie[] cookies = req.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("username")) {
                user.setUsername(c.getValue());
                break;
            }
        }
        HashMap<String, Integer> userSet = (HashMap<String, Integer>) req.getSession().getAttribute(user.getUsername() + "items");
        if (userSet == null) {
            userSet = new HashMap<>();
            userSet.put(req.getParameter("item"), 1);
        }
        else {
            int flag = 0;
            for (String s : userSet.keySet()) {
                if (s.equals(req.getParameter("item"))) {
                    userSet.put(s, userSet.get(s) + 1);
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                userSet.put(req.getParameter("item"), 1);
            }
        }
        req.getSession().setAttribute(user.getUsername() + "items", userSet);

        HashMap<String, Integer> tmp = (HashMap<String, Integer>)req.getSession().getAttribute(user.getUsername() + "items");
        for (String s : tmp.keySet()) {
            resp.getWriter().append(s + ": " + tmp.get(s) + "\n");
        }
        resp.sendRedirect("invoice.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
