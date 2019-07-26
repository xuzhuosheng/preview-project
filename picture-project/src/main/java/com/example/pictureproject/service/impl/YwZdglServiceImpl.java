package com.example.pictureproject.service.impl;


import com.example.pictureproject.dao.YwZdglDao;
import com.example.pictureproject.entity.YwZdgl;
import com.example.pictureproject.service.YwZdglService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository ("ywZdglService")
public class YwZdglServiceImpl implements YwZdglService {

    @Autowired
    private YwZdglDao ywZdglDao;

    private List<YwZdgl> dataList;

    @Override
    public List<YwZdgl> getZdglData(String searchContent) {
        dataList = new ArrayList<>();
        dataList = ywZdglDao.getZdglData(searchContent);
        return dataList;
    }

    @Override
    public void insertZdgl(String sname, String sdescribe, String surl, String username) {
        ywZdglDao.insertZdgl(sname, sdescribe, surl, username);
    }

    @Override
    public void UpdateZdgl(String id, String sname, String sdescribe, String surl) {
        ywZdglDao.UpdateZdgl(id, sname, sdescribe, surl);
    }

    @Override
    public List<YwZdgl> getZdglDataById(String id) {
        dataList = new ArrayList<>();
        dataList = ywZdglDao.getZdglDataById(id);
        return dataList;
    }

    @Override
    public void doDelZdgl(List<String> idList) {
        ywZdglDao.delZdgl(idList);
    }
}
