package com.example.mvc_project.services.impl;

import com.example.mvc_project.domain.entities.OwnerEntity;
import com.example.mvc_project.repositories.OwnerRepository;
import com.example.mvc_project.services.OwnerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OwnerServiceImpl implements OwnerService {

    private OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public OwnerEntity save(OwnerEntity ownerEntity) {
        return ownerRepository.save(ownerEntity);
    }

    @Override
    public List<OwnerEntity> findAll() {
        return StreamSupport.stream(ownerRepository
                .findAll()
                .spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OwnerEntity> findOne(Long id) {
        return ownerRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return ownerRepository.existsById(id);
    }

    @Override
    public OwnerEntity partialUpdate(Long id, OwnerEntity ownerEntity) {
        ownerEntity.setOwnerId(id);

        return ownerRepository.findById(id).map(existingOwner -> {
            Optional.ofNullable(ownerEntity.getRole()).ifPresent(existingOwner::setRole);
            Optional.ofNullable(ownerEntity.getName()).ifPresent(existingOwner::setName);
            Optional.ofNullable(ownerEntity.getEmail()).ifPresent(existingOwner::setEmail);
            Optional.ofNullable(ownerEntity.getPassword()).ifPresent(existingOwner::setPassword);
            return ownerRepository.save(existingOwner);
        }).orElseThrow(() -> new RuntimeException("Owner does not exists"));
    }

    @Override
    public void delete(Long id) {
        ownerRepository.deleteById(id);
    }
}
