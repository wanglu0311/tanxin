package com.tanxin.manage_cms.dao;

import com.tanxin.manage_cms.service.CmsPageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName PageServiceTest
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/12 20:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PageServiceTest {

    @Autowired
    CmsPageService pageService;

    @Test
    public void testGetPageHtml(){

        String pageHtml = pageService.getPageHtml("5fab91c443604e53d4460678");
        System.out.println(pageHtml);
    }
}
