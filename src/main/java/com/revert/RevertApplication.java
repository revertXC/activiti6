package com.revert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

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
