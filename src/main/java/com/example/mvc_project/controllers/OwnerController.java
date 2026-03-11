package com.example.mvc_project.controllers;

import com.example.mvc_project.domain.dto.OwnerDto;
import com.example.mvc_project.domain.entities.OwnerEntity;
import com.example.mvc_project.mappers.Mapper;
import com.example.mvc_project.services.OwnerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OwnerController {

    private OwnerService ownerService;
    private Mapper<OwnerEntity, OwnerDto> ownerMapper;

    public OwnerController(OwnerService ownerService, Mapper<OwnerEntity, OwnerDto> ownerMapper) {
        this.ownerService = ownerService;
        this.ownerMapper = ownerMapper;
    }

    @PostMapping(path = "/owners")
    public OwnerDto createOwner(@RequestBody OwnerDto owner) {
        OwnerEntity ownerEntity = ownerMapper.mapFrom(owner);
        OwnerEntity savedOwnerEntity = ownerService.createOwner(ownerEntity);
        return ownerMapper.mapTo(savedOwnerEntity);
    }

}
