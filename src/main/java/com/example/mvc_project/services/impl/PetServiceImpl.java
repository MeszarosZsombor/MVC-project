package com.example.mvc_project.services.impl;

import com.example.mvc_project.domain.entities.PetEntity;
import com.example.mvc_project.repositories.PetRepository;
import com.example.mvc_project.services.PetService;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService {

    private PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public PetEntity createPet(PetEntity petEntity) {
        return petRepository.save(petEntity);
    }
}
