package com.lovefound.love_found_api.adoption_application.models.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.lovefound.love_found_api.adoption_application.models.enums.ApplicationStatus;
import com.lovefound.love_found_api.pet.model.entity.Pet;
import com.lovefound.love_found_api.profiles.entities.AdopterProfile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "applications",
uniqueConstraints = {@UniqueConstraint(columnNames = {"adopter_id", "pet_id"})})

public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ---------- Relationships ----------

    @ManyToOne
    @JoinColumn(name = "adopter_id", nullable = false)
    private AdopterProfile adopter;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;


    // ---------- Application Status ----------

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;


    // ---------- Application Date ----------

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // ---------- Application Motivation ----------

    @Column(columnDefinition = "TEXT", nullable = false)
    private String motivation;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();

        if (status == null) {
            status = ApplicationStatus.PENDING;
        }
    }

    
}
