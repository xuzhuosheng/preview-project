package com.example.pictureproject.service;

import com.example.pictureproject.entity.YwEjtp;

import java.util.List;

public interface YwEjtpService {
    List<YwEjtp> getEjtpData(String yjid, String zdid);

    void doSaveEjtp(String yjid, String zdid, String pname, String pdescribe, String fileName, String creater);

    void doEjtpDel(List<String> idList);
}
