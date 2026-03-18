package com.example.mvc_project.services.impl;

import com.example.mvc_project.domain.entities.PetCategoryEntity;
import com.example.mvc_project.repositories.PetCategoryRepository;
import com.example.mvc_project.services.PetCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PetCategoryServiceImpl implements PetCategoryService {

    private PetCategoryRepository petCategoryRepository;

    public PetCategoryServiceImpl(PetCategoryRepository petCategoryRepository) {
        this.petCategoryRepository = petCategoryRepository;
    }

    @Override
    public PetCategoryEntity save(PetCategoryEntity petCategoryEntity) {
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

    @Override
    public Optional<PetCategoryEntity> findOne(Long id) {
        return petCategoryRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return petCategoryRepository.existsById(id);
    }

    @Override
    public PetCategoryEntity partialUpdate(Long id, PetCategoryEntity petCategoryEntity) {
        petCategoryEntity.setPetCategoryId(id);

        return petCategoryRepository.findById(id).map(existingPetCategory -> {
            Optional.ofNullable(petCategoryEntity.getPetType()).ifPresent(existingPetCategory::setPetType);
            return petCategoryRepository.save(existingPetCategory);
        }).orElseThrow(() -> new RuntimeException("Pet Category does not exists"));
    }
}
