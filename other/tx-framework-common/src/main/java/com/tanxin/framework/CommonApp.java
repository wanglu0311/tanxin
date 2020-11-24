package com.tanxin.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName CommonApp
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/9 18:54
 */
@SpringBootApplication
@EnableSwagger2
public class CommonApp {
    public static void main(String[] args) {
       SpringApplication.run(CommonApp.class,args);
    }
    
}
