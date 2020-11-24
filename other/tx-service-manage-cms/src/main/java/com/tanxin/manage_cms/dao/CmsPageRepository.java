package com.tanxin.manage_cms.dao;

import com.tanxin.framework.domain.cms.CmsPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName CmsPageRepository
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/10 11:32
 */
@Transactional
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

    CmsPage findByPageName(String pageName);

    CmsPage findByPageNameAndPageType(String pageName,String pagetype);

    int countBySiteIdAndPageType(String siteId,String pageType);

    Page<CmsPage> findBySiteIdAndPageType(String siteId, String pageType, Pageable pageable);


    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName,String siteId,String pageWebPath);



}
