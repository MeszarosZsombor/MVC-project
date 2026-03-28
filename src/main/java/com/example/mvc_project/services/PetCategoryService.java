package com.example.mvc_project.services;

import com.example.mvc_project.domain.entities.PetCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PetCategoryService {
    PetCategoryEntity save(PetCategoryEntity petCategoryEntity);

    List<PetCategoryEntity> findAll();

    Page<PetCategoryEntity> findAll(Pageable pageable);

    Optional<PetCategoryEntity> findOne(Long id);

    boolean isExists(Long id);

    PetCategoryEntity partialUpdate(Long id, PetCategoryEntity petCategoryEntity);

    void delete(Long id);
}
