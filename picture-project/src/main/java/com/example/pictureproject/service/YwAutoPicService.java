package com.example.pictureproject.service;

import com.example.pictureproject.entity.YwAutoPic;

import java.util.List;

public interface YwAutoPicService {
    List<YwAutoPic> getAutoPicData(String zdid, String searchContent);

    void doSaveAutoPic(String ptitle, String zdid, String creater, String fileName);

    void doAutoPicDel(List<String> idList);
}
