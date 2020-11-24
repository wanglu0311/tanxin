package com.tanxin.manage_course.service;

import com.tanxin.framework.domain.course.ext.CategoryNode;
import com.tanxin.framework.model.response.QueryResult;
import com.tanxin.manage_course.dao.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CategoryService
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/17 17:17
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    // 查询课程分类信息
    public QueryResult<CategoryNode> findCategory() {
        QueryResult<CategoryNode> result = new QueryResult<>();
        result.setList(categoryMapper.findCategory());
        return result;
    }
}

