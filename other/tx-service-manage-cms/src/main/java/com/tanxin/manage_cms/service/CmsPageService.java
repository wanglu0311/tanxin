package com.tanxin.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.tanxin.api.cms.CmsPageControllerApi;
import com.tanxin.framework.domain.cms.CmsPage;
import com.tanxin.framework.domain.cms.CmsSite;
import com.tanxin.framework.domain.cms.CmsTemplate;
import com.tanxin.framework.domain.cms.request.QueryPageRequest;
import com.tanxin.framework.domain.cms.response.CmsCode;
import com.tanxin.framework.domain.cms.response.CmsPageResult;
import com.tanxin.framework.domain.cms.response.CmsPostPageResult;
import com.tanxin.framework.exception.CustomException;
import com.tanxin.framework.exception.ExceptionCast;
import com.tanxin.framework.model.response.CommonCode;
import com.tanxin.framework.model.response.QueryResponseResult;
import com.tanxin.framework.model.response.QueryResult;
import com.tanxin.framework.model.response.ResponseResult;
import com.tanxin.manage_cms.config.RabbitConfig;
import com.tanxin.manage_cms.dao.CmsPageRepository;
import com.tanxin.manage_cms.dao.CmsSiteRepository;
import com.tanxin.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.ui.freemarker.SpringTemplateLoader;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName PageService
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/10 11:45
 */
@Service
public class CmsPageService implements CmsPageControllerApi {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*
     * @param currentPage: 当前页从1开始
     * @param pageSize: 每页记录数
     * @param queryPageRequest: 查询条件
     * @return: com.tanxin.framework.model.response.QueryResponseResult
     * @author: 禄
     * @date: 2020/11/10 11:51
     * @description:
     */
    @Override
    public QueryResponseResult findCmsPageList(int currentPage, int pageSize, QueryPageRequest queryPageRequest) {

        if (queryPageRequest == null) {

            queryPageRequest = new QueryPageRequest();

        }


        // 自定义条件查询
        // 条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());

        // 条件值对象
        CmsPage cmsPage = new CmsPage();

        // 设置条件值

        // 站点id
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {

            cmsPage.setSiteId(queryPageRequest.getSiteId());

        }

        // 模板id
        if (StringUtils.isNotEmpty(queryPageRequest.getTemplateId())) {

            cmsPage.setTemplateId(queryPageRequest.getTemplateId());

        }

        // 页面别名
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {

            cmsPage.setPageAliase(queryPageRequest.getPageAliase());

        }

        // 定义条件对象
        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        // 分页参数
        if (currentPage <= 0) {
            currentPage = 1;
        }

        currentPage -= 1;

        if (pageSize <= 0) {
            pageSize = 10;
        }

        PageRequest pageRequest = PageRequest.of(currentPage, pageSize);

        // 条件查询并进行分页
        Page<CmsPage> page = cmsPageRepository.findAll(example, pageRequest);

        QueryResult<CmsPage> queryResult = new QueryResult<>();
        queryResult.setList(page.getContent());
        queryResult.setTotal(page.getTotalElements());

        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }

    // 新增页面
    @Override
    public CmsPageResult addCmsPage(CmsPage cmsPage) {

        if (cmsPage == null) {

            //抛出非法参数异常
            throw new CustomException(CommonCode.FAIL);
        }

        // 校验页面名称,站点id,页面webpath的唯一性
        // 根据页面名称,站点id,页面webpath去cms_page集合 查到说明已经存在,查询不到继续添加
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());

        // 判断页面是否存在
        if (cmsPage1 != null) {

            //页面已经存在 抛出异常
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);

        }

        // 调用方法 新增页面
        cmsPage.setPageId(null);
        CmsPage save = cmsPageRepository.save(cmsPage);

        return new CmsPageResult(CommonCode.SUCCESS, save);


