package com.tanxin.api.filesystem;

import com.tanxin.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName FileSystemControllerApi
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/20 11:02
 */
@Api(tags = "文件管理接口",description = "文件管理接口")
public interface FileSystemControllerApi {

    // 上传文件
    @ApiOperation("上传文件接口")
    UploadFileResult upload(MultipartFile file,String filetag,String businesskey,String metadata);




}
