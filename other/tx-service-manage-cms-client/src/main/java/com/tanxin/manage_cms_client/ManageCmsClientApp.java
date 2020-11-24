package com.tanxin.manage_cms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName ManageCmsClientApp
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/13 19:17
 */
@SpringBootApplication
@EntityScan("com.tanxin.framework.domain.domain.cms")   // 扫描实体类
@ComponentScan(basePackages = "com.tanxin.framework")   // 扫描common包下的类
@ComponentScan(basePackages = "com.tanxin.manage_cms_client")   // 扫描本项目下的
public class ManageCmsClientApp {

    public static void main(String[] args) {
       SpringApplication.run(ManageCmsClientApp.class,args);
    }
    
}
