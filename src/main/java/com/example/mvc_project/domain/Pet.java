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
public class Pet {

    private Long petId;
    private String petName;
    private Integer weight;
    private Integer age;
    private String gender;
    private Long petCategoryId;
    private Boolean adopted;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}
