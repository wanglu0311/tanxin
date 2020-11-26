package com.tanxin.manage_course.client;

import com.tanxin.framework.domain.cms.CmsPage;
import com.tanxin.framework.domain.cms.response.CmsPageResult;
import com.tanxin.framework.domain.cms.response.CmsPostPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName CmsPageClient
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/21 20:13
 */
@FeignClient(value = "tx-service-manage-cms-client")
public interface CmsPageClient {

    // 添加页面,用于课程预览
    @PostMapping("/cms/page/save")
    CmsPageResult saveCmsPage(@RequestBody CmsPage cmsPage);

    @PostMapping("/cms/page/postPageQuick")
    CmsPostPageResult postPageQuick(CmsPage cmsPage) ;
}
