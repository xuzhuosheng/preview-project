package com.example.pictureproject.dao;

import com.example.pictureproject.entity.TXtUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository ("tXtUserDao")
public interface TXtUserDao {
    TXtUser getUser(@Param ("username") String username, @Param ("password") String password);
}
