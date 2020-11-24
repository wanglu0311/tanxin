package com.tanxin.govern.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName GovenCenterApp
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/20 20:40
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GovenCenterApp {
    public static void main(String[] args) {
       SpringApplication.run(GovenCenterApp.class,args);
    }

}
