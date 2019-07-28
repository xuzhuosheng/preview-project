package com.example.previewproject.dao;


import com.example.previewproject.entity.YwZdgl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository ("ywZdglDao")
public interface YwZdglDao {
//    List<YwZdgl> getZdglData(@Param ("searchContent") String searchContent);
//
//    void insertZdgl(@Param ("sname") String sname, @Param ("sdescribe") String sdescribe, @Param ("surl") String surl,
//                    @Param ("username") String username);
//
//    void delZdgl(@Param ("idList") List<String> idList);
//
//    List<YwZdgl> getZdglDataById(@Param ("id") String id);
//
//    void UpdateZdgl(@Param ("id") String id, @Param ("sname") String sname, @Param ("sdescribe") String sdescribe,
//                    @Param ("surl") String surl);

    YwZdgl getZdidByUrl(@Param ("ipAddress")String ipAddress);
}
