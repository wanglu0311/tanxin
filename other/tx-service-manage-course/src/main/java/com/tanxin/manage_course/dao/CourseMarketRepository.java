package com.tanxin.manage_course.dao;

import com.tanxin.framework.domain.course.CourseMarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName CourseMarketRepository
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/18 19:44
 */
@Transactional
public interface CourseMarketRepository extends JpaRepository<CourseMarket,String> {

    @Query(nativeQuery = true,value = "SELECT * FROM `course_market` ORDER BY expires desc LIMIT 1")
    CourseMarket findByExpiresdesc();

}
