package com.sniffaround.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Entity(name = "pets")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String species;
    @Column(nullable = false)
    private String breed;
    @Column(nullable = false)
    private Date birthDate;
    @Column(nullable = false)
    private String bio;
    @Column(nullable = false)
    private String photoURL;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false)
    private User user;
    @Column(nullable = false)
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;
}
