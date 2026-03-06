package com.example.mvc_project.domain;

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
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    private String petName;
    private Integer weight;
    private Integer age;
    private String gender;

    @ManyToOne
    @JoinColumn(name = "pet_category_id")
    private PetCategory petCategory;

    private Boolean adopted;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
