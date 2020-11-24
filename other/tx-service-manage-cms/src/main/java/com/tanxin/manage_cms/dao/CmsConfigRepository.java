package com.tanxin.manage_cms.dao;

import com.tanxin.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName CmsSIteRepository
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/11 13:24
 */
@Transactional
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {

}
