package com.tanxin.manage_course.dao;

import com.tanxin.framework.domain.course.Teachplan;
import com.tanxin.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName TeachPlanMapper
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/16 19:00
 */
@Mapper
@Transactional
public interface TeachPlanMapper {

    // 课程计划查询
    public TeachplanNode selectList(String courseId);

    // 添加课程计划
    TeachplanNode addTeachplan(Teachplan teachplan);
}
