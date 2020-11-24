package com.tanxin.manage_course.dao;

import com.github.pagehelper.Page;
import com.tanxin.framework.domain.course.CourseBase;
import com.tanxin.framework.domain.course.ext.CategoryNode;
import com.tanxin.framework.domain.course.ext.CourseInfo;
import com.tanxin.framework.domain.course.request.CourseListRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator.
 */
@Mapper
@Transactional
public interface CategoryMapper {

   // 查询课程分类信息
   List<CategoryNode> findCategory();

}
