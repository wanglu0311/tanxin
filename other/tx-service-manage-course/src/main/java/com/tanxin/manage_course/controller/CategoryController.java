package com.tanxin.manage_course.controller;

import com.tanxin.api.category.CategoryControllerApi;
import com.tanxin.api.course.CourseControllerApi;
import com.tanxin.framework.model.response.QueryResult;
import com.tanxin.manage_course.dao.CategoryMapper;
import com.tanxin.manage_course.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CategoryController
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/17 17:15
 */
@RestController
@RequestMapping("/category")
public class CategoryController implements CategoryControllerApi {

    @Autowired
    private CategoryService categoryService;

    @Override
    @GetMapping("/list")
    public QueryResult findCategory() {
        return categoryService.findCategory();
    }
}
