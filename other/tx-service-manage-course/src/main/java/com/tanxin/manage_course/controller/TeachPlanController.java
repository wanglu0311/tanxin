package com.tanxin.manage_course.controller;

import com.tanxin.api.course.CourseControllerApi;
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
import com.tanxin.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName TeachPlanController
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/16 20:24
 */
@RestController
@RequestMapping("/course")
public class TeachPlanController implements CourseControllerApi {

    @Autowired
    private CourseService courseService;



    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        return courseService.findTeachplanList(courseId);
    }


    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplanList(@RequestBody Teachplan teachplan) {
        return courseService.addTeachplan(teachplan);
    }

    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult findCourseInfo(@PathVariable("page") Integer page, @PathVariable("size") Integer size,  CourseListRequest courseListRequest) {
        return courseService.findCourseInfo(page,size,courseListRequest);
    }

    @Override
    @PostMapping("/coursebase/add")
    public ResponseResult addCourse(@RequestBody CourseBase courseBase) {
        return courseService.addCourse(courseBase);
    }

    @Override
    @GetMapping("/coursebase/{courseId}")
    public CourseInfo findCourse(@PathVariable("courseId") String courseId) {
        return courseService.findCourse(courseId);
    }

    @Override
    @PutMapping("/coursebase/update/{courseId}")
    public ResponseResult updateCoursebase(@PathVariable("courseId") String courseId,@RequestBody CourseBase courseBase) {
        return courseService.updateCoursebase(courseId,courseBase);
    }

    @Override
    @GetMapping("/courseMarket/{courseId}")
    public CourseMarket getCourseMarketById(@PathVariable("courseId") String courseId) {
        return courseService.getCourseMarketById(courseId);
    }

    @Override
    @PutMapping("/courseMarket/update/{courseId}")
    public ResponseResult updateCourseMarket(@PathVariable("courseId")String courseId,@RequestBody CourseMarket courseMarket) {
        return courseService.updateCourseMarket(courseId,courseMarket);
    }

    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult addCoursePic(@RequestParam("courseId") String courseId,@RequestParam("pic") String pic) {
        return courseService.addCoursePic(courseId,pic);
    }

    @Override
    @GetMapping("/coursepic/list/{courseId}")
    public CoursePic findCoursePic(@PathVariable("courseId") String courseId) {
        return courseService.findCoursePic(courseId);
    }

    @Override
    @DeleteMapping("/coursepic/delete")
    public ResponseResult deleteCouresPic(@RequestParam("courseId") String courseId) {
        return courseService.deleteCouresPic(courseId);
    }

    @Override
    @GetMapping("/courseview/{courseId}")
    public CourseView courseView(@PathVariable("courseId") String id) {
        System.out.println("id = " + id);
        return courseService.getCourseView(id);
    }

    @Override
    @PostMapping("/courseview/{id}")
    public CoursePublishResult preview(@PathVariable("id") String id) {
        return courseService.preview(id);
    }

    @Override
    @GetMapping("/latest")
    public String latest() {
        return courseService.latest();
    }

    @Override
    @PostMapping("/publish/{id}")
    public CoursePublishResult publish(@PathVariable("id") String id) {
        return courseService.publish(id);
    }

}
