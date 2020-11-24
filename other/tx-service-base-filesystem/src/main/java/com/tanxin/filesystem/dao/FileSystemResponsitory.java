package com.tanxin.filesystem.dao;

import com.tanxin.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @ClassName FileSystemResponsitory
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/20 11:29
 */
public interface FileSystemResponsitory extends MongoRepository<FileSystem,String> {

}
