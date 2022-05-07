package com.exmaple.Vo;

import com.exmaple.Entity.User;
import com.exmaple.Entity.UserResources;
import com.exmaple.Service.ItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * attention: Only this method can use doGet request.
 */

@WebServlet(urlPatterns = "/adDetails")
public class adDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");

        User user = new ItemService().getAdOwner(req.getParameter("adMd5"));
        UserResources userResources = new ItemService().getAdDetails(req.getParameter("adMd5"));
        new ItemService().upDateVisitor(req.getParameter("adMd5"));

        Date dueTime = userResources.getDueTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String nowStr=sdf.format(dueTime);

        resp.getWriter().append("<h1>广告详情界面</h1>");
        resp.getWriter().append("<b>广告所有者：</b>" + user.getUsername() + "\n");
        resp.getWriter().append("<b>广告到期时间：</b>" + nowStr + "\n");
        resp.getWriter().append("<b>浏览量：</b>" + userResources.getUniqueVisitors() + "\n");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
