package com.example.previewproject.service.impl;

import com.example.previewproject.dao.YwEjtpDao;
import com.example.previewproject.entity.YwEjtp;
import com.example.previewproject.service.YwEjtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository ("ywEjtpService")
public class YwEjtpServiceImpl implements YwEjtpService {

    @Autowired
    private YwEjtpDao ywEjtpDao;

    private List<YwEjtp> dataList2;

    @Override
    public List<YwEjtp> getEjtpData(String yjid, String zdid) {
        dataList2 = new ArrayList<>();
        dataList2 = ywEjtpDao.getEjtpData(yjid, zdid);
        return dataList2;
    }


}
