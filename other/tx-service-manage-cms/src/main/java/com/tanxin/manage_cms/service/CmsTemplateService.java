package com.tanxin.manage_cms.service;

import com.tanxin.api.cms.CmsTemplateControllerApi;
import com.tanxin.framework.domain.cms.CmsSite;
import com.tanxin.framework.domain.cms.CmsTemplate;
import com.tanxin.framework.model.response.CommonCode;
import com.tanxin.framework.model.response.QueryResponseResult;
import com.tanxin.framework.model.response.QueryResult;
import com.tanxin.manage_cms.dao.CmsTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tanxin.framework.model.response.CommonCode.*;

/**
 * @ClassName CmsSiteService
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/11 13:26
 */
@Service
public class CmsTemplateService implements CmsTemplateControllerApi {

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;


    @Override
    public QueryResponseResult findTemplateList() {

        List<CmsTemplate> list = cmsTemplateRepository.findAll();


        QueryResult<CmsTemplate> queryResult = new QueryResult<>();
        queryResult.setTotal(list.size());
        queryResult.setList(list);

        return new QueryResponseResult(SUCCESS,queryResult);
    }
}
