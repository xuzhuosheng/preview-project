package com.example.previewproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@MapperScan ("com.example.previewproject.dao")
public class PreviewProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreviewProjectApplication.class, args);
    }


}
