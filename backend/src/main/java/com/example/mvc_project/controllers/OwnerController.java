package com.example.mvc_project.controllers;

import com.example.mvc_project.domain.dto.LoginRequestDto;
import com.example.mvc_project.domain.dto.LoginResponseDto;
import com.example.mvc_project.domain.dto.OwnerDto;
import com.example.mvc_project.domain.entities.OwnerEntity;
import com.example.mvc_project.mappers.Mapper;
import com.example.mvc_project.repositories.OwnerRepository;
import com.example.mvc_project.security.JwtService;
import com.example.mvc_project.services.OwnerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
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
        OwnerEntity savedOwnerEntity = ownerService.save(ownerEntity);
        return new ResponseEntity<>(ownerMapper.mapTo(savedOwnerEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/owners")
    public Page<OwnerDto> listOwners(Pageable pageable) {
        Page<OwnerEntity> owners = ownerService.findAll(pageable);
        return owners.map(ownerMapper::mapTo);
    }

    @GetMapping(path = "/owners/{id}")
    public ResponseEntity<OwnerDto> getOwner(@PathVariable("id") Long id) {
        Optional<OwnerEntity> foundOwner = ownerService.findOne(id);
        return foundOwner.map(ownerEntity -> {
            OwnerDto ownerDto = ownerMapper.mapTo(ownerEntity);
            return new ResponseEntity<>(ownerDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/owners/{id}")
    public ResponseEntity<OwnerDto> fullUpdateOwner(@PathVariable("id") Long id,
                                                    @RequestBody OwnerDto ownerDto) {
        if(!ownerService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ownerDto.setOwnerId(id);
        OwnerEntity ownerEntity = ownerMapper.mapFrom(ownerDto);
        OwnerEntity savedOwnerEntity = ownerService.save(ownerEntity);

        return new ResponseEntity<>(ownerMapper.mapTo(savedOwnerEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/owners/{id}")
    public ResponseEntity<OwnerDto> partialUpdateOwner(@PathVariable("id") Long id,
                                                       @RequestBody OwnerDto ownerDto) {

        if(!ownerService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        OwnerEntity ownerEntity = ownerMapper.mapFrom(ownerDto);
        OwnerEntity updatedOwner = ownerService.partialUpdate(id, ownerEntity);
        return new ResponseEntity<>(ownerMapper.mapTo(updatedOwner), HttpStatus.OK);
    }

    @DeleteMapping(path = "/owners/{id}")
    public ResponseEntity deleteOwner(@PathVariable("id") Long id) {

        ownerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
