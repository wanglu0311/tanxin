package com.tanxin.manage_cms.service;

import com.tanxin.api.cms.CmsSiteControllerApi;
import com.tanxin.framework.domain.cms.CmsSite;
import com.tanxin.framework.model.response.CommonCode;
import com.tanxin.framework.model.response.QueryResponseResult;
import com.tanxin.framework.model.response.QueryResult;
import com.tanxin.framework.model.response.ResultCode;
import com.tanxin.manage_cms.dao.CmsSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CmsSiteService
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/11 13:26
 */
@Service
public class CmsSiteService implements CmsSiteControllerApi {

    @Autowired
    private CmsSiteRepository cmsSiteRepository;


    @Override
    public QueryResponseResult findSiteList() {

        List<CmsSite> list = cmsSiteRepository.findAll();


        QueryResult<CmsSite> queryResult = new QueryResult<>();
        queryResult.setTotal(list.size());
        queryResult.setList(list);

        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }
}
