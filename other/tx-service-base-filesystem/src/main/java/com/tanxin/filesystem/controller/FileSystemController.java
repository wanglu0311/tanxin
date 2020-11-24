package com.tanxin.filesystem.controller;

import com.tanxin.api.filesystem.FileSystemControllerApi;
import com.tanxin.filesystem.service.FileSystemService;
import com.tanxin.framework.domain.filesystem.response.UploadFileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName FileSystemController
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/20 13:56
 */
@RestController
@RequestMapping("/filesystem")
public class FileSystemController implements FileSystemControllerApi {

    @Autowired
    private FileSystemService fileSystemService;

    @Override
    @PostMapping("/upload")
    public UploadFileResult upload(MultipartFile file, String filetag, String businesskey, String metadata) {
        return fileSystemService.upload(file,filetag,businesskey,metadata);
    }
}
