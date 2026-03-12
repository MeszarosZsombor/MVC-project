package com.example.mvc_project.controllers;

import com.example.mvc_project.domain.dto.PetDto;
import com.example.mvc_project.domain.entities.PetEntity;
import com.example.mvc_project.mappers.Mapper;
import com.example.mvc_project.services.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetController {

    private PetService petService;
    private Mapper<PetEntity, PetDto> petMapper;

    public PetController(PetService petService, Mapper<PetEntity, PetDto> petMapper) {
        this.petService = petService;
        this.petMapper = petMapper;
    }

    @PostMapping(path = "/pets")
    public ResponseEntity<PetDto> createPet(@RequestBody PetDto pet) {
        PetEntity petEntity = petMapper.mapFrom(pet);
        PetEntity savedPetEntity = petService.createPet(petEntity);
        return new ResponseEntity<>(petMapper.mapTo(savedPetEntity), HttpStatus.CREATED);
    }

}
