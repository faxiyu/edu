package com.fxy.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.fxy.oss.service.OssService;
import com.fxy.oss.utils.ConstantPropertiesUtils;
import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid+fileName;

            //获取当前日期
            String datepath = new DateTime().toString("yyyy/MM/dd");
            fileName=datepath+"/"+fileName;
            ossClient.putObject(bucketName, fileName,inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            String url="https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
