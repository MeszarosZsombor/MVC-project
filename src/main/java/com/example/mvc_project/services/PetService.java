package com.example.mvc_project.services;

import com.example.mvc_project.domain.entities.PetEntity;

import java.util.List;

public interface PetService {
    PetEntity createPet(PetEntity petEntity);

    List<PetEntity> findAll();
}
