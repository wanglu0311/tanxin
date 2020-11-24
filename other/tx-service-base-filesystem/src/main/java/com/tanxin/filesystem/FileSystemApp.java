package com.tanxin.filesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName FileSystemApp
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/20 10:58
 */
@SpringBootApplication
@EntityScan("com.tanxin.framework.domain.filesystem")   // 扫描实体类
@ComponentScan(basePackages = "com.tanxin.api")// 扫描接口
@ComponentScan(basePackages = "com.tanxin.framework")   // 扫描common包下的类
@ComponentScan(basePackages = "com.tanxin.filesystem")   // 扫描本项目下的
public class FileSystemApp {
    public static void main(String[] args) {
       SpringApplication.run(FileSystemApp.class,args);
    }

}
