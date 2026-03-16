package com.example.mvc_project.services;

import com.example.mvc_project.domain.entities.PetCategoryEntity;

import java.util.List;
import java.util.Optional;

public interface PetCategoryService {
    PetCategoryEntity createPetCategory(PetCategoryEntity petCategoryEntity);

    List<PetCategoryEntity> findAll();

    Optional<PetCategoryEntity> findOne(Long id);
}
