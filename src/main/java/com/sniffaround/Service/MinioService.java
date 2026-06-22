package com.sniffaround.Service;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.Http;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Profile("!test")
@Service
public class MinioService {

    private final MinioClient minioClient;

    @Value("${minio.public-bucket}")
    private String publicBucket;

    @Value("${minio.private-bucket}")
    private String privateBucket;

    @Value("${minio.public-base-url}")
    private String publicBaseUrl;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadPublicFile(MultipartFile file) throws Exception {
        return upload(publicBucket, file);
    }

    public String uploadPrivateFile(MultipartFile file) throws Exception {
        return upload(privateBucket, file);
    }

    private String upload(String bucket, MultipartFile file) throws Exception {
        String objectKey = UUID.randomUUID() + "-" + file.getOriginalFilename();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(objectKey)
                        .stream(file.getInputStream(), file.getSize(), (long) -1)
                        .contentType(file.getContentType())
                        .build()
        );
        return objectKey;
    }

    public String getPublicUrl(String objectKey) {
        return publicBaseUrl + "/" + publicBucket + "/" + objectKey;
    }

    public String getPresignedUrl(String objectKey) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Http.Method.GET)
                        .bucket(privateBucket)
                        .object(objectKey)
                        .expiry(24, TimeUnit.HOURS)
                        .build()
        );
    }
}