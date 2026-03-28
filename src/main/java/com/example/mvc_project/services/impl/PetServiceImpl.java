package com.example.mvc_project.services.impl;

import com.example.mvc_project.domain.entities.PetEntity;
import com.example.mvc_project.repositories.PetRepository;
import com.example.mvc_project.services.PetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public PetEntity save(PetEntity petEntity) {
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
    public Page<PetEntity> findAll(Pageable pageable) {
        return petRepository.findAll(pageable);
    }

    @Override
    public Optional<PetEntity> findOne(Long id) {
        return petRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return petRepository.existsById(id);
    }

    @Override
    public PetEntity partialUpdate(Long id, PetEntity petEntity) {
        petEntity.setPetId(id);

        return petRepository.findById(id).map( existingPet -> {
            Optional.ofNullable(petEntity.getPetCategory()).ifPresent(existingPet::setPetCategory);
            Optional.ofNullable(petEntity.getPetName()).ifPresent(existingPet::setPetName);
            Optional.ofNullable(petEntity.getAdopted()).ifPresent(existingPet::setAdopted);
            Optional.ofNullable(petEntity.getAge()).ifPresent(existingPet::setAge);
            Optional.ofNullable(petEntity.getWeight()).ifPresent(existingPet::setWeight);
            Optional.ofNullable(petEntity.getGender()).ifPresent(existingPet::setGender);
            return petRepository.save(existingPet);
        }).orElseThrow(() -> new RuntimeException("Pet does not exists"));
    }

    @Override
    public void delete(Long id) {
        petRepository.deleteById(id);
    }
}
