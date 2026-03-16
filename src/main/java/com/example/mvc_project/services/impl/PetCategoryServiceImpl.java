package com.example.mvc_project.services.impl;

import com.example.mvc_project.domain.entities.PetCategoryEntity;
import com.example.mvc_project.repositories.PetCategoryRepository;
import com.example.mvc_project.services.PetCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<PetCategoryEntity> findAll() {
        return StreamSupport.stream(petCategoryRepository
                .findAll()
                .spliterator(),
                false)
                .collect(Collectors.toList());
    }
}
