package com.example.mvc_project.domain.dto;

import com.example.mvc_project.domain.entities.PetCategoryEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetDto {

    private Long petId;
    private String petName;
    private Integer weight;
    private Integer age;
    private String gender;
    private PetCategoryDto petCategory;
    private Boolean adopted;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
