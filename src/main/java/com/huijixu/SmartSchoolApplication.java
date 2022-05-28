package com.huijixu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@Transactional
public class SmartSchoolApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SmartSchoolApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }
}
