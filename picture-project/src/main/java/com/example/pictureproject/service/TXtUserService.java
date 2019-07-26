package com.example.pictureproject.service;

import com.example.pictureproject.entity.TXtUser;

public interface TXtUserService {
    TXtUser getUser(String username, String password);
}
