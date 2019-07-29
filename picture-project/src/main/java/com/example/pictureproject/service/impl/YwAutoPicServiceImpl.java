package com.example.pictureproject.service.impl;

import com.example.pictureproject.dao.YwAutoPicDao;
import com.example.pictureproject.entity.YwAutoPic;
import com.example.pictureproject.service.YwAutoPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository ("ywAutoPicService")
public class YwAutoPicServiceImpl implements YwAutoPicService {
    private List<YwAutoPic> dataList;

    @Autowired
    private YwAutoPicDao ywAutoPicDao;

    @Override
    public void doSaveAutoPic(String ptitle, String zdid, String creater, String fileName) {
        ywAutoPicDao.saveAutoPic(ptitle, zdid, creater, fileName);
    }

    @Override
    public List<YwAutoPic> getAutoPicData(String zdid, String searchContent) {
        dataList = ywAutoPicDao.getAutoPicData(zdid, searchContent);
        return dataList;
    }

    @Override
    public void doAutoPicDel(List<String> idList) {
        ywAutoPicDao.doAutoPicDel(idList);
    }
}
