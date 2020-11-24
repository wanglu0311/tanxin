package com.tanxin.manage_cms.controller;

import com.tanxin.api.cms.CmsTemplateControllerApi;
import com.tanxin.framework.model.response.QueryResponseResult;
import com.tanxin.manage_cms.service.CmsTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CmsSiteController
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/11 13:25
 */
@RestController
@RequestMapping("/cms/template")
//@Api(tags = "cms模板管理接口",description = "cms模板管理接口,提供模板的增删改查")
public class CmsTemplateController implements CmsTemplateControllerApi {

    @Autowired
    private CmsTemplateService cmsTemplateService;

    @GetMapping("/list")
    @Override
    public QueryResponseResult findTemplateList() {
        return cmsTemplateService.findTemplateList();
    }
}
