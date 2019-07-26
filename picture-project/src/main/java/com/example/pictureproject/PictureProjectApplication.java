package com.example.pictureproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan ("com.example.pictureproject.dao")
public class PictureProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PictureProjectApplication.class, args);
    }

}
