package com.example.mvc_project.services.impl;

import com.example.mvc_project.domain.entities.PetEntity;
import com.example.mvc_project.repositories.PetRepository;
import com.example.mvc_project.services.PetService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<PetEntity> findAll() {
        return StreamSupport.stream(petRepository
                .findAll()
                .spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PetEntity> findOne(Long id) {
        return petRepository.findById(id);
    }
}
