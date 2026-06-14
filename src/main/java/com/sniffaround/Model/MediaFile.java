package com.sniffaround.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity(name = "media_files")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MediaFile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String entityType;
    @Column(nullable = false, unique = true)
    private String storageURL;
    @Column(nullable = false)
    private String mimeType;
    @Column(nullable = false)
    private Long size;
    @Column(nullable = false)
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private  Timestamp deletedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;
}
