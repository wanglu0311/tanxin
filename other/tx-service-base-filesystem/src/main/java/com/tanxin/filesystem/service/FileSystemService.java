package com.tanxin.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.tanxin.filesystem.dao.FileSystemResponsitory;
import com.tanxin.framework.domain.filesystem.FileSystem;
import com.tanxin.framework.domain.filesystem.response.FileSystemCode;
import com.tanxin.framework.domain.filesystem.response.UploadFileResult;
import com.tanxin.framework.exception.ExceptionCast;
import com.tanxin.framework.model.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @ClassName FileSystemServic
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/20 11:33
 */
@Service
public class FileSystemService {

    @Value("${tanxin.fastdfs.connect_timeout_in_seconds}")
    private int connect_timeout_in_seconds;

    @Value("${tanxin.fastdfs.charset}")
    private String charset;

    @Value("${tanxin.fastdfs.network_timeout_in_seconds}")
    private int network_timeout_in_seconds;

    @Value("${tanxin.fastdfs.tracker_servers}")
    private String tracker_servers;

    @Autowired
    private FileSystemResponsitory fileSystemResponsitory;

    // 上传文件
    public UploadFileResult upload(MultipartFile file,String filetag,String businesskey,String metadata){

        // 判断文件是否为空
        if (file == null) {
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }

        // 将文件上传到fastDFS中,得到一个文件id
        String fileId = fdfs_upload(file);

        // 是否上传成功
        if (StringUtils.isEmpty(fileId)) {
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }

        // 将文件id及其他文件信息存储到mongodb中
        FileSystem fileSystem = new FileSystem();
        fileSystem.setFileId(fileId);
        fileSystem.setFilePath(fileId);
        fileSystem.setFiletag(filetag);
        fileSystem.setBusinesskey(businesskey);
        fileSystem.setFileName(file.getOriginalFilename());
        fileSystem.setFileType(file.getContentType());

        if (StringUtils.isNotEmpty(metadata)) {

            Map map = JSON.parseObject(metadata, Map.class);
            fileSystem.setMetadata(map);
        }

        // 保存到mongodb
        fileSystemResponsitory.save(fileSystem);

        return new UploadFileResult(CommonCode.SUCCESS,fileSystem);
    }


    /**
     * @param file: 文件
     * @return: java.lang.String 文件id
     * @author: 禄
     * @date: 2020/11/20 11:42
     * @description: 文件上传到fastDFS中
     */
    private String fdfs_upload(MultipartFile file) {

        // 初始化fastdfs环境
        initFdfsConfig();

        // 创建trackerClient
        TrackerClient trackerClient = new TrackerClient();

        try {
            TrackerServer trackerServer = trackerClient.getConnection();

            // 得到storage服务器
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);

            // 创建storageClient进行上传文件
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storeStorage);

            // 上传文件

            // 文件字节
            byte[] bytes = file.getBytes();

            // 得到文件原始名称
            String filename = file.getOriginalFilename();

            // 得到文件的拓展名
            String substring = filename.substring(filename.lastIndexOf(".")+1);

            return storageClient1.upload_file1(bytes, substring, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //  初始化fastdfs环境
    private void initFdfsConfig() {
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
            ClientGlobal.setG_charset(charset);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionCast.cast(FileSystemCode.FS_INITFDFSERROR);
        }

    }

}
