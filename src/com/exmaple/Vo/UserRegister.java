package com.exmaple.Vo;

import com.exmaple.Entity.User;
import com.exmaple.Service.UserServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Todo: 增加用户错误操作的反馈，使用session或cookies传递错误信息并打印在registration.jsp页面上，如："两次输入的密码不一致！"等。
 */

@WebServlet(urlPatterns = "/UserRegister")
public class UserRegister extends HttpServlet {

    interface CreateObject {
        String Create(String name);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String education = req.getParameter("education");
        String password = req.getParameter("password");
        String rePassword = req.getParameter("rePassword");
        String gender = req.getParameter("gender");
        String redirect = "";

//        System.out.println(username);
//        System.out.println(education);
//        System.out.println(password);
//        System.out.println(rePassword);
//        System.out.println(gender);
//        System.out.println(password.equals(rePassword));
//        System.out.println(new UserServices().Register(new User(username, password, education, gender)));

        try {
            if (password.equals(rePassword) && new UserServices().Register(new User(username, password, education, gender))) {
                redirect = "login.jsp";
            } else {
                redirect = "registration.jsp";
            }

        } catch (Exception e) {
            redirect = "registration.jsp";
        }
        resp.sendRedirect(redirect);
    }
}
