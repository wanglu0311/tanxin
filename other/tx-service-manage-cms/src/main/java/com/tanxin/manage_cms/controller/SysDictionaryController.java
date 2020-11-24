package com.tanxin.manage_cms.controller;

import com.tanxin.api.system.SysDictionaryControllerApi;
import com.tanxin.framework.domain.system.SysDictionary;
import com.tanxin.manage_cms.dao.SysDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @ClassName SysDictionaryController
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/17 18:49
 */
@RestController
@RequestMapping("/sys/dictionary")
public class SysDictionaryController implements SysDictionaryControllerApi {

    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

    @Override
    @GetMapping("/get/{sysid}")
    public SysDictionary findInfoById(@PathVariable("sysid")String sysid) {

        // 根据数据id查询具体数据
        Optional<SysDictionary> optional = sysDictionaryRepository.findById(sysid);

        return optional.orElse(null);
    }
}
