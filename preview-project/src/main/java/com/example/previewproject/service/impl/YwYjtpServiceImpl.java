package com.example.previewproject.service.impl;

import com.example.previewproject.dao.YwYjtpDao;
import com.example.previewproject.entity.YwYjtp;
import com.example.previewproject.service.YwYjtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository ("ywYjtpService")
public class YwYjtpServiceImpl implements YwYjtpService {

    @Autowired
    private YwYjtpDao ywYjtpDao;

    private List<YwYjtp> dataList;

    @Override
    public List<YwYjtp> getYjtpDataByZdid(int zdid) {
        dataList = new ArrayList<>();
        dataList = ywYjtpDao.getYjtpDataByZdid(zdid);
        return dataList;
    }
}
