package com.example.mvc_project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Adoption {

    private Long adoptionId;
    private Long ownerId;
    private Long petId;
    private OffsetDateTime adoptionDate;
    private OffsetDateTime updatedAt;

}
