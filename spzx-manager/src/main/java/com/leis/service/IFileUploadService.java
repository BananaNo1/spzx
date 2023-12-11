package com.leis.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {

    String fileUpload(MultipartFile multipartFile);
}
