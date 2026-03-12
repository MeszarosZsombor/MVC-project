package com.example.mvc_project.controllers;

import com.example.mvc_project.domain.dto.AdoptionDto;
import com.example.mvc_project.domain.entities.AdoptionEntity;
import com.example.mvc_project.mappers.Mapper;
import com.example.mvc_project.services.AdoptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdoptionController {

    private AdoptionService adoptionService;
    private Mapper<AdoptionEntity, AdoptionDto> adoptionMapper;

    public AdoptionController(AdoptionService adoptionService, Mapper<AdoptionEntity, AdoptionDto> adoptionMapper) {
        this.adoptionService = adoptionService;
        this.adoptionMapper = adoptionMapper;
    }

    @PostMapping(path = "/adoptions")
    public ResponseEntity<AdoptionDto> createAdoption(@RequestBody AdoptionDto adoption) {
        AdoptionEntity adoptionEntity = adoptionMapper.mapFrom(adoption);
        AdoptionEntity savedAdoptionEntity = adoptionService.createAdoption(adoptionEntity);
        return new ResponseEntity<>(adoptionMapper.mapTo(savedAdoptionEntity), HttpStatus.CREATED);
    }
}
