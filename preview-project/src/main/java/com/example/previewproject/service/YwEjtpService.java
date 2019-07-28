package com.example.previewproject.service;

import com.example.previewproject.entity.YwEjtp;

import java.util.List;

public interface YwEjtpService {
    List<YwEjtp> getEjtpData(String yjid, String zdid);

}
