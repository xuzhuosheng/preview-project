package com.example.pictureproject.controller.pic;


import com.example.pictureproject.entity.TXtUser;
import com.example.pictureproject.entity.YwEjtp;
import com.example.pictureproject.entity.YwYjtp;
import com.example.pictureproject.entity.YwZdgl;
import com.example.pictureproject.service.YwEjtpService;
import com.example.pictureproject.service.YwYjtpService;
import com.example.pictureproject.service.YwZdglService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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
public class PictureController {

    /*配置文件*/
    @Value ("${file.upload.path}")
    private String uploadFilePath;

    @Value ("${file.upload.path.relative}")
    private String relativePath;

    @Value ("${yjtpPath}")
    private String yjtpPath;

    @Value ("${ejtpPath}")
    private String ejtpPath;

    /*声明*/
    private ModelAndView view;
    private ModelMap map;
    private List<YwYjtp> dataList;
    private List<YwEjtp> dataList2;


    /*注入*/
    @Autowired
    private YwZdglService ywZdglService;

    @Autowired
    private YwYjtpService ywYjtpService;

    @Autowired
    private YwEjtpService ywEjtpService;

    @RequestMapping ("toPicIndex")
    public ModelAndView toPicIndex(HttpServletRequest request, ModelMap map) {
        view = new ModelAndView();
        String searchContent = request.getParameter("searchContent");
        String zdid = request.getParameter("zdid");
        try {
            List<YwZdgl> ywZdglList = new ArrayList<>();
            dataList = ywYjtpService.getYjtpData(searchContent, zdid);
            ywZdglList = ywZdglService.getZdglData("");
            map.put("ywZdglList", ywZdglList);
            map.put("dataList", dataList);
            map.put("searchContent", searchContent);
            map.put("zdid", zdid);
            map.put("headPath", relativePath.split("/")[1] + "/" + yjtpPath + "/");


            view.setViewName("pic/pic_index");

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    @RequestMapping ("toPicAdd")
    public ModelAndView toPicAdd(ModelMap map) {
        view = new ModelAndView();

        try {
            List<YwZdgl> ywZdglList = new ArrayList<>();
            ywZdglList = ywZdglService.getZdglData("");
            map.put("ywZdglList", ywZdglList);
            view.setViewName("pic/pic_add");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    @RequestMapping (value = "doSavePic", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doSavePic(HttpServletRequest request,
                              @RequestParam (value = "file", required = false) MultipartFile multipartFile,
                              HttpSession session) {
        map = new ModelMap();
        String zdid = request.getParameter("zdid");
        String pname = request.getParameter("pname");
        String pdescribe = request.getParameter("pdescribe");
        TXtUser user = (TXtUser) session.getAttribute("user");
        String creater = user.getUsername();
        String newImgName = "";
        try {


            if (multipartFile.getSize() > 0) {
                String imgFileName = multipartFile.getOriginalFilename();
                Date date = new Date();
                long time = date.getTime();
                newImgName = time + "_" + imgFileName;
                String path = uploadFilePath + yjtpPath + "/";
                File targetFile = new File(path, newImgName);
                multipartFile.transferTo(targetFile);
            }

            ywYjtpService.doSavePic(zdid, pname, pdescribe, newImgName, creater);

            map.put("msg", "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "保存失败！");
        }

        return map;
    }


    @RequestMapping (value = "doPicDel", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doPicDel(HttpServletRequest request) {
        map = new ModelMap();
        String ids = request.getParameter("ids");
        try {
            String idArr[] = ids.split(",");
            List<String> idList = Arrays.asList(idArr);
            ywYjtpService.doPicDel(idList);
            map.put("msg", "删除成功！");

        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "删除失败！" + e.getMessage());
        }
        return map;

    }

    @RequestMapping (value = "toPicEdit", method = RequestMethod.GET)
    public ModelAndView toPicEdit(HttpServletRequest request, ModelMap map) {
        String id = request.getParameter("id");
        try {
            view = new ModelAndView();
            view.setViewName("pic/pic_edit");
            dataList = new ArrayList<>();
            dataList = ywYjtpService.getYjtpDataById(id);
            List<YwZdgl> ywZdglList = new ArrayList<>();
            ywZdglList = ywZdglService.getZdglData("");
            map.put("dataList", dataList);
            map.put("ywZdglList", ywZdglList);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());

        }
        return view;
    }


    @RequestMapping (value = "toPicShow")
    public ModelAndView toPicShow(HttpServletRequest request, ModelMap map) {
        view = new ModelAndView();
        String yjid = request.getParameter("id");
        String zdid = request.getParameter("zdid");
        String pname = request.getParameter("pname");
        try {
            dataList2 = new ArrayList<>();
            dataList2 = ywEjtpService.getEjtpData(yjid, zdid);
            view.setViewName("pic/pic_show");
            map.put("dataList2", dataList2);
            map.put("yjtpId", yjid);
            map.put("zdid", zdid);
            map.put("pname", pname);
            map.put("headPath", relativePath.split("/")[1] + "/" + ejtpPath + "/");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;

    }

    @RequestMapping ("toShowAdd")
    public ModelAndView toShowAdd(HttpServletRequest request, ModelMap map) {
        view = new ModelAndView();
        String yjtpId = request.getParameter("yjtpId");
        String zdid = request.getParameter("zdid");
        map.put("yjtpId", yjtpId);
        map.put("zdid", zdid);
        view.setViewName("pic/pic_show_add");
        return view;
    }


    @RequestMapping (value = "doUploadPics", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doUploadPics(HttpServletRequest request,
                                 @RequestPart (value = "file", required = false) MultipartFile[] multipartFile,
                                 HttpSession session) {
        map = new ModelMap();

        String yjtpId = request.getParameter("yjtpId");
        String zdid = request.getParameter("zdid");
//        String pname = request.getParameter("pname");
//        String pdescribe = request.getParameter("pdescribe");
        try {
            if (multipartFile.length > 0) {

                for(int i = 0; i < multipartFile.length; i++) {
                    if (multipartFile[i].getSize() > 0) {
                        String imgFileName = multipartFile[i].getOriginalFilename();
                        Date date = new Date();
                        long time = date.getTime();
                        String newImgName = time + "_" + imgFileName;
                        String path = uploadFilePath + ejtpPath + "/" + "yjtp_" + zdid + "_" + yjtpId + "/";
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
            map.put("msg", "上传失败！");
        }

        return map;

    }

    @RequestMapping (value = "doSaveShowAdd", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doSaveShowAdd(HttpServletRequest request, HttpSession session) {
        map = new ModelMap();
        String yjid = request.getParameter("yjid");
        String zdid = request.getParameter("zdid");
        String pname = request.getParameter("pname");
        String pdescribe = request.getParameter("pdescribe");
        String uploadFileName = request.getParameter("uploadFileName");


        String[] uploadFileNameArr = {};
        if (uploadFileName != null && !"".equals(uploadFileName)) {
            uploadFileName = uploadFileName.substring(0, uploadFileName.length() - 1);
            uploadFileNameArr = uploadFileName.split(",");
        }
        TXtUser user = (TXtUser) session.getAttribute("user");
        String creater = user.getUsername();


        try {

            if (uploadFileNameArr != null && uploadFileNameArr.length > 0) {


                for(int i = 0; i < uploadFileNameArr.length; i++) {
                    ywEjtpService.doSaveEjtp(yjid, zdid, pname, pdescribe, uploadFileNameArr[i].toString(), creater);
                }
                map.put("msg", "保存成功！");

            } else {
                map.put("msg", "请先上传图片！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "保存失败！" + e.getMessage());
        }
        return map;

    }


    @RequestMapping (value = "doEjtpDel", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doEjtpDel(HttpServletRequest request) {
        String ids = request.getParameter("ids");
        map = new ModelMap();
        try {
            String idArr[] = ids.split(",");
            List<String> idList = Arrays.asList(idArr);
            ywEjtpService.doEjtpDel(idList);
            map.put("msg", "删除成功！");
            map.put("flag", "success");

        } catch (Exception e) {
            e.printStackTrace();
            map.put("flag", "error");

            map.put("msg", "删除失败！" + e.getMessage());
        }
        return map;
    }


}
