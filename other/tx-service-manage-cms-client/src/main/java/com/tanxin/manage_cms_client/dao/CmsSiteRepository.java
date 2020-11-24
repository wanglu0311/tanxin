package com.tanxin.manage_cms_client.dao;

import com.tanxin.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @ClassName CmsSIteRepository
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/11 13:24
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {


}
