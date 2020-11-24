package com.tanxin.api.category;

import com.tanxin.framework.model.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName CategoryControllerApi
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/17 17:14
 */
@Api(tags = "课程分类管理接口",description = "查询课程分类")
public interface CategoryControllerApi {

    @ApiOperation("课程分类查询")
    QueryResult findCategory();
}
