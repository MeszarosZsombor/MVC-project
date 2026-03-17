package com.example.mvc_project.services.impl;

import com.example.mvc_project.domain.entities.AdoptionEntity;
import com.example.mvc_project.repositories.AdoptionRepository;
import com.example.mvc_project.services.AdoptionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdoptionServiceImpl implements AdoptionService {

    private AdoptionRepository adoptionRepository;

    public AdoptionServiceImpl(AdoptionRepository adoptionRepository) {
        this.adoptionRepository = adoptionRepository;
    }

    @Override
    public AdoptionEntity save(AdoptionEntity adoptionEntity) {
        return adoptionRepository.save(adoptionEntity);
    }

    @Override
    public List<AdoptionEntity> findAll() {
        return StreamSupport.stream(adoptionRepository
                .findAll()
                .spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AdoptionEntity> findOne(Long id) {
        return adoptionRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return adoptionRepository.existsById(id);
    }
}
