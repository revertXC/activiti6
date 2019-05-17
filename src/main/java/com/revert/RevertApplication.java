package com.revert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication(scanBasePackages = {"com.revert"})
public class RevertApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(RevertApplication.class,args);
    }

    //war包需实现方法
    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder application) {
        return application.sources(RevertApplication.class);
    }

}
