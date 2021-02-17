package com.product.promotion.amazon_s3_storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class AmazonClientService {

  private AmazonS3 client;

  @Value("${amazon.endpointUrl}")
  private String endpointUrl;

  @Value("${amazon.bucketName}")
  private String bucketName;

  @Value("${amazon.region}")
  private String region;

  @Value("${amazon.accessKey}")
  private String accessKey;

  @Value("${amazon.secretKey}")
  private String secretKey;


  @PostConstruct
  private void initializeAmazonClient() {
    AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
    client = AmazonS3ClientBuilder
            .standard()
            .withRegion(region)
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .build();
  }


  private String generateFileName(MultipartFile multiPart) {
    return LocalDate.now() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
  }


  public String uploadFile(MultipartFile multipartFile) {
    String fileUrl = "";
    try {
      File file = convertMultiPartToFile(multipartFile);
      String fileName = generateFileName(multipartFile);
      fileUrl = endpointUrl + "/" + fileName;
      uploadFileTos3bucket(fileName, file);
      file.delete();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fileUrl;
  }


  private void uploadFileTos3bucket(String fileName, File file) {
    client.putObject(new PutObjectRequest(bucketName, fileName, file)
        .withCannedAcl(CannedAccessControlList.PublicRead));
  }


  private File convertMultiPartToFile(MultipartFile file) throws IOException {
    File convFile = new File(file.getOriginalFilename());
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }
}
