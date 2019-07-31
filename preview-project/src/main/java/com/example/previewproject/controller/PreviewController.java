package com.example.previewproject.controller;


import com.example.previewproject.entity.YwAutoPic;
import com.example.previewproject.entity.YwEjtp;
import com.example.previewproject.entity.YwYjtp;
import com.example.previewproject.entity.YwZdgl;
import com.example.previewproject.service.YwAutoPicService;
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

    @Value ("${autoPic}")
    private String autoPic;


    @Autowired
    private YwZdglService ywZdglService;

    @Autowired
    private YwYjtpService ywYjtpService;

    @Autowired
    private YwEjtpService ywEjtpService;

    @Autowired
    private YwAutoPicService ywAutoPicService;

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
                String ywAutoPicStr = getAutopicStr(zdgl.getId());
                map.put("ywAutoPicStr", ywAutoPicStr);
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


    public String getAutopicStr(int zdid) {
        String ywAutoPicStr = "";
        try {
            List<YwAutoPic> ywAutoPicList = new ArrayList<>();
            ywAutoPicList = ywAutoPicService.getAutoPicData(zdid);
            if (ywAutoPicList.size() > 0) {
                StringBuffer s = new StringBuffer();
                for(int i = 0; i < ywAutoPicList.size(); i++) {
                    s.append(relativePath.split("/")[1] + "/" + autoPic + "/" + ywAutoPicList.get(i).getPath() +
                            ",");
                }
                ywAutoPicStr = s.substring(0, s.length() - 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ywAutoPicStr;
    }

    @RequestMapping ("toEjtpIndex")
    public ModelAndView toEjtpIndex(HttpServletRequest request, ModelMap map) {
        String yjid = request.getParameter("yjid");
        String zdid = request.getParameter("zdid");

        ModelAndView view = new ModelAndView();
        try {
            List<YwEjtp> ywEjtpList = new ArrayList<>();
            ywEjtpList = ywEjtpService.getEjtpData(yjid, zdid);

            String ywEjtpStr = "";
            if (ywEjtpList.size() > 0) {
                StringBuffer buffer = new StringBuffer();
                for(int i = 0; i < ywEjtpList.size(); i++) {
                    buffer.append(relativePath.split("/")[1] + "/" + ejtpPath + "/" + "yjtp_" + zdid + "_" + yjid +
                            "/" + ywEjtpList.get(i).getPath() +
                            ",");
                }
                ywEjtpStr = buffer.substring(0, buffer.length() - 1);
            }

            String ywAutoPicStr = getAutopicStr(Integer.parseInt(zdid));
            map.put("ywEjtpList", ywEjtpList);
            map.put("ywEjtpStr", ywEjtpStr);
            map.put("ywAutoPicStr", ywAutoPicStr);

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
