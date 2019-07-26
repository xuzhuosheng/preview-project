package com.example.pictureproject.service.impl;


import com.example.pictureproject.dao.TXtUserDao;
import com.example.pictureproject.entity.TXtUser;
import com.example.pictureproject.service.TXtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository ("tXtUserService")
public class TXtUserServiceImpl implements TXtUserService {

    @Autowired
    private TXtUserDao tXtUserDao;

    @Override
    public TXtUser getUser(String username, String password) {

        TXtUser tXtUser = new TXtUser();
        tXtUser = tXtUserDao.getUser(username, password);
        return tXtUser;
    }
}
