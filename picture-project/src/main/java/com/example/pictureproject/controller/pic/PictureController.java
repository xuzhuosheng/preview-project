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

    /**
     * 跳转到图片管理方法
     * 搜索方法
     *
     * @param request
     * @param map
     * @return
     */
    @RequestMapping ("toPicIndex")
    public ModelAndView toPicIndex(HttpServletRequest request, ModelMap map) {
        view = new ModelAndView();
//        获取前端参数
        String searchContent = request.getParameter("searchContent");
        String zdid = request.getParameter("zdid");
        String sname = request.getParameter("sname");
        try {
//            获取终端
            List<YwZdgl> ywZdglList = new ArrayList<>();
//            获取一级图片数据
            dataList = ywYjtpService.getYjtpData(searchContent, zdid);
            ywZdglList = ywZdglService.getZdglData("");
            map.put("ywZdglList", ywZdglList);
            map.put("dataList", dataList);
            map.put("searchContent", searchContent);
            map.put("zdid", zdid);
            map.put("sname", sname);

//            返回路径
            map.put("headPath", relativePath.split("/")[1] + "/" + yjtpPath + "/");
            view.setViewName("pic/pic_index");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    /**
     * 跳转到添加图片方法
     *
     * @param map
     * @return
     */
    @RequestMapping ("toPicAdd")
    public ModelAndView toPicAdd(ModelMap map, HttpServletRequest request) {
        view = new ModelAndView();
        String zdid = request.getParameter("zdid");
        try {
            //            获取终端，页面作为select
            List<YwZdgl> ywZdglList = new ArrayList<>();
            ywZdglList = ywZdglService.getZdglDataById(zdid);
            map.put("ywZdglList", ywZdglList);
            view.setViewName("pic/pic_add");

        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());
        }

        return view;
    }

    /**
     * 保存一级图片方法
     * 一级图片作为封面只能上传一张
     *
     * @param request
     * @param multipartFile
     * @param session
     * @return
     */
    @RequestMapping (value = "doSavePic", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doSavePic(HttpServletRequest request,
                              @RequestParam (value = "file", required = false) MultipartFile multipartFile,
                              HttpSession session) {
        map = new ModelMap();
//        获取页面参数
        String zdid = request.getParameter("zdid");
        String pname = request.getParameter("pname");
        String pdescribe = request.getParameter("pdescribe");
//        通过session 获取当前用户
        TXtUser user = (TXtUser) session.getAttribute("user");
        String creater = user.getUsername();
        String newImgName = "";
        try {
//            上传图片
            if (multipartFile.getSize() > 0) {
                String imgFileName = multipartFile.getOriginalFilename();
                String type = multipartFile.getContentType().split("/")[1];

                Date date = new Date();
                long time = date.getTime();
                newImgName = time + "_" + String.valueOf((int) (Math.random() * 100 * 100 * 100) + 1) + "." + type;
                String path = uploadFilePath + yjtpPath + "/";
                File targetFile = new File(path, newImgName);
                multipartFile.transferTo(targetFile);
            }
//      上传成功后保存页面数据
            ywYjtpService.doSavePic(zdid, pname, pdescribe, newImgName, creater);
            map.put("msg", "保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "保存失败！");
        }
        return map;
    }


    @RequestMapping (value = "doUpdatePic", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doUpdatePic(HttpServletRequest request) {
        map = new ModelMap();
        String id = request.getParameter("yjtpId");//一级图片ID
        String zdid = request.getParameter("zdid");
        String pname = request.getParameter("pname");
        String pdescribe = request.getParameter("pdescribe");

        try {
            ywYjtpService.doUpdatePic(id, zdid, pname, pdescribe);
            map.put("msg", "保存成功！");

        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());
        }

        return map;

    }


    /**
     * 删除方法，支持单记录和多记录删除
     *
     * @param request
     * @return
     */
    @RequestMapping (value = "doPicDel", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doPicDel(HttpServletRequest request) {
        map = new ModelMap();
        String ids = request.getParameter("ids");//id1,id2,id3,id...
        try {
//            讲字符串ids转成特定的格式，才能本Mybatis做批量删除
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

    /**
     * 跳转到一级图片修改页面方法
     * 只允许修改字符数据，不允许重新上传图片
     *
     * @param request
     * @param map
     * @return
     */
    @RequestMapping (value = "toPicEdit", method = RequestMethod.GET)
    public ModelAndView toPicEdit(HttpServletRequest request, ModelMap map) {
        String id = request.getParameter("id");
        try {
            view = new ModelAndView();
            dataList = new ArrayList<>();
//            List<YwZdgl> ywZdglList = new ArrayList<>();
            dataList = ywYjtpService.getYjtpDataById(id);//根据id获取数据
//            ywZdglList = ywZdglService.getZdglData("");
            view.setViewName("pic/pic_edit");
            map.put("dataList", dataList);
//            map.put("ywZdglList", ywZdglList);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", e.getMessage());

        }
        return view;
    }


    /**
     * 跳转二级图片页面方法
     * 根据一级图片的id和zdid（终端id）
     *
     * @param request
     * @param map
     * @return
     */
    @RequestMapping (value = "toPicShow")
    public ModelAndView toPicShow(HttpServletRequest request, ModelMap map) {
        view = new ModelAndView();
//        获取页面参数
        String yjid = request.getParameter("id");
        String zdid = request.getParameter("zdid");
        String pname = request.getParameter("pname");
        String sname = request.getParameter("sname");
        try {
            dataList2 = new ArrayList<>();
            dataList2 = ywEjtpService.getEjtpData(yjid, zdid);
            view.setViewName("pic/pic_show");
            map.put("dataList2", dataList2);
            map.put("yjtpId", yjid);
            map.put("zdid", zdid);
            map.put("pname", pname);
//            二级图片页面显示路径
            map.put("headPath", relativePath.split("/")[1] + "/" + ejtpPath + "/");
            map.put("sname", sname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;

    }

    /**
     * 跳转到二级图片添加页面方法
     * 先将多图片上传，返回path，再保存数据库
     *
     * @param request
     * @param map
     * @return
     */
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

    /**
     * 批量上传图片方法
     * 由于前端批量上传插件的特殊性，一次提交多图片，该方法循环多次执行
     *
     * @param request
     * @param multipartFile
     * @param session
     * @return
     */
    @RequestMapping (value = "doUploadPics", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doUploadPics(HttpServletRequest request,
                                 @RequestPart (value = "file", required = false) MultipartFile[] multipartFile,
                                 HttpSession session) {
        map = new ModelMap();

        String yjtpId = request.getParameter("yjtpId");
        String zdid = request.getParameter("zdid");
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
//                        二级图片路径
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

    /**
     * 保存二级图片信息
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping (value = "doSaveShowAdd", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap doSaveShowAdd(HttpServletRequest request, HttpSession session) {
        map = new ModelMap();
        String yjid = request.getParameter("yjid");
        String zdid = request.getParameter("zdid");
        String pname = request.getParameter("pname");
        String pdescribe = request.getParameter("pdescribe");
//        获取上传图片的路径
        String uploadFileName = request.getParameter("uploadFileName");//path1,path2,path3,path...


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


    /**
     * 删除二级图片方法，支持单记录和多记录删除
     *
     * @param request
     * @return
     */
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