//        return new CmsPageResult(CommonCode.FAIL, null);

    }


    // 根据页面id查询页面信息
    @Override
    public CmsPage findCmsPageById(String id) {

        Optional<CmsPage> optional = cmsPageRepository.findById(id);

        return optional.orElse(null);

    }


    // 修改页面
    @Override
    public CmsPageResult editCmsPage(String id, CmsPage cmsPage) {

        CmsPage page = this.findCmsPageById(id);

        if (page != null) {

            // 准备更新数据
            // 设置要修改的数据

            // 更新模板id
            page.setTemplateId(cmsPage.getTemplateId());

            // 更新所属站点
            page.setSiteId(cmsPage.getSiteId());

            // 更新页面别名
            page.setPageAliase(cmsPage.getPageAliase());

            // 更新页面名称
            page.setPageName(cmsPage.getPageName());

            // 更新访问路径
            page.setPageWebPath(cmsPage.getPageWebPath());

            // 更新物理路径
            page.setPagePhysicalPath(cmsPage.getPagePhysicalPath());

            // 更新数据Url
            page.setDataUrl(cmsPage.getDataUrl());

            // 进行更新
            CmsPage save = cmsPageRepository.save(page);

            // 判断是否修改成功
            return new CmsPageResult(CommonCode.SUCCESS, save);

        }

        return new CmsPageResult(CommonCode.FAIL,null);
    }

    // 根据id删除页面
    @Override
    public ResponseResult deletePage(String id) {

        // 查询是否存在
        Optional<CmsPage> optional = cmsPageRepository.findById(id);

        if(optional.isPresent()){

            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);

        }

        return new ResponseResult(CommonCode.FAIL);
    }

    // 页面发布
    @Override
    public ResponseResult postPage(String pageId) {

        // 执行页面静态化
        String pageHtml = getPageHtml(pageId);

        // 将页面静态化文件存储到GridFS中
        CmsPage cmsPage = saveHtml(pageId,pageHtml);

        // 向MQ发消息
        sendPostPage(pageId);

        return new ResponseResult(CommonCode.SUCCESS);
    }


    // 页面静态化方法
    public String getPageHtml(String pageId){

        // 获取数据模型
        Map model = getModelByPageId(pageId);

        if (model == null){

            // 数据模型获取不到
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }

        // 获取页面模板的内容
        String template = getTemplateByPageId(pageId);
        if (StringUtils.isEmpty(template)) {

            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }

        // 执行静态化并返回
        return generateHtml(template, model);

    }



    // 保存页面,有则更新 没有则添加
    public CmsPageResult save(CmsPage cmsPage) {

        // 判断页面是否存在
        CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if ( cmsPage1 != null) {

            return this.editCmsPage(cmsPage1.getPageId(),cmsPage);
        }

        return this.addCmsPage(cmsPage);
    }

    // 一键发布
    public CmsPostPageResult postPageQuick(CmsPage cmsPage) {

        // 将页面的信息存储到cms_page 集合中
        CmsPageResult save = save(cmsPage);
        if (!save.isSuccess()) {
            ExceptionCast.cast(CommonCode.FAIL);
        }

        // 得到页面id
        CmsPage cmsPage1 = save.getCmsPage();
        String pageId =  cmsPage1.getPageId();

        // 执行页面发布(静态化 保存GridFS 向MQ发送消息)
        ResponseResult post = postPage(pageId);
        if (!post.isSuccess()) {
            ExceptionCast.cast(CommonCode.FAIL);
        }

        // 拼接页面url = cmsSite.siteDomain+site.WebPath+cmsPage.pageWebPath+cmsPage.pageName
        // 站点id
        String siteId = cmsPage1.getSiteId();
        CmsSite site = findCmsSiteById(siteId);

        String pageUrl = site.getSiteDomain() + site.getSiteWebPath() + cmsPage.getPageWebPath() + cmsPage.getPageName();

        return new CmsPostPageResult(CommonCode.SUCCESS,pageUrl);
    }



    // 根据站点id查询站点信息
    private CmsSite findCmsSiteById(String siteId) {
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        return optional.orElse(null);
    }
    // 获取数据模型
    private Map getModelByPageId(String pageId){

        // 取出页面的信息
        CmsPage cmsPage = this.findCmsPageById(pageId);

        if (cmsPage == null){

            ExceptionCast.cast(CmsCode.CMS_FAGE_NOTEXISTS);
        }

        // 取出页面的dataUrl
        String dataUrl = cmsPage.getDataUrl();

        if (StringUtils.isEmpty(dataUrl)) {

            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }

        // 通过restTemplate请求dataUrl获取数据
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        return forEntity.getBody();

    }

    // 获取页面的模板信息
    private String getTemplateByPageId(String pageId) {

        // 取出页面的信息
        CmsPage cmsPage = this.findCmsPageById(pageId);

        if (cmsPage == null){

            ExceptionCast.cast(CmsCode.CMS_FAGE_NOTEXISTS);
        }

        // 获取页面的模板id
        String templateId = cmsPage.getTemplateId();
        if (StringUtils.isEmpty(templateId)){

            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }

        // 查询模板信息
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);

        if (optional.isPresent()) {
            CmsTemplate cmsTemplate = optional.get();
            // 获取模板文件id
            String templateFileId = cmsTemplate.getTemplateFileId();

            //根据文件id查询文件
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(cmsTemplate.getTemplateFileId())));


            // 从GridFS中获取模板文件内容
            // 打开一个下载流对象
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            // 创建GridFsResource对象，获取流
            GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
            // 从流中取数据
            try {
                return IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
            } catch (IOException | IllegalStateException e) {
                e.printStackTrace();
            }


        }

        return null;
    }

    // 执行静态化
    private String generateHtml(String templateContent,Map model){

        // 创建配置对象
        Configuration configUration = new Configuration(Configuration.getVersion());

        // 创建模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", templateContent);

        // 配置模板加载器
        configUration.setTemplateLoader(stringTemplateLoader);

        // 获取模板
        try {
            Template template = configUration.getTemplate("template");
            // 调用api进行静态化
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    // 保存html到GridFS
    private CmsPage saveHtml(String pageId,String htmlContent){

        // 得到页面信息
        CmsPage cmsPage = findCmsPageById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }


        ObjectId objectId = null;

        try {
            // 将htmlContent内容转成输入流
            InputStream inputStream = IOUtils.toInputStream(htmlContent, "UTF-8");

            // 将html文件内容保存到GridFS
            objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());


        } catch (IOException e) {
            e.printStackTrace();
        }

        // 将html文件id更新到cmsPage中
        cmsPage.setHtmlFileId(objectId.toHexString());

        return cmsPageRepository.save(cmsPage);

    }

    // 向MQ发送消息
    private void sendPostPage(String pageId){

        // 得到页面信息
        CmsPage cmsPage = findCmsPageById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }


        // 创建消息对象
        Map<String,String> msg = new HashMap<>();
        msg.put("pageId", pageId);

        // 转成json串
        String jsonString = JSON.toJSONString(msg);

        // 发送消息给mq
        String siteId = cmsPage.getSiteId();
        rabbitTemplate.convertAndSend(RabbitConfig.EX_ROUTING_CMS_POSTPAGE,siteId,jsonString);

    }

}

