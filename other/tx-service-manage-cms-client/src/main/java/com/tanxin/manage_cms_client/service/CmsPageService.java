package com.tanxin.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.tanxin.framework.domain.cms.CmsPage;
import com.tanxin.framework.domain.cms.CmsSite;
import com.tanxin.manage_cms_client.dao.CmsPageRepository;
import com.tanxin.manage_cms_client.dao.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @ClassName CmsPageService
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/15 18:46
 */
@Service
public class CmsPageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmsPageService.class);

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    // 保存html页面到服务器的物理路径
    public void savePageToServerPath(String pageId){

        // 根据pageId查询cmsPage
        CmsPage cmsPage = findCmsPageById(pageId);

        // 得到html文件id
        String htmlFileId = cmsPage.getHtmlFileId();

        // 从GridFS中查询html文件
        InputStream inputStream = getFIleById(htmlFileId);

        if (inputStream == null) {

            LOGGER.error("getFIleById error  data is null",htmlFileId);
            return ;
        }

        // 得到站点的id
        String siteId = cmsPage.getSiteId();

        // 得到站点的信息
        CmsSite cmsSite = findCmsSiteById(siteId);

        // 得到站点的物理路径
        String sitePhysicalPath = cmsSite.getSitePhysicalPath();

        // 得到页面的物理路径
        String pagePath = sitePhysicalPath + cmsPage.getPagePhysicalPath() + cmsPage.getPageName();

        FileOutputStream fileOutputStream = null;

        // 将html文件保存到服务器物理路径上
        try {
            fileOutputStream = new FileOutputStream(new File(pagePath));
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    // 根据文件id从GridFS中查询文件内容
    public InputStream getFIleById(String htmlFileId) {

        // 文件对象
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(htmlFileId)));

        // 打开下载流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());

        // 定义gridFsResource
        GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);

        try {
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    // 根据页面id查询页面信息
    public CmsPage findCmsPageById(String pageId){

        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);

        return optional.orElse(null);

    }

    // 根据站点面id查询站点信息
    public CmsSite findCmsSiteById(String siteId){

        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);

        return optional.orElse(null);

    }

}
