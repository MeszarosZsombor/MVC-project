package com.example.mvc_project.controllers;

import com.example.mvc_project.domain.dto.OwnerDto;
import com.example.mvc_project.domain.entities.OwnerEntity;
import com.example.mvc_project.mappers.Mapper;
import com.example.mvc_project.services.OwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OwnerController {

    private OwnerService ownerService;
    private Mapper<OwnerEntity, OwnerDto> ownerMapper;

    public OwnerController(OwnerService ownerService, Mapper<OwnerEntity, OwnerDto> ownerMapper) {
        this.ownerService = ownerService;
        this.ownerMapper = ownerMapper;
    }

    @PostMapping(path = "/owners")
    public ResponseEntity<OwnerDto> createOwner(@RequestBody OwnerDto owner) {
        OwnerEntity ownerEntity = ownerMapper.mapFrom(owner);
        OwnerEntity savedOwnerEntity = ownerService.createOwner(ownerEntity);
        return new ResponseEntity<>(ownerMapper.mapTo(savedOwnerEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/owners")
    public List<OwnerDto> listOwners() {
        List<OwnerEntity> owners = ownerService.findAll();
        return owners.stream()
                .map(ownerMapper::mapTo)
                .collect(Collectors.toList());
    }
}
