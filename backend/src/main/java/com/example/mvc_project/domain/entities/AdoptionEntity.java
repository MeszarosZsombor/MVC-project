package com.example.mvc_project.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "adoptions")
public class AdoptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adoptionId;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private PetEntity pet;

    @CreationTimestamp
    private OffsetDateTime adoptionDate;

    @UpdateTimestamp
    private OffsetDateTime updatedAt;

}
