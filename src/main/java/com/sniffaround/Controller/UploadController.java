package com.sniffaround.Controller;

import com.sniffaround.Service.MinioService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/upload")
@AllArgsConstructor
public class UploadController {
    public final MinioService minioService;

    @PostMapping(value = "/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadPhoto(@RequestParam("file")MultipartFile file) {
        String objectKey = this.minioService.uploadPublicFile(file);
        String url = this.minioService.getPublicUrl(objectKey);
        return ResponseEntity.ok(Map.of("PhotoUrl", url));
    }
}
