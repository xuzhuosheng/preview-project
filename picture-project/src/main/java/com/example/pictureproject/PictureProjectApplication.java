package com.example.pictureproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@MapperScan ("com.example.pictureproject.dao")
 public class PictureProjectApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(PictureProjectApplication.class, args);
    }

}
