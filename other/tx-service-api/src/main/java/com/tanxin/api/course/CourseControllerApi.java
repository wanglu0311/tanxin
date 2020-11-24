package com.tanxin.api.course;

import com.tanxin.framework.domain.cms.response.CmsPageResult;
import com.tanxin.framework.domain.course.CourseBase;
import com.tanxin.framework.domain.course.CourseMarket;
import com.tanxin.framework.domain.course.CoursePic;
import com.tanxin.framework.domain.course.Teachplan;
import com.tanxin.framework.domain.course.ext.CourseInfo;
import com.tanxin.framework.domain.course.ext.CourseView;
import com.tanxin.framework.domain.course.ext.TeachplanNode;
import com.tanxin.framework.domain.course.request.CourseListRequest;
import com.tanxin.framework.domain.course.response.CoursePublishResult;
import com.tanxin.framework.model.response.QueryResponseResult;
import com.tanxin.framework.model.response.ResponseResult;
import com.tanxin.framework.model.response.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName CourseControllerApi
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/16 18:50
 */
@Api(tags = "课程管理接口", description = "课程管理接口,提供课程的增删改查")
public interface CourseControllerApi {

    @ApiOperation("课程计划查询")
    TeachplanNode findTeachplanList(String courseId);

    @ApiOperation("添加课程计划")
    ResponseResult addTeachplanList(Teachplan teachplan);

    @ApiOperation("课程信息查询")
    QueryResponseResult findCourseInfo(Integer page, Integer size, CourseListRequest courseListRequest);

    @ApiOperation("添加课程")
    ResponseResult addCourse(CourseBase courseBase);

    @ApiOperation("课程基本信息查询")
    CourseInfo findCourse(String courseId);

    @ApiOperation("课程基本信息修改")
    ResponseResult updateCoursebase(String courseId, CourseBase courseBase);

    @ApiOperation("查询课程营销信息")
    CourseMarket getCourseMarketById(String courseId);

    @ApiOperation("课程营销信息修改")
    ResponseResult updateCourseMarket(String courseId, CourseMarket courseMarket);

    @ApiOperation("添加课程图片")
    ResponseResult addCoursePic(String courseId, String pic);

    @ApiOperation("查询课程图片")
    CoursePic findCoursePic(String courseId);

    @ApiOperation("删除课程图片")
    ResponseResult deleteCouresPic(String courseId);


    @ApiOperation("课程视图查询")
    CourseView courseView(String courseId);


    @ApiOperation("课程预览")
    CoursePublishResult preview(String id);

    @ApiOperation("最新课程营销信息")
    String latest();

    @ApiOperation("发布课程")
    CoursePublishResult publish(String id);


}
