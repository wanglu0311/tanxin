package com.tanxin.manage_cms.dao;

import com.tanxin.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName CmsSIteRepository
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/11 13:24
 */
@Transactional
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {


}
