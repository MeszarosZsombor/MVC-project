package com.example.mvc_project.services.impl;

import com.example.mvc_project.domain.entities.PetCategoryEntity;
import com.example.mvc_project.repositories.PetCategoryRepository;
import com.example.mvc_project.services.PetCategoryService;
import org.springframework.stereotype.Service;

@Service
public class PetCategoryServiceImpl implements PetCategoryService {

    private PetCategoryRepository petCategoryRepository;

    public PetCategoryServiceImpl(PetCategoryRepository petCategoryRepository) {
        this.petCategoryRepository = petCategoryRepository;
    }

    @Override
    public PetCategoryEntity createPetCategory(PetCategoryEntity petCategoryEntity) {
        return petCategoryRepository.save(petCategoryEntity);
    }
}
