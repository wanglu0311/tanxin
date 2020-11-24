package com.tanxin.manage_cms.controller;

import com.tanxin.api.cms.CmsPageControllerApi;
import com.tanxin.framework.domain.cms.CmsPage;
import com.tanxin.framework.domain.cms.request.QueryPageRequest;
import com.tanxin.framework.domain.cms.response.CmsPageResult;
import com.tanxin.framework.domain.cms.response.CmsPostPageResult;
import com.tanxin.framework.model.response.QueryResponseResult;
import com.tanxin.framework.model.response.ResponseResult;
import com.tanxin.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName CmsPageController
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/10 9:52
 */
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {

    @Autowired
    private CmsPageService pageService;


    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findCmsPageList(@PathVariable("page") int currentPage, @PathVariable("size")int pageSize, QueryPageRequest queryPageRequest) {

        return pageService.findCmsPageList(currentPage, pageSize, queryPageRequest);

    }

    @Override
    @PostMapping("/add")
    public CmsPageResult addCmsPage(@RequestBody CmsPage cmsPage) {

        return pageService.addCmsPage(cmsPage);

    }

    @Override
    @GetMapping("/get/{id}")
    public CmsPage findCmsPageById(@PathVariable("id") String id) {
        return pageService.findCmsPageById(id);
    }

    @Override
    @PutMapping("/edit/{id}")
    public CmsPageResult editCmsPage(@PathVariable("id")String id,@RequestBody CmsPage cmsPage) {
        return pageService.editCmsPage(id,cmsPage);
    }

    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult deletePage(@PathVariable("id") String id) {
        return pageService.deletePage(id);
    }

    @Override
    @PostMapping("/postPage/{pageId}")
    public ResponseResult postPage(@PathVariable("pageId") String pageId) {
        return pageService.postPage(pageId);
    }

    @Override
    @PostMapping("/save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage) {
        return pageService.save(cmsPage);
    }


    @Override
    @PostMapping("/postPageQuick")
    public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage) {
        return pageService.postPageQuick(cmsPage);
    }
}
