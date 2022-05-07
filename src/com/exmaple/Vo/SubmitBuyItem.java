package com.exmaple.Vo;

import com.exmaple.Entity.User;
import com.exmaple.Entity.UserResources;
import com.exmaple.Service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(urlPatterns = "/submitUserItem")
public class SubmitBuyItem extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        String redirect = "";
        Cookie[] cookies = req.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("username")) {
                user.setUsername(c.getValue());
                break;
            }
        }
        HttpSession session = req.getSession();
        HashMap<String, Integer> hashMap = (HashMap<String, Integer>) session.getAttribute(user.getUsername() + "items");

        for (String s : hashMap.keySet()) {
            resp.getWriter().append(s + ": " + hashMap.get(s) + "\n");
        }
        req.getSession().setAttribute(user.getUsername() + "items", null);
        if (new ItemService().userSubmitBuyRequest(user.getUsername(), hashMap)) {
            redirect = "controlling.jsp";
        }
        else {
            redirect = "pricing_table.jsp";
        }
        resp.sendRedirect(redirect);
    }
}
