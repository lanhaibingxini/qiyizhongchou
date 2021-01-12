package com.offcn.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import lombok.ToString;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Data
@ToString
public class OssTemplate {
    private String endpoint; //http://oss-cn-beijing.aliyuncs.com 所在区域地址
    private String bucketDomain; //bucket的域名  https://yubing20201228.oss-cn-beijing.aliyuncs.com
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    /**
     * 上传文件的方法
     * 返回上传后的文件的访问路径
     * @param inputStream
     * @param fileName
     * @return
     * @throws IOException
     */
    public String upload(InputStream inputStream, String fileName){
        //1、加工文件夹和文件名
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String folderName = simpleDateFormat.format(new Date());
        fileName = UUID.randomUUID().toString().replace("-", "") + "_" + fileName;
        //2、创建OSS实例对象
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //3、上传文件流，指定bucket名称
        ossClient.putObject(bucketName, "pic/" + folderName + "/" + fileName, inputStream);
        //4、关闭资源
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ossClient.shutdown();
        //https://yubing20201228.oss-cn-beijing.aliyuncs.com/pic/cat.jpg
        String url = "http://" + bucketDomain + "/pic/" + folderName + "/" + fileName;
        System.out.println("上传文件访问路径:"+url);
        return url;
    }
}
