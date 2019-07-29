package com.example.previewproject.service.impl;

import com.example.previewproject.dao.YwAutoPicDao;
import com.example.previewproject.entity.YwAutoPic;
import com.example.previewproject.service.YwAutoPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository ("ywAutoPicService")
public class YwAutoPicServiceImpl implements YwAutoPicService {

    @Autowired
    private YwAutoPicDao ywAutoPicDao;

    @Override
    public List<YwAutoPic> getAutoPicData(int id) {
        List<YwAutoPic> ywAutoPicList = new ArrayList<>();
        ywAutoPicList = ywAutoPicDao.getAutoPicData(id);
        return ywAutoPicList;
    }
}
