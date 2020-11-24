package com.tanxin.manage_course.dao;

import com.tanxin.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName TeachplanRepository
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/17 10:45
 */
@Transactional
public interface TeachplanRepository extends JpaRepository<Teachplan,String> {

    List<Teachplan> findByParentidAndCourseid(String parentid, String courseid);

}
