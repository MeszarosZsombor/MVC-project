package com.example.mvc_project.domain.dto;

import com.example.mvc_project.domain.entities.OwnerEntity;
import com.example.mvc_project.domain.entities.PetEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdoptionDto {

    private Long adoptionId;
    private OwnerEntity owner;
    private PetEntity pet;
    private OffsetDateTime adoptionDate;
    private OffsetDateTime updatedAt;

}
