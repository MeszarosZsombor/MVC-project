package com.example.mvc_project.services;

import com.example.mvc_project.domain.entities.PetCategoryEntity;

import java.util.List;

public interface PetCategoryService {
    PetCategoryEntity createPetCategory(PetCategoryEntity petCategoryEntity);

    List<PetCategoryEntity> findAll();
}
