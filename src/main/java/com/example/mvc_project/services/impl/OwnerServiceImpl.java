package com.example.mvc_project.services.impl;

import com.example.mvc_project.domain.entities.OwnerEntity;
import com.example.mvc_project.repositories.OwnerRepository;
import com.example.mvc_project.services.OwnerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OwnerServiceImpl implements OwnerService {

    private OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public OwnerEntity createOwner(OwnerEntity ownerEntity) {
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
}
