package com.example.previewproject.dao;


import com.example.previewproject.entity.YwAutoPic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository ("ywAutoPicDao")
public interface YwAutoPicDao {
    List<YwAutoPic> getAutoPicData(@Param ("zdid") int zdid);


}
