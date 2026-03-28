package com.example.mvc_project.controllers;

import com.example.mvc_project.domain.dto.AdoptionDto;
import com.example.mvc_project.domain.entities.AdoptionEntity;
import com.example.mvc_project.mappers.Mapper;
import com.example.mvc_project.services.AdoptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        AdoptionEntity savedAdoptionEntity = adoptionService.save(adoptionEntity);
        return new ResponseEntity<>(adoptionMapper.mapTo(savedAdoptionEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/adoptions")
    public Page<AdoptionDto> listAdoptions(Pageable pageable) {
        Page<AdoptionEntity> adoptions = adoptionService.findAll(pageable);
        return adoptions.map(adoptionMapper::mapTo);
    }

    @GetMapping(path = "/adoptions/{id}")
    public ResponseEntity<AdoptionDto> getAdoption(@PathVariable("id") Long id) {
        Optional<AdoptionEntity> foundAdoption = adoptionService.findOne(id);
        return foundAdoption.map(adoptionEntity -> {
            AdoptionDto adoptionDto = adoptionMapper.mapTo(adoptionEntity);
            return new ResponseEntity<>(adoptionDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/adoptions/{id}")
    public ResponseEntity<AdoptionDto> fullUpdateAdoption(@PathVariable("id") Long id,
                                                          @RequestBody AdoptionDto adoptionDto) {

        if(!adoptionService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        adoptionDto.setAdoptionId(id);
        AdoptionEntity adoptionEntity = adoptionMapper.mapFrom(adoptionDto);
        AdoptionEntity savedAdoptionEntity = adoptionService.save(adoptionEntity);

        return new ResponseEntity<>(adoptionMapper.mapTo(savedAdoptionEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/adoptions/{id}")
    public ResponseEntity<AdoptionDto> partialUpdateAdoption(@PathVariable("id") Long id,
                                                          @RequestBody AdoptionDto adoptionDto) {

        if(!adoptionService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        adoptionDto.setAdoptionId(id);
        AdoptionEntity adoptionEntity = adoptionMapper.mapFrom(adoptionDto);
        AdoptionEntity savedAdoptionEntity = adoptionService.partialUpdate(id, adoptionEntity);

        return new ResponseEntity<>(adoptionMapper.mapTo(savedAdoptionEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/adoptions/{id}")
    public ResponseEntity deleteUpdate(@PathVariable("id") Long id) {
        adoptionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
