package com.tanxin.manage_cms.controller;

import com.tanxin.framework.web.BaseController;
import com.tanxin.manage_cms.service.CmsPageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName CmsPagePreviewController
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/12 21:23
 */
@Controller
@Api(tags = "cms页面预览",description = "cms页面预览")
public class CmsPagePreviewController extends BaseController {


    @Autowired
    private CmsPageService cmsPageService;


    // 页面预览
    @RequestMapping(value = "/cms/preview/{pageId}",method = RequestMethod.GET)
    public void preview(@PathVariable("pageId") String pageId) throws IOException {

        // 执行静态化
        String pageHtml = cmsPageService.getPageHtml(pageId);

        // 通过response对象将内容输出
        ServletOutputStream outputStream = response.getOutputStream();
        response.setHeader("content-type","text/html;charset=utf-8");
        outputStream.write(pageHtml.getBytes(StandardCharsets.UTF_8));


    }




}
