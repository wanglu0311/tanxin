package com.tanxin.manage_course.service;

import com.tanxin.api.cms.CmsPageControllerApi;
import com.tanxin.framework.domain.cms.request.QueryPageRequest;
import com.tanxin.framework.model.response.QueryResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName FeignService
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/21 18:58
 */
@FeignClient("tx-service-manage-cms-client")
public interface FeignService{

    @GetMapping("/cms/page/list/{page}/{size}")
    QueryResponseResult findCmsPageList(@PathVariable("page") int currentPage, @PathVariable("size")int pageSize,@RequestParam(required = false,value = "queryPageRequest") QueryPageRequest queryPageRequest);
}
