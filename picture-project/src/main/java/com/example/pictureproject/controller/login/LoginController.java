package com.example.pictureproject.controller.login;


import com.example.pictureproject.entity.TXtUser;
import com.example.pictureproject.service.TXtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {


    private ModelAndView view;

    @Autowired
    private TXtUserService tXtUserService;

    /**
     * 登录页面请求方法
     *
     * @return
     */
    @RequestMapping ("/toLogin")
    public ModelAndView toLogin() {
        view = new ModelAndView();
        view.setViewName("login");
        return view;
    }

    @RequestMapping ("/toWelcome")
    public ModelAndView toWelcome(HttpServletResponse response) {

        view = new ModelAndView();
        view.setViewName("welcome");
        return view;
    }

    @RequestMapping ("/toIndex2")
    public ModelAndView toIndex2() {

        view = new ModelAndView();
        view.setViewName("index2");
        return view;
    }


    @RequestMapping (value = "/doLogin", method = RequestMethod.POST)
    public ModelAndView doLogin(HttpServletRequest request, ModelMap map, HttpSession session) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        view = new ModelAndView();
        try {
            TXtUser tXtUser = tXtUserService.getUser(username, password);

            if (tXtUser == null) {
                map.put("msg", "登录失败！用户名或密码错误！");
                view.setViewName("login");
            } else {
                view.setViewName("index");
                session.setAttribute("user", tXtUser);
                map.put("user", tXtUser);
            }

        } catch (Exception e) {
            e.printStackTrace();
            view.setViewName("error");
            map.put("msg", "登录失败！" + e.getMessage());

        }

        return view;
    }
}
