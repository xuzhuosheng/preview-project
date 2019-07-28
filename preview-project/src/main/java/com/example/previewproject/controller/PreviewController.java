package com.example.previewproject.controller;


import com.example.previewproject.entity.YwEjtp;
import com.example.previewproject.entity.YwYjtp;
import com.example.previewproject.entity.YwZdgl;
import com.example.previewproject.service.YwEjtpService;
import com.example.previewproject.service.YwYjtpService;
import com.example.previewproject.service.YwZdglService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PreviewController {
    /*配置文件*/
    @Value ("${file.upload.path}")
    private String uploadFilePath;

    @Value ("${file.upload.path.relative}")
    private String relativePath;

    @Value ("${yjtpPath}")
    private String yjtpPath;

    @Value ("${ejtpPath}")
    private String ejtpPath;

    @Autowired
    private YwZdglService ywZdglService;

    @Autowired
    private YwYjtpService ywYjtpService;

    @Autowired
    private YwEjtpService ywEjtpService;

    @RequestMapping ("/toIndex")
    public ModelAndView toIndex(HttpServletRequest request, ModelMap map) {

        String ipAddress = request.getRemoteAddr();
        ModelAndView view = new ModelAndView();
        try {
            YwZdgl zdgl = new YwZdgl();
            zdgl = ywZdglService.getZdidByUrl(ipAddress);

            if (zdgl != null) {
                List<YwYjtp> ywYjtpList = new ArrayList<>();
                ywYjtpList = ywYjtpService.getYjtpDataByZdid(zdgl.getId());
                map.put("ywYjtpList", ywYjtpList);
                map.put("headPath", relativePath.split("/")[1] + "/" + yjtpPath + "/");
            } else {
                view.setViewName("index_2");
                map.put("msg", "该终端还没进行配置！请先配置！");
            }

            view.setViewName("index");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());
        }

        return view;
    }


    @RequestMapping ("toEjtpIndex")
    public ModelAndView toEjtpIndex(HttpServletRequest request, ModelMap map) {
        String yjid = request.getParameter("yjid");
        String zdid = request.getParameter("zdid");

        ModelAndView view = new ModelAndView();
        try {
            List<YwEjtp> ywEjtpList = new ArrayList<>();
            ywEjtpList = ywEjtpService.getEjtpData(yjid, zdid);

            map.put("ywEjtpList", ywEjtpList);
            map.put("headPath", relativePath.split("/")[1] + "/" + ejtpPath + "/");
            view.setViewName("index_1");

        } catch (Exception e) {
            e.printStackTrace();
            view.setViewName("error");
            map.put("msg", e.getMessage());
        }

        return view;
    }

    @RequestMapping ("/toError")
    public ModelAndView toError() {
        ModelAndView view = new ModelAndView();
        view.setViewName("error");
        return view;
    }
}
