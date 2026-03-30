package com.example.mvc_project.services.impl;

import com.example.mvc_project.domain.dto.AdoptionDto;
import com.example.mvc_project.domain.entities.AdoptionEntity;
import com.example.mvc_project.domain.entities.OwnerEntity;
import com.example.mvc_project.domain.entities.PetEntity;
import com.example.mvc_project.repositories.AdoptionRepository;
import com.example.mvc_project.repositories.OwnerRepository;
import com.example.mvc_project.repositories.PetRepository;
import com.example.mvc_project.services.AdoptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdoptionServiceImpl implements AdoptionService {

    private AdoptionRepository adoptionRepository;
    private OwnerRepository ownerRepository;
    private PetRepository petRepository;

    public AdoptionServiceImpl(AdoptionRepository adoptionRepository, OwnerRepository ownerRepository,
                               PetRepository petRepository) {
        this.adoptionRepository = adoptionRepository;
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
    }

    @Override
    public AdoptionEntity save(AdoptionDto adoptionDto) {
        OwnerEntity owner = ownerRepository.findById(adoptionDto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        PetEntity pet = petRepository.findById(adoptionDto.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        if (adoptionDto.getAdoptionId() != null) {

            AdoptionEntity existing = adoptionRepository.findById(adoptionDto.getAdoptionId())
                    .orElseThrow(() -> new RuntimeException("Adoption not found"));

            existing.setOwner(owner);
            existing.setPet(pet);

            return adoptionRepository.save(existing);
        }

        AdoptionEntity adoption = AdoptionEntity.builder()
                .owner(owner)
                .pet(pet)
                .build();

        return adoptionRepository.save(adoption);
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
    public Page<AdoptionEntity> findAll(Pageable pageable) {
        return adoptionRepository.findAll(pageable);
    }

    @Override
    public Optional<AdoptionEntity> findOne(Long id) {
        return adoptionRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return adoptionRepository.existsById(id);
    }

    @Override
    public AdoptionEntity partialUpdate(Long id, AdoptionDto adoptionDto) {

        return adoptionRepository.findById(id).map(existing -> {

            if (adoptionDto.getOwnerId() != null) {
                OwnerEntity owner = ownerRepository.findById(adoptionDto.getOwnerId())
                        .orElseThrow(() -> new RuntimeException("Owner not found"));
                existing.setOwner(owner);
            }

            if (adoptionDto.getPetId() != null) {
                PetEntity pet = petRepository.findById(adoptionDto.getPetId())
                        .orElseThrow(() -> new RuntimeException("Pet not found"));
                existing.setPet(pet);
            }

            return adoptionRepository.save(existing);

        }).orElseThrow(() -> new RuntimeException("Adoption not exists"));
    }

    @Override
    public void delete(Long id) {
        adoptionRepository.deleteById(id);
    }
}
