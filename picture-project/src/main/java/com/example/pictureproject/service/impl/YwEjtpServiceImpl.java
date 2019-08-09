package com.example.pictureproject.service.impl;

import com.example.pictureproject.dao.YwEjtpDao;
import com.example.pictureproject.entity.YwEjtp;
import com.example.pictureproject.service.YwEjtpService;
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
    public List<YwEjtp> getEjtpData(String yjid, String zdid,String searchContent) {
        dataList2 = new ArrayList<>();
        dataList2 = ywEjtpDao.getEjtpData(yjid, zdid,searchContent);
        return dataList2;
    }

    @Override
    public void doSaveEjtp(String yjid, String zdid, String pname, String pdescribe, String fileName, String creater) {
        ywEjtpDao.doSaveEjtp(yjid, zdid, pname, pdescribe, fileName, creater);
    }

    @Override
    public void doEjtpDel(List<String> idList) {
        ywEjtpDao.doEjtpDel(idList);
    }
}
