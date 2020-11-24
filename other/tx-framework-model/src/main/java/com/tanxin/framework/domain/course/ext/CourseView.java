package com.tanxin.framework.domain.course.ext;

import com.tanxin.framework.domain.course.CourseBase;
import com.tanxin.framework.domain.course.CourseMarket;
import com.tanxin.framework.domain.course.CoursePic;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName CourseView
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/21 16:34
 */
@Data
@NoArgsConstructor
@ToString
public class CourseView implements Serializable {

    private CourseBase courseBase;

    private CoursePic coursePic;

    private CourseMarket courseMarket;

    private TeachplanNode teachplanNode;


}
