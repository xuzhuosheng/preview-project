package com.example.pictureproject.controller.pic;

import com.example.pictureproject.entity.TXtUser;
import com.example.pictureproject.entity.YwAutoPic;
import com.example.pictureproject.entity.YwZdgl;
import com.example.pictureproject.service.YwAutoPicService;
import com.example.pictureproject.service.YwZdglService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Controller
public class YwAutoPicController {
    /*配置文件*/
    @Value ("${file.upload.path}")
    private String uploadFilePath;

    @Value ("${file.upload.path.relative}")
    private String relativePath;

    @Value ("${autoPic}")
    private String autoPic;

    /*注入*/
    @Autowired
    private YwAutoPicService ywAutoPicService;

    @Autowired
    private YwZdglService ywZdglService;

    /*自定义*/
    private ModelAndView view;

    private List<YwAutoPic> dataList;

    private List<YwZdgl> ywZdglList;

    private ModelMap map;

    @RequestMapping (value = "toAutoPicIndex")
    public ModelAndView toAutoPicIndex(HttpServletRequest request, ModelMap map) {
        view = new ModelAndView();
        String zdid = request.getParameter("zdid");
        String sname = request.getParameter("sname");
        String searchContent = request.getParameter("searchContent");
        try {

            dataList = new ArrayList<>();
            dataList = ywAutoPicService.getAutoPicData(zdid, searchContent);
//            ywZdglList = ywZdglService.getZdglData("");
//            map.put("ywZdglList", ywZdglList);
            map.put("dataList", dataList);
            map.put("searchContent", searchContent);
            map.put("zdid", zdid);
            map.put("sname", sname);
            map.put("headPath", relativePath.split("/")[1] + "/" + autoPic);
            view.setViewName("auto/pic_auto_index");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());
            view.setViewName("error");
        }
        return view;
    }


    @RequestMapping (value = "toAutoPicAdd")
    public ModelAndView toAutoPicAdd(ModelMap map, HttpServletRequest request) {
        view = new ModelAndView();
        String zdid = request.getParameter("zdid");
        String sname = request.getParameter("sname");
        try {
//            List<YwZdgl> ywZdglList = new ArrayList<>();
//            ywZdglList = ywZdglService.getZdglData("");
//            map.put("ywZdglList", ywZdglList);
            map.put("zdid", zdid);
            map.put("sname", sname);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());
        }
        view.setViewName("auto/pic_auto_add");
        return view;

    }

    @RequestMapping (value = "doUploadAutoPics", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doUploadAutoPics(HttpServletRequest request,
                                     @RequestParam (value = "file", required = false) MultipartFile[] multipartFile,
                                     HttpSession session) {
        map = new ModelMap();

//        String zdid = request.getParameter("zdid");
        try {
            if (multipartFile.length > 0) {

                for(int i = 0; i < multipartFile.length; i++) {
                    if (multipartFile[i].getSize() > 0) {
                        String imgFileName = multipartFile[i].getOriginalFilename();
                        String type = multipartFile[i].getContentType().split("/")[1];
                        Date date = new Date();
                        long time = date.getTime();
                        String newImgName =
                                time + "_" + String.valueOf((int) (Math.random() * 100 * 100 * 100) + 1) + "." + type;
                        String path = uploadFilePath + "/" + autoPic;
                        File targetFile = new File(path, newImgName);
                        if (!targetFile.exists()) {
                            targetFile.mkdirs();
                        }
                        multipartFile[i].transferTo(targetFile);
                        map.put("uploadFileName", newImgName);
                        map.put("msg", "上传成功！");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "上传失败！" + e.getMessage());
        }

        return map;

    }


    @RequestMapping (value = "doSaveAutoPic", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doSaveAutoPic(HttpServletRequest request, HttpSession session) {
        map = new ModelMap();

        String ptitle = request.getParameter("ptitle");
        String zdid = request.getParameter("zdid");
        String uploadFileName = request.getParameter("uploadFileName");

        String[] uploadFileNameArr = {};
        if (uploadFileName != null && !"".equals(uploadFileName)) {
            uploadFileName = uploadFileName.substring(0, uploadFileName.length() - 1);
            uploadFileNameArr = uploadFileName.split(",");
        }
        TXtUser user = (TXtUser) session.getAttribute("user");
        String creater = user.getUsername();
        try {

            if (uploadFileNameArr.length > 0) {
                for(int i = 0; i < uploadFileNameArr.length; i++) {
                    ywAutoPicService.doSaveAutoPic(ptitle, zdid, creater, uploadFileNameArr[i].toString());
                }
            }
            map.put("msg", "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());
        }


        return map;


    }

    @RequestMapping (value = "doAutoPicDel", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doAutoPicDel(HttpServletRequest request) {
        map = new ModelMap();
        String ids = request.getParameter("ids");//id1,id2,id3,id...
        try {
            String idArr[] = ids.split(",");
            List<String> idList = Arrays.asList(idArr);
            if (idList.size() > 0) {
                ywAutoPicService.doAutoPicDel(idList);
                map.put("flag", "success");
                map.put("msg", "删除成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "删除失败！" + e.getMessage());

        }


        return map;

    }

}
