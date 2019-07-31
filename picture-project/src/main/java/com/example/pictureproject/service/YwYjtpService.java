package com.example.pictureproject.service;

import com.example.pictureproject.entity.YwYjtp;

import java.util.List;

public interface YwYjtpService {
    void doSavePic(String zdid, String pname, String pdescribe, String path, String creater);

    List<YwYjtp> getYjtpData(String searchContent, String zdid);

    void doPicDel(List<String> idList);

    List<YwYjtp> getYjtpDataById(String id);

    void doUpdatePic(String id, String zdid, String pname, String pdescribe);
}
