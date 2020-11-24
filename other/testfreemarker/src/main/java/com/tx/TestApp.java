package com.tx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName TestApp
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/12 10:54
 */
@SpringBootApplication
public class TestApp {
    public static void main(String[] args) {
       SpringApplication.run(TestApp.class,args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }



}
