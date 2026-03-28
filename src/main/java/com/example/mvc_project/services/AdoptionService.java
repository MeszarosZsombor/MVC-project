package com.example.mvc_project.services;

import com.example.mvc_project.domain.entities.AdoptionEntity;

import java.util.List;
import java.util.Optional;

public interface AdoptionService {
    AdoptionEntity save(AdoptionEntity adoptionEntity);

    List<AdoptionEntity> findAll();

    Optional<AdoptionEntity> findOne(Long id);

    boolean isExists(Long id);

    AdoptionEntity partialUpdate(Long id, AdoptionEntity adoptionEntity);
}
