package com.example.mvc_project.controllers;

import com.example.mvc_project.domain.dto.PetDto;
import com.example.mvc_project.domain.entities.PetEntity;
import com.example.mvc_project.mappers.Mapper;
import com.example.mvc_project.services.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        PetEntity savedPetEntity = petService.save(petEntity);
        return new ResponseEntity<>(petMapper.mapTo(savedPetEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/pets")
    public List<PetDto> listPets() {
        List<PetEntity> pets = petService.findAll();
        return pets.stream()
                .map(petMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/pets/{id}")
    public ResponseEntity<PetDto> getPet(@PathVariable("id") Long id) {
        Optional<PetEntity> foundPet = petService.findOne(id);
        return foundPet.map(petEntity -> {
            PetDto petDto = petMapper.mapTo(petEntity);
            return new ResponseEntity<>(petDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/pets/{id}")
    public ResponseEntity<PetDto> fullUpdatePet(@PathVariable("id") Long id,
                                                @RequestBody PetDto petDto) {

        if(!petService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        petDto.setPetId(id);
        PetEntity petEntity = petMapper.mapFrom(petDto);
        PetEntity savedPetEntity = petService.save(petEntity);

        return new ResponseEntity<>(petMapper.mapTo(savedPetEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/pets/{id}")
    public ResponseEntity<PetDto> partialUpdatePet(@PathVariable("id") Long id,
                                                @RequestBody PetDto petDto) {

        if(!petService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        petDto.setPetId(id);
        PetEntity petEntity = petMapper.mapFrom(petDto);
        PetEntity savedPetEntity = petService.partialUpdate(id, petEntity);

        return new ResponseEntity<>(petMapper.mapTo(savedPetEntity), HttpStatus.OK);
    }
}
