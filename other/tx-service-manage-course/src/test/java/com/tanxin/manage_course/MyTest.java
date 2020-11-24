package com.tanxin.manage_course;

import com.tanxin.framework.model.response.QueryResponseResult;
import com.tanxin.framework.model.response.QueryResult;
import com.tanxin.manage_course.service.FeignService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName MyTest
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/21 19:02
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class MyTest {

    @Autowired
    private FeignService feignService;

    @Test
    public void myTest() {

        QueryResponseResult cmsPageList = feignService.findCmsPageList(1, 3, null);

        cmsPageList.getQueryResult().getList().forEach(item ->{
            System.out.println("item = " + item);
            System.out.println("==========");
        });
    }


}
