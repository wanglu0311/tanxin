package com.tanxin.manage_course.dao;

import com.github.pagehelper.Page;
import com.tanxin.framework.domain.course.CourseBase;
import com.tanxin.framework.domain.course.Teachplan;
import com.tanxin.framework.domain.course.ext.CategoryNode;
import com.tanxin.framework.domain.course.ext.CourseInfo;
import com.tanxin.framework.domain.course.request.CourseListRequest;
import com.tanxin.framework.model.response.QueryResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator.
 */
@Mapper
@Transactional
public interface CourseMapper {


    // 根据id查询课程基本信息
    CourseBase findCourseBaseById(String id);

    // 分页查询课程信息
    Page<CourseInfo> findCourseListPage(CourseListRequest courseListRequest);


    // 查询课程信息
    CourseInfo findCourse(String courseId);
}
