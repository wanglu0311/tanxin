package com.tanxin.manage_cms.dao;

import com.tanxin.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName CmsPageRepositoryTest
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/10 11:33
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    private CmsPageRepository cmsPageRepository;


    @Test
    public void testFindall(){

        cmsPageRepository.findAll().forEach(item->{
            System.out.println("item = " + item);
        });

    }
    @Test
    public void testFindPage(){

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());

        CmsPage cmsPage = new CmsPage();

//        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
//        cmsPage.setPageAliase("首页");

        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);



        Pageable pageable = PageRequest.of(0, 3);
        cmsPageRepository.findAll(example,pageable).forEach(item->{
            System.out.println("item = " + item);
        });

    }


}
