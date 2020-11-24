package com.tanxin.manage_cms.service;

import com.tanxin.api.cms.CmsConfigControllerApi;
import com.tanxin.framework.domain.cms.CmsConfig;
import com.tanxin.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @ClassName CmsConfigService
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/12 14:54
 */
@Service
public class CmsConfigService implements CmsConfigControllerApi {

    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    // 根据id查询cmsConfig
    @Override
    public CmsConfig getmodel(String id) {

        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);

        if (optional.isPresent()) {

            CmsConfig cmsConfig = optional.get();
            return cmsConfig;
        }

        return null;
    }
}
