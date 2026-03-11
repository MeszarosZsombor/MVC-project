package com.example.mvc_project.services.impl;

import com.example.mvc_project.domain.entities.OwnerEntity;
import com.example.mvc_project.repositories.OwnerRepository;
import com.example.mvc_project.services.OwnerService;
import org.springframework.stereotype.Service;

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
}
