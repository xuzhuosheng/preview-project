package com.example.pictureproject.dao;


import com.example.pictureproject.entity.YwAutoPic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository ("ywAutoPicDao")
public interface YwAutoPicDao {
    List<YwAutoPic> getAutoPicData(@Param ("zdid") String zdid, @Param ("searchContent") String searchContent);

    void saveAutoPic(@Param ("ptitle") String ptitle, @Param ("zdid") String zdid, @Param ("creater") String creater,
                     @Param ("fileName") String fileName);

    void doAutoPicDel(@Param ("idList") List<String> idList);
}
