package com.example.previewproject.dao;

import com.example.previewproject.entity.YwYjtp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository ("ywYjtpDao")
public interface YwYjtpDao {
//    void doSavePic(@Param ("zdid") String zdid, @Param ("pname") String pname, @Param ("pdescribe") String pdescribe,
//                   @Param ("path") String path, @Param ("creater") String creater);
//
//    List<YwYjtp> getYjtpData(@Param ("searchContent") String searchContent, @Param ("zdid") String zdid);
//
//    void doPicDel(@Param ("idList") List<String> idList);
//
//    List<YwYjtp> getYjtpDataById(@Param ("id") String id);

    List<YwYjtp> getYjtpDataByZdid(@Param ("zdid") int zdid);
}
