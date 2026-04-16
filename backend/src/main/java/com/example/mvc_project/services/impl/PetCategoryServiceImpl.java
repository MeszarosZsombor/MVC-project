package com.example.mvc_project.services.impl;

import com.example.mvc_project.domain.entities.PetCategoryEntity;
import com.example.mvc_project.repositories.PetCategoryRepository;
import com.example.mvc_project.services.PetCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.mvc_project.utilities.StringHelper.capitalizeWords;

@Service
public class PetCategoryServiceImpl implements PetCategoryService {

    private PetCategoryRepository petCategoryRepository;

    public PetCategoryServiceImpl(PetCategoryRepository petCategoryRepository) {
        this.petCategoryRepository = petCategoryRepository;
    }

    @Override
    public PetCategoryEntity save(PetCategoryEntity petCategoryEntity) {
        String normalized = capitalizeWords(
                petCategoryEntity.getPetType().trim().toLowerCase());

        boolean conflict = (petCategoryEntity.getPetCategoryId() == null)
                ? petCategoryRepository.existsByPetType(normalized)
                : petCategoryRepository.existsByPetTypeAndPetCategoryIdNot(normalized, petCategoryEntity.getPetCategoryId());

        if (conflict) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists");
        }

        petCategoryEntity.setPetType(normalized);

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
    public Page<PetCategoryEntity> findAll(Pageable pageable) {
        return petCategoryRepository.findAll(pageable);
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

    @Override
    public void delete(Long id) {
        petCategoryRepository.deleteById(id);
    }
}
