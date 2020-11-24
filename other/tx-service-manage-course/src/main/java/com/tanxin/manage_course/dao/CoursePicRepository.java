package com.tanxin.manage_course.dao;

import com.tanxin.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName CoursePicRepository
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/20 15:02
 */
@Transactional
public interface CoursePicRepository extends JpaRepository<CoursePic,String> {

    // 当返回值大于0 表示删除成功的记录数
    long deleteByCourseid(String courseId);

}
