package com.example.pictureproject.service;

import com.example.pictureproject.entity.YwZdgl;

import java.util.List;

public interface YwZdglService {
    List<YwZdgl> getZdglData(String searchContent);

    void insertZdgl(String sname, String sdescribe, String surl, String username);

    void doDelZdgl(List<String> idList);

    List<YwZdgl> getZdglDataById(String id);

    void UpdateZdgl(String id, String sname, String sdescribe, String surl);
}
