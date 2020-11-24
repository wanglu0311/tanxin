package com.tanxin.test.fastdfs;

import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName TestFastDFS
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/19 18:58
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFastDFS {

     // 上传测试
    @Test
    public void testUpload(){


        try {
            // 加载fastdfs-client.properties配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");

            // 定义一个客户端TrackerClient 用于请求TrackerServer
            TrackerClient trackerClient = new TrackerClient();

            // 连接tracker
            TrackerServer connection = trackerClient.getConnection();

            // 获取stroage
            StorageServer storeStorage = trackerClient.getStoreStorage(connection);

            // 创建stroageClient
            StorageClient1 storageClient1 = new StorageClient1(connection,storeStorage);

            // 向stroageClient上传文件
            // 本地文件的路径
            String filePath = "e:/Q头像伞.jpg";
            String fileId = storageClient1.upload_file1(filePath, "jpg", null);


            System.out.println("fileId = " + fileId);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }


     // 下载文件
    @Test
    public void dowlnTest(){

        try {
            // 加载fastdfs-client.properties配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");

            // 定义一个客户端TrackerClient 用于请求TrackerServer
            TrackerClient trackerClient = new TrackerClient();

            // 连接tracker
            TrackerServer connection = trackerClient.getConnection();

            // 获取stroage
            StorageServer storeStorage = trackerClient.getStoreStorage(connection);

            // 创建stroageClient
            StorageClient1 storageClient1 = new StorageClient1(connection,storeStorage);

            // 下载文件
            String fileId = "group1/M00/00/00/wKiYhV-2XXyADW2gAADKWgtpbvk311.jpg";
            byte[] bytes = storageClient1.download_file1(fileId);

            // 使用输出流保存文件
            FileOutputStream fileOutputStream = new FileOutputStream("C:/Users/DELL/Desktop/test.jpg");

            fileOutputStream.write(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
