package com.exmaple.Vo;

import com.exmaple.Service.UserServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Todo 登录界面下方有一个remember me的复选框，用cookies保存用户登录信息。
 * @apiNote Only Cookie.Username is not null or not "" can only reflect user has been login.
 *          Warning Only this servlet can create Cookies, Other methods are forbidden.
 */

@WebServlet(urlPatterns = "/UserLogin")
public class UserLogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String redirect = "";
        if (username == null) username = "";
        if (password == null) password = "";
//        System.out.println(username);
//        System.out.println(password);
        if (new UserServices().validateUser(username, password)) {
            resp.addCookie(new Cookie("username", username));
            resp.addCookie(new Cookie("gender", new UserServices().getUserDetails(username).getGender()));
            resp.addCookie(new Cookie("education", new UserServices().getUserDetails(username).getEducation()));
            redirect = "controlling.jsp";
        }
        else {
            redirect = "login.jsp";
        }
        resp.sendRedirect(redirect);
    }
}
