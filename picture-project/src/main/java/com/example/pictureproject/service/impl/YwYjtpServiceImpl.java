package com.example.pictureproject.service.impl;

import com.example.pictureproject.dao.YwYjtpDao;
import com.example.pictureproject.entity.YwYjtp;
import com.example.pictureproject.service.YwYjtpService;
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
    public void doSavePic(String zdid, String pname, String pdescribe, String path, String creater) {
        ywYjtpDao.doSavePic(zdid, pname, pdescribe, path, creater);
    }

    @Override
    public List<YwYjtp> getYjtpData(String searchContent, String zdid) {
        dataList = new ArrayList<>();
        dataList = ywYjtpDao.getYjtpData(searchContent, zdid);
        return dataList;
    }

    @Override
    public void doPicDel(List<String> idList) {
        ywYjtpDao.doPicDel(idList);
    }

    @Override
    public List<YwYjtp> getYjtpDataById(String id) {
        dataList = new ArrayList<>();
        dataList = ywYjtpDao.getYjtpDataById(id);
        return dataList;
    }

    @Override
    public void doUpdatePic(String id, String zdid, String pname, String pdescribe) {
        ywYjtpDao.updatePic(id, zdid, pname, pdescribe);

    }
}
