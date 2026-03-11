package com.example.mvc_project.controllers;

import com.example.mvc_project.domain.dto.PetCategoryDto;
import com.example.mvc_project.domain.entities.PetCategoryEntity;
import com.example.mvc_project.mappers.Mapper;
import com.example.mvc_project.repositories.PetCategoryRepository;
import com.example.mvc_project.services.PetCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetCategoryController {

    private PetCategoryService petCategoryService;
    private Mapper<PetCategoryEntity, PetCategoryDto> petCategoryMapper;

    public PetCategoryController(PetCategoryService petCategoryService, Mapper<PetCategoryEntity, PetCategoryDto> petCategoryMapper) {
        this.petCategoryService = petCategoryService;
        this.petCategoryMapper = petCategoryMapper;
    }

    @PostMapping(path = "/pet_categories")
    public ResponseEntity<PetCategoryDto> createPetCategory(@RequestBody PetCategoryDto petCategory) {
        PetCategoryEntity petCategoryEntity = petCategoryMapper.mapFrom(petCategory);
        PetCategoryEntity savedPetCategoryEntity = petCategoryService.createPetCategory(petCategoryEntity);
        return new ResponseEntity<>(petCategoryMapper.mapTo(savedPetCategoryEntity), HttpStatus.CREATED);
    }

}
