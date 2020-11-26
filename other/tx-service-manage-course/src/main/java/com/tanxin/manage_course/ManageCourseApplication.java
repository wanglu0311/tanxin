package com.tanxin.manage_course;

import feign.codec.Decoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootApplication
@EntityScan("com.tanxin.framework.domain.course")//扫描实体类
@ComponentScan(basePackages={"com.tanxin.api"})//扫描接口
@ComponentScan(basePackages={"com.tanxin.manage_course"})
@ComponentScan(basePackages={"com.tanxin.framework"})//扫描common下的所有类
@EnableDiscoveryClient
@EnableFeignClients
public class ManageCourseApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ManageCourseApplication.class, args);
    }
}
