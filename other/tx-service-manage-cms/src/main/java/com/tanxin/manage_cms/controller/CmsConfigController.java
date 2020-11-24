package com.tanxin.manage_cms.controller;

import com.tanxin.api.cms.CmsConfigControllerApi;
import com.tanxin.framework.domain.cms.CmsConfig;
import com.tanxin.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CmsConfigController
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/12 14:56
 */
@RestController
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {

    @Autowired
    private CmsConfigService cmsConfigService;


    @GetMapping("/getmodel/{id}")
    @Override
    public CmsConfig getmodel(@PathVariable("id") String id) {
        return cmsConfigService.getmodel(id);
    }
}
