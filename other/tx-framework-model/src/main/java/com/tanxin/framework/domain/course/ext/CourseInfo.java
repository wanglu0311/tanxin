package com.tanxin.framework.domain.course.ext;

import com.tanxin.framework.domain.course.CourseBase;
import lombok.Data;
import lombok.ToString;

/**
 * Created by admin on 2018/2/10.
 */
@Data
//@ToString
public class CourseInfo extends CourseBase {

    // 课程图片
    private String pic;


    @Override
    public String toString() {
        System.out.println("super.toString() = " + super.toString());
        return "CourseInfo{" +
                "pic='" + pic + '\'' +
                '}';
    }
}
