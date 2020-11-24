package com.tanxin.api.system;

import com.tanxin.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName SysDictionaryControllerApi
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/17 18:42
 */
@Api(tags = "数据字典接口",description = "数据字典管理接口,提供数据字典的增删改查")
public interface SysDictionaryControllerApi {

    @ApiOperation("根据id查询信息")
    SysDictionary findInfoById(String sysid);

}
