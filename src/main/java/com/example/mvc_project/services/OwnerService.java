package com.example.mvc_project.services;

import com.example.mvc_project.domain.entities.OwnerEntity;

import java.util.List;

public interface OwnerService {
    OwnerEntity createOwner(OwnerEntity ownerEntity);

    List<OwnerEntity> findAll();
}
