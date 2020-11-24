package com.tx.test.freemarker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @ClassName FreemarkerController
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/12 10:47
 */
@Controller  //使用Controller
@RequestMapping("/freemarker")
public class FreemarkerController {

    @RequestMapping("test1")
    public String test1(Map<String,String> map){

        map.put("name","张三三四");


        //返回freemarker模板的位置,基于resources/templates路径的
        return "test1";
    }

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/banner")
    public String index_banner(Map<String, Object> map){
        String dataUrl = "http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f";
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        map.putAll(body);

        return "indexbanner";
    }


    @RequestMapping("/course")
    public String course(Map<String, Object> map){
        String dataUrl = "http://localhost:31200/course/courseview/297e7c7c62b888f00162b8a965510001";
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        map.putAll(body);

        return "course";
    }


}
