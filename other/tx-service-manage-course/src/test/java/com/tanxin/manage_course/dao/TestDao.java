package com.tanxin.manage_course.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tanxin.framework.domain.course.Category;
import com.tanxin.framework.domain.course.CourseBase;
import com.tanxin.framework.domain.course.ext.CategoryNode;
import com.tanxin.framework.domain.course.ext.CourseInfo;
import com.tanxin.framework.domain.course.ext.TeachplanNode;
import com.tanxin.framework.domain.course.request.CourseListRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sound.midi.Soundbank;
import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDao {

    @Autowired
    CourseBaseRepository courseBaseRepository;

    @Resource
    CourseMapper courseMapper;

    @Resource
    CategoryMapper categoryMapper;

    @Resource
    private TeachPlanMapper teachPlanMapper;


    @Test
    public void testCourseBaseRepository(){
        Optional<CourseBase> optional = courseBaseRepository.findById("402885816240d276016240f7e5000002");
        if(optional.isPresent()){
            CourseBase courseBase = optional.get();
            System.out.println(courseBase);
        }

    }

    @Test
    public void testCourseMapper(){
        CourseBase courseBase = courseMapper.findCourseBaseById("402885816240d276016240f7e5000002");
        System.out.println(courseBase);

    }

    @Test
    public void testFind(){
        TeachplanNode teachplanNode = teachPlanMapper.selectList("4028e581617f945f01617f9dabc40000");
        System.out.println( teachplanNode);

    }

    @Test
    public void testPage(){

        PageHelper.startPage(1,3);
        Page<CourseInfo> page = courseMapper.findCourseListPage(new CourseListRequest("2"));

        page.getResult().forEach(item ->{
            System.out.println("item = " + item);
        });

    }

    @Test
    public void gategoryTest(){

        List<CategoryNode> category = categoryMapper.findCategory();

        category.forEach(item->{
            System.out.println(item);
            System.err.println("========================");
        });

    }

}
