package com.offcn.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class OSSTest {
    /**
     * OSS 使用步骤 阿里云
     * 1）、引入SDK
     * 2）、配置好相应的属性
     */
    public static void main(String[] args) throws FileNotFoundException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录https://ram.console.aliyun.com 创建。
        String accessKeyId = "LTAI4GDLfBiNBJE1A25pZSdL";
        String accessKeySecret = "fo5IbYZGuKKSdvXhphxrfFzMiAxLyu";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = new FileInputStream("D:\\upfile\\cat.jpg");
        ossClient.putObject("yubing20201228", "pic/cat.jpg", inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
