package com.example.mvc_project.controllers;

import com.example.mvc_project.domain.dto.PetCategoryDto;
import com.example.mvc_project.domain.entities.PetCategoryEntity;
import com.example.mvc_project.mappers.Mapper;
import com.example.mvc_project.services.PetCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        PetCategoryEntity savedPetCategoryEntity = petCategoryService.save(petCategoryEntity);
        return new ResponseEntity<>(petCategoryMapper.mapTo(savedPetCategoryEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/pet_categories")
    public List<PetCategoryDto> listPetCategories() {
        List<PetCategoryEntity> petCategories = petCategoryService.findAll();
        return petCategories.stream()
                .map(petCategoryMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/pet_categories/{id}")
    public ResponseEntity<PetCategoryDto> getPetCategory(@PathVariable("id") Long id) {
        Optional<PetCategoryEntity> foundPetCategory = petCategoryService.findOne(id);
        return foundPetCategory.map(petCategoryEntity -> {
            PetCategoryDto petCategoryDto = petCategoryMapper.mapTo(petCategoryEntity);
            return new ResponseEntity<>(petCategoryDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/pet_categories/{id}")
    public ResponseEntity<PetCategoryDto> fullUpdatePetCategory(@PathVariable("id") Long id,
                                                                @RequestBody PetCategoryDto petCategoryDto) {
        if(!petCategoryService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        petCategoryDto.setPetCategoryId(id);
        PetCategoryEntity petCategoryEntity = petCategoryMapper.mapFrom(petCategoryDto);
        PetCategoryEntity savedPetCategoryEntity = petCategoryService.save(petCategoryEntity);

        return new ResponseEntity<>(petCategoryMapper.mapTo(savedPetCategoryEntity), HttpStatus.OK);
    }
}
