package com.sniffaround.Config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MinioBucketInitializer {
    private final MinioClient minioClient;
    @Value("${minio.public-bucket}")
    private String publicBucket;
    @Value("${minio.private-bucket}")
    private String privateBucket;

    @PostConstruct
    public void init() throws Exception {
        ensureBucket(publicBucket, true);
        ensureBucket(privateBucket, false);
    }

    public void ensureBucket(String bucket, boolean isPublic) throws Exception {
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());

        if (! exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }

        if (isPublic) {
            String policy = """
        {
          "Version": "2012-10-17",
          "Statement": [{
            "Effect": "Allow",
            "Principal": "*",
            "Action": ["s3:GetObject"],
            "Resource": ["arn:aws:s3:::%s/*"]
          }]
        }
        """.formatted(bucket);

            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(bucket)
                            .config(policy)
                            .build()
            );
        }
    }

}
