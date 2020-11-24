package com.tanxin.framework.domain.cms.request;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @ClassName QueryPageResuest
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/10 9:15
 */
@Data
public class QueryPageRequest {

    // 站点ID
    private String siteId;
    // 页面ID
    @Id
    private String pageId;
    // 页面名称
    private String pageName;
    // 别名
    private String pageAliase;
    // 模版id
    private String templateId;


}
