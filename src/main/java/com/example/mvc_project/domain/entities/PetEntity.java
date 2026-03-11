package com.example.mvc_project.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pets")
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    private String petName;
    private Integer weight;
    private Integer age;
    private String gender;

    @ManyToOne
    @JoinColumn(name = "pet_category_id")
    private PetCategoryEntity petCategoryEntity;

    private Boolean adopted;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
