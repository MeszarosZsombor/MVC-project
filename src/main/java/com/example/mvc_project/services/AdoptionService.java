package com.example.mvc_project.services;

import com.example.mvc_project.domain.entities.AdoptionEntity;

import java.util.List;

public interface AdoptionService {
    AdoptionEntity createAdoption(AdoptionEntity adoptionEntity);

    List<AdoptionEntity> findAll();
}
