package com.example.mvc_project.services.impl;

import com.example.mvc_project.domain.dto.PetDto;
import com.example.mvc_project.domain.entities.PetCategoryEntity;
import com.example.mvc_project.domain.entities.PetEntity;
import com.example.mvc_project.mappers.Mapper;
import com.example.mvc_project.mappers.impl.PetMapperImpl;
import com.example.mvc_project.repositories.PetCategoryRepository;
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
    private PetCategoryRepository petCategoryRepository;
    private PetMapperImpl petMapper;

    public PetServiceImpl(PetRepository petRepository, PetCategoryRepository petCategoryRepository,
                          PetMapperImpl petMapper) {
        this.petRepository = petRepository;
        this.petCategoryRepository = petCategoryRepository;
        this.petMapper = petMapper;
    }

    @Override
    public PetEntity save(PetDto petDto) {
        PetCategoryEntity petCategory = petCategoryRepository.findById(petDto.getPetCategoryId())
                .orElseThrow(() -> new RuntimeException("Pet Category not found"));

        if(petDto.getPetId() != null) {
            PetEntity existing = petRepository.findById(petDto.getPetId())
                    .orElseThrow(() -> new RuntimeException("Pet not found"));

            petMapper.updateEntityFromDto(petDto, existing);
            existing.setPetCategory(petCategory);

            return petRepository.save(existing);
        }

        PetEntity pet = petMapper.mapFrom(petDto);
        pet.setPetCategory(petCategory);

        return petRepository.save(pet);
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
    public PetEntity partialUpdate(Long id, PetDto petDto) {

        return petRepository.findById(id).map(existingPet -> {

            if (petDto.getPetName() != null) {
                existingPet.setPetName(petDto.getPetName());
            }

            if (petDto.getAdopted() != null) {
                existingPet.setAdopted(petDto.getAdopted());
            }

            if (petDto.getAge() != null) {
                existingPet.setAge(petDto.getAge());
            }

            if (petDto.getWeight() != null) {
                existingPet.setWeight(petDto.getWeight());
            }

            if (petDto.getGender() != null) {
                existingPet.setGender(petDto.getGender());
            }

            if (petDto.getPetCategoryId() != null) {
                PetCategoryEntity category = petCategoryRepository
                        .findById(petDto.getPetCategoryId())
                        .orElseThrow(() -> new RuntimeException("Pet Category not found"));

                existingPet.setPetCategory(category);
            }

            return petRepository.save(existingPet);

        }).orElseThrow(() -> new RuntimeException("Pet does not exist"));
    }

    @Override
    public void delete(Long id) {
        petRepository.deleteById(id);
    }
}
