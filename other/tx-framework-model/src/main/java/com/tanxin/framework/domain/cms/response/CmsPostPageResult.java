package com.tanxin.framework.domain.cms.response;

import com.tanxin.framework.model.response.ResponseResult;
import com.tanxin.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName CmsPostPageResult
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/23 19:26
 */
@Data
@NoArgsConstructor
public class CmsPostPageResult extends ResponseResult implements Serializable {

    private String pageUrl;

    public CmsPostPageResult(ResultCode resultCode,String pageUrl) {
        super(resultCode);
        this.pageUrl = pageUrl;
    }
}
