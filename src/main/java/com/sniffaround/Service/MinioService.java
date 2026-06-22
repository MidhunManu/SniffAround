package com.sniffaround.Service;

import com.sniffaround.Exception.FileUploadException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.Http;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;
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

    public String uploadPublicFile(MultipartFile file) {
        return upload(publicBucket, file);
    }

    public String uploadPrivateFile(MultipartFile file) {
        return upload(privateBucket, file);
    }

    private String upload(String bucket, MultipartFile file) {
        String filename = Paths.get(Objects.requireNonNull(file.getOriginalFilename())).getFileName().toString();
        if (file.isEmpty()) {
            throw new FileUploadException(
                    "No file provided or file is empty",
                    HttpStatus.UNPROCESSABLE_CONTENT
            );
        }

        if (file.getContentType() == null || ! file.getContentType().startsWith("image/") || !isAllowedExtension(filename)) {
            throw new FileUploadException(
                    "Only image files are allowed",
                    HttpStatus.UNPROCESSABLE_CONTENT
            );
        }

        if (file.getSize() > 10 * 1024 * 1024) {
            throw new FileUploadException(
                    "File size exceeds 10 MB",
                    HttpStatus.CONTENT_TOO_LARGE
            );
        }
        try {
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
        } catch (IOException e) {
            throw new FileUploadException("Could not read file", HttpStatus.UNPROCESSABLE_CONTENT);
        } catch (MinioException e) {
            throw new FileUploadException("Storage unavailable", HttpStatus.INSUFFICIENT_STORAGE);
        }
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

    private boolean isAllowedExtension(String filename) {
        if (filename == null || !filename.contains(".")) return false;
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();

        return Set.of(
                "png",
                "jpg",
                "jpeg",
                "gif",
                "pdf",
                "webp",
                "bmp"
        ).contains(extension);

    }
}