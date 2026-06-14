package com.sniffaround.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity(name = "events")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String location;
    private Double lat;
    private Double lng;
    @Column(nullable = false)
    private Timestamp eventTime;
    @Column(nullable = false)
    private int capacity;
    @Column(nullable = false)
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer")
    private User organizer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community")
    private Community community;
}
