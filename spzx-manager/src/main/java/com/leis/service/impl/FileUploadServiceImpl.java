package com.leis.service.impl;

import cn.hutool.core.date.DateUtil;
import com.leis.properties.MinioProperties;
import com.leis.service.IFileUploadService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements IFileUploadService {

    @Autowired
    private MinioProperties minioProperties;

    @Override
    public String fileUpload(MultipartFile multipartFile) {

        try {
            MinioClient minioClient = MinioClient.builder().endpoint(minioProperties.getEndpointUrl())
                    .credentials(minioProperties.getAccessKey(),minioProperties.getSecreKey())
                    .build();
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if(!found){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            }

           String dateDir = DateUtil.format(new Date(),"yyyyMMdd");
           String uuid= UUID.randomUUID().toString().replace("-","");
           String fileName = dateDir+"/"+uuid+multipartFile.getOriginalFilename();

            PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(minioProperties.getBucketName())
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                    .object(fileName)
                    .build();
            minioClient.putObject(putObjectArgs);
            return minioProperties.getEndpointUrl() + "/" + minioProperties.getBucketName() + "/" + fileName;
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }
}
