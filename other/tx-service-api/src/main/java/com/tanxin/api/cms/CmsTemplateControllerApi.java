package com.tanxin.api.cms;

import com.tanxin.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName CmsSiteControllerApi
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/11 13:21
 */
@Api(tags = "cms模板管理接口",description = "cms模板管理接口,提供站点的增删改查")
public interface CmsTemplateControllerApi {


    @ApiOperation("查询所有模板信息")
    QueryResponseResult findTemplateList();
}
