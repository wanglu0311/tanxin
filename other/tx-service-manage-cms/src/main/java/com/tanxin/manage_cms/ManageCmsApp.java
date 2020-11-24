package com.tanxin.manage_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName ManageCmsApp
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/10 9:46
 */
@SpringBootApplication
@EntityScan("com.tanxin.framework.domain.domain.cms")// 扫描实体类
@ComponentScan(basePackages = "com.tanxin.api")// 扫描接口
@ComponentScan(basePackages = "com.tanxin.manage_cms")// 扫描本项目下的
@ComponentScan(basePackages = "com.tanxin.framework")// 扫描common包下的类
@EnableDiscoveryClient
public class ManageCmsApp {
    public static void main(String[] args) {
       SpringApplication.run(ManageCmsApp.class,args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }


}
