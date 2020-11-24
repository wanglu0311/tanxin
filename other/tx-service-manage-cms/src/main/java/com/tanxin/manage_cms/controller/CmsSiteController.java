package com.tanxin.manage_cms.controller;

import com.tanxin.api.cms.CmsSiteControllerApi;
import com.tanxin.framework.model.response.QueryResponseResult;
import com.tanxin.manage_cms.service.CmsSiteService;
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
@RequestMapping("/cms/site")
//@Api(tags = "cms站点管理接口",description = "cms站点管理接口,提供站点的增删改查")
public class CmsSiteController implements CmsSiteControllerApi {

    @Autowired
    private CmsSiteService cmsSiteService;

    @GetMapping("/list")
//    @ApiOperation("查询所有站点信息")
    public QueryResponseResult findSiteList(){
        return cmsSiteService.findSiteList();
    }


}
