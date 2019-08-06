package com.example.pictureproject.controller.zdgl;


import com.example.pictureproject.entity.TXtUser;
import com.example.pictureproject.entity.YwZdgl;
import com.example.pictureproject.service.YwZdglService;
import com.sun.net.httpserver.HttpsConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class YwZdglController {

    @Value ("${previewPort}")
    private String previewPort;

    private ModelAndView view;
    private ModelMap map;
    private List<YwZdgl> dataList;
    @Autowired
    private YwZdglService ywZdglService;

    @RequestMapping (value = "toZdglIndex")
    public ModelAndView toZdglIndex(ModelMap map, HttpServletRequest request) {
        String ServerUrl = request.getLocalAddr();

        view = new ModelAndView();
        String searchContent = request.getParameter("searchContent");
        try {
            dataList = new ArrayList<>();
            dataList = ywZdglService.getZdglData(searchContent);
            map.put("dataList", dataList);
            map.put("searchContent", searchContent);

            view.setViewName("zdgl/zdgl_index");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());
        }

        return view;
    }

    @RequestMapping ("toZdglAdd")
    public ModelAndView toZdglAdd(HttpServletRequest request, ModelMap map) {
        String ServerUrl = request.getLocalAddr();

        Date date = new Date();
        Long time = date.getTime();
        view = new ModelAndView();
        map.put("serverUrl",
                "http://" + ServerUrl + ":" + previewPort + "/" + "toIndex?serverId=" + time + "_" + String.valueOf((int) (Math.random() * 100 * 100 * 100) + 1));
        view.setViewName("zdgl/zdgl_add");
        return view;
    }


    @RequestMapping (value = "doSaveZdgl", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doSaveZdgl(HttpServletRequest request, HttpSession session) {
        map = new ModelMap();
        String sname = request.getParameter("sname");
        String sdescribe = request.getParameter("sdescribe");
        String surl = request.getParameter("surl");
        TXtUser tXtUser = (TXtUser) session.getAttribute("user");
        String username = tXtUser.getUsername();
        try {
            ywZdglService.insertZdgl(sname, sdescribe, surl, username);
            map.put("msg", "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "保存失败！" + e.getMessage());

        }

        return map;
    }


    @RequestMapping (value = "doDelZdgl", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doDelZdgl(HttpServletRequest request) {
        String ids = request.getParameter("ids");
        System.out.println(ids);
        map = new ModelMap();
        try {
            String idArr[] = ids.split(",");
            List<String> idList = Arrays.asList(idArr);
            ywZdglService.doDelZdgl(idList);
            map.put("msg", "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "删除失败！" + e.getMessage());
        }
        return map;
    }


    @RequestMapping (value = "toZdglEdit", method = RequestMethod.GET)
    public ModelAndView toZdglEdit(HttpServletRequest request, ModelMap map) {

        String id = request.getParameter("id");
        try {
            dataList = new ArrayList<>();
            dataList = ywZdglService.getZdglDataById(id);
            map.put("dataList", dataList);
            view = new ModelAndView();
            view.setViewName("zdgl/zdgl_edit");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @RequestMapping (value = "doUpdateZdgl", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doUpdateZdgl(HttpServletRequest request) {
        map = new ModelMap();
        String sname = request.getParameter("sname");
        String sdescribe = request.getParameter("sdescribe");
        String surl = request.getParameter("surl");
        String id = request.getParameter("id");
        try {
            ywZdglService.UpdateZdgl(id, sname, sdescribe, surl);
            map.put("msg", "保存成功！");

        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "保存失败！" + e.getMessage());
        }

        return map;
    }


}
