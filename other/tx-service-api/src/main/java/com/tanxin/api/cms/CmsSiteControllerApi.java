package com.tanxin.api.cms;

import com.tanxin.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName CmsSiteControllerApi
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/11 13:21
 */
@Api(tags = "cms站点管理接口",description = "cms站点管理接口,提供站点的增删改查")
public interface CmsSiteControllerApi {

    @ApiOperation("查询所有站点信息")
    QueryResponseResult findSiteList();
}
