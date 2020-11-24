package com.tanxin.manage_cms.dao;

import com.tanxin.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName SysDictionaryRepository
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/17 18:48
 */
@Transactional
public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {
}
