package com.example.mvc_project.controllers;

import com.example.mvc_project.domain.dto.PetCategoryDto;
import com.example.mvc_project.domain.entities.PetCategoryEntity;
import com.example.mvc_project.mappers.Mapper;
import com.example.mvc_project.services.PetCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<PetCategoryDto> listPetCategories(Pageable pageable) {
        Page<PetCategoryEntity> petCategories = petCategoryService.findAll(pageable);
        return petCategories.map(petCategoryMapper::mapTo);
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

    @PatchMapping(path = "/pet_categories/{id}")
    public ResponseEntity<PetCategoryDto> partialUpdatePetCategory(@PathVariable("id") Long id,

                                                                   @RequestBody PetCategoryDto petCategoryDto) {
        if(!petCategoryService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PetCategoryEntity petCategoryEntity = petCategoryMapper.mapFrom(petCategoryDto);
        PetCategoryEntity updatedPetCategoryEntity = petCategoryService.partialUpdate(id, petCategoryEntity);
        return new ResponseEntity<>(petCategoryMapper.mapTo(updatedPetCategoryEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/pet_categories/{id}")
    public ResponseEntity deletePetCategory(@PathVariable("id") Long id) {
        petCategoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
