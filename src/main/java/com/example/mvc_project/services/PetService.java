package com.example.mvc_project.services;

import com.example.mvc_project.domain.entities.PetEntity;

import java.util.List;
import java.util.Optional;

public interface PetService {
    PetEntity save(PetEntity petEntity);

    List<PetEntity> findAll();

    Optional<PetEntity> findOne(Long id);

    boolean isExists(Long id);

    PetEntity partialUpdate(Long id, PetEntity petEntity);
}
