package com.tanxin.api.cms;

import com.tanxin.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName CmsConfigControllerApi
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/12 14:45
 */
@Api(tags = "cms配置管理接口",description = "cms配置管理接口,提供数据模型的管理,查询接口")
public interface CmsConfigControllerApi {

    @ApiOperation("根据id查询Cms配置信息")
    public CmsConfig getmodel(String id);

}
