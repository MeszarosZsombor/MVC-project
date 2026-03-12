package com.example.mvc_project.services.impl;

import com.example.mvc_project.domain.entities.AdoptionEntity;
import com.example.mvc_project.repositories.AdoptionRepository;
import com.example.mvc_project.services.AdoptionService;
import org.springframework.stereotype.Service;

@Service
public class AdoptionServiceImpl implements AdoptionService {

    private AdoptionRepository adoptionRepository;

    public AdoptionServiceImpl(AdoptionRepository adoptionRepository) {
        this.adoptionRepository = adoptionRepository;
    }

    @Override
    public AdoptionEntity createAdoption(AdoptionEntity adoptionEntity) {
        return adoptionRepository.save(adoptionEntity);
    }
}
