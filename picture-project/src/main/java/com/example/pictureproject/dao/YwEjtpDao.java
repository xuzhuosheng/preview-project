package com.example.pictureproject.dao;


import com.example.pictureproject.entity.YwEjtp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository ("ywEjtpDao")
public interface YwEjtpDao {
    List<YwEjtp> getEjtpData(@Param ("yjid") String yjid, @Param ("zdid") String zdid);

    void doSaveEjtp(@Param ("yjid") String yjid, @Param ("zdid") String zdid,
                    @Param ("pname") String pname, @Param ("pdescribe") String pdescribe,
                    @Param ("fileName") String fileName, @Param ("creater") String creater);

    void doEjtpDel(@Param ("idList") List<String> idList);
}
