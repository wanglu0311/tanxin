package com.tanxin.manage_course.dao;

import com.tanxin.framework.domain.course.CoursePub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName CousrsePubRepository
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/26 20:49
 */
@Transactional
public interface CousrsePubRepository extends JpaRepository<CoursePub,String> {

}
