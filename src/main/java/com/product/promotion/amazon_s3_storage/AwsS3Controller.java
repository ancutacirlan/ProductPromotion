package com.product.promotion.amazon_s3_storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AwsS3Controller {

    AmazonClientService amazonClientService;

    @Autowired
    public AwsS3Controller(AmazonClientService amazonClientService) {
        this.amazonClientService = amazonClientService;
    }

    @PostMapping("/api/file/upload")
    public String uploadMultipartFile(@RequestParam("uploadfile") MultipartFile file) {
        return amazonClientService.uploadFile(file);
    }

}