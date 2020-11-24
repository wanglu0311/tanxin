package com.tanxin.framework.domain.cms.response;

import com.tanxin.framework.domain.cms.CmsPage;
import com.tanxin.framework.model.response.ResponseResult;
import com.tanxin.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by mrt on 2018/3/31.
 */
@Data
@NoArgsConstructor
public class CmsPageResult extends ResponseResult implements Serializable {
    CmsPage cmsPage;
    public CmsPageResult(ResultCode resultCode,CmsPage cmsPage) {
        super(resultCode);
        this.cmsPage = cmsPage;
    }
}
